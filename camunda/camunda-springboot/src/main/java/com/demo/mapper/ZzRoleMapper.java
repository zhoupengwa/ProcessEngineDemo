package com.demo.mapper;

import com.demo.domain.dto.ZzRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * @author zhoupeng
 */
public interface ZzRoleMapper {
    int deleteByPrimaryKey(String roleId);

    int insert(ZzRole record);

    int insertSelective(ZzRole record);

    ZzRole selectByPrimaryKey(String roleId);

    int updateByPrimaryKeySelective(ZzRole record);

    int updateByPrimaryKey(ZzRole record);

    List<ZzRole> listAll();

    List<ZzRole> selectByRoleIds(@Param("roleIds") Set<String> roleIds);
}