package org.justinbaur.bankteller.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import org.justinbaur.bankteller.domain.Account;
import org.justinbaur.bankteller.exceptions.JsonReadException;
import org.justinbaur.bankteller.exceptions.JsonWriteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AdminServiceImpl implements AdminService {

    @Autowired
    JsonFileHandler handler;

    public void createAccount(Integer balance) {
        Boolean accountCreated = false;
        Integer currentId = 1;
        Integer accountLimit = 10;

        System.out.println(String.format(
                "\nAttempting to create a new account.. Checking for available IDs between 1 and %s ", accountLimit));
        while (!accountCreated && currentId <= accountLimit) { // find the next unused ID
            try {
                if (handler.getAccountsMap().containsKey(currentId)) {
                    currentId++;
                    if (currentId > accountLimit) {
                        System.out.println("No free IDs found.");
                    }
                    continue;
                }

                Account acct = new Account(currentId, balance);
                List<Account> accountsList = handler.readAccounts();
                accountsList.add(acct);
                handler.writeAccounts(accountsList);

                accountCreated = true;
                System.out.println(String.format("Account with ID %s has been created.", currentId));
                return;
            } catch (JsonReadException e) {
                System.err.println("Failed to read from file:" + e.getLocalizedMessage());
            } catch (JsonWriteException e) {
                System.err.println("Failed to write file:" + e.getLocalizedMessage());
            }
            currentId++;
        }
        System.out.println("Unable to create account.");

    }

    public void deleteAccount(Integer id) {
        List<Account> accountsList = new ArrayList<Account>();

        Map<Integer, Account> tempMap = handler.getAccountsMap();
        if (tempMap.containsKey(id)) {
            tempMap.remove(id);
            for (Account acct : tempMap.values()) {
                accountsList.add(acct);
            }
            try {
                handler.writeAccounts(accountsList);
                System.out.println(String.format("Account with ID %s has been deleted.", id));
            } catch (JsonWriteException e) {
                System.err.println("Failed to write file:" + e.getLocalizedMessage());
            }
        } else {
            System.out.println(String.format("Account with ID %s not found.", id));
        }

    }

}
