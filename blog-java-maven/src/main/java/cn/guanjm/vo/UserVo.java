package cn.guanjm.vo;

import lombok.Data;

@Data
public class UserVo {
    private Long id;
    private String nickname;
    private String username;
    private String email;
    private String avatar;
    private String token;
}
