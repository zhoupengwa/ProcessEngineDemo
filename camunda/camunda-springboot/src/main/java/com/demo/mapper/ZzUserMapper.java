package com.demo.mapper;

import com.demo.domain.dto.ZzUser;

/**
 * @author zhoupeng
 */
public interface ZzUserMapper {
    int deleteByPrimaryKey(String userId);

    int insert(ZzUser record);

    int insertSelective(ZzUser record);

    ZzUser selectByPrimaryKey(String userId);

    int updateByPrimaryKeySelective(ZzUser record);

    int updateByPrimaryKey(ZzUser record);
}