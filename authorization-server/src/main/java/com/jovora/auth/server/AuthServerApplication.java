package com.jovora.auth.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.ConfigurableEnvironment;

@SpringBootApplication
public class AuthServerApplication {

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(AuthServerApplication.class);
        springApplication.addListeners(event -> {
            if (event instanceof ApplicationEnvironmentPreparedEvent applicationenvironmentpreparedevent) {
                ConfigurableEnvironment env = applicationenvironmentpreparedevent.getEnvironment();
                int port = PortUtil.readPortFromFile();
                env.getSystemProperties().put("server.port", port);
            }
        });
        springApplication.run(args);
    }

    @Bean
    public PortFileWriter portFileWriter() {
        return new PortFileWriter();
    }
}