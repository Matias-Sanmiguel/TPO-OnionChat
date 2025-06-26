package test;

import interfaces.IMensaje;
import modelo.Mensaje;
import modelo.CryptoUtil;
import java.nio.charset.StandardCharsets;
import java.security.PublicKey;
import java.security.KeyPair;


import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Scanner;

public class TestMensaje {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            //1. pide nombre de los nodos al usuario
            System.out.println("Ingresá el nombre del nodo origen: ");
            String idOrigen = scanner.nextLine().trim();

            System.out.println("Ingresá el nombre del nodo destino: ");
            String idDestino = scanner.nextLine().trim();

            //2. Genera claves para ambos nodos
            KeyPair clavesOrigen = CryptoUtil.generarParDeClaves();
            KeyPair clavesDestino = CryptoUtil.generarParDeClaves();


            //3. Pedir mensaje a enviar
            System.out.println("Escribí el mensaje a enviar: ");
            String texto =  scanner.nextLine();

            //4. creo objeto mensaje
            IMensaje mensaje = new Mensaje(texto);

            //5. cifro origen usando la clave pública del destino
            PublicKey clavePublicaDestino = clavesDestino.getPublic();
            byte[] origenCifrado = CryptoUtil.cifrar(idOrigen.getBytes(StandardCharsets.UTF_8), clavePublicaDestino);
            mensaje.setOrigenOfuscado(origenCifrado);

            //6. muestro resultados
            System.out.println("Mensaje preparado para envío.");
            System.out.println("Contenido original: " + mensaje.getContenido());

            String origenBase64 = Base64.getEncoder().encodeToString(mensaje.getOrigenOfuscado());
            System.out.println("Origen cifrado (Base64): " +origenBase64);

            System.out.println("Ruta definida: " + idOrigen + " -> " + idDestino);

        } catch (Exception e){
            System.err.println("Error en el proceso: " + e.getMessage());
            e.printStackTrace();
        }
    }
}