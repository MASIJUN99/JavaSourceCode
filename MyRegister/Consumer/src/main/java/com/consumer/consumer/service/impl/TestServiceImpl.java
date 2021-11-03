package com.consumer.consumer.service.impl;

import com.consumer.consumer.service.TestService;
import com.register.register.annotations.RemoteClass;
import org.springframework.stereotype.Service;

@Service
@RemoteClass
public class TestServiceImpl implements TestService {

  @Override
  public String sayHello(String name) {
    return "Hello" + name;
  }
}
