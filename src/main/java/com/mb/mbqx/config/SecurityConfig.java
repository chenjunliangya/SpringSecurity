package com.mb.mbqx.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

/**
 * @description: Security配置类
 * @author chenjunliang
 * @date 2022/6/2 17:49
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    /**
     * 数据源
     */
    private final DataSource dataSource;
    /**
     * 未登录拦截
     */
    private final MyAuthenticationExceptionHandler authenticationExceptionHandler;
    /**
     * 登录成功拦截
     */
    private final MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;
    /**
     * 登录失败拦截
     */
    private final MyAuthenticationFailureHandler myAuthenticationFailureHandler;

    public SecurityConfig(MyAuthenticationExceptionHandler authenticationExceptionHandler, DataSource dataSource, MyAuthenticationSuccessHandler myAuthenticationSuccessHandler, MyAuthenticationFailureHandler myAuthenticationFailureHandler, UserDetailsService userDetailsService) {
        this.authenticationExceptionHandler = authenticationExceptionHandler;
        this.dataSource = dataSource;
        this.myAuthenticationSuccessHandler = myAuthenticationSuccessHandler;
        this.myAuthenticationFailureHandler = myAuthenticationFailureHandler;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository(){
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
//        jdbcTokenRepository.setCreateTableOnStartup(true);//自动创建存储cookie表
        return jdbcTokenRepository;
    }

    /**
     * 配置用户不存在时可抛出异常
     */
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setHideUserNotFoundExceptions(false);//将这个异常设置false，这样就可以抛出用户不存在的异常
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());
        return daoAuthenticationProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(password());
        auth.authenticationProvider(daoAuthenticationProvider());//配置DaoAuthenticationProvider
    }

    @Bean
    PasswordEncoder password(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //用户退出
        http.logout().logoutUrl("/logout").logoutSuccessUrl("/login.html").permitAll();
        //设置未登录时候返回数据
        http.exceptionHandling().authenticationEntryPoint(authenticationExceptionHandler);
        http.formLogin()
                .loginProcessingUrl("/user/login").permitAll()//登录访问路径
                //登录成功拦截
                .successHandler(myAuthenticationSuccessHandler)
                //登录失败拦截
                .failureHandler(myAuthenticationFailureHandler)
                .and().authorizeRequests()
                //设置不需要认证可直接访问页面
                .antMatchers("/",
                        "/user/login",
                        "/login.html"
                ).permitAll()
                .anyRequest().authenticated()//所有请求都可以访问
                //配置自动登录--开始
                .and().rememberMe().tokenRepository(persistentTokenRepository())
                .tokenValiditySeconds(3600)//设置有效时常,单位秒
                .userDetailsService(userDetailsService)
                //配置自动登录--结束
                .and().csrf().disable();//关闭csrf防护
    }

}
