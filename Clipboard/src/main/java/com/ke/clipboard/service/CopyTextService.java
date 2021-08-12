package com.ke.clipboard.service;

import com.ke.clipboard.model.CopyText;

import java.text.ParseException;
import java.util.List;

public interface CopyTextService {
     List<CopyText> find();
     void insert(String msg) throws ParseException;
     void deletePreviousData();
}
