package com.exchange.orders.domain;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class OrderTransaction {

  private String buyOrderId;
  private String sellOrderId;
  private long quantity;
  private BigDecimal sellAmount;
  private ZonedDateTime transactionTime;

  @Override
  public String toString() {
    return "#" + buyOrderId + " " + sellAmount + " " + quantity + " " + sellOrderId;
  }

}
