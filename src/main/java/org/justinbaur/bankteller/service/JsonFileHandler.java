package org.justinbaur.bankteller.service;

import java.io.File;
import java.io.IOException;

import java.util.List;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.justinbaur.bankteller.domain.Account;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class JsonFileHandler {
   
   public static void writeJSON(Account account) throws JsonGenerationException, JsonMappingException, IOException{
      ObjectMapper mapper = new ObjectMapper();	
      mapper.writeValue(new File("src\\main\\resources\\accounts.json"), account);
   }

   public static Account readJSON() throws JsonParseException, JsonMappingException, IOException{
      ObjectMapper mapper = new ObjectMapper();
      Account account = mapper.readValue(new File("src\\main\\resources\\accounts.json"), Account.class);
      return account;
   }

   public static List<Account> readJSON_Array() throws JsonParseException, JsonMappingException, IOException{
      ObjectMapper mapper = new ObjectMapper();
      List<Account> account = mapper.readValue(new File("src\\main\\resources\\accounts.json"), new TypeReference<List<Account>>() {});
      return account;
   }
}