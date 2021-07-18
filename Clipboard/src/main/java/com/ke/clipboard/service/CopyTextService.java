package com.ke.clipboard.service;

import com.ke.clipboard.model.CopyText;

import java.text.ParseException;
import java.util.List;

public interface CopyTextService {
    public List<CopyText> find();
    public void insert(String msg) throws ParseException;
}
