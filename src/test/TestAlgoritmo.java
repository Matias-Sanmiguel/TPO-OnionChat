package test;

import modelo.Grafo;

import java.util.*;

public class TestAlgoritmo {
    public static void main(String[] args) {
        Grafo grafo = new Grafo();
        Random rand = new Random();//

        // Todo generar nodos segun usuario
        String[] nodos = {"A", "B", "C", "D", "E"};
        for (String nodo : nodos) {
            grafo.agregarNodo(nodo);
        }

// Generar aristas aleatorias
        int n = nodos.length;
        int maxAristas = n * (n - 1);
        int minAristas = n; // al menos 1 salida por nodo
        int cantidadAristas = minAristas + rand.nextInt(maxAristas - minAristas + 1);

        Set<String> aristasUsadas = new HashSet<>();

        while (aristasUsadas.size() < cantidadAristas) {
            String origen = nodos[rand.nextInt(n)];
            String destino = nodos[rand.nextInt(n)];

            if (origen.equals(destino)) continue;

            String clave = origen + "->" + destino;
            if (aristasUsadas.contains(clave)) continue;

            int peso = 1 + rand.nextInt(9); // latencia entre 1 y 9
            grafo.agregarArista(origen, destino, peso);
            aristasUsadas.add(clave);
        }

// Ejecutar Dijkstra
        String origen = "A";
        String destino = "E";

        Map<String, String> predecesores = new HashMap<>();
        Map<String, Integer> distancias = grafo.dijkstra(origen, predecesores);
        List<String> camino = grafo.reconstruirCamino(destino, predecesores);

// Mostrar resultados
        if (distancias.get(destino) == Integer.MAX_VALUE) {
            System.out.println("No hay camino de " + origen + " a " + destino);
        } else {
            StringBuilder caminoTexto = new StringBuilder();
            for (int i = 0; i < camino.size(); i++) {
                caminoTexto.append(camino.get(i));
                if (i < camino.size() - 1) {
                    caminoTexto.append(" -> ");
                }
            }

            System.out.println("Camino más corto de " + origen + " a " + destino + ": " + caminoTexto);
            System.out.println("Distancia total: " + distancias.get(destino));
        }
    }
}
