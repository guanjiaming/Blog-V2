package cn.guanjm.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class Article {

    private Long id;
    private String title;
    private String content;
    private Integer views;
    private Boolean isRecommend;
    private Boolean isPublish;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+0")
    private Date createTime;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+0")
    private Date updateTime;
    private Long UserId;
}
