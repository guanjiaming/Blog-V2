package cn.guanjm.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ExceptionEnum {
    LOGIN_FAILED(400, "登录失败，用户名或密码错误"),
    ARTICLE_NOT_FOUND(404, "博客不存在"),
    TOKEN_INVALID(500, "登录已过期"),
    TAG_NOTFOUND(404, "标签不存在"),
    TAG_INSERT_FAILED(500, "标签创建失败"),
    TAG_NAME_EXIST(500, "标签名已存在"),
    TAG_UPDATE_FAILED(500, "标签修改失败"),
    USER_NOT_FOUND(404, "用户不存在"),
    ARTICLE_SAVE_ERROR(500, "新增文章失败"),
    ARTICLE_UPDATE_ERROR(500, "更新文章失败"),
    ARTICLE_DELETE_ERROR(500, "删除文章失败"),
    INVALID_FILE_TYPE(500, "文件类型错误")
    ;
    private int code;
    private String msg;
}
