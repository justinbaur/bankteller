package org.justinbaur.bankteller.runner;

import java.text.DecimalFormat;
import java.util.InputMismatchException;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.justinbaur.bankteller.Exceptions.AccountNotFound;
import org.justinbaur.bankteller.service.AccountService;
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
        while (terminalnput.hasNext() && !(command = terminalnput.nextLine()).equals(Commands.EXIT)) {
            switch (command) {
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
                default:
                    System.out.println("Invalid command.");
                    break;
            }

            listMenu();
        }
    }

    private void login(){
        String loginMessage = "\nPlease enter a user ID to login, or type exit to terminate the application.\n->";

        System.out.print(loginMessage);
        while (terminalnput.hasNext() && !(command = terminalnput.nextLine()).equals(Commands.EXIT) && accountId == -1) {
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
        System.out.println("  balance - to check your current balance.");
        System.out.println("  deposit - to place money into your account.");
        System.out.println("  withdraw - to take money out of your account.");
        System.out.println("  logout - to log out of the current account.");
        System.out.println("  exit - to shut down your session.");
        System.out.print("->");
    }
}