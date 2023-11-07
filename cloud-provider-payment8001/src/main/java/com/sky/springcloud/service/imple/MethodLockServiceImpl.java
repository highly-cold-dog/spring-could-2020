package com.sky.springcloud.service.imple;

import cn.hutool.core.util.ObjectUtil;
import com.sky.entyties.MethodLock;
import com.sky.springcloud.dao.MysqlLockDao;
import com.sky.springcloud.service.MethodLockService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author dlf
 * @date 2023/2/16 19:01
 */
@Service
public class MethodLockServiceImpl implements MethodLockService {

    @Resource
    private MysqlLockDao methodLockDao;
    /**
     * 新增
     *
     * @param methodLock
     */
    @Override
    public void insert(MethodLock methodLock) {

    }

    /**
     * 更新
     *
     * @param methodLock
     */
    @Override
    public void update(MethodLock methodLock) {

    }

    /**
     * Load查询
     *
     * @param id
     */
    @Override
    public MethodLock load(int id) {
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MethodLock getLock(MethodLock methodLock) throws Exception {
        MethodLock search = new MethodLock();
        search.setLockKey("test01");
        MethodLock lock = methodLockDao.getLock(search);
        if (ObjectUtil.isEmpty(lock)){
            throw new Exception("分布式锁找不到!");
        }
        try {
            //模拟业务代码执行
            Thread.sleep(20000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return lock;
    }
}
