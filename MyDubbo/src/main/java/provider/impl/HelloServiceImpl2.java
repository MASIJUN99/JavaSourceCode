package provider.impl;

import provider.HelloService;

public class HelloServiceImpl2 implements HelloService {

  @Override
  public String sayHello(String username) {
    return "Hello2 " + username;
  }
}
