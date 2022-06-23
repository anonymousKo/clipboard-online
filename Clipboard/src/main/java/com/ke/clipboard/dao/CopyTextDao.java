package com.ke.clipboard.dao;

import com.ke.clipboard.model.CopyText;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Mapper
@Repository("MessageDao")
public interface CopyTextDao {
    List<CopyText> find(int limitCount);
    void insert(String msg, Date addTime);
    void deletePreviousData(String deleteDate);
    List<CopyText> query(String msg);
    CopyText queryById(Integer id);
    void update(CopyText copyText);
    List<CopyText> queryMarked();
}
