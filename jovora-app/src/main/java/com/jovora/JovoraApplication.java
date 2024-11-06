package com.jovora;

import com.jovora.config.CoreConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.bootstrap.config.PropertySourceLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class JovoraApplication implements ApplicationRunner {


    @Autowired
    private Environment env;

    public static void main(String[] args) {
        SpringApplication.run(JovoraApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        env.getActiveProfiles();

    }


}