package com.exchange.orders.dao;

import com.exchange.orders.common.OrderStatus;
import com.exchange.orders.common.OrderType;
import com.exchange.orders.dao.entity.OrderEntity;
import java.time.ZonedDateTime;
import java.util.Collection;


public class RedisDaoImpl implements IOrderDao {

  @Override
  public boolean save(OrderEntity type) {
    return false;
  }

  @Override
  public boolean update(OrderEntity type) {
    return false;
  }

  @Override
  public boolean delete(String id) {
    return false;
  }

  @Override
  public boolean updateStatus(String orderId, OrderStatus status) {
    return false;
  }

  @Override
  public OrderEntity findOrderById(String orderId) {
    return null;
  }

  @Override
  public Collection<OrderEntity> findOrdersByTypeDuration(OrderType type, ZonedDateTime inclusive,
      ZonedDateTime exclusive) {
    return null;
  }

  @Override
  public boolean expireOrderById(String orderId) {
    return false;
  }
}
