package App;

import modelo.CryptoUtil;
import modelo.Grafo;
import modelo.Mensaje;

import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.PublicKey;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Grafo grafo = new Grafo();
        Scanner scanner = new Scanner(System.in);
        Random rand = new Random();

        // 1. Seleccionar cantidad de nodos
        System.out.print("Ingrese la cantidad de nodos: ");
        int cantidadNodos = scanner.nextInt();
        scanner.nextLine();

        if (cantidadNodos < 2) {
            System.out.println("Debe haber al menos 2 nodos.");
            return;
        }

        // 2. Generar nodos automáticamente
        List<String> nodos = new ArrayList<>();
        for (int i = 0; i < cantidadNodos; i++) {
            nodos.add(convertirIndiceANombre(i)); // A, B, C, ...
        }

        System.out.println("Nodos generados: " + nodos);

        for (String nodo : nodos) {
            grafo.agregarNodo(nodo);
        }

        // 3. Generar conexiones aleatorias entre nodos
        int maxAristas = cantidadNodos * (cantidadNodos - 1);
        int minAristas = cantidadNodos;
        int cantidadAristas = minAristas + rand.nextInt(maxAristas - minAristas + 1);
        Set<String> aristasUsadas = new HashSet<>();

        while (aristasUsadas.size() < cantidadAristas) {
            String origen = nodos.get(rand.nextInt(cantidadNodos));
            String destino = nodos.get(rand.nextInt(cantidadNodos));
            if (origen.equals(destino)) continue;

            String clave = origen + "->" + destino;
            if (aristasUsadas.contains(clave)) continue;

            int peso = 1 + rand.nextInt(9);
            grafo.agregarArista(origen, destino, peso);
            aristasUsadas.add(clave);
        }

        // 4. Pedir nodo origen y destino
        String origen, destino;
        do {
            System.out.print("Ingrese el nodo origen: ");
            origen = scanner.nextLine().trim();
        } while (!nodos.contains(origen));

        do {
            System.out.print("Ingrese el nodo destino: ");
            destino = scanner.nextLine().trim();
        } while (!nodos.contains(destino) || destino.equals(origen));

        // 5. Ingreso del mensaje
        System.out.print("Escriba el mensaje a enviar: ");
        String texto = scanner.nextLine();

        try {
            // 6. Claves
            KeyPair clavesOrigen = CryptoUtil.generarParDeClaves();
            KeyPair clavesDestino = CryptoUtil.generarParDeClaves();

            // 7. Cifrado
            PublicKey clavePublicaDestino = clavesDestino.getPublic();
            byte[] origenOfuscado = CryptoUtil.cifrar(origen.getBytes(StandardCharsets.UTF_8), clavePublicaDestino);

            Mensaje mensaje = new Mensaje(texto, clavePublicaDestino, origenOfuscado);

            // 8. Ruta y simulación de envío
            Map<String, String> predecesores = new HashMap<>();
            Map<String, Integer> distancias = grafo.dijkstra(origen, predecesores);
            List<String> camino = grafo.reconstruirCamino(destino, predecesores);

            if (distancias.get(destino) == Integer.MAX_VALUE) {
                System.out.println("No hay camino de " + origen + " a " + destino);
                return;
            }

            System.out.println("Camino oculto (en tránsito):");

            System.out.println("[Transmisión segura en curso...]");

            //Debug opcional solo al final
            System.out.println("Camino más corto revelado: " + String.join(" -> ", camino));
            System.out.println("Distancia total: " + distancias.get(destino));

            System.out.println("Mensaje cifrado: " + Base64.getEncoder().encodeToString(mensaje.getContenidoCifrado()));
            System.out.println("Mensaje original (revelado solo por destino): " + texto);

        } catch (Exception e) {
            System.err.println("Error al cifrar el mensaje:");
            e.printStackTrace();
        }
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
