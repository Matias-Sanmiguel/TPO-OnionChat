package App;

import modelo.Mensaje;
import modelo.ConversacionLocal;
import modelo.Nodo;
import interfaces.INodo;
import interfaces.IMensaje;
import modelo.CryptoUtil;

import java.util.Base64;

public class Main {
    public static void main(String[] args) {
        try {
            // Crear nodos
            Nodo nodoA = new Nodo("Nodo-A");
            Nodo nodoB = new Nodo("Nodo-B");
            Nodo nodoC = new Nodo("Nodo-C");
            Nodo nodoD = new Nodo("Nodo-D"); // Este será el receptor

            // Conectar nodos
            nodoA.agregarVecino(nodoB);
            nodoB.agregarVecino(nodoC);
            nodoC.agregarVecino(nodoD);

            // Crear mensaje cifrado con la clave del receptor
            String texto = "Hola, este mensaje es solo para el Nodo-D";
            byte[] origenOfuscado = nodoA.getID().getBytes();  // puede ser algo anónimo
            IMensaje mensaje = new Mensaje(texto, nodoD.getClavePublica(), origenOfuscado);

            // 🔐 Mostrar contenido cifrado en consola
            System.out.println("🔒 Contenido cifrado del mensaje (Base64):");
            System.out.println(Base64.getEncoder().encodeToString(mensaje.getContenidoCifrado()));

            // ❌ Intento de descifrado con la clave privada equivocada
            try {
                byte[] descifrado = CryptoUtil.descifrar(mensaje.getContenidoCifrado(), nodoA.getClavePrivada());
                System.out.println("⚠️ Nodo-A pudo leer el mensaje (esto no debería pasar): " + new String(descifrado));
            } catch (Exception e) {
                System.out.println("✅ Nodo-A NO pudo descifrar el mensaje (como corresponde).");
            }

            // Enviar el mensaje
            System.out.println("\n📨 Enviando mensaje desde Nodo-A...");
            nodoA.recibirMensaje(mensaje);

            // Verificar conversación en el Nodo-D
            String conversacionID = Base64.getEncoder().encodeToString(origenOfuscado);
            var conversacion = nodoD.getConversacionLocal(conversacionID);
            if (conversacion != null) {
                System.out.println("\n✅ Conversación almacenada en Nodo-D con ID: " + conversacionID);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
