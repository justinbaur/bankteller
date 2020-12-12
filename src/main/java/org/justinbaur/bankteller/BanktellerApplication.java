package org.justinbaur.bankteller;

import javax.annotation.PostConstruct;

import org.justinbaur.bankteller.service.TellerTerminalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BanktellerApplication {

	@Autowired
	TellerTerminalService terminal;

	public static void main(String[] args) {
		SpringApplication.run(BanktellerApplication.class, args);
	}

	@PostConstruct
	public void init(){
		terminal.activateTerminalSession();
	}

}
