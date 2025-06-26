package modelo;

import interfaces.IClavePrivada;

import java.security.PrivateKey;

public class ClavePrivada implements IClavePrivada {
    private final PrivateKey privateKey;

    public ClavePrivada(PrivateKey privateKey) {
        this.privateKey = privateKey;
    }

    @Override
    public byte[] getBytes() {
        return privateKey.getEncoded();
    }

    public PrivateKey getRaw() {
        return privateKey;
    }
}
