package com.register.register.protocol.http;

import com.alibaba.fastjson.JSONObject;
import framework.Invocation;
import framework.registry.LocalRegister;
import java.lang.reflect.Method;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;


public class HttpServerHandler {
  public void handler(HttpServletRequest req, HttpServletResponse resp) {
    // 处理请求的逻辑

    // 解析请求->查找调用的什么服务
    // 如何确定具体服务？ 1.类名 2.方法名 3.参数类型 4参数
    try {
      Invocation invocation = JSONObject.parseObject(req.getInputStream(), Invocation.class);

      // 根据以下内容通过反射找到实现类
      String interfaceName = invocation.getInterfaceName();
      String methodName = invocation.getMethodName();
      Class[] paramTypes = invocation.getParamTypes();
      // 在注册的服务中找到指定的服务
      Class implClass = LocalRegister.get(interfaceName);
      Method method = implClass.getMethod(methodName, paramTypes);
      // 执行
      String res = (String) method.invoke(implClass.newInstance(), invocation.getParams());
      // 写入结果
      IOUtils.write(res, resp.getOutputStream());

    } catch (Exception e) {
      e.printStackTrace();
    }

  }
}
