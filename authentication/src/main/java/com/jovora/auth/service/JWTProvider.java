package com.jovora.auth.service;

import com.jovora.auth.entity.User;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Instant;
import java.util.Date;

@Service
public class JWTProvider implements InitializingBean {
    private PublicKey publicKey;
    private JWSSigner signer;
    @Value("${jwt.expiry.inSec}")
    private int expiryInSec;

    public String generateJWT(User user) {
        RSAKey jwk = new RSAKey.Builder((RSAPublicKey) publicKey).keyID(user.getUserId()).build();

        JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.RS256).jwk(jwk).build();
        JWTClaimsSet payload = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issueTime(new Date())
                .expirationTime(Date.from(Instant.now().plusSeconds(expiryInSec)))
//                .audience(license.getEmail())
//                .claim("identifier", license.getIdentifier())
//                .claim("email", license.getEmail())
                .issuer("jovora")
                .build();

        SignedJWT signedJWT = new SignedJWT(header, payload);
        try {
            signedJWT.sign(signer);
            return signedJWT.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
    }

    public JWT parseToken(String token) throws Exception {
        SignedJWT signedJWT = SignedJWT.parse(token);
        RSAKey rsaKey = signedJWT.getHeader().getJWK().toRSAKey();
        JWSVerifier verifier = new RSASSAVerifier(rsaKey);

        if (!signedJWT.verify(verifier)) {
            return null;
        }
        return signedJWT;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048);
        KeyPair pair = keyGen.generateKeyPair();
        PrivateKey privateKey = pair.getPrivate();
        publicKey = pair.getPublic();

        signer = new RSASSASigner(privateKey);
    }
}
