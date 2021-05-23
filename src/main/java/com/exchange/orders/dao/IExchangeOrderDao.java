package com.exchange.orders.dao;

import com.exchange.orders.common.OrderStatus;

public interface IExchangeOrderDao<T> {

  boolean save(T type);

  boolean update(T type);

  boolean delete(String id);

  boolean updateStatus(String orderId, OrderStatus status);
}
