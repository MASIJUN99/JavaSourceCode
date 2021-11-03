package com.txmanager.netty;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.txmanager.constant.TransactionCommandEnum;
import com.txmanager.constant.TransactionTypeEnum;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NettyServerHandler extends ChannelInboundHandlerAdapter {

  private Map<String, List<String>> transactionTypeMap = new HashMap<>();
  private Map<String, Boolean> isEndMap = new HashMap<>();
  private Map<String, Integer> transactionCountMap = new HashMap<>();

  private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

  @Override
  public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
    Channel channel = ctx.channel();
    channelGroup.add(ctx.channel());
  }

  @Override
  public synchronized void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    System.out.println("接收数据" + msg.toString());

    JSONObject jsonObject = JSON.parseObject((String) msg);
    String command = jsonObject.getString("command");
    String groupId = jsonObject.getString("groupId");
    String transactionType = jsonObject.getString("transactionType");
    Integer transactionCount = jsonObject.getInteger("transactionCount");
    Boolean isEnd = jsonObject.getBoolean("isEnd");

    if (TransactionCommandEnum.CREATE.equals(command)) {
      // 如果是新建事务
      transactionTypeMap.put(groupId, new ArrayList<String>());
    } else if (TransactionCommandEnum.ADD.equals(command)) {
      transactionTypeMap.get(groupId).add(transactionType);

      if (isEnd) {
        isEndMap.put(groupId, true);
        transactionCountMap.put(groupId, transactionCount);
      }

      JSONObject result = new JSONObject();
      result.put("groupId", groupId);
      if (isEndMap.get(groupId) &&
          transactionCountMap.get(groupId).equals(transactionTypeMap.get(groupId).size())) {

        if (transactionTypeMap.get(groupId).contains(TransactionTypeEnum.ROLLBACK.getType())) {
          // 如果包含了rollback就要全滚
          result.put("command", TransactionTypeEnum.ROLLBACK.getType());
          sendResult(result);
        } else {
          result.put("command", TransactionTypeEnum.COMMIT.getType());
          sendResult(result);
        }
      }
    }
  }

  private void sendResult(JSONObject result) {
    for (Channel channel : channelGroup) {
      System.out.println("发送数据:" + result.toJSONString());
      channel.writeAndFlush(result.toJSONString());
    }
  }
}
