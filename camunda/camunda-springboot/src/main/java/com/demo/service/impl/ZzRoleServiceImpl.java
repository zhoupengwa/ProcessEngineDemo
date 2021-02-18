package com.demo.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.demo.mapper.ZzRoleMapper;
import com.demo.domain.dto.ZzRole;
import com.demo.service.ZzRoleService;

import java.util.List;

/**
 * @author   zhoupeng
 * 
 */
@Service
public class ZzRoleServiceImpl implements ZzRoleService{

    @Resource
    private ZzRoleMapper zzRoleMapper;

    @Override
    public int deleteByPrimaryKey(String roleId) {
        return zzRoleMapper.deleteByPrimaryKey(roleId);
    }

    @Override
    public int insert(ZzRole record) {
        return zzRoleMapper.insert(record);
    }

    @Override
    public int insertSelective(ZzRole record) {
        return zzRoleMapper.insertSelective(record);
    }

    @Override
    public ZzRole selectByPrimaryKey(String roleId) {
        return zzRoleMapper.selectByPrimaryKey(roleId);
    }

    @Override
    public int updateByPrimaryKeySelective(ZzRole record) {
        return zzRoleMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(ZzRole record) {
        return zzRoleMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<ZzRole> listAll(ZzRole role) {

        return zzRoleMapper.listAll();
    }

}
