package com.example.service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
    
    
    MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public Message insertMessage(Message message) {
        if(validateMessage(message)) {
            return messageRepository.save(message);
        }
        return null;
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Message getMessageById(int id) {
        Optional<Message> messageOptional = messageRepository.findById(id);
        if(messageOptional.isPresent()) {
            return messageOptional.get();
        }
        return null;
    }

    public boolean deleteMessage(int id) {
        Optional<Message> message = messageRepository.findById(id);
        if(message.isPresent()) {
            messageRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Message updateMessage(int id, Message message) {
        Optional<Message> messageOptional = messageRepository.findById(id);
        if(messageOptional.isPresent() && validateMessage(message)) {
            Message existingMessage = messageOptional.get();
            existingMessage.setMessageText(message.getMessageText());
            return messageRepository.save(existingMessage);
        }
        return null;
    }

    public List<Message> getAllMessagesByUser(int id) {
        return messageRepository.findAllByPostedBy(id);
    }

    private boolean validateMessage(Message message) {
        if(!message.getMessageText().isEmpty() && message.getMessageText().length() < 256) {
            return true;
        }
        return false;
    }
}
