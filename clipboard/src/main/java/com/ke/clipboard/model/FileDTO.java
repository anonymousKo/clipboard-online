package com.ke.clipboard.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

// FileDTO class to represent file information
@RedisHash("files")
public class FileDTO {
    @Id
    private String id;
    private String name;

    public FileDTO(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        if (name.length() > 30) {
            return name.substring(0, 30) + "...";
        }
        return name;
    }

    public String getTrueName() {
        return name;
    }
}
