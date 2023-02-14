package com.dyledu.mapper;

import com.dyledu.domain.entity.Article;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Slf4j
public class ArticleMapperTest {
    @Autowired
    private ArticleMapper articleMapper;

    public void textArticleDatabase(){
        List<Article> articles = articleMapper.selectList(null);
        log.info(articles.toString());
    }

}
