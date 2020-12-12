package org.justinbaur.bankteller.service;

import java.text.DecimalFormat;
import java.util.InputMismatchException;
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

    String command = null;

    public void activateTerminalSession(){
        
        System.out.println("\nWelcome to the bank.");
        listMenu();
        
        while(terminalnput.hasNext() && !(command = terminalnput.nextLine()).equals(Commands.EXIT)){
            switch (command) {
                case Commands.BALANCE: displayBalance(accountService.getBalance());
                    break;
                case Commands.DEPOSIT: deposit();
                    break;
                default: System.out.println("Invalid command.");
                    break;
            }

            listMenu();
        }
    }

    private void displayBalance(Integer balance){
        DecimalFormat df = new DecimalFormat("###,###,###");
        System.out.println(String.format("Your current balance is: $%s.", df.format(balance)));
    }

    private void deposit(){

        Integer depositAmount = null;
        String depositMessage = "\nPlease enter an amount you would like to deposit or type exit to return.\n->";

        System.out.print(depositMessage);

        while(terminalnput.hasNext() && !(command = terminalnput.nextLine()).equals(Commands.EXIT)){
            try {
                depositAmount = Integer.parseInt(command);
                accountService.addBalance(depositAmount);
                System.out.println(String.format("You have deposited %s dollars.", depositAmount));
                displayBalance(accountService.getBalance());
            } catch (InputMismatchException | NumberFormatException e) {
                System.out.println("Invalid value - Please enter a valid integer.");
            }

            System.out.print(depositMessage);
        }
    }

    private void listMenu(){
        System.out.println("\nPlease follow the list of commands:");
        System.out.println("  balance - to check your current balance.");
        System.out.println("  deposit - to place money into your account.");
        System.out.println("  exit - to shut down your session.");
        System.out.print("->");
    }
}