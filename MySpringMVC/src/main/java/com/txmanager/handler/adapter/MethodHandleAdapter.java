package com.txmanager.handler.adapter;

import com.txmanager.handler.RequestMappingInfo;
import com.txmanager.mvc.RequestParam;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

@Component
public class MethodHandleAdapter implements HandlerAdapter {


  @Override
  public boolean support(Object handler) {
    return handler instanceof RequestMappingInfo;
  }

  @Override
  public Object handle(HttpServletRequest req, HttpServletResponse resp, Object handler)
      throws Exception {
    Map<String, String[]> parameterMap = req.getParameterMap();
    RequestMappingInfo requestMappingInfo = (RequestMappingInfo) handler;
    Method method = requestMappingInfo.getMethod();

    Object[] params = new Object[method.getParameterCount()];
    int i = 0;
    for (Parameter parameter : method.getParameters()) {
      if (parameter.isAnnotationPresent(RequestParam.class)) {
        String[] values = parameterMap.get(parameter.getAnnotation(RequestParam.class).value());
        if (values.length != 0) {
          params[i++] = values[0];
        }
      }
    }

    return method.invoke(requestMappingInfo.getBean(), params);
  }
}
