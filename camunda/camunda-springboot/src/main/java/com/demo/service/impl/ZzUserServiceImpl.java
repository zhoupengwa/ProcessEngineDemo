package com.demo.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.demo.domain.dto.ZzUser;
import com.demo.mapper.ZzUserMapper;
import com.demo.service.ZzUserService;
/**
 * @author   zhoupeng
 * 
 */
@Service
public class ZzUserServiceImpl implements ZzUserService{

    @Resource
    private ZzUserMapper zzUserMapper;

    @Override
    public int deleteByPrimaryKey(String userId) {
        return zzUserMapper.deleteByPrimaryKey(userId);
    }

    @Override
    public int insert(ZzUser record) {
        return zzUserMapper.insert(record);
    }

    @Override
    public int insertSelective(ZzUser record) {
        return zzUserMapper.insertSelective(record);
    }

    @Override
    public ZzUser selectByPrimaryKey(String userId) {
        return zzUserMapper.selectByPrimaryKey(userId);
    }

    @Override
    public int updateByPrimaryKeySelective(ZzUser record) {
        return zzUserMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(ZzUser record) {
        return zzUserMapper.updateByPrimaryKey(record);
    }

}
