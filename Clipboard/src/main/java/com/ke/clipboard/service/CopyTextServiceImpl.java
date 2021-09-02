package com.ke.clipboard.service;

import com.ke.clipboard.dao.CopyTextDao;
import com.ke.clipboard.model.CopyText;
import com.ke.clipboard.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Calendar;
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
        String formatDate=DateUtil.dateFormat(new Date());
        copyTextDao.insert(msg, formatDate);
        log.info("add text: {}, date: {}, " ,msg, formatDate);
    }
    @Override
    public List<CopyText> find(){
        return copyTextDao.find().stream()
                .sorted(Comparator.comparing(CopyText::getAddTime).reversed())
                .limit(15).collect(Collectors.toList());
    }

    @Override
    @Scheduled(cron = "1-59 0-2 * * * ")
    public void deletePreviousData(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH,-7);
        String deleteDate =DateUtil.dateFormat(calendar.getTime());
        copyTextDao.deletePreviousData(deleteDate);
        log.info("Delete data before {} ",deleteDate);
    }
}
