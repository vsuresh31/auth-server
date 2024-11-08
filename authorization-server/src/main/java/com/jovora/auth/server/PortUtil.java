package com.jovora.auth.server;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@Slf4j
class PortUtil {

    public static final String FILE_NAME = "auth-server";

    public static int readPortFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line = reader.readLine();
            if (line != null) {
                return Integer.parseInt(line.replaceAll("\\D+", ""));
            }
        } catch (IOException | NumberFormatException e) {
            log.error("Failed to write port number to file", e);
        }
        // Fallback to a random port if there's an issue with reading the file
        return 0;
    }
}
