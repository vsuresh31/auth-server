package com.jovora.auth.server;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.web.context.WebServerApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;

@Component
@Slf4j
public class PortFileWriter implements ApplicationListener<ApplicationReadyEvent> {

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        WebServerApplicationContext serverContext = (WebServerApplicationContext) event.getApplicationContext();
        int port = serverContext.getWebServer().getPort();

        Path file = Paths.get(PortUtil.FILE_NAME);
        try {
            Files.write(file, Collections.singleton(String.valueOf(port)), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
