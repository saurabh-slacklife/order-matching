package com.exchange.orders.domain;

import java.util.Comparator;

public class SellOrderComparator implements Comparator<Order> {

  /**
   * The exchange follows a FirstInFirstOut Price-Time order-matching rule, which states that: "The
   * first order in the order-book at a price level is the first order matched. All orders at the
   * same price level are filled according to time priority".
   */

  @Override
  public int compare(Order o1, Order o2) {
    if (o1.getPrice().compareTo(o2.getPrice()) == 0) {
      return o1.getLocalTime().isBefore(o2.getLocalTime()) == true ? -1 : 1;
    } else {
      return o1.getPrice().compareTo(o2.getPrice());
    }
  }
}
