package com.ke.clipboard.service;

import com.ke.clipboard.common.StaticData;
import com.ke.clipboard.dao.CopyTextDao;
import com.ke.clipboard.model.CopyText;
import com.ke.clipboard.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class CopyTextServiceImpl implements CopyTextService {
    @Autowired
    CopyTextDao copyTextDao;

    @Override
    @CacheEvict(value = "copyTextCache", allEntries = true)
    public int insert(String msg){
        CopyText copyText = new CopyText();
        copyText.setMsg(msg);
        copyText.setAddTime(new Date());
        copyTextDao.insert(copyText);
        log.info("add text: {} " ,msg);
        return copyText.getId();
    }

    @Override
    @Cacheable(value = "copyTextCache",
            key = "'find:' + #count + ':' + #isOnlyMarked")
    public List<CopyText> find(Integer count, boolean isOnlyMarked){
        if (!isOnlyMarked){
            if (count == null){
                count = StaticData.getQueryCount();
            }
            return copyTextDao.find(count);
        }
        return copyTextDao.queryMarked(count);
    }

    public String determineCacheName(Integer count) {
        if (count ==null || count == StaticData.getQueryCount()) {
            return "copyTextCache_default";  // 特定条件下使用不同的缓存区域
        }
        return "copyTextCache";  // 默认缓存区域
    }

//    @Scheduled(cron = "1-59 0-2 * * * ")
    private void deletePreviousData(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH,-7);
        String deleteDate =DateUtil.dateFormat(calendar.getTime());
        copyTextDao.deletePreviousData(deleteDate);
        log.info("Delete data before {} ",deleteDate);
    }

    @Override
    public List<CopyText> query(String msg){
        return copyTextDao.query(msg);
    }

    @Override
    public void remark(Integer id) {
        if (id == null) {

        }
        CopyText copyText = copyTextDao.queryById(id);
        if (copyText == null ){

        }
        copyText.setIsMarked((copyText.getIsMarked() + 1)%2);
        copyTextDao.update(copyText);
    }
}
