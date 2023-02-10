package com.dyledu.domain.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentVo {
    private Long id;


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
     * 所回复目标评论的username
     */
    private String toCommentUserName;

    /**
     * 该条评论的用户昵称
     */
    private String username;
    /**
     * 评论的子评论
     */
    private List<CommentVo> children;
    /**
     * 回复目标评论id
     */
    private Long toCommentId;

    /**
     *
     */
    private Long createBy;

    /**
     *
     */
    private LocalDateTime createTime;


}