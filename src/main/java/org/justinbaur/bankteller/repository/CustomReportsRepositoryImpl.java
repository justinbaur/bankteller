package org.justinbaur.bankteller.repository;

import java.util.ArrayList;
import java.util.List;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.justinbaur.bankteller.domain.Profile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CustomReportsRepositoryImpl implements CustomReportsRepository {

    MongoClient client;

    MongoDatabase mongoDB;
    MongoCollection<Profile> accountsCollection;

    public CustomReportsRepositoryImpl(@Value("${mongodb.username}") String username,
            @Value("${mongodb.password}") String password) {

        ConnectionString connString2 = new ConnectionString("mongodb+srv://" + username + ":" + password
                + "@cluster0.gidim.mongodb.net/bankteller?retryWrites=true&w=majority");

        CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build());
        CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), pojoCodecRegistry);

        MongoClientSettings clientSettings = MongoClientSettings.builder().applyConnectionString(connString2)
                .codecRegistry(codecRegistry).build();

        client = MongoClients.create(clientSettings);
        mongoDB = client.getDatabase("bankteller");
        mongoDB.withCodecRegistry(pojoCodecRegistry);
        accountsCollection = mongoDB.getCollection("accounts", Profile.class);
    }

    public List<Profile> accountsByState(String state) {
        List<Profile> profileList = new ArrayList<Profile>();
        FindIterable<Profile> findIterable = accountsCollection.find(Filters.eq("customer.address.state", state));
        MongoCursor<Profile> cursor = findIterable.iterator();
        try {
            while (cursor.hasNext()) {
                profileList.add(cursor.next());
            }
        } finally {
            cursor.close();
        }
        return profileList;
    }
}
