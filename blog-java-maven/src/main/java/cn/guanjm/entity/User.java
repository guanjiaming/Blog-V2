package cn.guanjm.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class User {
    private Long id;
    private String nickname;
    private String username;
    private String email;
    private String password;
    private String avatar;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+0")
    private Date createTime;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+0")
    private Date updateTime;
}
