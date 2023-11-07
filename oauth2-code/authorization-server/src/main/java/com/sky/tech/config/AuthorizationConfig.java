package com.sky.tech.config;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**授权服务器的配置
 * @author dlf
 * @date 2023/5/29 17:36
 */
@Configuration
@EnableAuthorizationServer //开启认证服务
@AllArgsConstructor
@NoArgsConstructor
public class AuthorizationConfig extends AuthorizationServerConfigurerAdapter {



    @Resource
    private UserDetailsService userDetailsService;

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private PasswordEncoder passwordEncoder;


    @Resource
    @Qualifier("jwtTokenStore")
    private TokenStore tokenStore;
    @Resource
    private JwtAccessTokenConverter jwtAccessTokenConverter;
    @Resource
    private JwtTokenEnhancer jwtTokenEnhancer;

    /**
     * 配置客户端详情信息(谁来申请令牌)
     * 用来配置客户端详情服务（ClientDetailsService），客户端详情信息在
     * 这里进行初始化，你能够把客户端详情信息写死在这里或者是通过数据库来存储调取详情信息。
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

        System.err.println("password_client:"+passwordEncoder.encode("client-secret"));

        System.err.println("password_123456:"+passwordEncoder.encode("123456"));

        /**
         * 使用内存得方式
         */
        clients.inMemory()
                .withClient("client-a")//客户端 client_id
                .secret(passwordEncoder.encode("client-secret"))//客户端 secret
                .authorizedGrantTypes("authorization_code","password")//授权类型，授权码/密码
                //配置token的有效期
                .accessTokenValiditySeconds(3600)
                //配置刷新token的有效期
                .refreshTokenValiditySeconds(864000)
                .scopes("app-read") //作用域
                .resourceIds("resource1") //资源id
                .redirectUris("http://localhost:9001/callback");//重定向地址




            // 如果有多个用户可以继续配置
    }

    //检查token的策略
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        //资源服务器认证时使用
        security
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("permitAll()");

        //允许表单认证
        security.allowFormAuthenticationForClients();
    }


    /**
     * 使用密码模式需要配置
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
        List<TokenEnhancer> delegates = new ArrayList<>();
        //配置JWT的内容增强器
        delegates.add(jwtTokenEnhancer);
        delegates.add(jwtAccessTokenConverter);
        enhancerChain.setTokenEnhancers(delegates);
        //配置端点
        endpoints.authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService)
                //配置存储令牌策略
                .tokenStore(tokenStore)
                .accessTokenConverter(jwtAccessTokenConverter)
                .tokenEnhancer(enhancerChain);
    }
}
