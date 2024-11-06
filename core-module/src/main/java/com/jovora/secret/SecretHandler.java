package com.jovora.secret;

public interface SecretHandler {

    void initialize();

    Secret encrypt(String sensitiveData);

    String decrypt(Secret secret);
}
