package cn.guanjm.web;

import cn.guanjm.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("apis")
public class UserController {

    @GetMapping("user")
    public ResponseEntity<User> getUserInfoByToken() {
        return null;
    }
}
