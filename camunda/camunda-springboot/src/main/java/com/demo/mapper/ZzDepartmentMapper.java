package com.demo.mapper;

import com.demo.domain.dto.ZzDepartment;

/**
 * @author zhoupeng
 */
public interface ZzDepartmentMapper {
    int deleteByPrimaryKey(String departmentId);

    int insert(ZzDepartment record);

    int insertSelective(ZzDepartment record);

    ZzDepartment selectByPrimaryKey(String departmentId);

    int updateByPrimaryKeySelective(ZzDepartment record);

    int updateByPrimaryKey(ZzDepartment record);
}