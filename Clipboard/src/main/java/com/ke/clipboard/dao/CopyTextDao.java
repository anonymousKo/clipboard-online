package com.ke.clipboard.dao;

import com.ke.clipboard.model.CopyText;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository("MessageDao")
public interface CopyTextDao {
    List<CopyText> find(int limitCount);
    void insert(String msg, String addTime);
    void deletePreviousData(String deleteDate);
}
