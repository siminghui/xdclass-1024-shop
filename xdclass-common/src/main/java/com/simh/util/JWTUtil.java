package com.simh.util;

import com.simh.model.LoginUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.Objects;

/**
 * @Author: 十七
 * @Date: 2022/7/8 4:31 下午
 * @Description: jwt 工具类
 */
@Slf4j
public class JWTUtil {

    /**
     *  token过期时间，正常是7天，方便测试改为70
     */
    private static final long EXPIRE = 1000 * 60 * 60 * 24 * 70;

    /**
     * 秘钥
     */
    private static final String SECRET = "siminghui.net666";

    /**
     * 令牌前缀
     */
    private static final String TOKEN_PREFIX = "xdclass1024shop";

    /**
     * subject
     */
    private static final String SUBJECT = "xdclass";

    /**
     * 根据用户信息生成令牌
     * @param loginUser
     * @return
     */
    public static String geneJsonWebToken(LoginUser loginUser) {

        if (Objects.isNull(loginUser)) {
            throw new NullPointerException("loginUser对象为空");
        }

        String token = Jwts.builder()
                .setSubject(SUBJECT)
                .claim("head_img", loginUser.getHeadImg())
                .claim("id", loginUser.getId())
                .claim("name", loginUser.getName())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
                .signWith(SignatureAlgorithm.HS256, SECRET).compact();

        token += TOKEN_PREFIX;
        return token;

    }

    /**
     * 校验token的方法
     * @param token
     * @return
     */
    public static Claims checkJWT(String token) {

        try{

            final Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token.replace(TOKEN_PREFIX, "")).getBody();

            return claims;

        }catch (Exception e) {
            log.info("jwt-token解密失败");
            return null;
        }

    }

}
