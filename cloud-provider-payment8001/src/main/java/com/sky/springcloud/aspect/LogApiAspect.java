package com.sky.springcloud.aspect;

import cn.hutool.json.JSONUtil;
import com.sky.springcloud.annotation.LogApi;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.NamedThreadLocal;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;

/**
 * AOP 解析注解
 *
 * @author dlf
 * @date 2022/10/15 13:25
 */
@Aspect
@Component
public class LogApiAspect {

    private static final ThreadLocal<Long> beginTimeThreadPool = new NamedThreadLocal<>("beginTime");
    private static final Logger log = LoggerFactory.getLogger(LogApiAspect.class);

    //切面点为标记了@LogApi注解的方法
    @Pointcut("@annotation(com.sky.springcloud.annotation.LogApi)")
    public void doPointcut() {
    }

    /**
     * 前置通知(在方法执行之前返回)用于拦截Controller层记录用户操作的开始时间
     *
     * @param joinPoint 切点
     * @throws Exception InterruptedException
     */
    @Before("doPointcut()")
    public void deBefore(JoinPoint joinPoint) throws Exception {
        Long beforeSecond = System.currentTimeMillis();
        //获取当前方法上注解的值
        LogApi logApi = ((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(LogApi.class);
        //获取当前方法上的参数
        Object[] args = joinPoint.getArgs();
        //获取切点所在类名
        String className = joinPoint.getTarget().getClass().getSimpleName();
        //获取切点方法名
        String method = joinPoint.getSignature().getName();
        beginTimeThreadPool.set(beforeSecond);
        Thread thread = Thread.currentThread();
        log.info("公共日志-开始-当前线程{}\n--类:{}\n--方法:{}\n--方法上注解:{}\n--传入参数数组:{}",
                thread.getName(), className, method, logApi, Arrays.toString(args));

    }


    //环绕通知
    @Around("doPointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        //通过反射获取当前被调用方法的Class
        Class type = joinPoint.getSignature().getDeclaringType();
        //获取类名
        String typeName = type.getSimpleName();
        //获取日志记录对象
        Logger logger = LoggerFactory.getLogger(type);
        //方法名
        String methodName = joinPoint.getSignature().getName();
        //获取参数列表
        Object[] args = joinPoint.getArgs();
        //参数Class的数组
        Class[] clazz = new Class[args.length];
        for (int i = 0; i < args.length; i++) {
            clazz[i] = args[i].getClass();
        }
        //通过反射获取调用的方法method
        Method method = type.getMethod(methodName, clazz);
        //获取方法的参数
        Parameter[] parameters = method.getParameters();
        //拼接字符串，格式为{参数1：值1，参数2：值2}
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];
            String name = parameter.getName();
            sb.append(name).append(":").append(args[i]).append(",");
        }

        if (sb.length() > 0) {
            sb.deleteCharAt(sb.lastIndexOf(","));
        }

        //执行结果
        Object result;
        try {
            //执行目标方法，获取执行结果
            result = joinPoint.proceed();
            logger.info("调用{},{}方法成功，方法参数为[{}]，返回结果为[{}]", typeName, methodName, sb.toString(),
                    JSONUtil.toJsonStr(result));
        } catch (Exception e) {
            logger.error("调用{},{}方法发生异常", typeName, methodName);
            //如果发生异常，则抛出异常
            throw e;
        } finally {
            logger.info("调用{},{}方法,耗时{}ms", typeName,
                    methodName, (System.currentTimeMillis() - startTime));
        }
        return result;
    }

    @After("doPointcut()")
    public void after(JoinPoint joinPoint) {
        Thread thread = Thread.currentThread();
        Long beforeSecond = beginTimeThreadPool.get();
        Long afterSecond = System.currentTimeMillis();
        Long spendTime = (afterSecond - beforeSecond) / 1000;
        log.info("公共日志-结束-当前线程{}-----消耗时间{}s", thread.getName(), spendTime);
    }
}
