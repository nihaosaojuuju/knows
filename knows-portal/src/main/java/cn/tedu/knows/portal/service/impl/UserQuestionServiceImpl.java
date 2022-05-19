package cn.tedu.knows.portal.service.impl;

import cn.tedu.knows.portal.service.IUserQuestionService;
import cn.tedu.knows.portal.model.UserQuestion;
import cn.tedu.knows.portal.mapper.UserQuestionMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author tedu.cn
 * @since 2022-02-24
 */
@Service
public class UserQuestionServiceImpl extends ServiceImpl<UserQuestionMapper, UserQuestion> implements IUserQuestionService {

}
