package com.exchange.orders.repository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * Contract for interaction with Data Access layer based on Specific Exchange.
 */
public interface IExchangeOrderRepository<T> {

  /**
   * Persists the Order
   * @param order {@link T}
   * @return true or false
  * */
  boolean save(T order);

  /**
   * Find Order by orderId
   * @param id {@link String}
   * @return order {@link T}
   * */
  T findOrderById(String id);

  /**
   * Update Order
   * @param order {@link String}
   * @return true or false
   * */
  boolean update(T order);

  /**
   * Mark the Order completed
   * @param id {@link UUID}
   * @return true or false
   * */
  boolean markComplete(UUID id);

  /**
   * Get all the stocks in Exchange based on Stock name
   * @param stockName {@link String}
   * @return collection<T>
   * */
  Collection<T> getStocks(String stockName);


}
