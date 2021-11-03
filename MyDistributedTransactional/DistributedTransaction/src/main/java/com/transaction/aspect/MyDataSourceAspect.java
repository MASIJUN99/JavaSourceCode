package com.transaction.aspect;

import com.transaction.connection.MyConnection;
import java.sql.Connection;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class MyDataSourceAspect {

  @Around("execution(* javax.sql.DataSource.getConnection(..))")
  public Connection around(ProceedingJoinPoint point) throws Throwable {
    return new MyConnection((Connection) point.proceed());  // 装饰器
  }

}
