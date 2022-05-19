package cn.tedu.knows.portal.service.impl;

import cn.tedu.knows.portal.exception.ServiceException;
import cn.tedu.knows.portal.mapper.UserMapper;
import cn.tedu.knows.portal.model.Comment;
import cn.tedu.knows.portal.mapper.CommentMapper;
import cn.tedu.knows.portal.model.User;
import cn.tedu.knows.portal.service.ICommentService;
import cn.tedu.knows.portal.vo.CommentVo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author tedu.cn
 * @since 2022-02-24
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CommentMapper commentMapper;
    @Override
    public Comment saveComment(CommentVo commentVo, String username) {
        //根据用户名获得用户对象
        User user = userMapper.findUserByUsername(username);
        Comment comment = new Comment()
                .setUserId(user.getId())
                .setUserNickName(user.getNickname())
                .setAnswerId(commentVo.getAnswerId())
                .setContent(commentVo.getContent())
                .setCreatetime(LocalDateTime.now());
        int num = commentMapper.insert(comment);
        if(num!=1){
            throw new ServiceException("数据库异常");
        }
        return comment;
    }

    @Override
    public boolean removeComment(Integer commentId, String username) {
        //根据当前登录的用户名获得用户所有信息
        User user = userMapper.findUserByUsername(username);
        //判断评论的是不是讲师
        if(user.getType().equals(1)){
            //如果是讲师可以删除任何评论
            int num = commentMapper.deleteById(commentId);
            return num==1;
        }
        // 不是讲师删除评论要判断当前登录的用户是不是评论的发布者
        // 而评论的发布者id在comment对象中,我们需要先通过id查询到它
       Comment comment = commentMapper.selectById(commentId);
        if(comment.getUserId().equals(user.getId())){
            //如果是发布评论者就可以删除该评论
            int num = commentMapper.deleteById(commentId);
            return num==1;
        }
        //如果不是发布评论者
       throw new ServiceException("你不能删除老师的评论");
    }

    @Override
    @Transactional
    public Comment updateComment(Integer commentId, CommentVo commentVO, String username) {
        // 获得用户信息
        User user=userMapper.findUserByUsername(username);
        // 按id查询当前要修改的评论对象
        Comment comment=commentMapper.selectById(commentId);
        // 如果是讲师或者是评论的发布者,可以修改评论
        if(user.getType().equals(1) ||
                comment.getUserId().equals(user.getId())){
            // 将要修改的属性直接赋值
            comment.setContent(commentVO.getContent());
            // 执行修改,将修改后的对象提交到数据库
            int num=commentMapper.updateById(comment);
            if(num!=1){
                throw new ServiceException("数据库忙!");
            }
            // 如果num是1就是修改成功了!返回修改成功的对象
            return comment;
        }
        throw new ServiceException("您不能修改别人的评论");
    }
}
