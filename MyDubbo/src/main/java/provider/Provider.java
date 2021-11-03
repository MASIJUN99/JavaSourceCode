package provider;

import framework.protocol.dubbo.DubboServer;
import framework.protocol.http.HttpServer;
import framework.registry.LocalRegister;
import framework.registry.RemoteMapRegister;
import framework.util.URL;
import provider.impl.HelloServiceImpl;
import provider.impl.HelloServiceImpl2;

public class Provider {

  public static void main(String[] args) {

    // 要有暴露服务的过程
    LocalRegister.regist(HelloService.class.getName(), HelloServiceImpl2.class);
    RemoteMapRegister.regist(HelloService.class.getName(), new URL("localhost", 8080));

    // 启动消费者
    // 根据配置选择网络请求中间件，例如Tomcat Netty Jetty
    HttpServer httpServer = new HttpServer();
    httpServer.start("localhost", 8080);

  }

}
