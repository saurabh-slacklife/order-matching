package com.exchange.orders.service;

import com.exchange.orders.domain.Order;
import com.exchange.orders.factory.OrderFactory;
import com.exchange.orders.repository.IExchangeOrderRepository;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor
public class OrderBookService {

  private IExchangeOrderRepository<Order> exchangeOrderRepository;

/*  public OrderBookService(final IExchangeOrderRepository<Order> exchangeOrderRepository) {
    this();
    this.exchangeOrderRepository = exchangeOrderRepository;
  }*/

  public void processOrder(Order order) {
    final IOrderStrategy orderStrategy = OrderFactory.getOrderService(order.getType());
    orderStrategy.processOrder(order);
  }
}
