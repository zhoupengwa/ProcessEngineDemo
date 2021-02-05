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
public class ZzDepartment {
    /**
     * 部门ID
     */
    private String departmentId;

    /**
     * 部门名称
     */
    private String departmentName;
}