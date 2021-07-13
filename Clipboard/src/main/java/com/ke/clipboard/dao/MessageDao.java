package com.ke.clipboard.dao;

import com.ke.clipboard.model.Message;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Mapper
@Repository("MessageDao")
public interface MessageDao {
    public List<Message> find();
    public boolean insert(String msg, Date addTime);
}
