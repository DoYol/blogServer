package com.dyledu.Controller;


import com.dyledu.annotation.SystemLog;
import com.dyledu.domain.ResponseResult;
import com.dyledu.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/article")
@Slf4j
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    /**
     * 获取热度为前十的文章列表
     * @return
     */
    @RequiresRoles("admin")
    @RequiresPermissions("一级权限")
    @GetMapping("/hotArticleList")
    @SystemLog(businessName = "获取热度为前十的文章列表")
    public ResponseResult hotArticleList() {
        return articleService.getHotArticleList();
    }

    @GetMapping("/articleList")
    @SystemLog(businessName = "获取文章列表")
    public ResponseResult getArticleList(Integer pageNum,Integer pageSize,Long categoryId){
      return articleService.getArticleList(pageNum,pageSize,categoryId);
    }
    @GetMapping("/{id}")
    @SystemLog(businessName = "获取对应ID的文章")
    public ResponseResult getArticleById(@PathVariable String id){
        return articleService.getArticleById(id);
    }

    @GetMapping("/updateViewCount/{id}")
    public ResponseResult updateViewCount(@PathVariable String id){
        return articleService.updateViewCount(id);
    }
}
