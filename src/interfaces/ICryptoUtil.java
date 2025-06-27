package interfaces;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

public interface ICryptoUtil {
    KeyPair generarParDeClaves() throws Exception;

    byte[] cifrar(byte[] datos, PublicKey clavePublica) throws Exception;

    byte[] descifrar(byte[] datos, PrivateKey clavePrivada) throws Exception;
}
