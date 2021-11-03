package com.me.test;

import com.me.spring.Autowire;
import com.me.spring.Component;
import com.me.spring.InitializingBean;
import com.me.spring.Scope;

@Component("userService")
@Scope("singleton")
public class UserServiceImpl implements UserService, InitializingBean {

    @Autowire
    OrderService orderService;

    private User defaultUser;

    @Override
    public void test() {
        System.out.println(orderService);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        defaultUser = new User();
    }

    public User getDefaultUser() {
        return defaultUser;
    }
}
