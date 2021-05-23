package com.exchange.orders.errors;

public enum ErrorMessages {

  FUTURE_DATE_ERROR("Order placed for future date", 404),
  INVALID_QUANTITY_ERROR("Invalid quantity for order", 404),
  INVALID_PRICE_ERROR("Invalid price for order", 404),
  INVALID_ORDER_TYPE_ERROR("Un-supported order type", 404);

  String message;
  int statusCode;

  ErrorMessages(String message, int statusCode) {
    this.message = message;
    this.statusCode = statusCode;
  }


  public String getMessage() {
    return message;
  }

  public int getStatusCode() {
    return statusCode;
  }


}
