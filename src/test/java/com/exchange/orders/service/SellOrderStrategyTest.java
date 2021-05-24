package com.exchange.orders.service;

import com.exchange.orders.common.OrderType;
import com.exchange.orders.domain.BuyOrderComparator;
import com.exchange.orders.domain.Order;
import com.exchange.orders.domain.SellOrderComparator;
import java.math.BigDecimal;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.PriorityQueue;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SellOrderStrategyTest {

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
  void whenNoSellOrderPresent_thenAddBuyOrderAndExit() {

    SellOrderStrategy sellOrderStrategy = new SellOrderStrategy(
        new ConcurrentHashMap<>());
    final ConcurrentHashMap<String, PriorityQueue<Order>> buyOrderMap = new ConcurrentHashMap<>();
    sellOrderStrategy.setBuyOrderMap(buyOrderMap);
    sellOrderStrategy.processOrder(sellOrder);
  }

  @Test
  void whenSellOrderPresent_thenAddAndProcessBuyOrderAndExit() {

    final ConcurrentHashMap<String, PriorityQueue<Order>> buyOrderMap = new ConcurrentHashMap<>();
    final PriorityQueue<Order> buyOrderQueue = new PriorityQueue<>(new BuyOrderComparator());
    buyOrderQueue.offer(buyOrder);
    buyOrderMap.put("BAC",buyOrderQueue);

    SellOrderStrategy sellOrderStrategy = new SellOrderStrategy(
        new ConcurrentHashMap<>());
    sellOrderStrategy.setBuyOrderMap(buyOrderMap);
    sellOrderStrategy.processOrder(sellOrder);
  }
}