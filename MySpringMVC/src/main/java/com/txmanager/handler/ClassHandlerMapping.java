package com.txmanager.handler;

import com.txmanager.mvc.RequestMapping;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.BeansException;
import org.springframework.stereotype.Component;

@Component
public class ClassHandlerMapping implements HandlerMapping {

  static Map<String, Object> map = new HashMap<>();

  @Override
  public Object getHandler(String url) {
    return map.get(url);
  }

  @Override
  public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
    if (beanName.startsWith("/")) {
      map.put(beanName, bean);
    } else if (bean.getClass().isAnnotationPresent(RequestMapping.class)) {
      map.put(bean.getClass().getAnnotation(RequestMapping.class).value(), bean);
    }
    return bean;
  }
}
