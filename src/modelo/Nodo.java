package modelo;

import interfaces.*;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.KeyPair;
import java.util.*;

public class Nodo implements INodo {

    private final String id;
    private final PublicKey clavePublica;
    private final PrivateKey clavePrivada;
    private final List<INodo> vecinos;
    private final Map<String, IConversacionLocal> conversaciones;
    private final ICryptoUtil cryptoUtil;

    public Nodo(String id) throws Exception {
        this.id = id;
        this.vecinos = new ArrayList<>();
        this.conversaciones = new HashMap<>();
        this.cryptoUtil = new CryptoUtil(); // ✅ Usamos la interfaz

        KeyPair pair = cryptoUtil.generarParDeClaves();
        this.clavePublica = pair.getPublic();
        this.clavePrivada = pair.getPrivate();
    }

    @Override
    public String getID() {
        return id;
    }

    @Override
    public PublicKey getClavePublica() {
        return clavePublica;
    }

    @Override
    public PrivateKey getClavePrivada() {
        return clavePrivada;
    }

    @Override
    public List<INodo> getVecinos() {
        return vecinos;
    }

    @Override
    public void agregarVecino(INodo vecino) {
        vecinos.add(vecino);
    }

    @Override
    public void enviarMensaje(IMensaje mensaje) throws Exception {
        recibirMensaje(mensaje);
    }

    @Override
    public void recibirMensaje(IMensaje mensaje) throws Exception {
        try {
            byte[] mensajePlano = cryptoUtil.descifrar(mensaje.getContenidoCifrado(), this.clavePrivada);
            String contenido = new String(mensajePlano);

            System.out.println("[" + id + "] Mensaje recibido: " + contenido);

            String origenID = Base64.getEncoder().encodeToString(mensaje.getOrigenOfuscado());
            IConversacionLocal conversacion = conversaciones.computeIfAbsent(origenID, k -> new ConversacionLocal(origenID));
            conversacion.guardarMensaje(mensaje);

        } catch (Exception e) {
            if (!vecinos.isEmpty()) {
                INodo siguiente = vecinos.get(new Random().nextInt(vecinos.size()));
                System.out.println("[" + id + "] Reenviando mensaje a " + siguiente.getID());
                siguiente.recibirMensaje(mensaje);
            } else {
                System.out.println("[" + id + "] No tengo vecinos a quienes reenviar.");
            }
        }
    }

    @Override
    public IConversacionLocal getConversacionLocal(String idConversacion) {
        return conversaciones.get(idConversacion);
    }
}
