package com.sky.springcloud.controller;

import com.sky.entyties.CommonResult;
import com.sky.entyties.MethodLock;
import com.sky.springcloud.service.MethodLockService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author dlf
 * @date 2023/2/17 11:18
 */
@RestController
@RequestMapping("/MethodLock/test")
public class MethodLockController {

    private final static String RESULT_SUCCESS_CODE = "200";

    @Resource
    private MethodLockService methodLockService;

    /**
     * 新增
     */
    @PostMapping("/add")
    public CommonResult<String> insert(MethodLock methodLock) {
        methodLockService.insert(methodLock);
        return new CommonResult(200, "插入数据库成功!", null);
    }


    /**
     * 更新
     */
    @PutMapping("/update")
    public CommonResult<String> update(MethodLock methodLock) {
        methodLockService.update(methodLock);
        return new CommonResult(200, "修改成功", null);
    }

    /**
     * Load查询
     */
    @GetMapping("/{id}")
    public CommonResult<MethodLock> load(Long id) {
        return new CommonResult(200, "查询成功", null);
    }

    @GetMapping("/getLock")
    public CommonResult<Boolean> getLock() throws Exception {
        MethodLock search = new MethodLock();
        search.setLockKey("test01");
        return new CommonResult(200, "success", methodLockService.getLock(search));
    }

}
