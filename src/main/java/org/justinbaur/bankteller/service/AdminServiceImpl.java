package org.justinbaur.bankteller.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.justinbaur.bankteller.domain.Account;
import org.justinbaur.bankteller.exceptions.JsonReadException;
import org.justinbaur.bankteller.exceptions.JsonWriteException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AdminServiceImpl implements AdminService {

    private static final Logger LOG = LoggerFactory.getLogger(AdminServiceImpl.class);

    @Autowired
    JsonFileHandler handler;

    public void createAccount(Integer balance) {
        Integer idRangeStart = 1;
        Integer accountLimit = 10;

        LOG.info("\nAttempting to create a new account.. Checking for available IDs between {} and {}.", idRangeStart, accountLimit);

        Integer availableId = findAvailableAccountID(idRangeStart, accountLimit);

        try {
            if (availableId > 0){
                Account acct = new Account(availableId, balance);

                List<Account> accountsList = handler.readAccounts();
                accountsList.add(acct);
                handler.writeAccounts(accountsList);
        
                //accountCreated = true;
                LOG.info("Account with ID {}} has been created.", availableId);
            } else {
                LOG.warn("Unable to create account - No available ID in specified range.");
            }
        } catch (JsonReadException e) {
            LOG.error("Failed to read from file:" + e.getLocalizedMessage());
        } catch (JsonWriteException e) {
            LOG.error("Failed to write file:" + e.getLocalizedMessage());
        }
        
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
                LOG.info("Account with ID {} has been deleted.", id);
            } catch (JsonWriteException e) {
                LOG.error("Failed to write file:" + e.getLocalizedMessage());
            }
        } else {
            LOG.warn("Account with ID %s not found.", id);
        }
    }

    public Integer findAvailableAccountID(Integer idRangeMin, Integer idRangeMax) {

        Integer currentId = idRangeMin;

        while(currentId <= idRangeMax){
            if (!handler.getAccountsMap().containsKey(currentId)) {
                return currentId;
            }
            currentId++;
        }
        return -1;
    }
}
