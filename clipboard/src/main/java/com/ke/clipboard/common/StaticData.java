package com.ke.clipboard.common;

import org.springframework.beans.factory.annotation.Value;

public class StaticData {

    private static int QUERY_COUNT;

    @Value("${queryCount}")
    public void setQueryCount(int queryCount) {
        QUERY_COUNT = queryCount;
    }

    public static int getQueryCount() {
        return QUERY_COUNT;
    }
}
