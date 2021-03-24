package org.justinbaur.bankteller;

import java.util.Map;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;

import org.justinbaur.bankteller.domain.Account;
import org.justinbaur.bankteller.domain.Address;
import org.justinbaur.bankteller.domain.CustomerInfo;
import org.justinbaur.bankteller.domain.Profile;
import org.justinbaur.bankteller.util.AccountTypes;

public class TestUtils {

    public static Map<String, Account> getTestAccounts() {
        Map<String, Account> testAccountsMap = new HashMap<>();
        testAccountsMap.put("account1", new Account("account1", AccountTypes.CHECKING, 500));
        testAccountsMap.put("account2", new Account("account2", AccountTypes.SAVINGS, 1500));
        return testAccountsMap;
    }

    public static Map<String, Profile> getTestProfiles() {
        Map<String, Profile> testProfilesMap = new HashMap<>();
        testProfilesMap.put("testProfile1", new Profile(true, new Date(2021, 2, 17), new CustomerInfo("Admin", "Profile", 
            new Address("Street", "ST", "City", "Country", 00000)), Collections.emptyList()));
        testProfilesMap.put("testProfile2", new Profile(false, new Date(2021, 2, 18), new CustomerInfo("Test", "Profile", 
            new Address("E Test St", "FL", "Jacksonville", "United States", 12345)), new ArrayList<Account>(getTestAccounts().values())));
        return testProfilesMap;
    } 
}
