package org.justinbaur.bankteller.runner;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.justinbaur.bankteller.exceptions.AccountNotFound;
import org.justinbaur.bankteller.service.AccountService;
import org.justinbaur.bankteller.service.AccountServiceInMemoryImpl;
import org.justinbaur.bankteller.util.Commands;

@Component
public class TellerTerminalRunner implements ApplicationRunner {

    @Autowired
    Scanner terminalnput;

    @Autowired
    AccountService accountService;

    Integer accountId = -1;
    String command = null;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        activateTerminalSession();
    }

    public void activateTerminalSession() {
        System.out.println("\nWelcome to the bank.");

        while(true){
            while(accountId == -1){
                login();
            }
            activeAccountSession();
        }
    }

    private void activeAccountSession(){
        listMenu();

        
        List<String> adminCommandList = Arrays.asList(Commands.CREATE, Commands.DELETE, Commands.LOGOUT, Commands.EXIT);
        List<String> userCommandList = Arrays.asList(Commands.BALANCE, Commands.DEPOSIT, Commands.WITHDRAW, Commands.LOGOUT, Commands.EXIT);
        List<String> currentCommandList = userCommandList;

        if(accountId == 0){
            currentCommandList = adminCommandList;
        }

        while (terminalnput.hasNext()) {
            command = terminalnput.nextLine();
            if(!currentCommandList.contains(command)){
                System.out.println("Invalid command.");
                continue;
            }
            switch (command) {
                case Commands.CREATE:
                    createAccount();
                    break;
                case Commands.DELETE:
                    deleteAccount();
                    break;
                case Commands.BALANCE:
                    displayBalance();
                    break;
                case Commands.DEPOSIT:
                    deposit();
                    break;
                case Commands.WITHDRAW:
                    withdraw();
                    break;
                case Commands.LOGOUT:
                    accountId = -1;
                    return;
                case Commands.EXIT:
                    terminateProgram();
                default:
                    System.out.println("Invalid command.");
                    break;
            }

            listMenu();
        }
    }

    private void createAccount(){
        //String message = "Please enter a starting balance for the account.";
        accountService.createAccount(0);
    }

    private void deleteAccount(){
        String message = "\nPlease enter the ID of the account you wish to delete or type exit to return.\n->";
        Integer id = -1;

        System.out.print(message);
        while (terminalnput.hasNext() && !(command = terminalnput.nextLine()).equals(Commands.EXIT)) {
            try {
                id = Integer.parseInt(command);
                if (id == 0) {
                    System.out.println("You can not delete this account.");
                } else if (id > 0){
                    accountService.deleteAccount(id);
                    return;
                } else {
                    System.out.println("Invalid value - Please enter a value greater than 0.");
                }
            } catch (AccountNotFound e) {
                System.err.println("Could not find account number: " + id);
            } catch (InputMismatchException | NumberFormatException e) {
                System.out.println("Invalid value - Please enter a valid integer.");
            }
            System.out.print(message);
        }
    }

    private void login(){
        accountId = -1;

        String loginMessage = "\nAre you an admin or a user?\n->";

        System.out.print(loginMessage);
        outerloop:
        while(terminalnput.hasNext() && accountId == -1){
            command = terminalnput.nextLine();
            switch (command) {
                case Commands.ADMIN:
                    accountId = 0;
                    return;
                case Commands.USER:
                    break outerloop;
                case Commands.EXIT:
                    terminateProgram();
                default:
                    System.out.println("Invalid command.");
                    break;
            }
            System.out.print(loginMessage);
        }

        System.out.println(accountId);
        
        loginMessage = "\nPlease enter a user ID to login, or type exit to terminate the application.\n->";

        System.out.print(loginMessage);

        while (terminalnput.hasNext() && accountId == -1) {
            command = terminalnput.nextLine();

            if(Commands.EXIT.equals(command)) { terminateProgram(); }
            try {
                accountId = Integer.parseInt(command);
                Boolean isAccount = accountService.checkAccount(accountId);
                if (isAccount == false){
                    accountId = -1;
                    System.out.println("This is not a valid account id, please try again.");
                    System.out.print("->");
                }
                else {
                    System.out.println("Welcome, " + accountId);
                    break;
                }
            } catch (InputMismatchException | NumberFormatException e) {
                System.out.println("Invalid value - Please enter a valid integer ID.");
                System.out.print(loginMessage);
            }
        }
    }

    private void displayBalance() {
        try {
            Integer balance = accountService.getBalance(accountId);
            DecimalFormat df = new DecimalFormat("###,###,###");
            System.out.println(String.format("Your current balance is: $%s.", df.format(balance)));
        } catch (AccountNotFound e){
            System.err.println("Could not find account number: " + accountId);
        }
    }

    private void deposit() {
        System.out.print("\nPlease enter an amount you would like to deposit or type exit to return.\n->");

        if (terminalnput.hasNext() && !(command = terminalnput.nextLine()).equals(Commands.EXIT)) {
            try {
                Integer depositAmount = Integer.parseInt(command);
                if (depositAmount > 0) {
                    accountService.addBalance(accountId, depositAmount);
                    System.out.println(String.format("You have deposited %s dollars.", depositAmount));
                    displayBalance();
                } else {
                    System.out.println("Invalid value - Please enter a value greater than 0.");
                }
            } catch (AccountNotFound e) {
                System.err.println("Could not find account number: " + accountId);
            } catch (InputMismatchException | NumberFormatException e) {
                System.out.println("Invalid value - Please enter a valid integer.");
            }
        }
    }

    private void withdraw() {
        try{ 
            if (accountService.getBalance(accountId) <= 0) {
                System.out.println("Insufficient account balance to withdraw.");
                return;
            }
        } catch(AccountNotFound e){
            System.err.println("Could not find account number: " + accountId);
        }

        System.out.print("\nPlease enter an amount you would like to withdraw or type exit to return.\n->");

        if (terminalnput.hasNext() && !(command = terminalnput.nextLine()).equals(Commands.EXIT)) {
            try {
                Integer withdrawAmount = Integer.parseInt(command);
                if (withdrawAmount > 0) {
                    if (accountService.getBalance(accountId) >= withdrawAmount) {
                        accountService.subtractBalance(accountId, withdrawAmount);
                        System.out.println(String.format("You have withdrawn %s dollars.", withdrawAmount));
                        displayBalance();
                    } else {
                        System.out.println("Insufficient funds.");
                    }
                } else {
                    System.out.println("Invalid value - Please enter a value greater than 0.");
                }
            } catch (AccountNotFound e) {
                System.err.println("Could not find account number: " + accountId);
            } catch (InputMismatchException | NumberFormatException e) {
                System.out.println("Invalid value - Please enter a valid integer.");
            }
        }
    }

    private void listMenu() {
        System.out.println("\nPlease follow the list of commands:");
        if(accountId == 0){ //admin commands
            System.out.println("  create - to create a new account.");
            System.out.println("  delete - to delete an existing account.");
        }
        if(accountId >= 1){ //standard user commands
            System.out.println("  balance - to check your current balance.");
            System.out.println("  deposit - to place money into your account.");
            System.out.println("  withdraw - to take money out of your account.");
        } 
        System.out.println("  logout - to log out of the current account.");
        System.out.println("  exit - to shut down your session.");
        System.out.print("->");
    }

    private void terminateProgram(){
        System.out.println("\nExiting the application...");
        System.exit(0);
    }
}