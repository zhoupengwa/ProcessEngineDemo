package com.demo.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.demo.mapper.ZPaicheMapper;
import com.demo.domain.dto.ZPaiche;
import com.demo.service.ZPaicheService;
/**
 * @author   zhoupeng
 * 
 */
@Service
public class ZPaicheServiceImpl implements ZPaicheService{

    @Resource
    private ZPaicheMapper zPaicheMapper;

    @Override
    public int deleteByPrimaryKey(String id) {
        return zPaicheMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(ZPaiche record) {
        return zPaicheMapper.insert(record);
    }

    @Override
    public int insertSelective(ZPaiche record) {
        return zPaicheMapper.insertSelective(record);
    }

    @Override
    public ZPaiche selectByPrimaryKey(String id) {
        return zPaicheMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(ZPaiche record) {
        return zPaicheMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(ZPaiche record) {
        return zPaicheMapper.updateByPrimaryKey(record);
    }

}
