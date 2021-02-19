package com.demo.mapper;

import com.demo.domain.dto.ZPaiche;

/**
 * @author   zhoupeng
 * 
 */
public interface ZPaicheMapper {
    int deleteByPrimaryKey(String id);

    int insert(ZPaiche record);

    int insertSelective(ZPaiche record);

    ZPaiche selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ZPaiche record);

    int updateByPrimaryKey(ZPaiche record);
}