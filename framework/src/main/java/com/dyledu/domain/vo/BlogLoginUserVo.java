package com.dyledu.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogLoginUserVo {
    private String token;
    private UserInfoVo userInfoVo;
}
