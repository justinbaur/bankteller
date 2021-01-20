package org.justinbaur.bankteller;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.justinbaur.bankteller.domain.Account;
import org.justinbaur.bankteller.exceptions.JsonWriteException;
import org.justinbaur.bankteller.service.AdminServiceImpl;
import org.justinbaur.bankteller.service.JsonFileHandler;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ExtendWith(MockitoExtension.class)
public class AdminServiceTests {
	private static final Logger LOG = LoggerFactory.getLogger(AdminServiceTests.class);

	@Mock
	JsonFileHandler handler;

	@InjectMocks
	AdminServiceImpl service;

	Map<Integer, Account> testAccountMap = new HashMap<>();

	@BeforeEach
	void init() {
		testAccountMap.put(0, new Account(0, 0));
		testAccountMap.put(1, new Account(1, 100));
	}

	@Test
	void createAccountHappyPath() throws JsonWriteException {
		Mockito.when(handler.getAccountsMap()).thenReturn(testAccountMap);
		Mockito.doNothing().when(handler).writeAccounts(Mockito.anyList());
        int balance = 50;  
		service.createAccount(balance);
        Mockito.verify(handler, Mockito.times(1)).writeAccounts(Mockito.anyList());
    }

	@Test
	void deleteAccountHappyPath() throws JsonWriteException {
		Mockito.when(handler.getAccountsMap()).thenReturn(testAccountMap);
		Mockito.doNothing().when(handler).writeAccounts(Mockito.anyList());
		Integer id = 1;
		service.deleteAccount(id);
        Mockito.verify(handler, Mockito.times(1)).writeAccounts(Mockito.anyList());
	}

	@Test
	void deleteAccountSadPath() throws JsonWriteException {
		Mockito.when(handler.getAccountsMap()).thenReturn(testAccountMap);
		Integer id = 5;
		service.deleteAccount(id);
		Assertions.assertFalse(TestUtils.getTestAccounts().containsKey(id));
    }
    
    
	@Test
	void findAvailableAccountIDHappyPath() {
        Mockito.when(handler.getAccountsMap()).thenReturn(testAccountMap);
		Integer idCheck = service.findAvailableAccountID(500, 501);
        Assertions.assertEquals(500, idCheck);
	}

	@Test
	void findAvailableAccountIDSadPath(){
        Mockito.when(handler.getAccountsMap()).thenReturn(TestUtils.getTestAccounts());
        Integer idCheck = service.findAvailableAccountID(2, 10);
        Assertions.assertEquals(2, idCheck);
    }
}

