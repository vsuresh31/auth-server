package com.jovora.auth.service;

import com.jovora.auth.entity.User;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
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
    JwtEncoder jwtEncoder;
    JwtDecoder jwtDecoder;
    private PrivateKey privateKey;
    private PublicKey publicKey;
    private JWSSigner signer;
    @Value("${jwt.expiry.inSec}")
    private int expiryInSec;

    public String generateJWT(User user) {
//        jwtEncoder.encode(null);
        RSAKey jwk = new RSAKey.Builder((RSAPublicKey) publicKey).keyID(user.getUserId()).build();

        JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.RS256).jwk(jwk).build();
        JWTClaimsSet payload = new JWTClaimsSet.Builder()
                .subject("Token")
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

    @Override
    public void afterPropertiesSet() throws Exception {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048);
        KeyPair pair = keyGen.generateKeyPair();
        privateKey = pair.getPrivate();
        publicKey = pair.getPublic();

        signer = new RSASSASigner(privateKey);
    }
}
