package com.jovora.license;

import javax.crypto.Cipher;
import java.security.PrivateKey;
import java.util.Base64;

public class DecryptExample {
    public static String decrypt(String cipherText, PrivateKey privateKey) throws Exception {
        byte[] bytes = Base64.getDecoder().decode(cipherText);
        
        Cipher decryptCipher = Cipher.getInstance("RSA"); // AES
        decryptCipher.init(Cipher.DECRYPT_MODE, privateKey);
        
        return new String(decryptCipher.doFinal(bytes));
    }
}
