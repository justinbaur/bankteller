package org.justinbaur.bankteller.repository;

import java.util.List;

import org.justinbaur.bankteller.domain.Profile;

public interface CustomReportsRepository {
    List<Profile> accountsByState(String state);
}
