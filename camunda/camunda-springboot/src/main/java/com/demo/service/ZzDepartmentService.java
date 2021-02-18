package com.demo.service;

import com.demo.domain.dto.ZzDepartment;

import java.util.List;

/**
 * @author zhoupeng
 */
public interface ZzDepartmentService {


    int deleteByPrimaryKey(String departmentId);

    int insert(ZzDepartment record);

    int insertSelective(ZzDepartment record);

    ZzDepartment selectByPrimaryKey(String departmentId);

    int updateByPrimaryKeySelective(ZzDepartment record);

    int updateByPrimaryKey(ZzDepartment record);

    List<ZzDepartment> listAll(ZzDepartment department);
}
