package org.justinbaur.bankteller.runner;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.justinbaur.bankteller.domain.Account;
import org.justinbaur.bankteller.domain.Address;
import org.justinbaur.bankteller.exception.AccountNotFound;
import org.justinbaur.bankteller.exception.InsufficientBalance;
import org.justinbaur.bankteller.exception.ProfileNotFound;
import org.justinbaur.bankteller.service.UserProfileService;
import org.justinbaur.bankteller.service.AdminService;
import org.justinbaur.bankteller.service.Time;
import org.justinbaur.bankteller.util.AccountTypes;
import org.justinbaur.bankteller.util.Commands;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class TellerTerminalRunner implements ApplicationRunner {

    private static final Logger LOG = LoggerFactory.getLogger(TellerTerminalRunner.class);

    @Autowired
    Scanner terminalnput;

    @Autowired
    UserProfileService profileService;

    @Autowired
    AdminService adminService;

    String command = null;

    @Value("${message.welcome}")
    String welcomeMessage;

    Time myTime = Time.getInstance();

    @Override
    public void run(ApplicationArguments args) throws Exception {
        LOG.info(myTime.printTime() + " - Starting terminal session");
        activateTerminalSession();
    }

    /**
     * Base loop of the console. Requires users to login before accessing any other
     * part of the program.
     */
    public void activateTerminalSession() {
        LOG.info(welcomeMessage);

        while (true) {
            String currentId = null;
            while (currentId == null) {
                currentId = login();
            }
            activeProfileSession(currentId);
        }
    }

    /**
     * Represents the 'main menu' screen of the console after logging in, and
     * displays relevant console commands.
     * 
     * @param currentId current logged-in profile ID
     */
    private void activeProfileSession(String currentId) {
        listMenu(currentId);

        List<String> adminCommandList = Arrays.asList(Commands.CREATE_PROFILE, Commands.CREATE_ACCOUNT,
                Commands.DELETE_PROFILE, Commands.DELETE_ACCOUNT, Commands.REPORTS_BY_STATE, Commands.LOGOUT,
                Commands.EXIT);
        List<String> userCommandList = Arrays.asList(Commands.BALANCE, Commands.DEPOSIT, Commands.WITHDRAW,
                Commands.LOGOUT, Commands.EXIT);
        List<String> currentCommandList = userCommandList;

        try {
            if (profileService.getProfile(currentId).getIsAdmin()) {
                currentCommandList = adminCommandList;
            }
        } catch (ProfileNotFound e) {
            LOG.warn("Could not find profile ID: {}", currentId);
        }

        while (terminalnput.hasNext() && currentId != null) {
            command = terminalnput.nextLine();
            if (!currentCommandList.contains(command)) {
                LOG.warn("Invalid command.");
                continue;
            }
            switch (command) {
                case Commands.CREATE_PROFILE:
                    createProfile();
                    break;
                case Commands.DELETE_PROFILE:
                    deleteProfile();
                    break;
                case Commands.CREATE_ACCOUNT:
                    createAccount();
                    break;
                case Commands.DELETE_ACCOUNT:
                    deleteAccount();
                    break;
                case Commands.REPORTS_BY_STATE:
                    reportsByState();
                    break;
                case Commands.BALANCE:
                    displayBalance(currentId);
                    break;
                case Commands.DEPOSIT:
                    deposit(currentId);
                    break;
                case Commands.WITHDRAW:
                    withdraw(currentId);
                    break;
                case Commands.LOGOUT:
                    currentId = null;
                    return;
                case Commands.EXIT:
                    terminateProgram();
                default:
                    LOG.warn("Invalid command.");
                    break;
            }
            listMenu(currentId);
        }
    }

    /**
     * Log into an existing account via profile ID. Asks for profile ID via console
     * input.
     * 
     * @return profile ID of logged-in user
     */
    private String login() {
        String id = null;
        String loginMessage = "\nPlease enter a user ID to login, or type exit to terminate the application.\n->";

        LOG.info(loginMessage);

        while (terminalnput.hasNext() && id == null) {
            command = terminalnput.nextLine();
            LOG.info("You have entered: {}", command);

            if (Commands.EXIT.equals(command)) {
                terminateProgram();
            }
            try {
                Boolean isProfile = profileService.checkProfile(command);
                if (isProfile == false) {
                    LOG.warn("This is not a valid profile id, please try again.");
                    LOG.info("->");
                } else {
                    id = command;
                    if (profileService.getProfile(id).getIsAdmin()) {
                        LOG.info("Welcome ADMIN");
                    } else {
                        LOG.info("Welcome, " + profileService.getProfile(id).getCustomer().getFirstName());
                    }
                    break;
                }
            } catch (ProfileNotFound e) {
                LOG.warn("Profile not found.");
                LOG.info(loginMessage);
            } catch (InputMismatchException | NumberFormatException e) {
                LOG.warn("Invalid value - Please enter a valid ID.");
                LOG.info(loginMessage);
            }
        }
        return id;
    }

    /**
     * Admin-level method to create a new user profile in the database. Asks for
     * name and address via console input.
     */
    private void createProfile() {
        String firstName = null;
        String lastName = null;
        String country = null;
        String state = null;
        String city = null;
        Integer zipCode = null;
        String street = null;

        String message = "\nPlease enter the user's first name.\n->";
        LOG.info(message);
        if (terminalnput.hasNext() && !(command = terminalnput.nextLine()).equals(Commands.EXIT) && firstName == null) {
            firstName = command;
        }

        message = "\nPlease enter the user's last name.\n->";
        LOG.info(message);
        if (terminalnput.hasNext() && !(command = terminalnput.nextLine()).equals(Commands.EXIT) && lastName == null) {
            lastName = command;
        }

        message = "\nPlease enter the user's state. [2-letter abbreviation] \n->";
        LOG.info(message);
        while (terminalnput.hasNext() && !(command = terminalnput.nextLine()).equals(Commands.EXIT) && state == null) {
            if (command.length() == 2) {
                state = command.toUpperCase();
                break;
            } else {
                LOG.warn("Please enter a 2-letter abbrevation for the state.");
            }
            LOG.info(message);
        }

        message = "\nPlease enter the user's city.\n->";
        LOG.info(message);
        if (terminalnput.hasNext() && !(command = terminalnput.nextLine()).equals(Commands.EXIT) && city == null) {
            city = command;
        }

        message = "\nPlease enter the user's zip code.\n->";
        LOG.info(message);
        while (terminalnput.hasNext() && !(command = terminalnput.nextLine()).equals(Commands.EXIT)
                && zipCode == null) {
            try {
                zipCode = Integer.parseInt(command);
                break;
            } catch (InputMismatchException | NumberFormatException e) {
                LOG.warn("Invalid value - Please enter a valid number.");
            }
            LOG.info(message);
        }

        message = "\nPlease enter the user's street name.\n->";
        LOG.info(message);
        if (terminalnput.hasNext() && !(command = terminalnput.nextLine()).equals(Commands.EXIT) && street == null) {
            street = command;
        }

        Address address = new Address(street, state, city, country, zipCode);
        adminService.createProfile(firstName, lastName, address);

        LOG.info("Profile for {} {} has been created", firstName, lastName);
    }

    /**
     * Admin-level method to delete an existing profile from the database. Asks for
     * profile ID via console input. Use with caution.
     */
    private void deleteProfile() {
        String message = "\nPlease enter the ID of the profile you wish to delete or type exit to return.\n->";
        String id = null;

        LOG.info(message);
        while (terminalnput.hasNext() && !(command = terminalnput.nextLine()).equals(Commands.EXIT)) {
            try {
                id = command;
                if (profileService.checkProfile(id)) {
                    adminService.deleteProfile(id);
                    return;
                } else {
                    LOG.warn("Invalid value - Please enter a value greater than 0.");
                }
            } catch (ProfileNotFound e) {
                LOG.warn("Could not find profile ID: {}", id);
            } catch (InputMismatchException | NumberFormatException e) {
                LOG.warn("Invalid value - Please enter a valid integer.");
            }
            LOG.info(message);
        }
    }

    /**
     * Admin-level method to create a new account for a specific profile. Asks for
     * profile ID and desired account name and type via console input.
     */
    private void createAccount() {
        String id = null;
        String accountName = null;
        String accountType = null;
        Integer balance = null;
        

        String message = "\nPlease enter the profile ID.\n->";
        LOG.info(message);
        while (terminalnput.hasNext() && !(command = terminalnput.nextLine()).equals(Commands.EXIT) && id == null) {
            try {
                if (profileService.checkProfile(command)) {
                    id = command;
                    LOG.info("Profile found.");
                    break;
                }
            } catch (ProfileNotFound e) {
                LOG.warn("Profile {} not found", command);
            }
            LOG.info(message);
        }

        message = "\nPlease enter the account name.\n->";
        LOG.info(message);
        while (terminalnput.hasNext() && !(command = terminalnput.nextLine()).equals(Commands.EXIT)
                && accountName == null) {
            try{
                if(profileService.getAccounts(id) == null || !getAccountNames(id).contains(accountName)){
                    accountName = command;
                    break;
                } else {
                    LOG.warn("You can not have two accounts with the same name. Please enter a unique name for the account.");
                }
            } catch (ProfileNotFound e){
                LOG.warn("Profile {} not found", command);
            }
            LOG.info(message);
        }

        message = "\nPlease enter the account type.\n->";
        LOG.info(message);
        List<String> accountTypes = Arrays.asList(AccountTypes.CHECKING, AccountTypes.SAVINGS,
                AccountTypes.CERTIFICATE_OF_DEPOSIT, AccountTypes.MONEY_MARKET,
                AccountTypes.INDIVIDUAL_RETIREMENT_ACCOUNT);
        while (terminalnput.hasNext() && !(command = terminalnput.nextLine()).equals(Commands.EXIT)
                && accountType == null) {
            if (accountTypes.contains(command)) {
                accountType = command;
                break;
            } else {
                LOG.warn("Incorrect account type.");
            }
            LOG.info(message);
        }

        message = "\nPlease enter the user's starting balance.\n->";
        LOG.info(message);
        while (terminalnput.hasNext() && !(command = terminalnput.nextLine()).equals(Commands.EXIT)
                && balance == null) {
            try {
                balance = Integer.parseInt(command);
                break;
            } catch (InputMismatchException | NumberFormatException e) {
                LOG.warn("Invalid value - Please enter a valid number.");
            }
            LOG.info(message);
        }

        try {
            adminService.createAccount(id, accountName, accountType, balance);
        } catch (ProfileNotFound e) {
            LOG.warn("Profile {} not found", id);
        }
    }

    /**
     * Admin-level method to delete an account for a specific profile. Asks for
     * profile ID and account name via console input. Use with caution.
     */
    private void deleteAccount() {
        String id = null;
        String accountName = null;

        String message = "\nPlease enter the profile ID.\n->";
        LOG.info(message);
        while (terminalnput.hasNext() && !(command = terminalnput.nextLine()).equals(Commands.EXIT) && id == null) {
            id = command;
            try {
                if (profileService.checkProfile(id)) {
                    LOG.info("Profile found.");
                    break;
                }
            } catch (ProfileNotFound e) {
                LOG.warn("Profile {} not found", id);
            }
        }

        LOG.info("{}'s available accounts: {}", id, getAccountNames(id));

        message = "\nPlease enter the name of the account you wish to delete.\n->";
        LOG.info(message);
        if (terminalnput.hasNext() && !(command = terminalnput.nextLine()).equals(Commands.EXIT)
                && accountName == null) {
            accountName = command;
        }

        try {
            adminService.deleteAccount(id, accountName);
        } catch (ProfileNotFound e) {
            LOG.warn("Profile {} not found", id);
        } catch (AccountNotFound e) {
            LOG.warn("Account {} not found", accountName);
        }
    }

    /**
     * Admin-level example method of querying database profiles and returning those
     * matching a given state. Asks for state name via console input.
     */
    private void reportsByState() {
        String state = null;

        LOG.info("Please choose a state to filter profiles by.");
        if (terminalnput.hasNext() && !(command = terminalnput.nextLine()).equals(Commands.EXIT)) {
            state = command;
        }

        adminService.printReportByState(state);
    }

    /**
     * User-level method to display the balance of one of their (logged-in user's)
     * chosen bank accounts. Asks for account name via console input.
     * 
     * @param currentId current logged-in profile ID
     */
    private void displayBalance(String currentId) {
        String message = "\nPlease enter the account name you would like to check the balance of.\n Available accounts: {} \n->";
        String accountName = null;

        LOG.info(message, getAccountNames(currentId));
        while (terminalnput.hasNext() && !(command = terminalnput.nextLine()).equals(Commands.EXIT)
                && !getAccountNames(currentId).contains(accountName)) {
            try {
                accountName = command;
                if (getAccountNames(currentId).contains(accountName)) {
                    Integer balance = profileService.getBalance(currentId, accountName);
                    DecimalFormat df = new DecimalFormat("###,###,###");
                    LOG.info("Your current balance is: ${}.", df.format(balance));
                    break;
                } else {
                    LOG.warn("Account with name {} does not exist for current profile.");
                }
            } catch (ProfileNotFound e) {
                LOG.warn("Could not find profile ID: {}", currentId);
            } catch (AccountNotFound e) {
                LOG.warn("Could not find account: {}", accountName);
            }
            LOG.info(message, getAccountNames(currentId));
        }
    }

    /**
     * User-level method to deposit money into one of their (logged-in user's)
     * chosen bank accounts. Asks for account name and deposit amount via console
     * input.
     * 
     * @param currentId current logged-in profile ID
     */
    private void deposit(String currentId) {
        String message = "\nPlease enter the name of the account you would like to make a deposit to.\n Available accounts: {} \n->";
        String accountName = null;

        LOG.info(message, getAccountNames(currentId));
        while (terminalnput.hasNext() && !(command = terminalnput.nextLine()).equals(Commands.EXIT)
                && !getAccountNames(currentId).contains(accountName)) {
            accountName = command;
            if (getAccountNames(currentId).contains(accountName)) {
                break;
            } else {
                LOG.warn("Account with name {} does not exist for current profile.", accountName);
            }
            LOG.info(message, getAccountNames(currentId));
        }

        message = "\nPlease enter an amount you would like to deposit or type exit to return.\n->";
        LOG.info(message);
        while (terminalnput.hasNext() && !(command = terminalnput.nextLine()).equals(Commands.EXIT)) {
            try {
                Integer depositAmount = Integer.parseInt(command);
                if (depositAmount > 0) {
                    profileService.addBalance(currentId, accountName, depositAmount);
                    LOG.info("You have deposited {} dollars.", depositAmount);
                    LOG.info("Current balance in {}: {}", accountName,
                            profileService.getBalance(currentId, accountName));
                } else {
                    LOG.warn("Invalid value - Please enter a value greater than 0.");
                }
            } catch (ProfileNotFound e) {
                LOG.warn("Could not find profile ID: {}", currentId);
            } catch (AccountNotFound e) {
                LOG.warn("Could not find account: {}", accountName);
            } catch (InputMismatchException | NumberFormatException e) {
                LOG.warn("Invalid value - Please enter a valid integer.");
            }
            LOG.info(message);
        }

    }

    /**
     * User-level method to withdraw money from one of their (logged-in user's)
     * chosen bank accounts. Asks for account name and withdraw amount via console
     * input.
     * 
     * @param currentId current logged-in profile ID
     */
    private void withdraw(String currentId) {
        String message = "\nPlease enter the name of the account you would like to make a withdrawal from.\n Available accounts: {} \n->";
        String accountName = null;

        LOG.info(message, getAccountNames(currentId));
        while (terminalnput.hasNext() && !(command = terminalnput.nextLine()).equals(Commands.EXIT)
                && !getAccountNames(currentId).contains(accountName)) {
            accountName = command;
            if (getAccountNames(currentId).contains(accountName)) {
                break;
            } else {
                LOG.warn("Account with name {} does not exist for current profile.", accountName);
            }
            LOG.info(message, getAccountNames(currentId));
        }

        message = "\nPlease enter an amount you would like to withdraw or type exit to return.\n->";
        LOG.info(message);
        while (terminalnput.hasNext() && !(command = terminalnput.nextLine()).equals(Commands.EXIT)) {
            try {
                Integer withdrawAmount = Integer.parseInt(command);
                if (withdrawAmount > 0) {
                    profileService.subtractBalance(currentId, accountName, withdrawAmount);
                    LOG.info("You have withdrawn {} dollars.", withdrawAmount);
                    LOG.info("Current balance in {}: {}", accountName,
                            profileService.getBalance(currentId, accountName));
                } else {
                    LOG.warn("Invalid value - Please enter a value greater than 0.");
                }
            } catch (ProfileNotFound e) {
                LOG.warn("Could not find profile ID: " + currentId);
            } catch (AccountNotFound e) {
                LOG.warn("Could not find account: {}", accountName);
            } catch (InsufficientBalance e) {
                LOG.warn("Insufficient balance to make withdrawal.");
            } catch (InputMismatchException | NumberFormatException e) {
                LOG.warn("Invalid value - Please enter a valid integer.");
            }
            LOG.info(message);
        }
    }

    /**
     * Get a List of String accountNames for a given Profile object matching String currentId.
     * 
     * @param currentId the ID of the Profile to check the database for.
     * @return List of Strings of account names for given Profile.
     */
    public List<String> getAccountNames(String currentId){
        List<String> accountNames = new ArrayList<String>();
        List<Account> accountList = new ArrayList<Account>();
        try{
            accountList = profileService.getAccounts(currentId);
        } catch (ProfileNotFound e){
            LOG.warn("Could not find profile ID: " + currentId);
        }
        
        for(Account acct : accountList){
            accountNames.add(acct.getAccountName());
        }
        return accountNames;
    }

    /**
     * Displays a list of console commands and their descriptions, for either user
     * or admin. Uses current profile ID to determine if the current user is a
     * normal user or an admin.
     * 
     * @param currentId current logged-in profile ID
     */
    private void listMenu(String currentId) {
        LOG.info("\nPlease follow the list of commands:");
        try {
            if (profileService.getProfile(currentId).getIsAdmin()) {
                LOG.info("  create profile - to create a new profile.");
                LOG.info("  delete profile - to delete an existing profile.");
                LOG.info("  create account - to create an account for an existing profile.");
                LOG.info("  delete profile - to delete an account within an existing profile.");
                LOG.info("  reports by state - display a list of profiles filtered by state");
            } else {
                LOG.info("  balance - to check an account's current balance.");
                LOG.info("  deposit - to place money into an account.");
                LOG.info("  withdraw - to take money out of an account.");
            }
        } catch (ProfileNotFound e) {
            LOG.warn("Profile Not Found.");
        }
        LOG.info("  logout - to log out of the current profile.");
        LOG.info("  exit - to shut down your session.");
        LOG.info("->");
    }

    /**
     * Exits the program.
     */
    private void terminateProgram() {
        LOG.info("\nExiting the application...");
        System.exit(0);
    }
}