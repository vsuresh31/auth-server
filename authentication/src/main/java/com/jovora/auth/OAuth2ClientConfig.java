//package com.jovora.auth;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//public class OAuth2ClientConfig {
//
//    @Bean
//    public SecurityFilterChain clientSecurityFilterChain(HttpSecurity http) throws Exception {
//        http.authorizeHttpRequests(authorizeRequests -> authorizeRequests.requestMatchers("/login").permitAll().
//                        anyRequest().authenticated())
//                .oauth2Login(oauth2Login -> oauth2Login.loginPage("/login"))
//                .oauth2Client(Customizer.withDefaults());
//        return http.build();
//    }
//}
