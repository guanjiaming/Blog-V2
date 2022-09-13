package cn.guanjm.web;

import cn.guanjm.common.config.JwtProperties;
import cn.guanjm.common.enums.ExceptionEnum;
import cn.guanjm.common.exception.UmException;
import cn.guanjm.common.utils.CookieUtils;
import cn.guanjm.common.utils.JwtUtils;
import cn.guanjm.common.vo.ResponseResult;
import cn.guanjm.entity.User;
import cn.guanjm.entity.UserInfo;
import cn.guanjm.service.UserService;
import cn.guanjm.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
@RestController
@RequestMapping("/apis")
public class AuthController {

    @Autowired
    private JwtProperties prop;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<ResponseResult<UserVo>> login(
                                      @RequestParam("username") String username,
                                      @RequestParam("password") String password,
                                      HttpServletResponse response, HttpServletRequest request,
                                      HttpSession session) {

        try {
            User user = userService.queryUserByUsernameAndPassword(username, password);

            UserInfo userInfo = new UserInfo();
            userInfo.setId(user.getId());
            userInfo.setUsername(username);

            String token = JwtUtils.generateToken(userInfo, prop.getPrivateKey(), prop.getExpire());

            CookieUtils.newBuilder(response).httpOnly().request(request)
                    .build("UM_TOKEN", token);

            log.info("token---- {}", token);

            UserVo userVo = new UserVo();
            userVo.setId(user.getId());
            userVo.setUsername(user.getUsername());
            userVo.setAvatar(user.getAvatar());
            userVo.setNickname(user.getNickname());
            userVo.setEmail(user.getEmail());

            userVo.setToken(token);

            session.setAttribute("user", userInfo);

            ResponseResult<UserVo> userResponse = new ResponseResult<>(userVo);
            return ResponseEntity.ok(userResponse);
        } catch (Exception e) {
            log.error("[登录失败]", e);
            throw new UmException(ExceptionEnum.LOGIN_FAILED);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpSession session) {
        session.removeAttribute("user");
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/verify")
    public ResponseEntity<Void> verify() {

        return null;
    }
}
