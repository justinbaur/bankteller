package org.justinbaur.bankteller.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.justinbaur.bankteller.domain.Account;
import org.justinbaur.bankteller.domain.Profile;
import org.justinbaur.bankteller.domain.Address;
import org.justinbaur.bankteller.domain.CustomerInfo;
import org.justinbaur.bankteller.exceptions.AccountNotFound;
import org.justinbaur.bankteller.exceptions.ProfileNotFound;
import org.justinbaur.bankteller.repository.ProfileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AdminServiceDatabaseImpl extends ProfileServiceImpl implements AdminService {

    private static final Logger LOG = LoggerFactory.getLogger(AdminServiceDatabaseImpl.class);

    @Autowired
    ProfileRepository repository;

    @Override
    public void createProfile(String firstName, String lastName, Address address) {

        String id = firstName + lastName;

        do {
            id += (int) (Math.random() * 10000);
        } while (repository.existsById(id));

        Date accountCreated = new java.util.Date();
        CustomerInfo customer = new CustomerInfo(firstName, lastName, address);
        List<Account> accounts = new ArrayList<Account>();
        Boolean isAdmin = false;

        Profile profile = new Profile(id, isAdmin, accountCreated, customer, accounts);

        repository.insert(profile);
    }

    @Override
    public void deleteProfile(String profileId) throws ProfileNotFound {
        Profile profile = getProfile(profileId);
        repository.delete(profile);
    }

    @Override
    public void createAccount(String profileId, String accountName, String accountType, Integer balance)
            throws ProfileNotFound {
        Profile profile = getProfile(profileId);
        Account account = new Account(accountName, accountType, balance);
        List<Account> accountsList = profile.getAccounts();
        accountsList.add(account);
        profile.setAccounts(accountsList);
        repository.save(profile);
    }

    @Override
    public void deleteAccount(String profileId, String accountName) throws ProfileNotFound, AccountNotFound {
        Profile profile = getProfile(profileId);
        List<Account> accounts = getProfile(profileId).getAccounts();
        for (Account account : accounts) {
            if (account.getAccountName().equals(accountName)) {
                accounts.remove(account);
                LOG.info("Account {} successfully deleted.", accountName);
                break;
            } else {
                throw new AccountNotFound("Account not found");
            }
        }
        profile.setAccounts(accounts);
        repository.save(profile);
    }

    @Override
    public void printReportByState(String state) {
        LOG.info(repository.accountsByState(state).toString());
    }

}
