package org.justinbaur.bankteller.configuration;

import java.util.Scanner;

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
}
