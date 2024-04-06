package com.sky.aspct;

import com.sky.annotation.Autofill;
import com.sky.constant.AutoFillConstant;
import com.sky.context.BaseContext;
import com.sky.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.asm.Advice;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 * TODO 由于使用了mybatis-flex所以只能在service层添加注解，导致一些方法上没有传递实体类无法添加注解
 * @author: sunjianrong
 * @email: sunruolifeng@gmail.com
 * @date: 06/04/2024 8:52 AM
 */
@Aspect
@Component
@Slf4j
public class AutoFillAspct {
    // 切入点
    @Pointcut("execution(* com.sky.service.impl.*.*(..)) && @annotation(com.sky.annotation.Autofill)")
    public void autoFillPointCut(){}
    // 前置通知
    @Before("autoFillPointCut()")
    public void autoFill(JoinPoint joinPoint) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // 获取当前操作的类型
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        // 获取方法上的注解对象
        Autofill annotation = signature.getMethod().getAnnotation(Autofill.class);
        OperationType operationType = annotation.value();
        // 获取方法实体参数
        Object[] args = joinPoint.getArgs();
        if (args == null || args.length == 0){
            return;
        }
        Object arg = args[0];
        // 准备赋值数据
        LocalDateTime now = LocalDateTime.now();
        Long currentId = BaseContext.getCurrentId();
        // 为对应的属性赋值
        if (operationType == OperationType.INSERT){
            // 赋值4个
            Method setCreateTime = arg.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_TIME, LocalDateTime.class);
            Method setCreateUser = arg.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_USER, Long.class);
            Method setUpdateTime = arg.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
            Method setUpdateUser = arg.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);
            // 使用反射
            setCreateTime.invoke(arg, now);
            setUpdateTime.invoke(arg,now);
            setCreateUser.invoke(arg, currentId);
            setUpdateUser.invoke(arg,currentId);
        }else if (operationType == OperationType.UPDATE){
            // 赋值2个
            Method setUpdateTime = arg.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
            Method setUpdateUser = arg.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);
            setUpdateTime.invoke(arg,now);
            setUpdateUser.invoke(arg,currentId);
        }
    }
}
