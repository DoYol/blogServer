package com.dyledu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dyledu.domain.ResponseResult;
import com.dyledu.domain.entity.Article;

public interface ArticleService extends IService<Article>{
    ResponseResult getHotArticleList();

    ResponseResult getArticleById(String id);

    ResponseResult getArticleList();
}
