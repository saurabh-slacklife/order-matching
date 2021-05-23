package com.exchange.orders.dao;

import com.exchange.orders.common.OrderStatus;
import com.exchange.orders.common.OrderType;
import com.exchange.orders.dao.entity.OrderEntity;
import java.time.ZonedDateTime;
import java.util.Collection;

public interface IOrderDao extends IExchangeOrderDao<OrderEntity> {

  OrderEntity findOrderById(String orderId);

  Collection<OrderEntity> findOrdersByTypeDuration(OrderType type, ZonedDateTime inclusive,
      ZonedDateTime exclusive);

  boolean expireOrderById(String orderId);

  default boolean updateOrderStatus(String orderId, OrderStatus status) {
    return this.updateStatus(orderId, status);
  }

}
