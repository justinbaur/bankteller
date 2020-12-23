package org.justinbaur.bankteller.service;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.justinbaur.bankteller.domain.Account;
import org.justinbaur.bankteller.exceptions.AccountNotFound;
import org.springframework.stereotype.Component;

@Component
public class AccountServiceInMemoryImpl implements AccountService {

    Map<Integer, Integer> accounts = new HashMap<>();

    public AccountServiceInMemoryImpl() {
        accounts.put(Integer.valueOf(1010), 0);
        accounts.put(Integer.valueOf(2020), 20000000);
    }

    public List<Integer> getAccountIds() {
        List<Integer> accountIds = new ArrayList<Integer>();

        System.out.println("Populating account IDs");
        try {
            List<Account> accountList = JsonFileHandler.readJSON_Array();
            System.out.println("here");

            for (Account acct : accountList){
                accountIds.add(acct.id);
            }

        } catch (JsonParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JsonMappingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.out.println(accountIds);
        return accountIds;
    }

    public void createAccount(Integer balance){
        Boolean accountCreated = false;
        Integer currentId = 1;

        System.out.println("Attempting to create a new account..");
        while(!accountCreated && currentId < 100){ //find the next unused ID
            try {
                if(getAccountIds().contains(currentId)){
                    System.out.println(String.format("Account with ID %s is already taken.", currentId));
                    continue;
                }
                Account acct = new Account(currentId, balance);
                JsonFileHandler.writeJSON(acct);

                accountCreated = true;
                System.out.println(String.format("Account with ID %s has been created.", currentId));
                return;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            currentId++;
        }
        System.out.println("Unable to create account.");
    }

    public void deleteAccount(Integer id){
        //check if account exists
        System.out.println(String.format("Account with ID %s has been deleted.", id));
    }

    public Integer getBalance(Integer accountId) throws AccountNotFound {
        if (accounts.containsKey(accountId)) {
            return accounts.get(accountId);
        }
        else {
            throw new AccountNotFound("No account found");
        }
    }

    public void addBalance(Integer accountId, Integer addAmount) throws AccountNotFound {
        if (accounts.containsKey(accountId)) {
            accounts.put(accountId, getBalance(accountId) + addAmount);
        }
        else{
            throw new AccountNotFound("No account found");
        }
    }

    public void subtractBalance(Integer accountId, Integer subtractAmount) throws AccountNotFound {
        if (accounts.containsKey(accountId)) {
            accounts.put(accountId, getBalance(accountId) - subtractAmount);
        }
        else{
            throw new AccountNotFound("No account found");
        }
    }

    @Override
    public Boolean checkAccount(Integer accountId) {
        return accounts.containsKey(accountId);
    }
}
