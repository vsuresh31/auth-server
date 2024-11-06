package com.jovora.secret;

import com.jovora.secret.SecretConfig;
import com.jovora.secret.SecretException;
import com.jovora.secret.SecretHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Base64;
import java.util.Random;


@Slf4j
@Component
public class SecretHandlerImp implements SecretHandler {

    private static final String ALIAS = "secret";
    private static final String ALGO = "AES/GCM/NoPadding";
    private final SecretConfig secretConfig;
    private KeyStore keyStore;
    private final Random rnd = new Random();

    public SecretHandlerImp(SecretConfig secretConfig) {
        this.secretConfig = secretConfig;
    }

    private String getSaltString() {
        String saltChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890-";
        StringBuilder salt = new StringBuilder();
        while (salt.length() < 128) {
            @SuppressWarnings("java:S2140")
            int index = (int) (rnd.nextFloat() * saltChars.length());
            salt.append(saltChars.charAt(index));
        }
        return salt.toString();
    }

    private Path privateKeyPath() {
        return Paths.get(secretConfig.getPrivateKeyFileName());
    }

    private char[] privateKey() {
        try {
            return Files.readString(privateKeyPath()).toCharArray();
        } catch (IOException e) {
            log.error("failed to read private key", e);
            return new char[0];
        }
    }

    private void createKeystore() throws CertificateException, IOException, NoSuchAlgorithmException, KeyStoreException {
        keyStore.load(null, privateKey());

        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(256);
        SecretKey secretKey = keyGen.generateKey();

        KeyStore.SecretKeyEntry keyEntry = new KeyStore.SecretKeyEntry(secretKey);
        KeyStore.ProtectionParameter passwordProtection = new KeyStore.PasswordProtection(privateKey());
        keyStore.setEntry(ALIAS, keyEntry, passwordProtection);

        try (FileOutputStream fos = new FileOutputStream(secretConfig.getKeystoreName())) {
            keyStore.store(fos, privateKey());
        }
    }

    @Override
    public void initialize() {
        try {

            keyStore = KeyStore.getInstance("PKCS12");

            String directory = System.getProperty("user.dir");

            if (!Paths.get(directory, secretConfig.getKeystoreName()).toFile().exists() || !privateKeyPath().toFile().exists()) {
                Files.write(privateKeyPath(), getSaltString().getBytes());
                createKeystore();
            }

            try (FileInputStream fis = new FileInputStream(secretConfig.getKeystoreName())) {
                keyStore.load(fis, privateKey());
            }
        } catch (Exception e) {
            log.error("Failed to initialize secret", e);
            throw new SecretException("Failed to initialize secret");
        }

    }

    @Override
    public Secret encrypt(String rawPwd) {
        try {
            Key key = keyStore.getKey(ALIAS, privateKey());
            Cipher cipher = Cipher.getInstance(ALGO);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            String ivData = Base64.getUrlEncoder().encodeToString(cipher.getIV());
            String encryptedData = Base64.getUrlEncoder().encodeToString(cipher.doFinal(rawPwd.getBytes()));
            return new Secret(encryptedData, ALIAS, ivData);
        } catch (NoSuchPaddingException | IllegalBlockSizeException | NoSuchAlgorithmException | BadPaddingException |
                 InvalidKeyException | UnrecoverableKeyException | KeyStoreException e) {
            log.error("Failed to encrypt", e);
            throw new SecretException("Failed to encrypt");
        }
    }

    @Override
    public String decrypt(Secret secret) {
        try {
            Key key = keyStore.getKey(ALIAS, privateKey());

            Cipher cipher = Cipher.getInstance(ALGO);

            byte[] encryptedData = Base64.getUrlDecoder().decode(secret.getData());
            byte[] iv = Base64.getUrlDecoder().decode(secret.getIv());

            cipher.init(Cipher.DECRYPT_MODE, key, new GCMParameterSpec(128, iv));
            return new String(cipher.doFinal(encryptedData));
        } catch (Exception e) {
            log.error("Failed to decrypt", e);
            throw new SecretException("Failed to decrypt");
        }
    }
}
