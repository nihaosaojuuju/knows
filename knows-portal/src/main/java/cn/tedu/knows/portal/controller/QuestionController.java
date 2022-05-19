package cn.tedu.knows.portal.controller;


import cn.tedu.knows.portal.exception.ServiceException;
import cn.tedu.knows.portal.model.Question;
import cn.tedu.knows.portal.service.IQuestionService;
import cn.tedu.knows.portal.vo.QuestionVO;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author tedu.cn
 * @since 2021-12-27
 */
@RestController
@RequestMapping("/v1/questions")
@Slf4j // 使当前类中的方法能够使用log对象记录日志
public class QuestionController {

    @Autowired
    private IQuestionService questionService;

    @GetMapping("/my")
    public PageInfo<Question> my(
            // @AuthenticationPrincipal注解效果是从Spring-Security中
            // 获得登录用户的信息对象UserDetails,赋值给后面的参数
            @AuthenticationPrincipal UserDetails user,
            Integer pageNum) {
        Integer pageSize = 8;
        if (pageNum == null) {
            pageNum = 1;
        }
        PageInfo<Question> pageInfo = questionService
                .getMyQuestions(user.getUsername(), pageNum, pageSize);
        return pageInfo;
    }

    // 学生发布问题访问的控制层方法
    // @PostMapping("")表示访问它的路径为localhost:8080/v1/questions
    @PostMapping("")
    public String createQuestion(
            @Validated QuestionVO questionVO,
            BindingResult result,
            @AuthenticationPrincipal UserDetails user) {
        log.debug("接收表单信息为:{}", questionVO);
        if (result.hasErrors()) {
            String msg = result.getFieldError().getDefaultMessage();
            return msg;
        }
        // 在这里调用业务逻辑层的新增问题方法
        questionService.saveQuestion(questionVO, user.getUsername());
        // 返回运行信息
        return "ok";
    }

    // 查询讲师任务列表
    @GetMapping("/teacher")
    // 当前登录用户必须是讲师身份才能查询讲师任务列表
    // 使用Spring-Security提供的权限\角色验证的功能来实现限制
    // @PreAuthorize注解设置要求当前登录用户持有ROLE_TEACHER角色才能访问
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public PageInfo<Question> teacher(
            @AuthenticationPrincipal UserDetails user,
            Integer pageNum){
        Integer pageSize=8;
        if(pageNum==null)
            pageNum=1;
        // 调用业务逻辑层方法
        PageInfo<Question> pageInfo=questionService
                .getTeacherQuestions(user.getUsername(),
                        pageNum,pageSize);
        return pageInfo;

    }

    // 根据问题id查询问题详情
    // SpringMvc支持我们编写占位符匹配url
    // 下面路径中{id}就是一个占位符
    // 当一个请求例如:localhost:8080/v1/questions/149路径时
    // 路径中/149就会自动匹配/{id}的占位符,并且149会赋值给id
    // 我们可以在控制器方法中获得{id}的值来使用
    @GetMapping("/{id}")
    public Question question(
            // 获得占位符{id}的赋值的方法
            // 1.必须编写@PathVariable注解
            // 2.参数的名称必须和{}中的名称一致
            @PathVariable Integer id){
        Question question=questionService.getQuestionById(id);
        return  question;
    }

}