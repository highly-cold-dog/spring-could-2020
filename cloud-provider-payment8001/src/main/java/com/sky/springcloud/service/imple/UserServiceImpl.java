package com.sky.springcloud.service.imple;

import com.sky.springcloud.event.UserRegisterEvent;
import com.sky.springcloud.model.UserBean;
import com.sky.springcloud.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author dlf
 * @date 2022/10/14 15:22
 */

@Service
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    /**
     * 事件发布由ApplicationContext对象管控的，发布事件前需要注入ApplicationContext
     * 对象调用publishEvent完成事件发布
     */
    @Resource
    ApplicationContext applicationContext;

    @Override
    public String register(UserBean userBean) {
        log.info("start===============");
        log.info("业务逻辑开始处理===");
        applicationContext.publishEvent(new UserRegisterEvent(this, userBean));
        return "success";
    }
}
