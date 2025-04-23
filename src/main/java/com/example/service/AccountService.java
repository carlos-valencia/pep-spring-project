package com.example.service;

import com.example.entity.Account;
import com.example.exception.DuplicateAccountException;
import com.example.repository.AccountRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    /**
     * 
     * @param account
     * @return
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

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public Account getMatchingAccount(Account account) {
        return accountRepository.findByUsername(account.getUsername());
    }

    public Account getAccountById(int id) {
        Optional<Account> accountOptional = accountRepository.findById(id);
        if(accountOptional.isPresent()) {
            return accountOptional.get();
        }
        return null;
    }

    private boolean validAccount(Account account) {
        if(!account.getUsername().isEmpty() && account.getPassword().length() >= 4) {
            return true;
        }
        else {
            return false;
        }
    }
}
