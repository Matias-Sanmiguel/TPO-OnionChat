package interfaces;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.List;

public interface INodo {
    String getID();
    PublicKey getClavePublica();
    PrivateKey getClavePrivada();
    List<INodo> getVecinos();
    void agregarVecino(INodo vecino);
    void recibirMensaje(IMensaje mensaje) throws Exception;
    void enviarMensaje(IMensaje mensaje) throws Exception;
    IConversacionLocal getConversacionLocal(String idConversacion);
}
