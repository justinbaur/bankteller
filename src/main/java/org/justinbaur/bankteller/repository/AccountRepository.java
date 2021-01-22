package org.justinbaur.bankteller.repository;

import org.justinbaur.bankteller.domain.Account;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends MongoRepository<Account, String>, CustomReportsRepository{   

}
