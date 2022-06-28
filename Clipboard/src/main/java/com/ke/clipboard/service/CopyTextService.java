package com.ke.clipboard.service;

import com.ke.clipboard.model.CopyText;

import java.text.ParseException;
import java.util.List;

public interface CopyTextService {
     List<CopyText> find(Integer count, boolean isOnlyMarked);
     void insert(String msg) throws ParseException;
     List<CopyText> query(String msg);
     void remark(Integer id);
}
