package org.justinbaur.bankteller.runner;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.justinbaur.bankteller.exceptions.AccountNotFound;
import org.justinbaur.bankteller.exceptions.UpdateException;
import org.justinbaur.bankteller.service.AccountService;
import org.justinbaur.bankteller.service.AdminService;
import org.justinbaur.bankteller.service.Time;
import org.justinbaur.bankteller.util.Commands;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class TellerTerminalRunner implements ApplicationRunner {

    private static final Logger LOG = LoggerFactory.getLogger(TellerTerminalRunner.class);

    @Autowired
    Scanner terminalnput;

    @Autowired
    AccountService accountService;

    @Autowired
    AdminService adminService;

    Integer accountId = -1;
    String command = null;

    @Value("${message.welcome}")
    String welcomeMessage;

    Time myTime = Time.getInstance();

    @Override
    public void run(ApplicationArguments args) throws Exception {
        LOG.info(myTime.printTime() + " - Starting terminal session");
        activateTerminalSession();
    }

    public void activateTerminalSession() {
        LOG.info(welcomeMessage);

        while (true) {
            while (accountId == -1) {
                login();
            }
            activeAccountSession();
        }
    }

    private void activeAccountSession() {
        listMenu();

        List<String> adminCommandList = Arrays.asList(Commands.CREATE, Commands.DELETE, Commands.LOGOUT, Commands.EXIT);
        List<String> userCommandList = Arrays.asList(Commands.BALANCE, Commands.DEPOSIT, Commands.WITHDRAW,
                Commands.LOGOUT, Commands.EXIT);
        List<String> currentCommandList = userCommandList;

        if (accountId == 0) {
            currentCommandList = adminCommandList;
        }

        while (terminalnput.hasNext()) {
            command = terminalnput.nextLine();
            if (!currentCommandList.contains(command)) {
                LOG.warn("Invalid command.");
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
                LOG.warn("Invalid command.");
                    break;
            }

            listMenu();
        }
    }

    private void createAccount() {
        adminService.createAccount(0);
    }

    private void deleteAccount() {
        String message = "\nPlease enter the ID of the account you wish to delete or type exit to return.\n->";
        Integer id = -1;

        LOG.info(message);
        while (terminalnput.hasNext() && !(command = terminalnput.nextLine()).equals(Commands.EXIT)) {
            try {
                id = Integer.parseInt(command);
                if (id == 0) {
                    LOG.warn("You can not delete this account.");
                } else if (id > 0) {
                    adminService.deleteAccount(id);
                    return;
                } else {
                    LOG.warn("Invalid value - Please enter a value greater than 0.");
                }
            } catch (AccountNotFound e) {
                LOG.warn("Could not find account number: " + id);
            } catch (InputMismatchException | NumberFormatException e) {
                LOG.warn("Invalid value - Please enter a valid integer.");
            }
            LOG.info(message);
        }
    }

    private void login() {
        accountId = -1;

        String loginMessage = "\nAre you an admin or a user?\n->";

        LOG.info(loginMessage);
        outerloop: while (terminalnput.hasNext() && accountId == -1) {
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
                    LOG.warn("Invalid command.");
                    break;
            }
            LOG.info(loginMessage);
        }

        LOG.info("" + accountId);

        loginMessage = "\nPlease enter a user ID to login, or type exit to terminate the application.\n->";

        LOG.info(loginMessage);

        while (terminalnput.hasNext() && accountId == -1) {
            command = terminalnput.nextLine();

            if (Commands.EXIT.equals(command)) {
                terminateProgram();
            }
            try {
                accountId = Integer.parseInt(command);
                Boolean isAccount = accountService.checkAccount(accountId);
                if (isAccount == false) {
                    accountId = -1;
                    LOG.warn("This is not a valid account id, please try again.");
                    LOG.info("->");
                } else {
                    LOG.info("Welcome, " + accountId);
                    break;
                }
            } catch (InputMismatchException | NumberFormatException e) {
                LOG.warn("Invalid value - Please enter a valid integer ID.");
                LOG.info(loginMessage);
            }
        }
    }

    private void displayBalance() {
        try {
            Integer balance = accountService.getBalance(accountId);
            DecimalFormat df = new DecimalFormat("###,###,###");
            LOG.info("Your current balance is: ${}.", df.format(balance));
        } catch (AccountNotFound e) {
            LOG.warn("Could not find account number: " + accountId);
        }
    }

    private void deposit() {
        LOG.info("\nPlease enter an amount you would like to deposit or type exit to return.\n->");

        if (terminalnput.hasNext() && !(command = terminalnput.nextLine()).equals(Commands.EXIT)) {
            try {
                Integer depositAmount = Integer.parseInt(command);
                if (depositAmount > 0) {
                    try {
                        accountService.addBalance(accountId, depositAmount);
                    } catch (UpdateException e) {
                        LOG.error("Failed to withdraw $" + depositAmount);
                    }
                    LOG.info("You have deposited {} dollars.", depositAmount);
                    displayBalance();
                } else {
                    LOG.warn("Invalid value - Please enter a value greater than 0.");
                }
            } catch (AccountNotFound e) {
                LOG.warn("Could not find account number: " + accountId);
            } catch (InputMismatchException | NumberFormatException e) {
                LOG.warn("Invalid value - Please enter a valid integer.");
            }
        }
    }

    private void withdraw() {
        try {
            if (accountService.getBalance(accountId) <= 0) {
                LOG.warn("Insufficient account balance to withdraw.");
                return;
            }
        } catch (AccountNotFound e) {
            LOG.warn("Could not find account number: " + accountId);
        }

        LOG.info("\nPlease enter an amount you would like to withdraw or type exit to return.\n->");

        if (terminalnput.hasNext() && !(command = terminalnput.nextLine()).equals(Commands.EXIT)) {
            try {
                Integer withdrawAmount = Integer.parseInt(command);
                if (withdrawAmount > 0) {
                    if (accountService.getBalance(accountId) >= withdrawAmount) {
                        try {
                            accountService.subtractBalance(accountId, withdrawAmount);
                        } catch (UpdateException e) {
                            LOG.error("Failed to withdraw $" + withdrawAmount);
                        }
                        LOG.info("You have withdrawn {}} dollars.", withdrawAmount);
                        displayBalance();
                    } else {
                        LOG.warn("Insufficient funds.");
                    }
                } else {
                    LOG.warn("Invalid value - Please enter a value greater than 0.");
                }
            } catch (AccountNotFound e) {
                LOG.warn("Could not find account number: " + accountId);
            } catch (InputMismatchException | NumberFormatException e) {
                LOG.warn("Invalid value - Please enter a valid integer.");
            }
        }
    }

    private void listMenu() {
        LOG.info("\nPlease follow the list of commands:");
        if (accountId == 0) { // admin commands
            LOG.info("  create - to create a new account.");
            LOG.info("  delete - to delete an existing account.");
        }
        if (accountId >= 1) { // user commands
            LOG.info("  balance - to check your current balance.");
            LOG.info("  deposit - to place money into your account.");
            LOG.info("  withdraw - to take money out of your account.");
        }
        LOG.info("  logout - to log out of the current account.");
        LOG.info("  exit - to shut down your session.");
        LOG.info("->");
    }

    private void terminateProgram() {
        LOG.info("\nExiting the application...");
        System.exit(0);
    }
}