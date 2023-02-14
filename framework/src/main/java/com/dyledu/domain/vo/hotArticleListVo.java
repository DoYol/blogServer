package com.dyledu.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class hotArticleListVo {
    private Long id;
    private String title;
    private Long viewCount;

}
