package org.justinbaur.bankteller;

//import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.justinbaur.bankteller.exceptions.AccountNotFound;
import org.justinbaur.bankteller.service.AccountServiceImpl;
import org.justinbaur.bankteller.service.JsonFileHandler;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ExtendWith(MockitoExtension.class)
class AccountServiceTests {

	private static final Logger LOG = LoggerFactory.getLogger(AccountServiceTests.class);

	@Mock
	JsonFileHandler handler;

	@InjectMocks
	AccountServiceImpl service;

	@Test
	void getBalanceHappyPath() throws AccountNotFound {
		Mockito.when(handler.getAccountsMap()).thenReturn(TestUtils.getTestAccounts());
		try {
			int expectedBalance = 0;
			int actualBalance = service.getBalance(0);
			Assertions.assertEquals(expectedBalance, actualBalance);
		} catch (AccountNotFound e) {
			Assertions.fail("Account should have been found");
		}		
	}

}
