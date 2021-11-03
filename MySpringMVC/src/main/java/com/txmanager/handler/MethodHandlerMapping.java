package com.txmanager.handler;

import com.txmanager.mvc.RequestMapping;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.BeansException;
import org.springframework.stereotype.Component;

@Component
public class MethodHandlerMapping implements HandlerMapping {

  static Map<String, RequestMappingInfo> map = new HashMap<>();

  @Override
  public Object getHandler(String url) {
    return map.get(url);
  }

  @Override
  public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
    String prefix = bean.getClass().isAnnotationPresent(RequestMapping.class) ? bean.getClass()
        .getAnnotation(RequestMapping.class).value() : "";  // class' RequestMapping

    for (Method method : bean.getClass().getDeclaredMethods()) {
      RequestMappingInfo requestMappingInfo = createRequestMappingInfo(method, bean);
      if (requestMappingInfo != null) {
        map.put(prefix + requestMappingInfo.getUrl(), requestMappingInfo);
      }
    }
    return bean;
  }

  private RequestMappingInfo createRequestMappingInfo(Method method, Object bean) {
    RequestMappingInfo requestMappingInfo = null;
    if (method.isAnnotationPresent(RequestMapping.class)) {
      requestMappingInfo = new RequestMappingInfo();
      requestMappingInfo.setMethod(method);
      requestMappingInfo.setBean(bean);
       requestMappingInfo.setUrl(method.getAnnotation(RequestMapping.class).value());
    }
    return requestMappingInfo;
  }
}

