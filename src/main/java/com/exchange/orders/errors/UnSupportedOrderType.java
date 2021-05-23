package com.exchange.orders.errors;

public class UnSupportedOrderType extends RuntimeException {

  private String message;
  private int statusCode;

  public UnSupportedOrderType(String message, int statusCode) {
    this.message = message;
    this.statusCode = statusCode;
  }


}
