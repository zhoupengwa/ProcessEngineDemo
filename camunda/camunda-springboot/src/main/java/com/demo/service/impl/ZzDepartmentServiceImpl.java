package com.demo.service.impl;

import com.demo.domain.dto.ZzRole;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.demo.mapper.ZzDepartmentMapper;
import com.demo.domain.dto.ZzDepartment;
import com.demo.service.ZzDepartmentService;

import java.util.List;

/**
 * @author   zhoupeng
 * 
 */
@Service
public class ZzDepartmentServiceImpl implements ZzDepartmentService{

    @Resource
    private ZzDepartmentMapper zzDepartmentMapper;

    @Override
    public int deleteByPrimaryKey(String departmentId) {
        return zzDepartmentMapper.deleteByPrimaryKey(departmentId);
    }

    @Override
    public int insert(ZzDepartment record) {
        return zzDepartmentMapper.insert(record);
    }

    @Override
    public int insertSelective(ZzDepartment record) {
        return zzDepartmentMapper.insertSelective(record);
    }

    @Override
    public ZzDepartment selectByPrimaryKey(String departmentId) {
        return zzDepartmentMapper.selectByPrimaryKey(departmentId);
    }

    @Override
    public int updateByPrimaryKeySelective(ZzDepartment record) {
        return zzDepartmentMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(ZzDepartment record) {
        return zzDepartmentMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<ZzDepartment> listAll(ZzDepartment department) {
        return zzDepartmentMapper.listAll();
    }

}
