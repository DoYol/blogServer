package com.dyledu.constans;

public class SystemConstants {
    /**
     *  文章是草稿
     */
    public static final int ARTICLE_STATUS_DRAFT = 1;
    /**
     *  文章是正常分布状态
     */
    public static final int ARTICLE_STATUS_NORMAL = 0;

    /**
     * String类型的   正常状态
     */
    public static final String STATUS_NORMAL = "0";

    /**
     * 博客系统存入 redis 中 key 的前缀
     */
    public static final String BLOG_TOKEN="bloglogin:";

    /**
     * 博客后台管理系统存入 redis 中 key 的前缀
     */
    public static final String ADMIN_TOKEN="adminlogin:";

    /**
     * 评论列表  表示根评论的ID
     */
    public static final int COMMENT_ROLE_ID=-1;

    /**
     * Redis存储 博客前台文章浏览量  的keys
     */
    public static final String REDIS_ARTICLE_VIEW_COUNT="article:viewCount";

    /**
     * 角色为admin
     */
    public static final String ADMIN_ROLE_KEY="admin";

    public static final String CATE="M";

    /**
     * menu表中 菜单权限
     */
    public static final String MENU="C";

    /**
     * menu表中 按钮权限
     */
    public static final String BUTTON="F";
}
