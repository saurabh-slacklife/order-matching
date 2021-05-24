package com.exchange.orders.service;

import com.exchange.orders.domain.Order;
import com.exchange.orders.domain.OrderTransaction;
import com.exchange.orders.domain.SellOrderComparator;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.concurrent.locks.ReentrantLock;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@NoArgsConstructor
@Getter
@Slf4j
public class SellOrderStrategy implements IOrderStrategy<Order> {

/*  @Autowired
  private IExchangeOrderRepository<Order> exchangeOrderRepository;*/

  private Map<String, PriorityQueue<Order>> sellOrderMap;
  private Map<String, PriorityQueue<Order>> buyOrderMap;

  private final ReentrantLock lock = new ReentrantLock();

  public SellOrderStrategy(Map<String, PriorityQueue<Order>> sellOrderMap) {
    this();
    this.sellOrderMap = sellOrderMap;
  }

  @Override
  public void processOrder(@NonNull final Order sellOrder) {
    if (this.addOrder(sellOrder)) {
      log.info("Sell Order={} added for processing", sellOrder);
      lock.lock();
      try {
        executeSellOrder(sellOrder);
      } finally {
        lock.unlock();
      }
    }
  }

  private boolean addOrder(@NonNull final Order sellOrder) {
    if (!this.sellOrderMap.containsKey(sellOrder.getStockName().toUpperCase())) {
      this.sellOrderMap
          .put(sellOrder.getStockName(), new PriorityQueue<>(new SellOrderComparator()));
    }

/*        TODO Add persist Sell stock through Exchange repository.
           The implementation is omitted per the requirement
    this.exchangeOrderRepository.save(order);*/

    return this.sellOrderMap.get(sellOrder.getStockName()).offer(sellOrder);
  }

  private void executeSellOrder(Order sellOrder) {
    if (!this.buyOrderMap.containsKey(sellOrder.getStockName())) {
      log.info("There is no order for buy, hence can't sell");
      return;
    }

    final PriorityQueue<Order> buyOrders = this.buyOrderMap.get(sellOrder.getStockName());

    while (buyOrders.size() > 0
        && buyOrders.peek().getPrice().compareTo(sellOrder.getPrice()) >= 0) {

      final Order buyOrder = buyOrders.poll();

      if (buyOrder.getQuantity() <= sellOrder.getQuantity()) {
        executeCompleteBuyOrderMatch(buyOrder, sellOrder);
      } else {
        executePartialBuyOrderMatch(buyOrder, sellOrder);
        buyOrders.offer(buyOrder);
      }

      if (sellOrder.getQuantity() == 0) {
        return;
      }
    }
  }

  private void executeCompleteBuyOrderMatch(Order buyOrder, Order sellOrder) {

    log.info("Executing complete match - SellOrder={} and BuyOrder={}", sellOrder, buyOrder);

    if (sellOrder.getQuantity() == buyOrder.getQuantity()) {
      this.sellOrderMap.get(sellOrder.getStockName()).remove(sellOrder);
    }
    sellOrder.setQuantity(sellOrder.getQuantity() - buyOrder.getQuantity());


      /*          TODO Update Sell stock through Exchange repository.
             The implementation is omitted per the requirement
      this.exchangeOrderRepository.update(sellOrder);*/

    final OrderTransaction orderTransaction = logOrderTransaction(buyOrder, sellOrder,
        buyOrder.getQuantity());

    log.info("Complete Order Transaction completed={}", orderTransaction);
      /*TODO Mark Buy order completed through Exchange repository.
      The implementation is omitted per the requirement
      this.exchangeOrderRepository.markComplete(buyOrder.getId());
      */

  }

  private void executePartialBuyOrderMatch(Order buyOrder, Order sellOrder) {
    long buyOrderQuantity = buyOrder.getQuantity();

    log.info("Executing partial match -  SellOrder={} and BuyOrder={}", sellOrder, buyOrder);

    buyOrderQuantity -= sellOrder.getQuantity();
    buyOrder.setQuantity(buyOrderQuantity);
    final OrderTransaction orderTransaction = logOrderTransaction(buyOrder, sellOrder,
        sellOrder.getQuantity());

    /*TODO Mark Buy order completed through Exchange repository.
       The implementation is omitted per the requirement
      this.exchangeOrderRepository.markComplete(sellOrder.getId());
    */
    this.sellOrderMap.get(sellOrder.getStockName()).remove(sellOrder);
    sellOrder.setQuantity(sellOrder.getQuantity() - buyOrder.getQuantity());

    log.info("Partial Order Transaction completed={}", orderTransaction);
  }

  private OrderTransaction logOrderTransaction(Order buyOrder, Order sellOrder, long soldQuantity) {
        /*TODO Handle transaction history logging through IExchangeRepository.
           The implementation is omitted per the requirement
           */
    return new OrderTransaction(buyOrder.getId(),
        sellOrder.getId(),
        soldQuantity,
        sellOrder.getPrice(),
        ZonedDateTime.now(ZoneId.of("UTC")));
  }

  public void setBuyOrderMap(
      Map<String, PriorityQueue<Order>> buyOrderMap) {
    this.buyOrderMap = buyOrderMap;
  }

}
