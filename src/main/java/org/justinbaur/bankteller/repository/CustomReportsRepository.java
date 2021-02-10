package org.justinbaur.bankteller.repository;

import java.util.List;

import org.justinbaur.bankteller.domain.Profile;

/**
 * Reports for database queries.
 */
public interface CustomReportsRepository {

    /**
     * Example method of querying database profiles and returning those matching a
     * given state.
     *
     * @param state String capitalized, 2-letter state abbrevation to filter
     *              profiles by
     * @return List of profiles matching the given state
     */
    List<Profile> accountsByState(String state);
}
