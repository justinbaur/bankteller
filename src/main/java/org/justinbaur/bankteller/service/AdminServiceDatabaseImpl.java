package org.justinbaur.bankteller.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.justinbaur.bankteller.domain.Account;
import org.justinbaur.bankteller.domain.Profile;
import org.justinbaur.bankteller.domain.Address;
import org.justinbaur.bankteller.domain.CustomerInfo;
import org.justinbaur.bankteller.exception.AccountNotFound;
import org.justinbaur.bankteller.exception.ProfileNotFound;
import org.justinbaur.bankteller.repository.ProfileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AdminServiceDatabaseImpl extends ProfileServiceImpl implements AdminService {

    private static final Logger LOG = LoggerFactory.getLogger(AdminServiceDatabaseImpl.class);

    ProfileRepository repository;

    public AdminServiceDatabaseImpl(@Autowired ProfileRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public void createProfile(Profile profile) {
        LOG.debug("Inserting profile into repository..");
        repository.insert(profile);
    }

    @Override
    public void createProfile(String firstName, String lastName, Address address) {

        Date accountCreated = new java.util.Date();
        CustomerInfo customer = new CustomerInfo(firstName, lastName, address);
        List<Account> accounts = new ArrayList<Account>();
        Boolean isAdmin = false;

        Profile profile = new Profile(isAdmin, accountCreated, customer, accounts);

        repository.insert(profile);
    }

    @Override
    public void updateProfile(Profile profile) {
        repository.save(profile);
    }

    @Override
    public void deleteProfile(String profileId) throws ProfileNotFound {
        Profile profile = getProfile(profileId);
        repository.delete(profile);
    }

    @Override
    public void createAccount(String profileId, Account account)
            throws ProfileNotFound {
        Profile profile = getProfile(profileId);
        List<Account> accountsList = profile.getAccounts();
        accountsList.add(account);
        profile.setAccounts(accountsList);
        repository.save(profile);
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
    public void updateAccount(String profileId, Account newAccount) throws ProfileNotFound, AccountNotFound {
        Profile profile = getProfile(profileId);
        List<Account> accountsList = profile.getAccounts();
        for(Account account : accountsList){
            if(account.getAccountName().equals(newAccount.getAccountName())){
                accountsList.remove(account);
                accountsList.add(newAccount);
                LOG.info("Account {} successfully updated.", newAccount.getAccountName());
                break;
            } else {
                throw new AccountNotFound("Account not found");
            }
        }
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
