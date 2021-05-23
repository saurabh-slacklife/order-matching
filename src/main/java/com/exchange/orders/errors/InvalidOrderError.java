package com.exchange.orders.errors;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class InvalidOrderError extends RuntimeException {

  private String message;
  private int statusCode;

  public InvalidOrderError(String message, int statusCode) {
    this.message = message;
    this.statusCode = statusCode;
  }
}
