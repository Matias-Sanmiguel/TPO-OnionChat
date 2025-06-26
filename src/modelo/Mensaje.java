package modelo;

import interfaces.IMensaje;

import java.security.PublicKey;
import modelo.CryptoUtil;

public class Mensaje implements IMensaje {

    private final byte[] contenidoCifrado;
    private final byte[] origenOfuscado;

    public Mensaje(String textoPlano, PublicKey clavePublicaDestino, byte[] origenOfuscado) throws Exception {
        this.contenidoCifrado = CryptoUtil.cifrar(textoPlano.getBytes(), clavePublicaDestino);
        this.origenOfuscado = origenOfuscado;
    }

    @Override
    public byte[] getContenidoCifrado() {
        return contenidoCifrado;
    }

    @Override
    public byte[] getOrigenOfuscado() {
        return origenOfuscado;
    }
}
