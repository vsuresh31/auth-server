package com.jovora;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class JovoraApplication implements ApplicationRunner {

    private final ApplicationContext context;

    public JovoraApplication(ApplicationContext context) {
        this.context = context;
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {
        PaymentProcessor paymentService = context.getBean(PaymentProcessor.class);
        System.out.println(paymentService.process());
    }

    public static void main(String[] args) {
        SpringApplication.run(JovoraApplication.class, args);
    }


}