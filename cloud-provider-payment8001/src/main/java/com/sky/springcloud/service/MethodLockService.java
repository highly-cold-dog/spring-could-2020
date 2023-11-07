package com.sky.springcloud.service;

import com.sky.entyties.MethodLock;

/**
 * @author dlf
 * @date 2023/2/16 19:00
 */
public interface MethodLockService {

    /**
     * 新增
     */
    public void insert(MethodLock methodLock);


    /**
     * 更新
     */
    public void update(MethodLock methodLock);

    /**
     * Load查询
     */
    public MethodLock load(int id);

    /*获取分布式锁*/
    MethodLock getLock(MethodLock methodLock) throws Exception;

}
