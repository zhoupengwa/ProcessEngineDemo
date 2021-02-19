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
public class ZLog {
    /**
    * 日志ID
    */
    private String id;

    /**
    * 任务名称
    */
    private String task;

    /**
    * 任务ID
    */
    private String taskId;

    /**
    * 用户ID
    */
    private String userId;

    /**
    * 操作步骤,对应zz_code中关键字
    */
    private String isagreed;

    /**
    * 日志记录
    */
    private String log;

    /**
    * 记录创建时间
    */
    private Date createTime;
}