package com.jovora.license;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.util.Base64;

public class LicenseGenerator {
    private static final String RSA = "RSA";

    public static String generate(String licenseData, PrivateKey privateKey) throws InvalidKeyException, SignatureException, NoSuchAlgorithmException {
        licenseData = " {\"productId\": \"1234\", \"expiryDate\": \"2025-12-31\", \"deviceId\": \"ABC123\"}";
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(privateKey);
        signature.update(licenseData.getBytes());
        byte[] signedData = signature.sign();
        return Base64.getEncoder().encodeToString(signedData);
    }

    public static boolean verifyLicenseKey(String licenseData, String licenseKey, PublicKey publicKey) throws NoSuchAlgorithmException {
        try {
            byte[] decodedLicense = Base64.getDecoder().decode(licenseKey);
            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initVerify(publicKey);
            signature.update(licenseData.getBytes(StandardCharsets.UTF_8));
            return signature.verify(decodedLicense);
        } catch (Exception e) {
            return false;
        }
    }

}
