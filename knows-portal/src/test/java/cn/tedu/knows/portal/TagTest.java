package cn.tedu.knows.portal;

import cn.tedu.knows.portal.mapper.TagMapper;
import cn.tedu.knows.portal.mapper.UserMapper;
import cn.tedu.knows.portal.model.Permission;
import cn.tedu.knows.portal.model.Tag;
import cn.tedu.knows.portal.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootTest
public class TagTest {
   @Autowired
    TagMapper tagMapper;
   @Test
    public void getAll(){
       List<Tag> list = tagMapper.selectList(null);
       for (Tag t : list){
           System.out.println(t);
       }
   }
   //// 密码加密操作
   PasswordEncoder encoder = new BCryptPasswordEncoder();
   @Test
    public void pwd(){
       String str = "123456";
       String pwd = encoder.encode(str);
       System.out.println(pwd);
   }

   @Test
    public void match(){
       boolean b = encoder.matches("123456",
               "$2a$10$RfZFfum9uKuGlgu/vgcGteb7Pkk8ZaVtP/XVTSiszY291Aufd67qq");
       System.out.println("验证结果为:"+b);
   }


   @Autowired
    UserMapper userMapper;
   @Test
   public void userTest(){
       //根据用户名获得用户信息
       User user = userMapper.findUserByUsername("tc2");
       //根据用户id查询用户权限
       List<Permission> list = userMapper.findUserPermissionsById(user.getId());
       System.out.println(user);
       for(Permission p: list){
           System.out.println(p);
       }
   }
}
