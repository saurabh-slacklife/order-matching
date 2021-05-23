package com.exchange.orders.service;

import lombok.NonNull;

public interface IOrderStrategy<T> {
  void processOrder(@NonNull T order);

}
