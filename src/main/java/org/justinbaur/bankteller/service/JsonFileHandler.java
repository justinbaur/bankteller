package org.justinbaur.bankteller.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.justinbaur.bankteller.domain.Account;
import org.justinbaur.bankteller.exceptions.JsonReadException;
import org.justinbaur.bankteller.exceptions.JsonWriteException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JsonFileHandler {

   private static final Logger LOG = LoggerFactory.getLogger(JsonFileHandler.class);

   @Autowired
   ObjectMapper mapper;

   public Map<Integer, Account> getAccountsMap() {

      Map<Integer, Account> map = new HashMap<Integer, Account>();

      List<Account> accountList;

      try {
         accountList = readAccounts();

         for (Account acct : accountList) {
            map.put(acct.getId(), acct);
         }
      } catch (JsonReadException e) {
         LOG.error("Failed to get accounts map.");
      }

      return map;
   }

   public void writeAccounts(List<Account> accounts) throws JsonWriteException {
      try {
         sortAccounts(accounts);
         mapper.writeValue(new File("src\\main\\resources\\accounts.json"), accounts);
      } catch (IOException e) {
         LOG.error("Failed to write file:" + e.getLocalizedMessage());
         throw new JsonWriteException("Failed to write account.");
      }
   }

   public List<Account> readAccounts() throws JsonReadException {
      try {
         List<Account> accounts = new ArrayList<Account>(
               Arrays.asList(mapper.readValue(new File("src\\main\\resources\\accounts.json"), Account[].class)));
         sortAccounts(accounts);
         return accounts;
      } catch (IOException e) {
         LOG.error("Failed to read from file:" + e.getLocalizedMessage());
         throw new JsonReadException("Failed to read account.");
      }
   }

   public void updateAccount(Account acct) throws JsonReadException, JsonWriteException {
      List<Account> acctList = readAccounts();
      Map<Integer, Account> acctMap = getAccountsMap();

      acctMap.put(acct.getId(), acct);

      acctList = acctMap.values().stream().collect(Collectors.toList());

      writeAccounts(acctList);
   }

   public void sortAccounts(List<Account> accounts) {
      Comparator<Account> byId = Comparator.comparing(Account::getId);
      Collections.sort(accounts, byId);
   }

}