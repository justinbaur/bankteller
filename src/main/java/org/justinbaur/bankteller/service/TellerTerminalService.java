package org.justinbaur.bankteller.service;

import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.justinbaur.bankteller.util.Commands;

@Component
public class TellerTerminalService {
    
    @Autowired
    Scanner terminalnput;
    
    @Autowired
    AccountService accountService;

    public void activateTerminalSession(){
        String command = null;
        System.out.println("\nWelcome to the bank\n");
        listMenu();
        
        while(terminalnput.hasNext() && !(command = terminalnput.nextLine()).equals(Commands.EXIT)){
            switch (command) {
                case Commands.BALANCE: displayBalance(accountService.getBalance());
                    break;
                default: System.out.println("Invalid command\n");
                    break;
            }

            listMenu();
        }
    }

    private void displayBalance(Integer balance){
        System.out.println(String.format("Your current balance is: %s\n", balance));
    }

    private void listMenu(){
        System.out.println("Please follow the list of commands:");
        System.out.println("  balance - to check your current balance");
        System.out.println("  exit - to shut down your session");
        System.out.print("->");
    }
}