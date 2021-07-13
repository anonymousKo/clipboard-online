package com.ke.clipboard.controller;

import com.ke.clipboard.dao.MessageDao;
import com.ke.clipboard.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/msg")
public class TextController {
    @Autowired
    MessageDao messageDao;

    @RequestMapping(value = "/test")
    public  boolean add(@RequestBody String message) {
        Date addTime = new Date();
        return(messageDao.insert(message,addTime));
    }
    @GetMapping("/findAll")
    public List<Message> findAll(){
        return messageDao.find();
    }

}
