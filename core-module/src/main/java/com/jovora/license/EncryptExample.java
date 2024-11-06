package com.jovora.license;

import javax.crypto.Cipher;
import java.security.PublicKey;
import java.util.Base64;

public class EncryptExample {
    public static String encrypt(String plainText, PublicKey publicKey) throws Exception {
        Cipher encryptCipher = Cipher.getInstance("RSA");
        encryptCipher.init(Cipher.ENCRYPT_MODE, publicKey);
        
        byte[] cipherText = encryptCipher.doFinal(plainText.getBytes());
        
        return Base64.getEncoder().encodeToString(cipherText);
    }
}
