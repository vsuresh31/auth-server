package com.jovora.license;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import java.security.*;
import java.security.interfaces.RSAPublicKey;

public class LicenseGenerator {
    public static String generate(License license) throws NoSuchAlgorithmException, JOSEException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048);
        KeyPair pair = keyGen.generateKeyPair();

        PrivateKey privateKey = pair.getPrivate();
        PublicKey publicKey = pair.getPublic();

        JWSSigner signer = new RSASSASigner(privateKey);
        RSAKey jwk = new RSAKey.Builder((RSAPublicKey) publicKey).keyID("license-key").build();

        SignedJWT signedJWT = new SignedJWT(
                new JWSHeader.Builder(JWSAlgorithm.RS256).jwk(jwk).build(),
                new JWTClaimsSet.Builder().subject("License for " + license.getUserName())
                        .issueTime(license.getPurchaseDate())
                        .expirationTime(license.getExpiryDate())
                        .audience(license.getEmail())
                        .claim("identifier", license.getIdentifier())
                        .claim("email", license.getEmail())
                        .issuer("jovora")
                        .build());
        signedJWT.sign(signer);
        return signedJWT.serialize();
    }
}
