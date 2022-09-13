package cn.guanjm.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class Tag {
    private Long id;
    private String name;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+0")
    private Date createTime;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+0")
    private Date updateTime;
    private Long userId;

}
