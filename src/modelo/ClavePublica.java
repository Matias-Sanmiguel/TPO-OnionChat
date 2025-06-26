package modelo;

import interfaces.IClavePublica;

import java.security.PublicKey;

public class ClavePublica implements IClavePublica {
    private final PublicKey publicKey;

    public ClavePublica(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    @Override
    public byte[] getBytes() {
        return publicKey.getEncoded();
    }

    public PublicKey getRaw() {
        return publicKey;
    }
} 