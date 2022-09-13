package cn.guanjm.common.vo;

import cn.guanjm.common.enums.ExceptionEnum;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ResponseResult<T> {
    private Integer code;
    private String msg;
    private T data;

    public ResponseResult(T data) {
        this.code = 200;
        this.msg = "success";
        this.data = data;
    }
}
