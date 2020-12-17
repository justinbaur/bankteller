package org.justinbaur.bankteller.runner;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.justinbaur.bankteller.service.AccountServiceInMemoryImpl;
import org.justinbaur.bankteller.util.Commands;

@Component
public class TellerTerminalRunner implements ApplicationRunner {

    @Autowired
    Scanner terminalnput;

    AccountServiceInMemoryImpl cAcct = null; //current account
    HashMap<Integer, AccountServiceInMemoryImpl> accountMap;

    String command = null;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        activateTerminalSession();
    }

    public void activateTerminalSession() {

        System.out.println("\nWelcome to the bank.");

        accountMap = populateAccounts();

        cAcct = login();

        System.out.println(String.format("\nWelcome, %s", cAcct.getName()));

        listMenu();

        while (terminalnput.hasNext() && !(command = terminalnput.nextLine()).equals(Commands.EXIT)) {
            switch (command) {
                case Commands.BALANCE:
                    displayBalance(cAcct.getBalance());
                    break;
                case Commands.DEPOSIT:
                    deposit();
                    break;
                case Commands.WITHDRAW:
                    withdraw();
                    break;
                case Commands.LOGOUT:
                    cAcct = login();
                    break;
                default:
                    System.out.println("Invalid command.");
                    break;
            }

            listMenu();
        }
    }

    private void displayBalance(Integer balance) {
        DecimalFormat df = new DecimalFormat("###,###,###");
        System.out.println(String.format("Your current balance is: $%s.", df.format(balance)));
    }

    private void deposit() {
        System.out.print("\nPlease enter an amount you would like to deposit or type exit to return.\n->");

        if (terminalnput.hasNext() && !(command = terminalnput.nextLine()).equals(Commands.EXIT)) {
            try {
                Integer depositAmount = Integer.parseInt(command);
                if (depositAmount > 0) {
                    cAcct.addBalance(depositAmount);
                    System.out.println(String.format("You have deposited %s dollars.", depositAmount));
                    displayBalance(cAcct.getBalance());
                } else {
                    System.out.println("Invalid value - Please enter a value greater than 0.");
                }
            } catch (InputMismatchException | NumberFormatException e) {
                System.out.println("Invalid value - Please enter a valid integer.");
            }
        }
    }

    private void withdraw() {
        if (cAcct.getBalance() <= 0) {
            System.out.println("Insufficient account balance to withdraw.");
            return;
        }

        System.out.print("\nPlease enter an amount you would like to withdraw or type exit to return.\n->");

        if (terminalnput.hasNext() && !(command = terminalnput.nextLine()).equals(Commands.EXIT)) {
            try {
                Integer withdrawAmount = Integer.parseInt(command);
                if (withdrawAmount > 0) {
                    if (cAcct.getBalance() >= withdrawAmount) {
                        cAcct.subtractBalance(withdrawAmount);
                        System.out.println(String.format("You have withdrawn %s dollars.", withdrawAmount));
                        displayBalance(cAcct.getBalance());
                    } else {
                        System.out.println("Insufficient funds.");
                    }
                } else {
                    System.out.println("Invalid value - Please enter a value greater than 0.");
                }
            } catch (InputMismatchException | NumberFormatException e) {
                System.out.println("Invalid value - Please enter a valid integer.");
            }
        }
    }

    private void listMenu() {
        System.out.println(String.format("\nCurrently logged in as: %s", cAcct.getName()));
        System.out.println("Please follow the list of commands:");
        System.out.println("  balance - to check your current balance.");
        System.out.println("  deposit - to place money into your account.");
        System.out.println("  withdraw - to take money out of your account.");
        System.out.println("  logout - to log out of the current account.");
        System.out.println("  exit - completely exit the application.");
        System.out.print("->");
    }

    private HashMap<Integer, AccountServiceInMemoryImpl> populateAccounts(){
        Integer[] accountIDList = new Integer[] {0001, 1000, 1001, 1002, 1003, 1004};
        String[] accountUsernameList = new String[] {"ADMIN", "Dustin D", "Justin B", "Boydvan L", "Gino T", "Glenn D"};
        Integer[] accountStartingBalanceList = new Integer[] {999999, 15, 55000, 55001, 236, 1450};

        HashMap<Integer, AccountServiceInMemoryImpl> accountLookup = new HashMap<Integer, AccountServiceInMemoryImpl>();

        try {
            for (int i = 0; i < accountIDList.length; i++ ) {
                AccountServiceInMemoryImpl newAccount = new AccountServiceInMemoryImpl(accountIDList[i], accountUsernameList[i]);
                newAccount.addBalance(accountStartingBalanceList[i]);
                accountLookup.put(accountIDList[i], newAccount);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }

        return accountLookup;
    }

    public AccountServiceInMemoryImpl login(){
        AccountServiceInMemoryImpl currentAccount = null;

        String loginMessage = "\nPlease enter a user ID to login, or type exit to terminate the application.\n->";

        System.out.print(loginMessage);
        while (terminalnput.hasNext() && !(command = terminalnput.nextLine()).equals(Commands.EXIT) && currentAccount == null) {
            try {
                Integer idInput = Integer.parseInt(command);
                if(!accountMap.containsKey(idInput) ){
                    System.out.println("User ID not found.");
                    System.out.print(loginMessage);
                    continue;
                }
                currentAccount = accountMap.get(idInput);
                break;
            } catch (InputMismatchException | NumberFormatException e) {
                System.out.println("Invalid value - Please enter a valid integer ID.");
                System.out.print(loginMessage);
            }
        }

        return currentAccount;
    }
}