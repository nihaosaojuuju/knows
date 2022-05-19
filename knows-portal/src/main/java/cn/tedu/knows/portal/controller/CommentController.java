package cn.tedu.knows.portal.controller;


import cn.tedu.knows.portal.exception.ServiceException;
import cn.tedu.knows.portal.model.Comment;
import cn.tedu.knows.portal.service.ICommentService;
import cn.tedu.knows.portal.vo.CommentVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author tedu.cn
 * @since 2022-02-24
 */
@RestController
@RequestMapping("/v1/comments")
@Slf4j
public class CommentController {
    @Autowired
    private ICommentService commentService;

    @PostMapping
    public Comment postComment(@Validated CommentVo commentVo,
                               BindingResult result,
                               @AuthenticationPrincipal UserDetails user){
        log.debug("接收到的表单信息:{}",commentVo);
        if(result.hasErrors()){
            String msg = result.getFieldError().getDefaultMessage();
            throw new ServiceException(msg);
        }
        //这里调用义务逻辑层代码
        Comment comment = commentService.saveComment(commentVo,user.getUsername());
        return comment;
    }
    //按id删除评论的控制层方法
    @GetMapping("/{id}/delete")
    public String removeComment(@PathVariable Integer id,@AuthenticationPrincipal UserDetails user){
        boolean isDelete=commentService.removeComment(id,user.getUsername());
        if(isDelete){
            return "删除成功!";
        }else {
           return "删除失败";
        }
    }

    //按id修改评论的控制层方法
    @PostMapping("/{id}/update")
    public Comment updateComment(@PathVariable Integer id,
                                 @Validated CommentVo commentVo,
                                 BindingResult result,
                                 @AuthenticationPrincipal UserDetails user){
        log.debug("接收到的表单信息:{}",commentVo);
        log.debug("要修改的评论id:{}",id);
        if(result.hasErrors()){
            String msg =result.getFieldError().getDefaultMessage();
            throw new ServiceException(msg);
        }
        Comment comment = commentService.updateComment(id,commentVo,user.getUsername());
        return comment;
    }
}
