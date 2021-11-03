package com.txmanager.handler.adapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface HandlerAdapter {

  boolean support(Object handler);  // 是否支持处理器

  Object handle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception;

}
