package cn.tedu.knows.faq;

import cn.tedu.knows.commons.model.Tag;
import cn.tedu.knows.faq.mapper.TagMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisAccessor;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class KnowsFaqApplicationTests {
    @Autowired
    TagMapper tagMapper;
    @Test
    void contextLoads() {
        List<Tag> tags = tagMapper.selectList(null);
        for(Tag tag : tags){
            System.out.println(tag);
        }
    }
    @Resource
    RedisTemplate<String,String> redisTemplate;
    @Test
    public void redis(){
        // 向redis中新增一个数据
        redisTemplate.opsForValue().set("myname", "周雪玲");
        System.out.println("添加成功");

    }
    @Test
    public void getValue(){
        // 从Redis中获得指定key的value
        String name = redisTemplate.opsForValue().get("myname");
        System.out.println(name);
    }
}
