//package com.jovora.auth;
//
//import com.nimbusds.jose.jwk.JWKSet;
//import com.nimbusds.jose.jwk.RSAKey;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.oauth2.jwt.JwtDecoder;
//import org.springframework.security.oauth2.jwt.JwtEncoder;
//import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
//import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
//
//import java.security.KeyPair;
//import java.security.KeyPairGenerator;
//import java.security.NoSuchAlgorithmException;
//import java.security.interfaces.RSAPublicKey;
//
//@Configuration
//public class JWTConfig {
//
//    @Bean
//    public JwtEncoder jwtEncoder() {
//        RSAKey rsaKey = new RSAKey.Builder((RSAPublicKey) keyPair().getPublic()).build();
//        JWKSource<SecurityContext> jwkSource = (jwkSelector, context) -> jwkSelector.select(new JWKSet(rsaKey));
//        return new NimbusJwtEncoder(jwkSource);
//
//        new NimbusJwtEncoder(new RSAKey.Builder((RSAPublicKey) keyPair().getPublic()).build();)
//
//    }
//
//
//    @Bean
//    public JwtDecoder jwtDecoder() {
//    }
//
//
//    @Bean
//    public KeyPair keyPair() {
//        KeyPairGenerator keyGen = null;
//        try {
//            keyGen = KeyPairGenerator.getInstance("RSA");
//        } catch (NoSuchAlgorithmException e) {
//            throw new RuntimeException(e);
//        }
//        keyGen.initialize(2048);
//        return keyGen.generateKeyPair();
//    }
//}
