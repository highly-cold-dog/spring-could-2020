package com.sky.entyties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 基于MySql 排他锁的方式实现分布式锁
 *
 * @author dlf
 * @date 2023/2/16 18:40
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MethodLock implements Serializable {
    private static final long serialVersionUID = 42L;

    /**
     * 主键
     */
    private int id;

    /**
     * 锁的键值
     */
    private String lockKey;

    /**
     * 锁的超时时间
     */
    private Date lockTimeout;

    /**
     * 备注信息
     */
    private String remarks;

    /**
     *
     */
    private Date updateTime;



}