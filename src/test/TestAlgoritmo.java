import modelo.Grafo;

import java.util.*;




public static void main(String[] args) {
    Grafo grafo = new Grafo();
    Random rand = new Random();

    // Generar nodos automáticamente
    List<String> nodos = generarNombresDeNodos();
    if (nodos.isEmpty()) return;

    System.out.println("Nodos generados: " + nodos);

    for (String nodo : nodos) {
        grafo.agregarNodo(nodo);
    }

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

    // Pedir origen y destino al usuario
    Scanner scanner = new Scanner(System.in);
    String origen, destino;

    do {
        System.out.print("Ingrese el nodo origen: ");
        origen = scanner.nextLine().trim();
    } while (!nodos.contains(origen));

    do {
        System.out.print("Ingrese el nodo destino: ");
        destino = scanner.nextLine().trim();
    } while (!nodos.contains(destino) || destino.equals(origen));

    // Ejecutar Dijkstra
    Map<String, String> predecesores = new HashMap<>();
    Map<String, Integer> distancias = grafo.dijkstra(origen, predecesores);
    List<String> camino = grafo.reconstruirCamino(destino, predecesores);

    if (distancias.get(destino) == Integer.MAX_VALUE) {
        System.out.println("No hay camino de " + origen + " a " + destino);
    } else {
        System.out.println("Camino más corto de " + origen + " a " + destino + ": " + String.join(" -> ", camino));
        System.out.println("Distancia total: " + distancias.get(destino));
    }
}

public static List<String> generarNombresDeNodos() {
    Scanner scanner = new Scanner(System.in);
    List<String> nodos = new ArrayList<>();

    System.out.print("Ingrese la cantidad de nodos: ");
    int cantidadNodos = scanner.nextInt();
    scanner.nextLine();

    if (cantidadNodos < 2) {
        System.out.println("Debe haber al menos 2 nodos.");
        return Collections.emptyList();
    }

    for (int i = 0; i < cantidadNodos; i++) {
        String nombre = convertirIndiceANombre(i);
        nodos.add(nombre);
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
