package interfaces;

import java.security.PublicKey;
import java.util.List;

public interface IMensaje {
    String getContenido();
    void setContenido(String contenido);

    byte[] getContenidoCifrado();
    void setContenidoCifrado(byte[] contenidoCifrado);

    byte[] getOrigenOfuscado();
    void setOrigenOfuscado(byte[] origenOfuscado);

    List<byte[]> getCapasCifradas();
    void setCapasCifradas(List<byte[]> capasCifradas);
}
