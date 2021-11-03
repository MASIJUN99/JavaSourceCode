package com.transaction.netty;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.transaction.constant.TransactionTypeEnum;
import com.transaction.transactional.Transaction;
import com.transaction.transactional.TransactionManager;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class NettyClientHandler extends ChannelInboundHandlerAdapter {

  private ChannelHandlerContext context;

  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    context = ctx;
  }

  @Override
  public synchronized void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    System.out.println("接受数据:" + msg.toString());
    JSONObject jsonObject = JSON.parseObject((String) msg);

    String groupId = jsonObject.getString("groupId");
    String command = jsonObject.getString("command");

    System.out.println("接收command:" + command);

    // 拿到本地事务
    Transaction transaction = TransactionManager.getTransaction(groupId);

    if (TransactionTypeEnum.COMMIT.equals(command) && TransactionTypeEnum.COMMIT
        .equals(transaction.getTransactionType().getType())) {
      transaction.setTransactionType(TransactionTypeEnum.COMMIT);
    } else {
      transaction.setTransactionType(TransactionTypeEnum.ROLLBACK);
    }

    // 执行
    transaction.getTask().signalTask();

  }

  public synchronized Object call(JSONObject data) throws Exception {
    context.writeAndFlush(data.toJSONString());
    return null;
  }
}
