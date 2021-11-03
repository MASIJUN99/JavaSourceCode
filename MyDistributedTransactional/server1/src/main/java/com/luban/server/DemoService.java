package com.luban.server;

import com.transaction.annotation.DistributedTransactional;
import com.transaction.util.HttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DemoService {

  @Autowired
  private DemoDao demoDao;

  @DistributedTransactional
  @Transactional
  public void test() {
    demoDao.insert(1, 1, 1, 1);
    HttpClient.get("http://localhost:8082/server2/test");
    int i = 100 / 0;
  }

  public void test2() {
    demoDao.insert(3, 3, 3, 3);
  }
}
