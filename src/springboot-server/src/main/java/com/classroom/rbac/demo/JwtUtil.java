package com.classroom.rbac.demo;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {

    private static final String base64SecretBytes = Base64.getEncoder().encodeToString("secretKey".getBytes());
    private static final Key KEY = new SecretKeySpec(base64SecretBytes.getBytes(), SignatureAlgorithm.HS512.getJcaName());

    //生成token
    public static String generateToken(Integer userId) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        long expMillis = nowMillis + 3600000; // Token有效期，这里设置为1小时
        Date exp = new Date(expMillis);

        String token = Jwts.builder()
                .claim("userId", userId.toString()) // 将 userId 转换为字符串类型
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(SignatureAlgorithm.HS512, KEY)
                .compact();

        //System.out.println("生成的token是：" + token); //调试使用
        return token;

    }

    //验证token有效性
    public boolean validateToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(KEY).parseClaimsJws(token);
            // 如果没有抛出异常，表示验证通过
            return true;
        } catch (Exception e) {
            // 如果抛出异常，表示验证失败
            return false;
        }
    }

    //解析token，映射为userId的String类型字符串
    public static String getUserIdFromToken(String token) throws Exception {
        try {
            // System.out.println("拿到的token是：" + token);   //调试使用
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(KEY).parseClaimsJws(token);
            String userIdString = claimsJws.getBody().get("userId", String.class);
            if (userIdString == null) {
                throw new Exception("Token中未包含有效的userId");
            }
            return userIdString;
        } catch (Exception e) {
            System.out.println(e);
            throw new Exception("Token验证失败");
        }
    }
}
