package com.sky.apply.user.service.impl;

import com.alibaba.cola.statemachine.Action;
import com.alibaba.cola.statemachine.StateMachine;
import com.alibaba.cola.statemachine.StateMachineFactory;
import com.sky.apply.common.BusinessConstant;
import com.sky.apply.common.Context;
import com.sky.apply.enums.Events;
import com.sky.apply.enums.States;
import com.sky.apply.user.dao.UserDao;
import com.sky.apply.user.entity.UserDo;
import com.sky.apply.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author dlf
 * @date 2023/4/20 18:15
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    @Override
    public int addOne(UserDo userDo) {
        return userDao.add(userDo);
    }

    @Override
    public Action<States, Events, Context> action() {
        //审批提交的具体流程
        return (from, to, event, ctx) -> {
            UserDo turnsForm = ctx.getUserDo();
            log.info("原来的状态为:"+turnsForm.getProcess());
            System.err.println("UserDo:"+userDao);
            turnsForm.setProcess("待审批");
            log.info("目标状态为:"+turnsForm.getProcess());
        };
    }

    @Override
    public String actionSys() {
        Context context = new Context();
        UserDo userDo = new UserDo();
        userDo.setUserName("machineTest");
        userDo.setPassWord("dlf123");
        userDo.setProcess("发起申请....");
        StateMachine<States, Events, Context> stateMachine = StateMachineFactory.get(BusinessConstant.APPLY_STATION_MACHINE_START);
        stateMachine.showStateMachine();
        stateMachine.fireEvent(States.APPLY, Events.SAVE_APPLICATION, context);
        StateMachine<States, Events, Context> contextStateMachine =    StateMachineFactory.get(BusinessConstant.APPLY_STATION_MACHINE_START);
        return userDo.getProcess();
    }
}
