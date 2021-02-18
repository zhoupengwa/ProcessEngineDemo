package com.demo.mapper;

import com.demo.domain.dto.ZzDepartment;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

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

    List<ZzDepartment> listAll();

    List<ZzDepartment> selectByDepartmentIds(@Param("departmentIds") Set<String> departmentIds);

}