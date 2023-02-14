package com.dyledu.runner;

import com.dyledu.constans.SystemConstants;
import com.dyledu.domain.entity.Article;
import com.dyledu.service.ArticleService;
import com.dyledu.utils.RedisCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Slf4j
public class ViewCountRunner implements CommandLineRunner {

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private ArticleService articleService;

    @Override
    public void run(String... args) throws Exception {
        log.info("查询博客信息将文章id和viewCount存入redis中");
        List<Article> articleList = articleService.list();
        Map<String, Integer> map = articleList.stream().collect(Collectors.toMap((item) -> {
            return item.getId().toString();
        }, (item) -> {
            return item.getViewCount().intValue();
        }));
        redisCache.setCacheMap(SystemConstants.REDIS_ARTICLE_VIEW_COUNT,map);
    }
}
