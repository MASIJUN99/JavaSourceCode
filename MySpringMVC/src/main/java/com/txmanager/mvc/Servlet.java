package com.txmanager.mvc;

import com.txmanager.handler.HandlerMapping;
import com.txmanager.handler.adapter.HandlerAdapter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Map;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Servlet extends HttpServlet {

  private Collection<HandlerMapping> handlers;
  private Collection<HandlerAdapter> adapters;

  @Override
  public void init(ServletConfig config) throws ServletException {
    // 通过配置文件的init-param获得spring容器的配置文件
    ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(
        config.getInitParameter("contextConfigLocation"));
    // 获得扫描到的map
    Map<String, HandlerMapping> handlerBeans = applicationContext.getBeansOfType(HandlerMapping.class);
    handlers = handlerBeans.values();
    Map<String, HandlerAdapter> adapterBeans = applicationContext.getBeansOfType(HandlerAdapter.class);
    adapters = adapterBeans.values();
    super.init(config);
  }

  private Object getHandlers(HttpServletRequest req) {
    if (!handlers.isEmpty()) {
      Object handler;
      for (HandlerMapping handlerMapping : handlers) {
        if ((handler = handlerMapping.getHandler(req.getRequestURI())) != null) {
           return handler;
        };
      }
    }
    return null;
  }

  private HandlerAdapter getAdapters(Object handle) {
    if (!adapters.isEmpty()) {
      for (HandlerAdapter adapter : adapters) {
        if (adapter.support(handle)) {
          return adapter;
        }
      }
    }
    return null;
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    Object handler = getHandlers(req);
    // 适配器模式，来匹配是HttpServlet还是Controller
    HandlerAdapter adapters = getAdapters(handler);

    Object result = null;
    try {
      // 执行
      result = adapters.handle(req, resp, handler);
    } catch (Exception e) {
      e.printStackTrace();
    }

    PrintWriter p = resp.getWriter();
//    p.println(JSON.toJSONString(result));
    p.println(result);

  }
}
