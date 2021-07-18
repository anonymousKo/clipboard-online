package com.ke.clipboard.dao;

import com.ke.clipboard.model.CopyText;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository("MessageDao")
public interface CopyTextDao {
    public List<CopyText> find();
    public void insert(String msg, String addTime);
}
