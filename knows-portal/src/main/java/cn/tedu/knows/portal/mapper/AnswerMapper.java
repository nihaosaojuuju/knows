package cn.tedu.knows.portal.mapper;

import cn.tedu.knows.portal.model.Answer;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* <p>
    *  Mapper 接口
    * </p>
*
* @author tedu.cn
* @since 2022-02-24
*/
@Repository
public interface AnswerMapper extends BaseMapper<Answer> {
    // Mybatis关联查询,实现查询指定问题的所有回答包含所有回答的评论
    // 它对应resources中mapper里AnswerMapper.xml中的同名的xml配置
    List<Answer> findAnswersByQuestionId(Integer questionId);
    //根据answerId修改采纳状态的方法
    @Update("update answer set accept_status=#{acceptStatus} where id=#{answerId}")
    int updateAcceptStatus(@Param("acceptStatus") Integer acceptStatus, @Param("answerId") Integer answerId );

}
