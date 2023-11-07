package com.sky.apply.config;

import com.alibaba.cola.statemachine.builder.StateMachineBuilder;
import com.alibaba.cola.statemachine.builder.StateMachineBuilderFactory;
import com.sky.apply.common.BusinessConstant;
import com.sky.apply.common.Context;
import com.sky.apply.enums.Events;
import com.sky.apply.enums.States;
import com.sky.apply.user.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * 状态机的初始化配置
 *
 * @author dlf
 * @date 2023/4/20 17:41
 */
@Configuration
public class StatemachineConfig {

    @Resource
    private UserService userService;


   /* @PostConstruct
    public void init() {
        testExternalTransitionNormalApply();
    }*/

    /**
     * State：状态
     * Event：事件，状态由事件触发，引起变化
     * Transition：流转，表示从一个状态到另一个状态
     * External Transition：外部流转，两个不同状态之间的流转
     * Internal Transition：内部流转，同一个状态之间的流转
     * Condition：条件，表示是否允许到达某个状态
     * Action：动作，到达某个状态之后，可以做什么
     * StateMachine：状态机
     */
    @Bean
    public StateMachineBuilder<States, Events, Context> testExternalTransitionNormalApply() {
        // 第一步：生成一个状态机builder
        StateMachineBuilder<States, Events, Context> builder = StateMachineBuilderFactory.create();
        // 第二步：设置一个外部状态转移类型的builder，并设置from\to\on\when\perform
        builder.externalTransition()    // 外部状态流转
                .from(States.APPLY)     // 起始状态：申请
                .to(States.FIRST_TRIAL)        // 目的状态：初审
                .on(Events.SUBMIT_APPLICATION)       // 事件：提交申请
                //.when(checkCondition()) // 流转需要校验的条件，校验不通过不会进行doAction
                .perform(userService.action());   // 执行流转操作 这个action 我们可以按自己所需修改，比如这种Action<R,T> service的方法Service::method
        // 第三步：设置状态机的id和ready，并在StateMachineFactory中的stateMachineMap进行注册
        builder.build(BusinessConstant.APPLY_STATION_MACHINE_START);
      /*  // 第四步：触发状态机
        StateMachine<States, Events, Context> stateMachine = StateMachineFactory.get(BusinessConstant.APPLY_STATION_MACHINE_START);
        stateMachine.showStateMachine();
        // 通过状态机执行 待审核状态执行审核操作，
        States target1 = stateMachine.fireEvent(States.APPLY, Events.SUBMIT_APPLICATION, new Context());*/

        return builder;
    }
}
