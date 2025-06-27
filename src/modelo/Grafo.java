package modelo;

import interfaces.IGrafo;

import java.util.*;

public class Grafo implements IGrafo {
    private Map<String, List<Arista>> adyacentes = new HashMap<>();

    public void agregarNodo(String nodo) {
        adyacentes.putIfAbsent(nodo, new ArrayList<>());
    }

    public void agregarArista(String origen, String destino, int peso) {
        adyacentes.get(origen).add(new Arista(destino, peso));
    }

    public Map<String, Integer> dijkstra(String origen, Map<String, String> predecesores) {
        Map<String, Integer> distancias = new HashMap<>();
        for (String nodo : adyacentes.keySet()) {
            distancias.put(nodo, Integer.MAX_VALUE);
        }
        distancias.put(origen, 0);

        PriorityQueue<Map.Entry<String, Integer>> cola = new PriorityQueue<>(
                Comparator.comparingInt(Map.Entry::getValue)
        );

        cola.add(new AbstractMap.SimpleEntry<>(origen, 0));

        while (!cola.isEmpty()) {
            String actual = cola.poll().getKey();

            for (Arista arista : adyacentes.get(actual)) {
                int nuevaDistancia = distancias.get(actual) + arista.peso;
                if (nuevaDistancia < distancias.get(arista.destino)) {
                    distancias.put(arista.destino, nuevaDistancia);
                    predecesores.put(arista.destino, actual);
                    cola.add(new AbstractMap.SimpleEntry<>(arista.destino, nuevaDistancia));
                }
            }
        }

        return distancias;
    }

    public List<String> reconstruirCamino(String destino, Map<String, String> predecesores) {
        List<String> camino = new ArrayList<>();
        while (destino != null) {
            camino.add(destino);
            destino = predecesores.get(destino);
        }
        Collections.reverse(camino);
        return camino;
    }
}

