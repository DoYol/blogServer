package com.dyledu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dyledu.domain.ResponseResult;
import com.dyledu.domain.entity.Comment;

public interface CommentService extends IService<Comment> {
    ResponseResult getCommentList(Long articleId, int pageNum, int pageSize);
}
