package com.luban.server;

import com.transaction.annotation.DistributedTransactional;
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
        demoDao.insert(2,2,2,2);
    }
}
