package consumer;


import framework.Invocation;
import framework.ProxyFactory;
import framework.protocol.http.HttpClient;
import provider.HelloService;

public class Consumer {

  public static void main(String[] args) {

    HelloService helloService = ProxyFactory.getProxy(HelloService.class);
    String res = helloService.sayHello("MASIJUN");

    System.out.println(res);


  }

}
