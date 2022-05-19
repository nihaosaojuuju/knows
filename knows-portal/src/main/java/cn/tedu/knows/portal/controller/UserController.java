package cn.tedu.knows.portal.controller;


import cn.tedu.knows.portal.model.User;
import cn.tedu.knows.portal.service.IUserService;
import cn.tedu.knows.portal.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author tedu.cn
 * @since 2022-02-24
 */
@RestController
@RequestMapping("/v1/users")
public class UserController {
    @Autowired
    private IUserService userService;

    @GetMapping("/master")
    public List<User> master(){
        //调用业务逻辑层的方法获得查询出来的所有讲师并返回
        List<User> users = userService.getTeachers();
        return users;
    }

    @GetMapping("/me")
    public UserVO me(@AuthenticationPrincipal UserDetails user){
        //将当前登录用户的用户名传到getUserVo中当作参数赋值给userVo返回给客户端
        UserVO userVO = userService.getUserVo(user.getUsername());

        System.out.println(userVO);
        return userVO;
    }
}
