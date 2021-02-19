package com.demo.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.demo.mapper.ZLogMapper;
import com.demo.domain.dto.ZLog;
import com.demo.service.ZLogService;
/**
 * @author   zhoupeng
 * 
 */
@Service
public class ZLogServiceImpl implements ZLogService{

    @Resource
    private ZLogMapper zLogMapper;

    @Override
    public int deleteByPrimaryKey(String id) {
        return zLogMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(ZLog record) {
        return zLogMapper.insert(record);
    }

    @Override
    public int insertSelective(ZLog record) {
        return zLogMapper.insertSelective(record);
    }

    @Override
    public ZLog selectByPrimaryKey(String id) {
        return zLogMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(ZLog record) {
        return zLogMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(ZLog record) {
        return zLogMapper.updateByPrimaryKey(record);
    }

}
