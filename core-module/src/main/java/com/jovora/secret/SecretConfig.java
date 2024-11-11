package com.jovora.secret;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration(proxyBeanMethods = false)
@PropertySource("classpath:secret.properties")
@ConfigurationProperties(prefix = "ap1001")
@Getter
@Setter
public class SecretConfig {

    private String keystoreName;
    private String privateKeyFileName;
}
