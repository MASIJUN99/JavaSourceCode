package com.me.spring;

import com.sun.istack.internal.Nullable;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public interface BeanPostProcessor {

  default Object postProcessorBeforeInitialization(Object bean, String beanName) {
    return bean;
  }

  default Object postProcessorAfterInitialization(Object bean, String beanName) {
    return bean;
  }
}
