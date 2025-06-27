package interfaces;

import java.util.List;
import java.util.Map;

public interface IGrafo {
    void agregarNodo(String nodo);
    void agregarArista(String origen, String destino, int peso);
    Map<String, Integer> dijkstra(String origen, Map<String, String> predecesores);
    List<String> reconstruirCamino(String destino, Map<String, String> predecesores);
}