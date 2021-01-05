package org.justinbaur.bankteller.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import org.justinbaur.bankteller.domain.Account;
import org.justinbaur.bankteller.exceptions.AccountNotFound;
import org.justinbaur.bankteller.exceptions.JsonReadException;
import org.justinbaur.bankteller.exceptions.JsonWriteException;
import org.justinbaur.bankteller.exceptions.UpdateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountServiceImpl implements AccountService {

    @Autowired
    JsonFileHandler handler;

    public Integer getBalance(Integer accountId) throws AccountNotFound {
        if (handler.getAccountsMap().containsKey(accountId)) {
            return handler.getAccountsMap().get(accountId).getBalance();
        } else {
            throw new AccountNotFound("No account found");
        }
    }

    public void addBalance(Integer accountId, Integer addAmount) throws AccountNotFound, UpdateException {
        if (handler.getAccountsMap().containsKey(accountId)) {
            Account acct = handler.getAccountsMap().get(accountId);
            acct.setBalance(getBalance(accountId) + addAmount);
            try {
                handler.updateAccount(acct);
            } catch (JsonReadException e) {
                System.err.println("Failed to read from file:" + e.getLocalizedMessage());
            } catch (JsonWriteException e) {
                System.err.println("Failed to write file:" + e.getLocalizedMessage());
            }
        } else {
            throw new AccountNotFound("No account found");
        }
    }

    public void subtractBalance(Integer accountId, Integer subtractAmount) throws AccountNotFound, UpdateException {
        if (handler.getAccountsMap().containsKey(accountId)) {
            Account acct = handler.getAccountsMap().get(accountId);
            acct.setBalance(getBalance(accountId) - subtractAmount);
            try {
                handler.updateAccount(acct);
            } catch (JsonReadException e) {
                System.err.println("Failed to read from file:" + e.getLocalizedMessage());
            } catch (JsonWriteException e) {
                System.err.println("Failed to write file:" + e.getLocalizedMessage());
            }
        } else {
            throw new AccountNotFound("No account found");
        }
    }

    @Override
    public Boolean checkAccount(Integer accountId) {
        return handler.getAccountsMap().containsKey(accountId);
    }
}
