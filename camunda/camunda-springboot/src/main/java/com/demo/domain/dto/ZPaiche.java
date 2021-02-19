package com.demo.domain.dto;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author   zhoupeng
 * 
 */
@Getter
@Setter
@ToString
public class ZPaiche {
    /**
    * 派车ID
    */
    private String id;

    /**
    * 对应流程实例ID
    */
    private String pid;

    /**
    * 用户ID
    */
    private String userId;

    /**
    * 开始时间
    */
    private Date startDateTime;

    /**
    * 乘车人数
    */
    private Integer persons;

    /**
    * 乘车电话
    */
    private String phone;

    /**
    * 乘车地点
    */
    private String startPosition;

    /**
    * 到达目的地
    */
    private String endPosition;

    /**
    * 司机姓名
    */
    private String driver;

    /**
    * 车号
    */
    private String car;

    /**
    * 备注
    */
    private String bzhu;

    /**
    * 记录创建时间
    */
    private Date createTime;
}