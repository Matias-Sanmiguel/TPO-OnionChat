package modelo;

import interfaces.IConversacionLocal;
import interfaces.IMensaje;

import java.util.ArrayList;
import java.util.List;

public class ConversacionLocal implements IConversacionLocal {

    private final String id;
    private final List<IMensaje> mensajes;

    public ConversacionLocal(String id) {
        this.id = id;
        this.mensajes = new ArrayList<>();
    }

    @Override
    public void guardarMensaje(IMensaje mensaje) {
        mensajes.add(mensaje);
        System.out.println("📥 Mensaje guardado en conversación con ID: " + id);
    }
}
