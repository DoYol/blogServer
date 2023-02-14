package com.dyledu.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dyledu.constans.SystemConstants;
import com.dyledu.domain.ResponseResult;
import com.dyledu.domain.entity.Comment;
import com.dyledu.domain.entity.User;
import com.dyledu.domain.vo.CommentPageVo;
import com.dyledu.domain.vo.CommentVo;
import com.dyledu.mapper.CommentMapper;
import com.dyledu.service.CommentService;
import com.dyledu.service.UserService;
import com.dyledu.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    private UserService userService;

    /**
     * 获取文章评论列表
     * @param articleId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public ResponseResult getCommentList(Long articleId, int pageNum, int pageSize) {
        Page<Comment> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getArticleId,articleId).eq(Comment::getRootId, SystemConstants.COMMENT_ROLE_ID);
        this.page(page,queryWrapper);
        List<Comment> records = page.getRecords();
//        获取评论数据的评论人昵称和被评论人昵称
        List<CommentVo> commentVoList = this.toCommentVoList(records);
        commentVoList.stream().map((item)->{
            LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Comment::getRootId,item.getId()).orderByAsc(Comment::getCreateTime);
            List<Comment> list = this.list(wrapper);
            if(list != null){
                List<CommentVo> commentVos = this.toCommentVoList(list);
                item.setChildren(commentVos);
            }
            return item;
        }).collect(Collectors.toList());
        CommentPageVo commentPageVo = new CommentPageVo(commentVoList, page.getTotal());
        return ResponseResult.okResult(commentPageVo);
    }


    /**
     * 获取评论数据的评论人昵称和被评论人昵称
     * @param commentList
     * @return
     */
    private List<CommentVo> toCommentVoList(List<Comment> commentList){
        List<CommentVo> commentVoList = BeanCopyUtils.copyBeanList(commentList, CommentVo.class);
        List<CommentVo> commentVoLists = commentVoList.stream().map((item) -> {
            User user = userService.getById(item.getCreateBy());
            item.setUsername(user.getNickName());
            if(item.getRootId()!= SystemConstants.COMMENT_ROLE_ID){
                User user1 = userService.getById(item.getToCommentUserId());
                item.setToCommentUserName(user1.getNickName());
            }
            return item;
        }).collect(Collectors.toList());
        return commentVoLists;
    }

    /**
     * 添加评论
     * @param comment
     * @return
     */
    @Override
    public ResponseResult addComment(Comment comment) {
        this.save(comment);
        return ResponseResult.okResult();
    }
}
