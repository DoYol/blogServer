package com.dyledu.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;



import javax.servlet.http.HttpServletRequest;
import java.util.Date;


/**
 * JWT工具类
 */
public class JwtUtil {

    //有效期为
    public static final Long JWT_TTL = 24 * 60 * 60 * 1000L;// 60 * 60 *1000  一个小时
    //设置秘钥明文
    public static final String JWT_KEY = "yunLong";


    /**
     * 解析token
     * @param token
     * @return
     */
    public static String parseJWT(String token){
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getClaim("userID")
                .asString();
    }

    /* *
     * @Author qy
     * <p> 校验token是否正确 </p>
     * @Param token
     * @Param username
     * @Param secret
     * @Return boolean
     */
    public static boolean verify(String token, String userId) {
        try {
            // 设置加密算法
            Algorithm algorithm = Algorithm.HMAC256(JWT_KEY);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim("userID", userId)
                    .build();
            // 效验TOKEN
            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    /* *
     * @Author qy
     * <p>生成签名,30min后过期 </p>
     * @Param [username, secret]
     * @Return java.lang.String
     */
    public static String sign(String userID) {
        Date date = new Date(System.currentTimeMillis() + JWT_TTL);
        Algorithm algorithm = Algorithm.HMAC256(JWT_KEY);
        // 附带username信息
        return JWT.create()
                .withClaim("userID", userID)
                .withExpiresAt(date)
                .sign(algorithm);
    }

    /* *
     * @Author qy
     * <p> 获得用户ID </p>
     * @Param [request]
     * @Return java.lang.String
     */
    public static String getUserNameByToken(HttpServletRequest request) {
        String token = request.getHeader("token");
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getClaim("userID")
                .asString();
    }
}