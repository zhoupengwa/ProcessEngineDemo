package com.demo.service.impl;

import com.demo.domain.dto.ZzDepartment;
import com.demo.domain.dto.ZzRole;
import com.demo.domain.dto.ZzUser;
import com.demo.domain.vo.ZzUserVO;
import com.demo.mapper.ZzDepartmentMapper;
import com.demo.mapper.ZzRoleMapper;
import com.demo.mapper.ZzUserMapper;
import com.demo.service.ZzUserService;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author zhoupeng
 */
@Service
public class ZzUserServiceImpl implements ZzUserService {

    @Resource
    private ZzUserMapper zzUserMapper;

    @Resource
    private ZzRoleMapper zzRoleMapper;

    @Resource
    private ZzDepartmentMapper zzDepartmentMapper;

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

    @Override
    public List<ZzUserVO> listAll(ZzUser user) {
        List<ZzUser> zzUsers = zzUserMapper.listAll();
        if (zzUsers.size() == 0) {
            return Lists.newArrayList();
        }
        Set<String> roleIds = zzUsers.parallelStream().map(ZzUser::getRoleId).collect(Collectors.toSet());
        Set<String> departmentIds = zzUsers.parallelStream().map(ZzUser::getDepartmentId).collect(Collectors.toSet());
        List<ZzRole> roleList = zzRoleMapper.selectByRoleIds(roleIds);
        List<ZzDepartment> departmentList = zzDepartmentMapper.selectByDepartmentIds(departmentIds);

        Map<String, String> roleNameMap = roleList.parallelStream()
                .collect(Collectors.toMap(ZzRole::getRoleId, ZzRole::getRoleName));
        Map<String, String> departmentNameMap = departmentList.parallelStream()
                .collect(Collectors.toMap(ZzDepartment::getDepartmentId, ZzDepartment::getDepartmentName));

        List<ZzUserVO> resultList = new ArrayList<>(zzUsers.size());
        for (ZzUser zzUser : zzUsers) {
            ZzUserVO vo = new ZzUserVO();
            vo.setUserId(zzUser.getUserId());
            vo.setUsername(zzUser.getUsername());
            String departmentId = zzUser.getDepartmentId();
            vo.setDepartmentId(departmentId);
            vo.setDepartmentName(departmentNameMap.get(departmentId));
            String roleId = zzUser.getRoleId();
            vo.setRoleId(roleId);
            vo.setRoleName(roleNameMap.get(roleId));
            resultList.add(vo);
        }
        return resultList;
    }

}
