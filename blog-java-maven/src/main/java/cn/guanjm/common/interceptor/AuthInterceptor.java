package cn.guanjm.common.interceptor;

import cn.guanjm.common.config.JwtProperties;
import cn.guanjm.common.utils.JwtUtils;
import cn.guanjm.entity.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class AuthInterceptor implements HandlerInterceptor {

    private JwtProperties prop;

    private static final ThreadLocal<UserInfo> tl = new ThreadLocal<>();

    /**
     * 构造函数
     *
     * @param prop jwt属性
     */
    public AuthInterceptor(JwtProperties prop) {
        this.prop = prop;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Object user = request.getSession().getAttribute("user");
        // 获取cookie中的token
        // String token = CookieUtils.getCookieValue(request, prop.getCookieName());
        String token = request.getHeader("token");
        log.info("token: {}", token);
        try {
            //解析token
            UserInfo userInfo = JwtUtils.getInfoFromToken(token, prop.getPublicKey());

            //传递user
            tl.set(userInfo);

            //放行
            return true;
        } catch (Exception e) {
            log.error("解析用户身份失败。", e);
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //最后用完数据，一定要清空
        tl.remove();
    }

    public static UserInfo getUser() {
        return tl.get();
    }

}