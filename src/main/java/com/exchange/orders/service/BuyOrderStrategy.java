package com.exchange.orders.service;

import com.exchange.orders.domain.BuyOrderComparator;
import com.exchange.orders.domain.Order;
import com.exchange.orders.domain.OrderTransaction;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.concurrent.locks.ReentrantLock;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@NoArgsConstructor
public class BuyOrderStrategy implements IOrderStrategy<Order> {
/*
  @Autowired
  private IExchangeOrderRepository<Order> exchangeOrderRepository;*/

  private Map<String, PriorityQueue<Order>> sellOrderMap;
  private Map<String, PriorityQueue<Order>> buyOrderMap;

  private final ReentrantLock lock = new ReentrantLock();

  public BuyOrderStrategy(Map<String, PriorityQueue<Order>> buyOrderMap) {
    this();
    this.buyOrderMap = buyOrderMap;
  }

  /**
   * Add and executes the order
   *
   * @param order {@link Order}
   */

  @Override
  public void processOrder(@NonNull final Order order) {
    if (this.addOrder(order)) {
      log.info("Buy Order={} added for processing", order);
      lock.lock();
      try {
        executeBuyOrder(order);
      } finally {
        lock.unlock();
      }
    }
  }

  private boolean addOrder(@NonNull final Order order) {
    if (!this.buyOrderMap.containsKey(order.getStockName())) {
      this.buyOrderMap.put(order.getStockName(), new PriorityQueue<>(new BuyOrderComparator()));
    }

//    TODO Add persist Buy stock through Exchange repository.
//     The implementation is omitted per the requirement
//    this.exchangeOrderRepository.save(order);

    return this.buyOrderMap.get(order.getStockName()).offer(order);
  }

  /**
   * The two If conditions and hence the method calls handle the Complete Match and Partial Buy
   * order match. i.e.
   * <p>
   * - If BuyOrderQuantity <= SellOrderQuantity, then execute Full Buy order and Full/Remaining Sell
   * order quantity
   * <p>
   * - Else BuyOrderQuantity -= BuyQuantity-SellOrderQuantity, then execute Sell Order, update
   * reference of BuyOrder in PriorityQueue
   * <p>
   *
   * @param buyOrder {@link Order}
   */
  private void executeBuyOrder(Order buyOrder) {
//    Nothing to SELL
    if (!this.sellOrderMap.containsKey(buyOrder.getStockName())) {
      log.info("There is no order available for sell for Stock={} and hence can't be bought.",
          buyOrder.getStockName());
      return;
    }

    PriorityQueue<Order> sellOrdersQueue = this.sellOrderMap
        .get(buyOrder.getStockName());

    while (sellOrdersQueue.size() > 0
        && buyOrder.getPrice().compareTo(sellOrdersQueue.peek().getPrice()) >= 0) {

      Order sellOrder = sellOrdersQueue.poll();

      if (buyOrder.getQuantity() <= sellOrder.getQuantity()) {
        handleCompleteMatch(buyOrder, sellOrder);
        this.buyOrderMap.get(buyOrder.getStockName()).remove(buyOrder);
      } else {
        handleAndUpdatePartialMatch(buyOrder, sellOrder);
      }

      if (buyOrder.getQuantity() == 0) {
        return;
      }
    }
  }

  private void handleCompleteMatch(Order buyOrder, Order sellOrder) {
    log.info("Executing complete match - BuyOrder={} and SellOrder={}", buyOrder, sellOrder);

    if (sellOrder.getQuantity() != buyOrder.getQuantity()) {
    /*TODO Mark order completed through IExchangeRepository.
       The implementation is omitted per the requirement
    this.exchangeOrderRepository.markComplete(sellOrder.getId());
    */

      sellOrder.setQuantity(sellOrder.getQuantity() - buyOrder.getQuantity());
    }

    var orderTransaction = logOrderTransaction(buyOrder, sellOrder, buyOrder.getQuantity());
    log.info("Buy Order Transaction completed={}", orderTransaction);

    /*TODO Mark order completed through IExchangeRepository.
       The implementation is omitted per the requirement
    this.exchangeOrderRepository.markComplete(buyOrder.getId());
    */
    this.buyOrderMap.get(buyOrder.getStockName()).remove(buyOrder);
  }

  private void handleAndUpdatePartialMatch(Order buyOrder, Order sellOrder) {

    long buyOrderQuantity = buyOrder.getQuantity();

    log.info("Executing partial match - BuyOrder={} and SellOrder={}", buyOrder, sellOrder);

    buyOrderQuantity -= sellOrder.getQuantity();
    buyOrder.setQuantity(buyOrderQuantity);

    var orderTransaction = logOrderTransaction(buyOrder, sellOrder, sellOrder.getQuantity());
    this.sellOrderMap.get(sellOrder.getStockName()).remove(sellOrder);
    sellOrder.setQuantity(sellOrder.getQuantity() - buyOrder.getQuantity());
    /*TODO Mark order completed through IExchangeRepository.
       The implementation is omitted per the requirement
      this.exchangeOrderRepository.markComplete(sellOrder.getId());
    */
    log.info("Buy Order Transaction completed={}", orderTransaction);
  }

  private OrderTransaction logOrderTransaction(Order buyOrder, Order sellOrder, long soldQuantity) {

    /*TODO Handle transaction history logging through IExchangeRepository.
       The implementation is omitted per the requirement*/

    return new OrderTransaction(buyOrder.getId(),
        sellOrder.getId(),
        soldQuantity,
        sellOrder.getPrice(),
        ZonedDateTime.now(ZoneId.of("UTC")));
  }

  public void setSellOrderMap(Map<String, PriorityQueue<Order>> sellOrderMap) {
    this.sellOrderMap = sellOrderMap;
  }
}
