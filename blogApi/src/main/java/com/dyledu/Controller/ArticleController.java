package com.dyledu.Controller;


import com.dyledu.annotation.SystemLog;
import com.dyledu.domain.ResponseResult;
import com.dyledu.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    /**
     * 获取热度为前十的文章列表
     * @return
     */
    @GetMapping("/hotArticleList")
    @SystemLog(businessName = "获取热度为前十的文章列表")
    public ResponseResult hotArticleList() {
        return articleService.getHotArticleList();
    }

    @GetMapping("/articleList")
    @SystemLog(businessName = "获取文章列表")
    public ResponseResult getArticleList(){
      return articleService.getArticleList();
    }
    @GetMapping("/{id}")
    @SystemLog(businessName = "获取对应ID的文章")
    public ResponseResult getArticleById(@PathVariable String id){
        return articleService.getArticleById(id);
    }
}
