package com.exchange.orders.dao.entity;

import com.exchange.orders.common.OrderType;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderEntity {

//  @Id
  private String id;
  private String stockName;
  private OrderType type;
  private long placedTs;
  private long quantity;
  private BigDecimal price;

}
