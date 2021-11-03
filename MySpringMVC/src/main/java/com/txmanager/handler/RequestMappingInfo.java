package com.txmanager.handler;

import java.lang.reflect.Method;

public class RequestMappingInfo {

  private Method method;
  private Object bean;
  private String url;

  public Method getMethod() {
    return method;
  }

  public void setMethod(Method method) {
    this.method = method;
  }

  public Object getBean() {
    return bean;
  }

  public void setBean(Object bean) {
    this.bean = bean;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }
}

