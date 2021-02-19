package com.demo.service;

import com.demo.domain.dto.ZLog;
    /**
 * @author   zhoupeng
 * 
 */
public interface ZLogService{


    int deleteByPrimaryKey(String id);

    int insert(ZLog record);

    int insertSelective(ZLog record);

    ZLog selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ZLog record);

    int updateByPrimaryKey(ZLog record);

}
