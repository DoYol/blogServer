package com.dyledu.Controller;

import com.dyledu.annotation.SystemLog;
import com.dyledu.domain.ResponseResult;
import com.dyledu.domain.entity.Comment;
import com.dyledu.enums.AppHttpCodeEnum;
import com.dyledu.exception.SystemException;
import com.dyledu.service.CommentService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/list")
    @SystemLog(businessName = "获取评论列表")
    public ResponseResult getCommentList(Long articleId,int pageNum,int pageSize){
       return commentService.getCommentList(articleId,pageNum,pageSize);
    }

    @SystemLog(businessName = "添加评论")
    @RequiresRoles(value = {"admin","user","link"},logical = Logical.OR)
    @PostMapping
    public ResponseResult addComment(@RequestBody Comment comment){
        if(comment.getContent()== null || comment.getContent().equals("") ){
            throw new SystemException(AppHttpCodeEnum.CONTENT_NOTNULL);
        }
       return commentService.addComment(comment);
    }
}
