package com.exchange.orders.domain;

import com.exchange.orders.errors.InvalidOrderError;
import java.math.BigDecimal;
import java.math.RoundingMode;
import org.junit.jupiter.api.Test;

import static com.exchange.orders.errors.ErrorMessages.INVALID_PRICE_ERROR;
import static com.exchange.orders.errors.ErrorMessages.INVALID_QUANTITY_ERROR;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OrderTest {

  @Test
  void whenPriceLessThanZero_thenThrowInvalidOrderError() {

    final Order order = new Order();
    assertThrows(InvalidOrderError.class, () -> order.setPrice(new BigDecimal(-1.1)),
        INVALID_PRICE_ERROR.getMessage());
  }

  @Test
  void whenPriceMoreThanZeroWithOnePrecision_thenSetPrice() {
    final Order order = new Order();
    order.setPrice(new BigDecimal(1.1));

    final BigDecimal expectedBD = new BigDecimal(1.1).setScale(2, RoundingMode.HALF_UP);

    assertEquals(order.getPrice(), expectedBD);
    assertEquals(order.getPrice().precision(), expectedBD.precision());
  }

  @Test
  void whenQuantityMoreThanZero_thenSetQuantity() {
    final Order order = new Order();
    order.setQuantity(1L);

    assertEquals(order.getQuantity(), 1);
  }

  @Test
  void whenQuantityLessThanZero_thenThrowInvalidOrderError() {
    final Order order = new Order();

    assertThrows(InvalidOrderError.class, () -> order.setQuantity(-1L),
        INVALID_QUANTITY_ERROR.getMessage());
  }

}