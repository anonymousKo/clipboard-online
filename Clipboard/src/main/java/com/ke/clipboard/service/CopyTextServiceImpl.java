package com.ke.clipboard.service;

import com.ke.clipboard.dao.CopyTextDao;
import com.ke.clipboard.model.CopyText;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CopyTextServiceImpl implements CopyTextService {
    @Autowired
    CopyTextDao copyTextDao;
    @Override
    public void insert(String msg){
        Date addTime = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formatDate = simpleDateFormat.format(addTime);
        copyTextDao.insert(msg, formatDate);
        log.info("add text: {}, date: {}, " ,msg, formatDate);
    }
    @Override
    public List<CopyText> find(){
        return copyTextDao.find().stream()
                .sorted(Comparator.comparing(CopyText::getAddTime).reversed())
                .collect(Collectors.toList());
    }

}
