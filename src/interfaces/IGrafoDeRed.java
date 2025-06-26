package interfaces;

import java.util.List;

public interface IGrafoDeRed {
    List<INodo> getNodos();
    void agregarNodo(INodo nodo);
    void AgregarConexion(INodo nodo1, INodo nodo2);
    List<INodo> generarRutaOnion(INodo origen, INodo destino, int longitud);
}
