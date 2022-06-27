package com.ke.clipboard.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "message")
public class CopyText implements Serializable {
    @Id
    private int id;

    @Column(length = 16383,nullable = false)
    private String msg;

    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date addTime;

    @Column(columnDefinition = "integer(1) not null default 0")
    private Integer isMarked;
}
