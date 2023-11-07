package com.sky.apply.user.service;

import com.alibaba.cola.statemachine.Action;
import com.sky.apply.common.Context;
import com.sky.apply.enums.Events;
import com.sky.apply.enums.States;
import com.sky.apply.user.entity.UserDo;

/**
 * @author dlf
 * @date 2023/4/20 18:15
 */
public interface UserService {

    public int addOne(UserDo userDo);

    public Action<States, Events, Context> action();


    public String actionSys();


}
