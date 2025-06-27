package modelo;

import interfaces.ICryptoUtil;
import javax.crypto.Cipher;
import java.security.*;

public class CryptoUtil implements ICryptoUtil {

    @Override
    public KeyPair generarParDeClaves() throws Exception {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(2048);
        return generator.generateKeyPair();
    }

    @Override
    public byte[] cifrar(byte[] datos, PublicKey clavePublica) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, clavePublica);
        return cipher.doFinal(datos);
    }

    @Override
    public byte[] descifrar(byte[] datos, PrivateKey clavePrivada) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, clavePrivada);
        return cipher.doFinal(datos);
    }
}
