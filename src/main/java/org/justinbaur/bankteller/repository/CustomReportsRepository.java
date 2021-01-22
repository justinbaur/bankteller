package org.justinbaur.bankteller.repository;

import java.util.List;

import org.justinbaur.bankteller.domain.Account;

public interface CustomReportsRepository {
    List<Account> accountsByState(String state);
}
