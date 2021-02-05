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
public class ZzRole {
    /**
     * 职务ID
     */
    private String roleId;

    /**
     * 职务名称
     */
    private String roleName;
}