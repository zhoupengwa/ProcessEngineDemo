package com.demo.service;

import com.demo.domain.dto.ZPaiche;
    /**
 * @author   zhoupeng
 * 
 */
public interface ZPaicheService{


    int deleteByPrimaryKey(String id);

    int insert(ZPaiche record);

    int insertSelective(ZPaiche record);

    ZPaiche selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ZPaiche record);

    int updateByPrimaryKey(ZPaiche record);

}
