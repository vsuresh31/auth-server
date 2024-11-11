package com.jovora;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.metrics.buffering.BufferingApplicationStartup;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.metrics.StartupStep;

import java.util.Arrays;

@SpringBootApplication
@Lazy
public class JovoraApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(JovoraApplication.class);
//        application.setApplicationStartup(new BufferingApplicationStartup(2048));
        application.run(args);
    }
}