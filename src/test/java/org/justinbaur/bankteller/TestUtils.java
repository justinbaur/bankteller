package org.justinbaur.bankteller;

import java.util.HashMap;
import java.util.Map;

import org.justinbaur.bankteller.domain.Account;

public class TestUtils {
    
    public static Map<Integer, Account> getTestAccounts(){
        Map<Integer, Account> testAccountMap = new HashMap<>();
        testAccountMap.put(0, new Account(0, 0));
        return testAccountMap;
    }
}
