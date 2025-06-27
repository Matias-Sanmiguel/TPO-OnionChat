package App;

import interfaces.IGrafo;
import interfaces.ICryptoUtil;
import interfaces.IMensaje;
import modelo.CryptoUtil;
import modelo.Grafo;
import modelo.Mensaje;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random rand = new Random();

        // --- Interfaces TDA ---
        IGrafo grafo = new Grafo();
        ICryptoUtil cryptoUtil = new CryptoUtil();

        // --- 1. Generar nodos ---
        List<String> nodos = generarNombresDeNodos(scanner);
        if (nodos.isEmpty()) return;

        System.out.println("Nodos generados: " + nodos);
        for (String nodo : nodos) grafo.agregarNodo(nodo);

        // --- 2. Agregar aristas aleatorias ---
        int n = nodos.size();
        int maxAristas = n * (n - 1);
        int minAristas = n;
        int cantidadAristas = minAristas + rand.nextInt(maxAristas - minAristas + 1);

        Set<String> aristasUsadas = new HashSet<>();
        while (aristasUsadas.size() < cantidadAristas) {
            String origen = nodos.get(rand.nextInt(n));
            String destino = nodos.get(rand.nextInt(n));
            if (origen.equals(destino)) continue;
            String clave = origen + "->" + destino;
            if (aristasUsadas.contains(clave)) continue;
            int peso = 1 + rand.nextInt(9);
            grafo.agregarArista(origen, destino, peso);
            aristasUsadas.add(clave);
        }

        // --- 3. Ingreso de nodos origen y destino ---
        String origen, destino;
        do {
            System.out.print("Ingrese el nodo origen: ");
            origen = scanner.nextLine().trim();
        } while (!nodos.contains(origen));

        do {
            System.out.print("Ingrese el nodo destino: ");
            destino = scanner.nextLine().trim();
        } while (!nodos.contains(destino) || destino.equals(origen));

        // --- 4. Ingreso del mensaje ---
        System.out.print("Escriba el mensaje a enviar: ");
        String textoPlano = scanner.nextLine();

        try {
            // --- 5. Generar claves ---
            KeyPair clavesOrigen = cryptoUtil.generarParDeClaves();
            KeyPair clavesDestino = cryptoUtil.generarParDeClaves();
            PublicKey clavePublicaDestino = clavesDestino.getPublic();
            PrivateKey clavePrivadaDestino = clavesDestino.getPrivate();

            // --- 6. Encriptar mensaje y origen ---
            byte[] origenOfuscado = cryptoUtil.cifrar(origen.getBytes(), clavePublicaDestino);
            byte[] contenidoCifrado = cryptoUtil.cifrar(textoPlano.getBytes(), clavePublicaDestino);

            IMensaje mensaje = new Mensaje("");
            mensaje.setContenidoCifrado(contenidoCifrado);
            mensaje.setOrigenOfuscado(origenOfuscado);

            // --- 7. Ejecutar Dijkstra ---
            Map<String, String> predecesores = new HashMap<>();
            Map<String, Integer> distancias = grafo.dijkstra(origen, predecesores);
            List<String> camino = grafo.reconstruirCamino(destino, predecesores);

            if (distancias.get(destino) == Integer.MAX_VALUE) {
                System.out.println("No hay camino de " + origen + " a " + destino);
                return;
            }

            // --- 8. Mostrar resultados ---
            System.out.println("\nCamino oculto (en tránsito):");
            System.out.println("[Transmisión segura en curso...]");

            System.out.println("\nCamino más corto revelado (con fines de debug): " + String.join(" -> ", camino));
            System.out.println("Distancia total: " + distancias.get(destino));

            String mensajeCifradoStr = Base64.getEncoder().encodeToString(mensaje.getContenidoCifrado());
            String mensajeOriginal = new String(cryptoUtil.descifrar(mensaje.getContenidoCifrado(), clavePrivadaDestino));

            System.out.println("\nMensaje cifrado: " + mensajeCifradoStr);
            System.out.println("Mensaje original (revelado solo por destino): " + mensajeOriginal);

        } catch (Exception e) {
            System.err.println("Error en el proceso de cifrado o transmisión.");
            e.printStackTrace();
        }
    }

    public static List<String> generarNombresDeNodos(Scanner scanner) {
        List<String> nodos = new ArrayList<>();

        System.out.print("Ingrese la cantidad de nodos: ");
        int cantidadNodos = scanner.nextInt();
        scanner.nextLine();

        if (cantidadNodos < 2) {
            System.out.println("Debe haber al menos 2 nodos.");
            return Collections.emptyList();
        }

        for (int i = 0; i < cantidadNodos; i++) {
            nodos.add(convertirIndiceANombre(i));
        }

        return nodos;
    }

    private static String convertirIndiceANombre(int indice) {
        StringBuilder nombre = new StringBuilder();
        while (indice >= 0) {
            nombre.insert(0, (char) ('A' + (indice % 26)));
            indice = indice / 26 - 1;
        }
        return nombre.toString();
    }
}
