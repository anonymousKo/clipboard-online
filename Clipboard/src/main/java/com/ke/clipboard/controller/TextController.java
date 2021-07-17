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
public class TextController {
    @Autowired
    MessageDao messageDao;

    @RequestMapping(value = "/add")
    public  List<Message> add(@RequestBody String message) {
        Date addTime = new Date();
        messageDao.insert(message,addTime);
        return (messageDao.find());
    }
    @GetMapping("/findall")
    public List<Message> findAll(){
        return messageDao.find();
    }

}
