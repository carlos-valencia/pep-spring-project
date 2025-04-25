package com.example.service;

import com.example.entity.Account;
import com.example.exception.DuplicateAccountException;
import com.example.repository.AccountRepository;

import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 * This is a class that handles Account object operations.
 * Serves as a connection between the Controller and the Repository layers
 */
@Service
public class AccountService {
    AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    /**
     * Inserts Account object into database if username is not taken, and if input is valid
     * @param account
     * @return Account object
     */
    public Account insertAccount(Account account) throws DuplicateAccountException{
        Account matchingAccount = getMatchingAccount(account);

        if(matchingAccount != null) {
            throw new DuplicateAccountException("Account already exists");
        }

        if(validAccount(account)) {
            return accountRepository.save(account);
        }

        return null;
    }

    /**
     * Retrieves matching account, searching by username
     * @param account
     * @return Account object
     */
    public Account getMatchingAccount(Account account) {
        return accountRepository.findByUsername(account.getUsername());
    }

    /**
     * Retrieves an Account from the databse if existing, or null if nonexistent
     * @param id
     * @return Account object
     */
    public Account getAccountById(int id) {
        Optional<Account> accountOptional = accountRepository.findById(id);
        if(accountOptional.isPresent()) {
            return accountOptional.get();
        }
        return null;
    }

    /**
     * Validates an account's username not being empty and password length
     * @param account
     * @return boolean (valid or not valid)
     */
    private boolean validAccount(Account account) {
        if(!account.getUsername().isEmpty() && account.getPassword().length() >= 4) {
            return true;
        }
        else {
            return false;
        }
    }
}
