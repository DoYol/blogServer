package com.dyledu.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoVo {
    private Long id;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 用户性别（0男，1女，2未知）
     */
    private String sex;
    /**
     * 手机号
     */
    private String phonenumber;
    /**
     * 头像
     */
    private String avatar;
}
