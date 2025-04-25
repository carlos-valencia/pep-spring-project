package com.example.service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

/**
 * This is a class that handles Message object operations.
 * Serves as a connection between the Controller and the Repository layers
 */
@Service
public class MessageService {
    
    MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    /**
     * Inserts Message object into database if message is valid
     * @param message
     * @return Message object
     */
    public Message insertMessage(Message message) {
        if(validateMessage(message)) {
            return messageRepository.save(message);
        }
        return null;
    }

    /**
     * Retrieves all messages from the database
     * @return List<Message> object
     */
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    /**
     * Retrieves a Message from the database if existing, or null if nonexistent
     * @param id
     * @return Message object
     */
    public Message getMessageById(int id) {
        Optional<Message> messageOptional = messageRepository.findById(id);
        if(messageOptional.isPresent()) {
            return messageOptional.get();
        }
        return null;
    }

    /**
     * Deletes a Message matching with the id from the database, if it exists, and signals whether operation was successful
     * @param id
     * @return boolean value (operation successful or not?)
     */
    public boolean deleteMessage(int id) {
        Optional<Message> message = messageRepository.findById(id);
        if(message.isPresent()) {
            messageRepository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Updates a message if new message text is valid and if message exists
     * @param id
     * @param message
     * @return Message object
     */
    public Message updateMessage(int id, Message message) {
        Optional<Message> messageOptional = messageRepository.findById(id);
        if(messageOptional.isPresent() && validateMessage(message)) {
            Message existingMessage = messageOptional.get();
            existingMessage.setMessageText(message.getMessageText());
            return messageRepository.save(existingMessage);
        }
        return null;
    }

    /**
     * Retrieves all messages posted by the same user
     * @param id
     * @return List<Message> object
     */
    public List<Message> getAllMessagesByUser(int id) {
        return messageRepository.findAllByPostedBy(id);
    }

    /**
     * Validates a message's text and returns whether it is valid or not
     * @param message
     * @return boolean (valid or not valid)
     */
    private boolean validateMessage(Message message) {
        if(!message.getMessageText().isEmpty() && message.getMessageText().length() < 256) {
            return true;
        }
        return false;
    }
}
