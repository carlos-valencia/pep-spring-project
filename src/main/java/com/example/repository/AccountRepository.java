package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.entity.Account;

/**
 * This class defines an interface to be used to connect the service layer and database, for the Account entity
 */
public interface AccountRepository extends JpaRepository<Account, Integer> {

    /**
     * Declares a method that will automatically implement a query to find an account that matches the username, used for finding duplicate accounts when registering
     * @param username
     * @return Account object
     */
    Account findByUsername(String username);
}
