package cn.guanjm.common.advice;

import cn.guanjm.common.enums.ExceptionEnum;
import cn.guanjm.common.vo.ExceptionResult;
import cn.guanjm.common.exception.UmException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 统一异常处理
 */
@ControllerAdvice
public class CommonExceptionHandler {

    @ExceptionHandler(UmException.class)
    public ResponseEntity<ExceptionResult> handlerException(UmException e) {
        ExceptionEnum em = e.getExceptionEnum();
        return ResponseEntity.ok(new ExceptionResult(em));
//        return ResponseEntity.status(em.getCode()).body(new ExceptionResult(em));
    }
}
