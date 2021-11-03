package com.register.register.registry;

import com.alibaba.fastjson.JSONObject;
import com.register.register.util.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RemoteMapRegister implements BeanPostProcessor{

  private static String host = "192.168.126.132";
  private static int port = 6379;

  @Autowired
  StringRedisTemplate redisTemplate;

  private static Map<String, List<URL>> REGISTER = new HashMap<>();

  @Value("${server.port}")
  private int serverPort;
  @Value("${application.name")
  private String applicationName;


  public void regist(String interfaceName, URL url) {
    // Redis方法
    redisTemplate.opsForList().rightPush(interfaceName, JSONObject.toJSONString(url));
  }

//
//  public List<URL> get(String interfaceName) {
//    return jedis.lrange(interfaceName, 0, -1).stream().map(json -> {
//      return JSONObject.parseObject(json, new TypeReference<URL>() {
//      });
//    }).collect(Collectors.toList());
//  }

  @Override
  public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
    System.out.println(serverPort);
    System.out.println(applicationName);
    return null;
  }
}
