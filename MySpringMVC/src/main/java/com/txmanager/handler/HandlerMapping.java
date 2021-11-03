package com.txmanager.handler;

import org.springframework.beans.factory.config.BeanPostProcessor;

public interface HandlerMapping extends BeanPostProcessor {

  Object getHandler(String url);

}
