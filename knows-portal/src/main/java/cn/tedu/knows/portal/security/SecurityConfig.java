package cn.tedu.knows.portal.security;

import cn.tedu.knows.portal.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

//这个注解意思是配置了spring框架的，加了这个注解的类就会被自动保存到Spring容器中
@Configuration
//启动Spring-Security配置的注解
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //只有添加了下面代码之后，我们编写的UserDetailsServiceImpl中的实现类才会被Spring-Security使用
        auth.userDetailsService(userDetailsService);
    }
    //设置页面权限的配置方法
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable() //设置禁用防跨域攻击
                .authorizeRequests() //开始设置页面的访问权限
                //指定路径
                .antMatchers(
                        "/css/*",
                        "/js/*",
                        "/img/**",
                        "/bower_components/**",
                        "/login.html",
                        "/register.html", //注册路径
                        "/register" //注册控制器路径

                ).permitAll() //上面设置的路径全部放行(不需要登陆就能访问)
                .anyRequest() //其他请求
                .authenticated() //需要登陆才能访问
                .and()          //是一个分割，表示上面的配置完毕，开始下面新的配置
                .formLogin() //使用单表登录
                .loginPage("/login.html") // 配置登录页为login.html
                .loginProcessingUrl("/login")// 配置登录表单提交信息的路径
                .failureUrl("/login.html?error") //配置登录失败时的页面
                .defaultSuccessUrl("/index.html")// 登录成功后的页面
                .and()
                .logout() //开始设置登录
                .logoutUrl("/logout") //设置登出路径
                .logoutSuccessUrl("/login.html?logout");// 设置登出成功后的页面

        //* defaultSuccessUrl设置的是默认登录成功的页面路径
        //  特指用户没有特定指向页面时登录成功才会显示的页面
        //  如果用户因为指向了特定页面而进入的登录页面,在登录成功之后
}

//  会显示用户指向的特定页面
    }