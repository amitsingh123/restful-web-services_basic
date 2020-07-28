package com.amit.rest.restfulwebservices.hello;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class HelloResource {
    static private List<Message> messages = new ArrayList<>();
    static
    {
        messages.add(new Message("Amit"));
    }

    public List<Message> findAll() {
        return messages;
    }

    public  void save(Message message) {
        messages.add(message);
    }
}
