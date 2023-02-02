package com.dyledu.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dyledu.constans.SystemConstants;
import com.dyledu.domain.ResponseResult;
import com.dyledu.domain.entity.Article;
import com.dyledu.domain.vo.hotArticleListVo;
import com.dyledu.mapper.ArticleMapper;
import com.dyledu.service.ArticleService;
import com.dyledu.utils.BeanCopyUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
    /**
     * 获取浏览量最多的十篇文章
     * @return
     */
    @Override
    public ResponseResult getHotArticleList() {
        Page<Article> articlePage = new Page<>(1,10);
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL).orderByDesc(Article::getViewCount);
        this.page(articlePage,queryWrapper);
        List<Article> articleList = articlePage.getRecords();
        List<hotArticleListVo> hotArticleListVoList = BeanCopyUtils.copyBeanList(articleList, hotArticleListVo.class);
        return ResponseResult.okResult(hotArticleListVoList);
    }

    /**
     * 获取文章列表
     * @return
     */
    @Override
    public ResponseResult getArticleList() {
        return null;
    }

    /**
     * 获取对应ID的文章
     * @param id
     * @return
     */
    @Override
    public ResponseResult getArticleById(String id) {
        return null;
    }



}
