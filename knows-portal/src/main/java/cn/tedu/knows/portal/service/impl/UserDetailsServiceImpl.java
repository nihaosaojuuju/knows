package cn.tedu.knows.portal.service.impl;

import cn.tedu.knows.portal.mapper.UserMapper;
import cn.tedu.knows.portal.model.Permission;
import cn.tedu.knows.portal.model.Role;
import cn.tedu.knows.portal.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

//添加注解保存到Spring容器中，以方便需要的位置调用
@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //方法的参数是要登录的用户的用户名username
        //1. 根据用户名获得用户对象
        User user=userMapper.findUserByUsername(username);
        //2. 验证用户对象是不是为空,如果为空登录失败
       if(user==null){
           return null;
       }
        //3. 根据用户id查询出用户的所有权限
        List<Permission> permissions = userMapper.findUserPermissionsById(user.getId());

        //4. 将权限的List集合转换为String类型数组
        String[] autu = new String[permissions.size()];
        int i=0;
        for(Permission p: permissions){
            autu[i]=p.getName();
            i++;
        }
        // 根据用户id查询用户所有角色
        List<Role> roles = userMapper.findUserRoleById(user.getId());
        // 数组扩容,以保证能够保存新的角色信息
        autu = Arrays.copyOf(autu,autu.length+roles.size());
        // {"/add","/delete","/save","/update",null}
        // 将角色信息保存在auth数组中
        for(Role role : roles){
            autu[i]=role.getName();
            i++;
        }
        //5. 创建UserDetails对象,并向它的属性中赋值
        UserDetails details= org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(autu)
                //设置当前账号是否锁定，false表示不锁定
                .accountLocked(user.getLocked()==1)
                //设置当前账号可用，false表示可用
                .disabled(user.getEnabled()==0)
                .build();

        //6. 返回UserDetails对象
        return details;
    }
}
