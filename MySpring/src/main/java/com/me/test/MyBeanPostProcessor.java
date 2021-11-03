package com.me.test;

import com.me.spring.BeanPostProcessor;
import com.me.spring.Component;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

@Component
public class MyBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessorBeforeInitialization(Object bean, String beanName) {
        if (beanName.equals("userService")) {
            // 此处应该判断是否存在切点
            Object proxyInstance = Proxy.newProxyInstance(BeanPostProcessor.class.getClassLoader(),
                bean.getClass().getInterfaces(), new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        // 代理逻辑
                        System.out.println("执行代理对象的逻辑");
                        return method.invoke(bean, args);
                    }
                });
            return proxyInstance;
        }
        return bean;
    }

    @Override
    public Object postProcessorAfterInitialization(Object bean, String beanName) {
        if (beanName.equals("userService")) {
            System.out.println("初始化后");
        }
        return bean;
    }
}
