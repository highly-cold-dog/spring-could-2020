package com.statemachine.cn;

import com.alibaba.cola.statemachine.Action;
import com.alibaba.cola.statemachine.Condition;
import com.alibaba.cola.statemachine.StateMachine;
import com.alibaba.cola.statemachine.StateMachineFactory;
import com.alibaba.cola.statemachine.builder.StateMachineBuilder;
import com.alibaba.cola.statemachine.builder.StateMachineBuilderFactory;
import org.junit.Test;

import java.util.logging.Logger;

/**
 * 有关状态机的测试
 *
 * @author dlf
 * @date 2023/4/18 16:18
 */

public class StateMachineTest {

    private static final Logger log = Logger.getLogger("StateMachineTest");
    static String MACHINE_ID_ONE = "TEST_MACHINE_ID_ONE";

    static String MACHINE_ID_TWO = "TEST_MACHINE_ID_TWO";


    static String MACHINE_ID_THREE = "TEST_MACHINE_ID_THREE";


    static String MACHINE_ID_FOUR = "TEST_MACHINE_ID_FOUR";


    static String MACHINE_ID_FIVE = "TEST_MACHINE_ID_FIVE";


    static enum States {

        /**
         * 申请
         */
        APPLY(1, "申请"),

        /**
         * 初审
         */
        FIRST_TRIAL(2, "初审"),


        /**
         * 终审
         */
        FINAL_JUDGEMENT(3, "终审"),

        /**
         * 移出
         */
        REMOVE(4, "移出");

        States(Integer value, String name) {
            this.value = value;
            this.name = name;
        }

        private final Integer value;
        private final String name;

        public Integer getValue() {
            return value;
        }

        public String getName() {
            return name;
        }
    }


    static enum Events {
        /**
         * 保存申请
         */
        SAVE_APPLICATION,

        /**
         * 提交申请
         */
        SUBMIT_APPLICATION,

        /**
         * 审批通过
         */
        AUDIT_AGREE,

        /**
         * 审批退回
         */
        AUDIT_REJECT;


    }

    /**
     * 参数
     */
    static class Context {
        String operator = "flw";
        String entityId = "1885573359";
        String name = "dlf";
    }


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
    @Test
    public void testExternalTransitionNormalApply() {
        // 第一步：生成一个状态机builder
        StateMachineBuilder<States, Events, Context> builder = StateMachineBuilderFactory.create();
        // 第二步：设置一个外部状态转移类型的builder，并设置from\to\on\when\perform
        builder.externalTransition()    // 外部状态流转
                .from(States.APPLY)     // 起始状态：申请
                .to(States.FIRST_TRIAL)        // 目的状态：初审
                .on(Events.SUBMIT_APPLICATION)       // 事件：提交申请
                .when(checkCondition()) // 流转需要校验的条件，校验不通过不会进行doAction
                .perform(doAction());   // 执行流转操作 这个action 我们可以按自己所需修改，比如这种Action<R,T> service的方法Service::method
        // 第三步：设置状态机的id和ready，并在StateMachineFactory中的stateMachineMap进行注册
        builder.build(MACHINE_ID_ONE);
        // 第四步：触发状态机
        StateMachine<States, Events, Context> stateMachine = StateMachineFactory.get(MACHINE_ID_ONE);
        stateMachine.showStateMachine();
        // 通过状态机执行 待审核状态执行审核操作，
        States target1 = stateMachine.fireEvent(States.APPLY, Events.SUBMIT_APPLICATION, new Context());
    }


    @Test
    public void testExternalTransitionNormalFirstTrial(){
        StateMachineBuilder<States,Events,Context> builder = StateMachineBuilderFactory.create();
        builder.externalTransition()
                .from(States.FIRST_TRIAL)
                .to(States.FINAL_JUDGEMENT)
                .on(Events.SUBMIT_APPLICATION)
                .when(checkCondition())
                .perform(doAction());
        builder.build(MACHINE_ID_FOUR);
        // 第四步：触发状态机
        StateMachine<States, Events, Context> stateMachine = StateMachineFactory.get(MACHINE_ID_FOUR);
        stateMachine.showStateMachine();
        // 通过状态机执行 待审核状态执行审核操作，
        //States target1 = stateMachine.fireEvent(States.APPLY, Events.SUBMIT_APPLICATION, new Context());

    }

    @Test
    public void testExternalTransitionNormal2(){
        StateMachineBuilder<States, Events, Context> builder = StateMachineBuilderFactory.create();
        builder.externalTransition()
                .from(States.FINAL_JUDGEMENT)
                .to(States.REMOVE)
                .on(Events.AUDIT_AGREE)
                .when(checkCondition())
                .perform(doAction());
        builder.build(MACHINE_ID_FIVE);
    }


    /**
     * 状态在申请时 可以进行保存
     */
    @Test
    public void testInternalNormal(){
        StateMachineBuilder<States, Events, Context> builder = StateMachineBuilderFactory.create();
        // 内部流转 internal transition
        // 假设现在只是用户补全资料，只需要进行一些更新数据操作，不需要状态流转。这种需求可以通过内部状态流转实现
        builder.internalTransition()
                .within(States.APPLY)
                .on(Events.SAVE_APPLICATION)
                .when(checkCondition())
                .perform(doAction());
        StateMachine<States, Events, Context> stateMachine = builder.build(MACHINE_ID_TWO);
        // 打印状态机里面的流程流转图谱
        stateMachine.showStateMachine();
        // 通过状态机执行 待审核状态执行审核操作，
        States target = stateMachine.fireEvent(States.APPLY, Events.SAVE_APPLICATION, new Context());

    }


    /**
     * 只要退回则回到申请箱的状态
     */
    @Test
    public void testExternalTransitionsNormal(){
        StateMachineBuilder<States, Events, Context> builder = StateMachineBuilderFactory.create();
        // external transitions 任意一个状态
        builder.externalTransitions()
                .fromAmong(States.FIRST_TRIAL, States.FINAL_JUDGEMENT)
                .to(States.APPLY)
                .on(Events.AUDIT_REJECT)
                .when(checkCondition())
                .perform(doAction());
        StateMachine<States, Events, Context> stateMachine = builder.build(MACHINE_ID_THREE);
        // 打印状态机里面的流程流转图谱
        stateMachine.showStateMachine();
        // 通过状态机执行 待审核状态执行审核操作，
        States target1 = stateMachine.fireEvent(States.FIRST_TRIAL, Events.AUDIT_REJECT, new Context());
        States target2 = stateMachine.fireEvent(States.FINAL_JUDGEMENT, Events.AUDIT_REJECT, new Context());
    }




    /**
     * 条件，表示是否满足某个状态
     */
    private Condition<StateMachineTest.Context> checkCondition() {
        //默认返回true
        return (context) -> true;
    }

    /**
     * 动作，到达某个状态之后，可以做什么
     */
    private Action<States, Events, Context> doAction() {
        return (from, to, event, ctx) -> log.info(ctx.operator + " is operating " + ctx.entityId + " from:" + from.getName() + " to:" + to.getName() + " on:" + event + ";" + ctx.name);

    }
}
