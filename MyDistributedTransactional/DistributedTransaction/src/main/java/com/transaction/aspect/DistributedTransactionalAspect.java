package com.transaction.aspect;

import com.transaction.constant.TransactionTypeEnum;
import com.transaction.transactional.TransactionManager;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class DistributedTransactionalAspect implements Ordered {

  @Around("@annotation(com.transaction.annotation.DistributedTransactional)")
  public void invoke(ProceedingJoinPoint point) {
    // 创建全局事务
    String groupId = TransactionManager.createTransactionGroup();

    try {
      point.proceed();
      // 成功，注册一个提交的分支事务
      TransactionManager.addTransaction(groupId, TransactionTypeEnum.COMMIT);
    } catch (Throwable throwable) {
      // 注册回滚事务
      TransactionManager.addTransaction(groupId, TransactionTypeEnum.ROLLBACK);
      throwable.printStackTrace();
    }

  }

  @Override
  public int getOrder() {
    return 0;
  }
}
