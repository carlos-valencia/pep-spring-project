package com.FormulaFan.controller;

import com.FormulaFan.entity.Account;
import com.FormulaFan.entity.Message;
import com.FormulaFan.exception.DuplicateAccountException;
import com.FormulaFan.service.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */

 @RestController
public class SocialMediaController {

    private AccountService accountService;
    private MessageService messageService;

    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService) {
        this.accountService = accountService;
        this.messageService = messageService;
    }

    
    /**
     * Handler for user registration, sets status and body based on valid details entered
     * @param account
     * @return ResponseEntity
     */
    @PostMapping("/register")
    public ResponseEntity registerUser(@RequestBody Account account) {
        try {
            Account addedAccount = accountService.insertAccount(account);
            if(addedAccount != null) {
                return ResponseEntity.status(200).body(addedAccount);
            }
            else {
                return ResponseEntity.status(400).body(null);
            }
        } catch(DuplicateAccountException e) {
            return ResponseEntity.status(409).body(null);
        }
        
    }

    /**
     * Handler for user login, sets status and body based on proper authorization
     * @param account
     * @return ResponseEntity
     */
    @PostMapping("/login")
    public ResponseEntity loginUser(@RequestBody Account account) {
        Account matchingAccount = accountService.getMatchingAccount(account);
        if(matchingAccount != null && account.getPassword().equals(matchingAccount.getPassword())) {
            return ResponseEntity.status(200).body(matchingAccount);
        }
        else {
            return ResponseEntity.status(401).body(null);
        }
        
    }

    /**
     * Sample handler to test app usage from web browser
     * @return ResponseEntity
     */
    @GetMapping("/login")
    public ResponseEntity sampleLogin() {
        return ResponseEntity.status(200).body("Hello World");
    }

    /**
     * Handler for creating message, sets status and body based on valid message and poster account
     * @param message
     * @return ResponseEntity
     */
    @PostMapping("/messages")
    public ResponseEntity createMessage(@RequestBody Message message) {
        
        Account poster = accountService.getAccountById(message.getPostedBy());

        if(poster != null) {
            Message createdMessage = messageService.insertMessage(message);
            if(createdMessage != null) {
                return ResponseEntity.status(200).body(createdMessage);
            }
            else {
                return ResponseEntity.status(400).body(null);
            }
        }
        else {
            return ResponseEntity.status(400).body(null);
        }
    }

    /**
     * Handler for retrieving all messages, will always set status to 200, can result in body of null if empty
     * @return ResponseEntity
     */
    @GetMapping("/messages")
    public ResponseEntity retrieveAllMessages() {
        List<Message> messages = messageService.getAllMessages();
        return ResponseEntity.status(200).body(messages);
    }

    /**
     * Handler for retrieving message by id, will always set status to 200, can result in body of null if nonexisting message
     * @param message_id
     * @return ResponseEntity
     */
    @GetMapping("/messages/{message_id}")
    public ResponseEntity retrieveMessageById(@PathVariable int message_id) {
        Message message = messageService.getMessageById(message_id);
        return ResponseEntity.status(200).body(message);
    }

    /**
     * Handler for deleting message, will always set status to 200, will set body based on success of operation
     * @param message_id
     * @return ResponseEntity
     */
    @DeleteMapping("/messages/{message_id}")
    public ResponseEntity deleteMessage(@PathVariable int message_id) {
        boolean success = messageService.deleteMessage(message_id);
        if(success) {
            return ResponseEntity.status(200).body(1);
        }
        else {
            return ResponseEntity.status(200).body(null);
        }
    }

    /**
     * Handler for updating message, will set status and body based on success of operation
     * @param message
     * @param messageId
     * @return ResponseEntity
     */
    @PatchMapping("/messages/{messageId}")
    public ResponseEntity updateMessage(@RequestBody Message message, @PathVariable int messageId) {
        Message updatedMessage = messageService.updateMessage(messageId, message);
        if(updatedMessage != null) {
            return ResponseEntity.status(200).body(1);
        }
        else {
            return ResponseEntity.status(400).body(null);
        }
    }

    /**
     * Handler for retrieving all messages by user, will always set status to 200, can result in body of null if empty
     * @param accountId
     * @return ResponseEntity
     */
    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity retrieveMessagesByUser(@PathVariable int accountId) {
        Account poster = accountService.getAccountById(accountId);
        if(poster != null) {
            List<Message> messages = messageService.getAllMessagesByUser(poster.getAccountId());
            return ResponseEntity.status(200).body(messages);
        }
        return ResponseEntity.status(200).body(null);
    }

    

}
