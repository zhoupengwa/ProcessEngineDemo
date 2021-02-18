package com.demo.service;

import com.demo.domain.dto.ZzUser;
import com.demo.domain.vo.ZzUserVO;

import java.util.List;

/**
 * @author zhoupeng
 */
public interface ZzUserService {


    int deleteByPrimaryKey(String userId);

    int insert(ZzUser record);

    int insertSelective(ZzUser record);

    ZzUser selectByPrimaryKey(String userId);

    int updateByPrimaryKeySelective(ZzUser record);

    int updateByPrimaryKey(ZzUser record);

    List<ZzUserVO> listAll(ZzUser user);
}
