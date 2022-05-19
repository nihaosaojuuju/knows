package cn.tedu.knows.portal;

import cn.tedu.knows.portal.mapper.AnswerMapper;
import cn.tedu.knows.portal.model.Answer;
import com.baomidou.mybatisplus.annotation.TableField;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
@SpringBootTest
public class testAnswer {
   @Autowired
    AnswerMapper answerMapper;
   @Test
    public void testAnswer(){
       List<Answer> answers = answerMapper.findAnswersByQuestionId(149);
       for(Answer a:answers){
           System.out.println(a);
       }
   }
}
