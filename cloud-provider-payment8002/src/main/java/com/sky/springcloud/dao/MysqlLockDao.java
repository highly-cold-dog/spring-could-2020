package com.sky.springcloud.dao;

import com.sky.entyties.MethodLock;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author dlf
 * @date 2023/2/16 18:50
 */
@Mapper
public interface MysqlLockDao {

    /**
     * 新增
     */
    public int insert(@Param("methodLock") MethodLock methodLock);


    /**
     * 更新
     */
    public int update(@Param("methodLock") MethodLock methodLock);

    /**
     * Load查询
     */
    public MethodLock load(@Param("id") int id);

    /*获取分布式锁*/
   MethodLock getLock(@Param("methodLock") MethodLock methodLock);

}
