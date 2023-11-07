import cn.hutool.core.date.DateUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

/**
 * @author dlf
 * @date 2023/5/12 13:53
 */
public class JWTTest {
    public static void main(String[] args) {
        String token = generateTokenExpire();
        parseToken(token);
    }


    /**
     * 生成token
     */
    public static String generateToken() {
        String token;
        JwtBuilder builder = Jwts.builder().setId("888")
                .setSubject("小白")
                // 用于设置签发时间
                .setIssuedAt(new Date())
                //设置签名私钥
                .signWith(SignatureAlgorithm.HS256, "dinglongfei");
        token = builder.compact();
        System.err.println(token);
        return token;
    }

    /**
     * 解析token
     */
    public  static void parseToken(String token){
        Claims claims = Jwts.parser().setSigningKey("dinglongfei")
                .parseClaimsJws(token)
                .getBody();
        System.out.println("id:"+claims.getId());
        System.out.println("subject:"+claims.getSubject());
        System.out.println("签发时间:"+ DateUtil.date(claims.getIssuedAt()));
        System.out.println("过期时间:"+DateUtil.date(claims.getExpiration()));
        System.out.println("当前系统时间:"+DateUtil.date(new Date()));
    }

    /**
     * 生成带有过期时间得token
     */
    public static String generateTokenExpire() {
        //为了方便测试，我们将过期时间设置为1分钟
        long now = System.currentTimeMillis();
        //过期时间为1分钟
        long exp = now + 1000*60;
        String token;
        JwtBuilder builder = Jwts.builder().setId("888")
                .setSubject("小白")
                // 用于设置签发时间
                .setIssuedAt(new Date())
                //设置签名私钥
                .signWith(SignatureAlgorithm.HS256, "dinglongfei")
                //设置过期时间
                .setExpiration(new Date(exp))
                //冗余其他用户信息字段
                .claim("roles","admin");
        token = builder.compact();
        System.err.println(token);
        return token;
    }



}
