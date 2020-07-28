package com.amit.rest.restfulwebservices.hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/api/v1")
public class HelloController {

    @Autowired
    private HelloResource helloResource;

    @Autowired
    private MessageSource messageSource;

    @GetMapping(path = "/hello")
    public String hello() {
        return "Hello to String Rest Demo";
    }

    @GetMapping(path = "/hellomessage/{name}")
    public Message helloMessage(@PathVariable String name) {
        return new Message("hello to lombok "+name);
    }

    @GetMapping(path = "/messages")
    public List<Message> getAllMessage() {
        return helloResource.findAll();
    }

    @PostMapping(path ="/messages")
    public void saveMessage(@RequestBody Message message) {
         helloResource.save(message);
    }

    @GetMapping(path="/helloi18")
    public String helloI18Message() {
        return messageSource.getMessage("good.morning.message",null, LocaleContextHolder.getLocale());
    }
}
