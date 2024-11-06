package com.jovora.license;

import java.security.*;

public class LicenseExample {
    // https://www.baeldung.com/spring-boot-bean-validation
    public static void main(String[] args) throws Exception {
        // Generate keys
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048);
        KeyPair pair = keyGen.generateKeyPair();
        PublicKey publicKey = pair.getPublic();
        PrivateKey privateKey = pair.getPrivate();
        
        // Encrypt data
        String originalData = "LicenseData";
        String encryptedData = EncryptExample.encrypt(originalData, publicKey);
        System.out.println("Encrypted: " + encryptedData);
        
        // Decrypt data
        String decryptedData = DecryptExample.decrypt(encryptedData, privateKey);
        System.out.println("Decrypted: " + decryptedData);


        /*

        // Encryption
Cipher encryptCipher = Cipher.getInstance("AES");
encryptCipher.init(Cipher.ENCRYPT_MODE, secretKey);
byte[] cipherText = encryptCipher.doFinal(plainText.getBytes());

// Decryption
Cipher decryptCipher = Cipher.getInstance("AES");
decryptCipher.init(Cipher.DECRYPT_MODE, secretKey);
byte[] decryptedText = decryptCipher.doFinal(cipherText);



        // Signing
Signature sign = Signature.getInstance("SHA256withRSA");
sign.initSign(privateKey);
sign.update(data);
byte[] signature = sign.sign();

// Verification
Signature verify = Signature.getInstance("SHA256withRSA");
verify.initVerify(publicKey);
verify.update(data);
boolean isVerified = verify.verify(signature);

         */
    }
}
