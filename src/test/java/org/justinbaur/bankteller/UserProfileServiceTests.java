package org.justinbaur.bankteller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.justinbaur.bankteller.domain.Account;
import org.justinbaur.bankteller.domain.Profile;
import org.justinbaur.bankteller.exception.AccountNotFound;
import org.justinbaur.bankteller.exception.InsufficientBalance;
import org.justinbaur.bankteller.exception.ProfileNotFound;
import org.justinbaur.bankteller.repository.ProfileRepository;
import org.justinbaur.bankteller.service.UserProfileServiceDatabaseImpl;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ExtendWith(MockitoExtension.class)
class UserProfileServiceTests {

	private static final Logger LOG = LoggerFactory.getLogger(UserProfileServiceTests.class);

	@InjectMocks
	UserProfileServiceDatabaseImpl service;

	@Mock
	ProfileRepository repository;

	@Mock
	Map<String, Account> testAccounts;

	@Mock
	Map<String, Profile> testProfiles;

	@BeforeEach
	void init() {
		testAccounts = TestUtils.getTestAccounts();
		testProfiles = TestUtils.getTestProfiles();
	}

	@Test
	void getBalanceHappyPath() throws AccountNotFound, ProfileNotFound {
		String profileId = "testProfile2";
		String accountName = "account1";
		Mockito.when(repository.existsById(profileId)).thenReturn(true);
		Mockito.when(repository.findById(profileId)).thenReturn(Optional.of(testProfiles.get(profileId)));
		int expectedBalance = 500;
		int actualBalance = service.getBalance(profileId, accountName);
		Assertions.assertEquals(expectedBalance, actualBalance);
	}

	
	@Test
	void getBalanceSadPath_AccountNotFound() throws AccountNotFound {
		String profileId = "testProfile1";
		String accountName = "account1";
		Mockito.when(repository.existsById(profileId)).thenReturn(true);
		Mockito.when(repository.findById(profileId)).thenReturn(Optional.of(testProfiles.get(profileId)));
		Exception exception = Assertions.assertThrows(AccountNotFound.class, () -> {
			service.getBalance(profileId, accountName);
		});
		String expectedMessage = "No account found.";
		String actualMessage = exception.getMessage();
		Assertions.assertTrue(actualMessage.equals(expectedMessage));
	}

	
	@Test
	void addBalanceHappyPath() throws ProfileNotFound, AccountNotFound {
		String profileId = "testProfile2";
		String accountName = "account1";
		Mockito.when(repository.existsById(profileId)).thenReturn(true);
		Mockito.when(repository.findById(profileId)).thenReturn(Optional.of(testProfiles.get(profileId)));
		int expectedBalance = 750;
		int addAmount = 250;
		service.addBalance(profileId, accountName, addAmount);
		Assertions.assertEquals(expectedBalance, service.getBalance(profileId, accountName));
		Mockito.verify(repository, Mockito.times(1)).save(Mockito.any(Profile.class));
	}
	
	
	@Test
	void addBalanceSadPath_AccountNotFound() {
		String profileId = "testProfile1";
		String accountName = "account1";
		Mockito.when(repository.existsById(profileId)).thenReturn(true);
		Mockito.when(repository.findById(profileId)).thenReturn(Optional.of(testProfiles.get(profileId)));
		int addAmount = 250;
		Exception exception = Assertions.assertThrows(AccountNotFound.class, () -> {
			service.addBalance(profileId, accountName, addAmount);
		});
		String expectedMessage = "No account found.";
		String actualMessage = exception.getMessage();
		Assertions.assertTrue(actualMessage.equals(expectedMessage));
	}

	
	@Test
	void subtractBalanceHappyPath() throws ProfileNotFound, AccountNotFound, InsufficientBalance {
		String profileId = "testProfile2";
		String accountName = "account1";
		Mockito.when(repository.existsById(profileId)).thenReturn(true);
		Mockito.when(repository.findById(profileId)).thenReturn(Optional.of(testProfiles.get(profileId)));
		int expectedBalance = 250;
		int subtractAmount = 250;
		service.subtractBalance(profileId, accountName, subtractAmount);
		Assertions.assertEquals(expectedBalance, service.getBalance(profileId, accountName));
		Mockito.verify(repository, Mockito.times(1)).save(Mockito.any(Profile.class));
	}

	@Test
	void subtractBalanceSadPath_AccountNotFound() {
		String profileId = "testProfile1";
		String accountName = "account1";
		Mockito.when(repository.existsById(profileId)).thenReturn(true);
		Mockito.when(repository.findById(profileId)).thenReturn(Optional.of(testProfiles.get(profileId)));
		int subtractAmount = 250;
		Exception exception = Assertions.assertThrows(AccountNotFound.class, () -> {
			service.addBalance(profileId, accountName, subtractAmount);
		});
		String expectedMessage = "No account found.";
		String actualMessage = exception.getMessage();
		Assertions.assertTrue(actualMessage.equals(expectedMessage));
	}

	@Test
	void subtractBalanceSadPath_InsufficientBalance() {
		String profileId = "testProfile2";
		String accountName = "account1";
		Mockito.when(repository.existsById(profileId)).thenReturn(true);
		Mockito.when(repository.findById(profileId)).thenReturn(Optional.of(testProfiles.get(profileId)));
		int subtractAmount = 15000;
		Exception exception = Assertions.assertThrows(InsufficientBalance.class, () -> {
			service.subtractBalance(profileId, accountName, subtractAmount);
		});
		String expectedMessage = "Insufficient balance.";
		String actualMessage = exception.getMessage();
		Assertions.assertTrue(actualMessage.equals(expectedMessage));
	}

	/*
	@Test
	void checkAccountHappyPath() {
		Mockito.when(handler.getAccountsMap()).thenReturn(TestUtils.getTestProfiles());
		int accountId = 1;
		Assertions.assertTrue(service.checkAccount(accountId));


	}

	/*
	@Test
	void checkAccountSadPath(){
		Mockito.when(handler.getAccountsMap()).thenReturn(TestUtils.getTestProfiles());
		int accountId = -1;
		Assertions.assertFalse(service.checkAccount(accountId));
	} */
}
