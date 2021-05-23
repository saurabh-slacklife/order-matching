package com.exchange.orders.domain;

import com.exchange.orders.common.OrderType;
import com.exchange.orders.errors.InvalidOrderError;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import static com.exchange.orders.errors.ErrorMessages.INVALID_PRICE_ERROR;
import static com.exchange.orders.errors.ErrorMessages.INVALID_QUANTITY_ERROR;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Order {

  private String id;
  @NonNull
  private String stockName;
  @NonNull
  private OrderType type;
  @NonNull
  private LocalTime localTime;

  // TODO I'm not aware of the bound of quantity hence using 64 bits
  private long quantity;
  @NonNull
  private BigDecimal price;


  /*Assuming that the orders are placed on same day and hence no validation logic on Exchange market time window
   * Else ZonedDateTime can be used*/
/*  public void setPlacedDateTime(final ZonedDateTime placedDateTime) {
    if (LocalDate.now(ZoneId.of("UTC")).getDayOfYear() > placedDateTime.getDayOfYear()) {
      throw new FutureOrderError(FUTURE_DATE_ERROR.getMessage(),
          FUTURE_DATE_ERROR.getStatusCode());
    }
    this.placedDateTime = placedDateTime;
  }*/

  public void setQuantity(long quantity) {
    if (0L <= quantity) {
      this.quantity = quantity;
    } else {
      throw new InvalidOrderError(INVALID_QUANTITY_ERROR.getMessage(),
          INVALID_QUANTITY_ERROR.getStatusCode());
    }
  }

  public void setPrice(final BigDecimal price) {
    if (null != price && price.compareTo(BigDecimal.ZERO) >= 0) {
      this.price = price.setScale(2, RoundingMode.HALF_UP);
    } else {
      throw new InvalidOrderError(INVALID_PRICE_ERROR.getMessage(),
          INVALID_PRICE_ERROR.getStatusCode());
    }
  }
}