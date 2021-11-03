package com.register.register;

import framework.protocol.http.HttpClient;
import framework.registry.RemoteMapRegister;
import framework.util.URL;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProxyFactory {

  public static <T> T getProxy(final Class interfaceClass) {

    Map<String, List<URL>> cache = new HashMap<>();

    // jdk动态代理，返回代理对象，执行的逻辑就是发送请求返回T结果
    return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class[]{interfaceClass},
        new InvocationHandler() {
          @Override
          public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            HttpClient httpClient = new HttpClient();
            Invocation invocation = new Invocation(interfaceClass.getName(), method.getName(), method.getParameterTypes(),
                args);
            // 配置注册中心地址，所有的服务都要放在注册中心
            List<URL> urls = new ArrayList<>();
            if (cache.containsKey(interfaceClass.getName())) {
              urls = cache.get(interfaceClass.getName());
            } else {
              urls = RemoteMapRegister.get(interfaceClass.getName());
              cache.put(interfaceClass.getName(), urls);
            }

            // 负载均衡选择url
            URL url = LoadBalancer.getURL(urls);

            return httpClient.send(url.getHostname(), url.getPort(), invocation);
          }
        });
  }

}
