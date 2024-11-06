package com.jovora.license;

import javax.crypto.Cipher;
import java.security.*;
import java.util.Base64;

public class KeyPairGeneratorExample {
    public static void main(String[] args) throws Exception {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048);
        KeyPair pair = keyGen.generateKeyPair();

        PrivateKey privateKey = pair.getPrivate();
        PublicKey publicKey = pair.getPublic();

        String plainText = " {\"productId\": \"1234\", \"expiryDate\": \"2025-12-31\", \"deviceId\": \"ABC123\"}";




        // Encryption
        Cipher encryptCipher = Cipher.getInstance("RSA");
        encryptCipher.init(Cipher.ENCRYPT_MODE, privateKey);
        byte[] cipherText = encryptCipher.doFinal(plainText.getBytes());
        System.out.println(Base64.getEncoder().encodeToString(cipherText));

        // Decryption
        Cipher decryptCipher = Cipher.getInstance("RSA");
        decryptCipher.init(Cipher.DECRYPT_MODE, publicKey);
        byte[] decryptedText = decryptCipher.doFinal(cipherText);
        System.out.println(new String(decryptedText));


        // Signing
        Signature sign = Signature.getInstance("SHA256withRSA");
        sign.initSign(privateKey);
        sign.update(plainText.getBytes());
        byte[] signature = sign.sign();
        System.out.println(new String(Base64.getEncoder().encode(signature)));

        // Verification
        Signature verify = Signature.getInstance("SHA256withRSA");
        verify.initVerify(publicKey);
        verify.update(plainText.getBytes());
        boolean isVerified = verify.verify(signature);
    }
}
