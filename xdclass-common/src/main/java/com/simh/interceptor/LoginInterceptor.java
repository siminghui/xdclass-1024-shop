package com.simh.interceptor;

import com.simh.enums.BizCodeEnum;
import com.simh.model.LoginUser;
import com.simh.util.CommonUtil;
import com.simh.util.JWTUtil;
import com.simh.util.JsonData;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * @Author: 十七
 * @Date: 2022/7/11 8:24 下午
 * @Description:
 */
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    public static ThreadLocal<LoginUser> threadLocal = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String accessToken = request.getHeader("token");
        if (Objects.isNull(accessToken)) {
            accessToken = request.getParameter("token");
        }

        // 不为空
        if (StringUtils.isNotBlank(accessToken)) {
            Claims claims = JWTUtil.checkJWT(accessToken);
            if (Objects.isNull(claims)) {
                // 未登录
                CommonUtil.sendJsonMessage(response, JsonData.buildResult(BizCodeEnum.ACCOUNT_UN_LOGIN));
                return false;
            }

            long userId = Long.parseLong(String.valueOf(claims.get("id")));
            String headImg = (String) claims.get("head_img");
            String name = (String) claims.get("name");
            String mail = (String) claims.get("mail");

            LoginUser loginUser = new LoginUser();
            loginUser.setId(userId);
            loginUser.setHeadImg(headImg);
            loginUser.setMail(mail);
            loginUser.setName(name);

            // 传递token（用户信息） 1.attribute 2.threadLocal
            //request.setAttribute("loginUser", loginUser);
            threadLocal.set(loginUser);



            return true;
        }

        CommonUtil.sendJsonMessage(response, JsonData.buildResult(BizCodeEnum.ACCOUNT_UN_LOGIN));
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
