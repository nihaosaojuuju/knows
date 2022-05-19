package cn.tedu.knows.sys.service;


import cn.tedu.knows.commons.model.Permission;
import cn.tedu.knows.commons.model.Role;
import cn.tedu.knows.commons.model.User;
import cn.tedu.knows.sys.vo.RegisterVO;
import cn.tedu.knows.sys.vo.UserVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tedu.cn
 * @since 2022-02-24
 */
public interface IUserService extends IService<User> {
    //学生注册功能的业务逻辑层方法的声明
    void registerStudent(RegisterVO registerVO);

    //查询所有讲师的业务逻辑层方法声明
   List<User> getTeachers();

    // 查询所有讲师的Map的方法
    Map<String,User> getTeacherMap();

    //根据用户名查询用户信息面板的方法
    UserVO getUserVo(String username);

    // 根据用户名获得用户对象的方法
    User getUserByUsername(String username);

    // 根据用户id获得所有权限
    List<Permission> getPermissionsById(Integer id);

    // 根据用户id获得所有角色
    List<Role> getRoleById(Integer id);

}
