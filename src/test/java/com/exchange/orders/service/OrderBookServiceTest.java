package com.exchange.orders.service;

import com.exchange.orders.common.OrderType;
import com.exchange.orders.domain.Order;
import com.exchange.orders.errors.UnSupportedOrderType;
import com.sun.security.auth.UnixNumericUserPrincipal;
import java.math.BigDecimal;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class OrderBookServiceTest {

  Order buyOrder;
  Order sellOrder;

  @BeforeEach
  public void createBuyOrder() {
    buyOrder = new Order();
    buyOrder.setId(UUID.randomUUID().toString());
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
    buyOrder.setLocalTime(LocalTime.parse("09:10", formatter));
    buyOrder.setStockName("BAC");
    buyOrder.setType(OrderType.BUY);
    buyOrder.setQuantity(10l);
    buyOrder.setPrice(BigDecimal.TEN);
  }

  @BeforeEach
  public void createSellOrder() {
    sellOrder = new Order();
    sellOrder.setId(UUID.randomUUID().toString());
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
    sellOrder.setLocalTime(LocalTime.parse("09:11", formatter));
    sellOrder.setStockName("BAC");
    sellOrder.setType(OrderType.SELL);
    sellOrder.setQuantity(5l);
    sellOrder.setPrice(BigDecimal.TEN);
  }

  @Test
  void whenValidBuyOrder_thenCreateBuyOrderStrategy() {
    final OrderBookService orderBookService = new OrderBookService();
    orderBookService.processOrder(buyOrder);
  }

  @Test
  void whenValidSellOrder_thenCreateBuyOrderStrategy() {
    final OrderBookService orderBookService = new OrderBookService();
    orderBookService.processOrder(sellOrder);
  }

  @Test
  void whenValidOrderTransaction_thenProcessOrders() {
    final OrderBookService orderBookService = new OrderBookService();
    orderBookService.processOrder(buyOrder);
    orderBookService.processOrder(sellOrder);
  }

}