package org.justinbaur.bankteller.configuration;

import java.util.Scanner;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public Scanner terminalInput() {
        return new Scanner(System.in);
    }

}
