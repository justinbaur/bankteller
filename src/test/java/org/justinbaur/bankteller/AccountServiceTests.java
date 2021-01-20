package org.justinbaur.bankteller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.justinbaur.bankteller.domain.Account;
import org.justinbaur.bankteller.exceptions.AccountNotFound;
import org.justinbaur.bankteller.exceptions.JsonReadException;
import org.justinbaur.bankteller.exceptions.JsonWriteException;
import org.justinbaur.bankteller.exceptions.UpdateException;
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
			int accountId = 0;
			int expectedBalance = 0;
			int actualBalance = service.getBalance(accountId);
			Assertions.assertEquals(expectedBalance, actualBalance);
		} catch (AccountNotFound e) {
			Assertions.fail("Account should have been found");
		}
	}

	@Test
	void getBalanceSadPath_AccountNotFound() throws AccountNotFound {
		Mockito.when(handler.getAccountsMap()).thenReturn(TestUtils.getTestAccounts());
		int accountId = -1;
		Exception exception = Assertions.assertThrows(AccountNotFound.class, () -> {
			service.getBalance(accountId);
		});
		String expectedMessage = "No account found";
		String actualMessage = exception.getMessage();
		Assertions.assertTrue(actualMessage.equals(expectedMessage));
	}

	@Test
	void addBalanceHappyPath() throws AccountNotFound, UpdateException, JsonReadException, JsonWriteException {
		Mockito.when(handler.getAccountsMap()).thenReturn(TestUtils.getTestAccounts());
		int accountId = 1;
		int expectedBalance = 250;
		int addAmount = 150;
		service.addBalance(accountId, addAmount);
		Assertions.assertEquals(expectedBalance, service.getBalance(accountId));
		Mockito.verify(handler, Mockito.times(1)).updateAccount(Mockito.any(Account.class));
	}

	@Test
	void addBalanceSadPath_AccountNotFound() {
		Mockito.when(handler.getAccountsMap()).thenReturn(TestUtils.getTestAccounts());
		int accountId = -1;
		int addAmount = 150;
		Exception exception = Assertions.assertThrows(AccountNotFound.class, () -> {
			service.addBalance(accountId, addAmount);
		});
		String expectedMessage = "No account found";
		String actualMessage = exception.getMessage();
		Assertions.assertTrue(actualMessage.equals(expectedMessage));
	}

	@Test
	void subtractBalanceHappyPath() throws AccountNotFound, UpdateException, JsonReadException, JsonWriteException {
		Mockito.when(handler.getAccountsMap()).thenReturn(TestUtils.getTestAccounts());
		int accountId = 1;
		int expectedBalance = 5;
		int subtractAmount = 95;
		service.subtractBalance(accountId, subtractAmount);
		Assertions.assertEquals(expectedBalance, service.getBalance(accountId));
		Mockito.verify(handler, Mockito.times(1)).updateAccount(Mockito.any(Account.class));
	}

	@Test
	void subtractBalanceSadPath_AccountNotFound() {
		Mockito.when(handler.getAccountsMap()).thenReturn(TestUtils.getTestAccounts());
		int accountId = -1;
		int subtractAmount = 95;
		Exception exception = Assertions.assertThrows(AccountNotFound.class, () -> {
			service.subtractBalance(accountId, subtractAmount);
		});
		String expectedMessage = "No account found";
		String actualMessage = exception.getMessage();
		Assertions.assertTrue(actualMessage.equals(expectedMessage));
	}

	@Test
	void checkAccountHappyPath() {
		Mockito.when(handler.getAccountsMap()).thenReturn(TestUtils.getTestAccounts());
		int accountId = 1;
		Assertions.assertTrue(service.checkAccount(accountId));
	}

	@Test
	void checkAccountSadPath(){
		Mockito.when(handler.getAccountsMap()).thenReturn(TestUtils.getTestAccounts());
		int accountId = -1;
		Assertions.assertFalse(service.checkAccount(accountId));
	}
}
