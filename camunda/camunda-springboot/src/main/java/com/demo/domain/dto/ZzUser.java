package com.demo.domain.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author zhoupeng
 */
@Getter
@Setter
@ToString
public class ZzUser {
    /**
     * 用户ID
     */
    private String userId;

    /**
     * 部门ID
     */
    private String departmentId;

    /**
     * 角色ID
     */
    private String roleId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String psd;
}