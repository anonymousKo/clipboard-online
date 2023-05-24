package com.ke.clipboard.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    public static String dateFormat(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(date);
    }
}
