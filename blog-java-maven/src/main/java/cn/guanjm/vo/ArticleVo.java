package cn.guanjm.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ArticleVo {
    private Long id;
    private String title;
    private String content;
    private List<Long> tagIds;
    private Integer views;
    private Boolean isRecommend;
    private Boolean isPublish;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+0")
    private Date createTime;
}
