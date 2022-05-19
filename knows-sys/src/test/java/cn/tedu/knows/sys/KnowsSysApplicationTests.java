package cn.tedu.knows.sys;

import cn.tedu.knows.commons.model.User;
import cn.tedu.knows.sys.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class KnowsSysApplicationTests {
    @Resource
    UserMapper userMapper;
    @Test
    void contextLoads() {
        User user = userMapper.findUserByUsername("st2");
        System.out.println(user);
    }

}
