package com.dyledu.Controller;

import com.dyledu.annotation.SystemLog;
import com.dyledu.domain.ResponseResult;
import com.dyledu.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
