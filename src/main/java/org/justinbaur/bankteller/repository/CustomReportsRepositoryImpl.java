package org.justinbaur.bankteller.repository;

import java.util.List;

import com.mongodb.client.MongoClient;

import org.justinbaur.bankteller.domain.Account;
import org.springframework.beans.factory.annotation.Autowired;

public class CustomReportsRepositoryImpl implements CustomReportsRepository {
    
    @Autowired
    MongoClient client;
    
    /*
    *	MongoDatabase mongoDB = mongoClient.getDatabase("bankteller");
    *   MongoCollection<Document> collection = mongoDB.getCollection("accounts");
    */
    public List<Account> accountsByState(String state){
        
        return null;
    }
}
