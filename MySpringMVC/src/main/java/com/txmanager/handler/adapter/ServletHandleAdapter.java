package com.txmanager.handler.adapter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

@Component
public class ServletHandleAdapter implements HandlerAdapter {


  @Override
  public boolean support(Object handler) {
    return handler instanceof HttpServlet;
  }

  @Override
  public Object handle(HttpServletRequest req, HttpServletResponse resp, Object handler)
      throws Exception {
    ((HttpServlet) handler).service(req, resp);
    return null;
  }
}
