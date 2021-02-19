package com.demo.mapper;

import com.demo.domain.dto.ZLog;

/**
 * @author   zhoupeng
 * 
 */
public interface ZLogMapper {
    int deleteByPrimaryKey(String id);

    int insert(ZLog record);

    int insertSelective(ZLog record);

    ZLog selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ZLog record);

    int updateByPrimaryKey(ZLog record);
}