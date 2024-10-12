package com.jovora;

import org.springframework.stereotype.Component;

@Component
public class PaypalPaymentProcessor implements PaymentProcessor {
    @Override
    public String process() {
        return "from payment processor";
    }
}
