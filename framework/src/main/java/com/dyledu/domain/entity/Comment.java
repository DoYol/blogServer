package com.dyledu.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;


@Data
@TableName("sg_comment")
public class Comment implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 评论类型（0代表文章评论，1代表友链评论）
     */
    private String type;

    /**
     * 文章id
     */
    private Long articleId;

    /**
     * 根评论id
     */
    private Long rootId;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 所回复的目标评论的userid
     */
    private Long toCommentUserId;

    /**
     * 回复目标评论id
     */
    private Long toCommentId;

    /**
     *
     */
//    @TableField(fill = FieldFill.INSERT)
    private Long createBy;

    /**
     *
     */
//    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     *
     */
//    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateBy;

    /**
     *
     */
//    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 删除标志（0代表未删除，1代表已删除）
     */
    private Integer delFlag;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
