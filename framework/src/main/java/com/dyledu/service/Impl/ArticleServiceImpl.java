package com.dyledu.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dyledu.constans.SystemConstants;
import com.dyledu.domain.ResponseResult;
import com.dyledu.domain.entity.Article;
import com.dyledu.domain.entity.Category;
import com.dyledu.domain.vo.ArticleByIdVo;
import com.dyledu.domain.vo.ArticleListVo;
import com.dyledu.domain.vo.hotArticleListVo;
import com.dyledu.mapper.ArticleMapper;
import com.dyledu.service.ArticleService;
import com.dyledu.service.CategoryService;
import com.dyledu.utils.BeanCopyUtils;
import com.dyledu.utils.RedisCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    private RedisCache redisCache;
    @Autowired
    private CategoryService categoryService;

    /**
     * 获取浏览量最多的十篇文章
     *
     * @return
     */
    @Override
    public ResponseResult getHotArticleList() {
        Page<Article> articlePage = new Page<>(1, 10);
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL).orderByDesc(Article::getViewCount);
        this.page(articlePage, queryWrapper);
        List<Article> articleList = articlePage.getRecords();
        List<hotArticleListVo> hotArticleListVoList = BeanCopyUtils.copyBeanList(articleList, hotArticleListVo.class);
        return ResponseResult.okResult(hotArticleListVoList);
    }

    /**
     * 获取文章列表
     *
     * @return
     */
    @Override
    public ResponseResult getArticleList(Integer pageNum, Integer pageSize, Long categoryId) {
        Page<Article> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL)
                .eq(categoryId != null, Article::getCategoryId, categoryId)
                .orderByDesc(Article::getIsTop);
        this.page(page,queryWrapper);
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(page.getRecords(), ArticleListVo.class);
        articleListVos.stream().map((item)->{
            String categoryName = categoryService.getById(item.getCategoryId()).getName();
            item.setCategoryName(categoryName);
//            从redis中获取最新的viewCount
            Integer viewCount = redisCache.getCacheMapValue(SystemConstants.REDIS_ARTICLE_VIEW_COUNT, item.getId().toString());
            item.setViewCount(viewCount.longValue());
            return item;
        }).collect(Collectors.toList());
        return ResponseResult.okResult(articleListVos);
    }



    /**
     * 获取对应ID的文章
     *
     * @param id
     * @return
     */
    @Override
    public ResponseResult getArticleById(String id) {
        Article article = this.getById(id);
        ArticleByIdVo articleByIdVo = BeanCopyUtils.copyBean(article, ArticleByIdVo.class);
        //            从redis中获取最新的viewCount
        Integer viewCount = redisCache.getCacheMapValue(SystemConstants.REDIS_ARTICLE_VIEW_COUNT, article.getId().toString());
        articleByIdVo.setViewCount(viewCount.longValue());
        Category category = categoryService.getById(articleByIdVo.getCategoryId());
        articleByIdVo.setCategoryName(category.getName());
        return ResponseResult.okResult(articleByIdVo);
    }

    /**
     * 更新浏览量
     * @param id
     * @return
     */
    @Override
    public ResponseResult updateViewCount(String id) {
        redisCache.incrementCacheMapValue(SystemConstants.REDIS_ARTICLE_VIEW_COUNT,id,1);
        return ResponseResult.okResult();
    }
}
