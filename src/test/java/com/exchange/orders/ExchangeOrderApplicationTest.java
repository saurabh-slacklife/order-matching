package com.exchange.orders;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.Test;

class ExchangeOrderApplicationTest {

  class OrderTest implements Comparable<OrderTest> {

    Integer value;

    public void setValue(Integer value) {
      this.value = value;
    }

    public Integer getValue() {
      return value;
    }

    OrderTest(Integer val) {
      this.value = val;
    }

    @Override
    public int compareTo(OrderTest o) {
      return this.getValue().compareTo(o.getValue());
    }
  }

  @Test
  public void testPq() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
    final LocalTime parsedTime = LocalTime.parse("09:45", formatter);
//    final LocalDateTime parsedDateTime = LocalDateTime.parse("09:45", formatter);
    System.out.println(parsedTime.toString());
//    System.out.println(parsedDateTime.toString());


  }

}