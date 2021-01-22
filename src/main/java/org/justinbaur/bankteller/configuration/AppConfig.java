package org.justinbaur.bankteller.configuration;

import java.util.Scanner;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.justinbaur.bankteller.service.JsonFileHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@PropertySource("file:${mongodb.secret}")
@Configuration
public class AppConfig {

    @Bean
    public Scanner terminalInput() {
        return new Scanner(System.in);
    }

    @Bean
    public ObjectMapper mapper() {
        return new ObjectMapper();
    }

    @Bean
    public JsonFileHandler handler() {
        return new JsonFileHandler();
    }
}
