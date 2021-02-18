package com.demo.service;

import com.demo.domain.dto.ZzRole;

import java.util.List;

/**
 * @author zhoupeng
 */
public interface ZzRoleService {


    int deleteByPrimaryKey(String roleId);

    int insert(ZzRole record);

    int insertSelective(ZzRole record);

    ZzRole selectByPrimaryKey(String roleId);

    int updateByPrimaryKeySelective(ZzRole record);

    int updateByPrimaryKey(ZzRole record);

    List<ZzRole> listAll(ZzRole role);
}
