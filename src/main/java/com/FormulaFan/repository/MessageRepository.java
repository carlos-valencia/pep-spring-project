package com.FormulaFan.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.FormulaFan.entity.Message;

/**
 * This class defines an interface to be used to connect the service layer and database, for the Message entity
 */
public interface MessageRepository extends JpaRepository<Message, Integer> {

    /**
     * Declares a method that will automatically implement a query to retrieve all messages with the same posted_by value
     * @param postedBy
     * @return List<Message> object
     */
    List<Message> findAllByPostedBy(int postedBy);
}
