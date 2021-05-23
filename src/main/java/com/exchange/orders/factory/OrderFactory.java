package com.exchange.orders.factory;

import com.exchange.orders.common.OrderType;
import com.exchange.orders.domain.Order;
import com.exchange.orders.errors.UnSupportedOrderType;
import com.exchange.orders.service.BuyOrderStrategy;
import com.exchange.orders.service.IOrderStrategy;
import com.exchange.orders.service.SellOrderStrategy;
import java.util.PriorityQueue;
import java.util.concurrent.ConcurrentHashMap;

import static com.exchange.orders.errors.ErrorMessages.INVALID_ORDER_TYPE_ERROR;

public class OrderFactory {

  private static BuyOrderStrategy buyOrderStrategy;
  private static SellOrderStrategy sellOrderStrategy;

  static {

    buyOrderStrategy = new BuyOrderStrategy(
        new ConcurrentHashMap<String, PriorityQueue<Order>>());

    sellOrderStrategy = new SellOrderStrategy(
        new ConcurrentHashMap<String, PriorityQueue<Order>>());

    buyOrderStrategy.setSellOrderMap(sellOrderStrategy.getSellOrderMap());
    sellOrderStrategy.setBuyOrderMap(buyOrderStrategy.getBuyOrderMap());

  }

  public static IOrderStrategy<Order> getOrderService(OrderType orderType)
      throws UnSupportedOrderType {

    switch (orderType) {
      case SELL:
        return sellOrderStrategy;
      case BUY:
        return buyOrderStrategy;
      default:
        throw new UnSupportedOrderType(INVALID_ORDER_TYPE_ERROR.getMessage(),
            INVALID_ORDER_TYPE_ERROR.getStatusCode());
    }
  }
}
