package com.exchange.orders.repository;

import com.exchange.orders.dao.IOrderDao;
import com.exchange.orders.domain.Order;
import java.util.Collection;
import java.util.UUID;


public class NseExchangeRepositoryImpl implements IExchangeOrderRepository<Order> {

  private IOrderDao orderDao;

  public NseExchangeRepositoryImpl(IOrderDao orderDao) {
    this.orderDao = orderDao;
  }

  @Override
  public boolean save(Order order) {
    return false;
  }

  @Override
  public Order findOrderById(String id) {
    return null;
  }

  @Override
  public boolean update(Order order) {
    return false;
  }

  @Override
  public boolean markComplete(UUID id) {
    return false;
  }

  @Override
  public Collection<Order> getStocks(String stockName) {
    return null;
  }
}
