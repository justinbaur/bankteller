package org.justinbaur.bankteller.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@PropertySource("file:${mongodb.secret}")
@Configuration
public class AppConfig {

}
