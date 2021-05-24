package com.exchange.orders.domain;

import com.exchange.orders.common.OrderType;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class SellOrderComparatorTest {

  static List<Order> orders;

  @BeforeAll
  public static void generateOrders() {
    orders = new ArrayList<>();

    try (BufferedReader bufferedReader = new BufferedReader(
        new FileReader("src/test/resources/sell-orders.txt"))) {

      String line;
      while ((line = bufferedReader.readLine()) != null) {
        orders.add(parseLineToOrder(line));
      }
    } catch (FileNotFoundException e) {
      log.error("Invalid file path", e);
      System.exit(1);
    } catch (IOException e) {
      log.error("Invalid file path", e);
      System.exit(1);
    }
  }

  private static Order parseLineToOrder(String line) {
    final String[] inputOrder = line.split("\\s+");
    final Order order = new Order();
    order.setId(inputOrder[0]);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
    order.setLocalTime(LocalTime.parse(inputOrder[1], formatter));
    order.setStockName(inputOrder[2]);
    order.setType(OrderType.valueOf(inputOrder[3].toUpperCase()));
    order.setPrice(new BigDecimal(inputOrder[4]));
    order.setQuantity(Long.parseLong(inputOrder[5]));
    return order;

  }

  /**
   * The Queue are specific to Stock Name, hence the ordering of orders are independent of Stock
   * Names. In The application code, a Map<StockName, PriorityQueue>, hence a separate Queue for
   * each stock
   */
  @Test
  public void testValidaOrderSellQueue() {
    final PriorityQueue<Order> pQueueSellOrders = new PriorityQueue<>(new SellOrderComparator());
    orders.stream().forEach(o -> pQueueSellOrders.offer(o));
    List<String> expectedOrderIdsInOrder = buildSellOrders();

    IntStream.range(0, orders.size()).forEach(i -> {
      final Order sellOrder = pQueueSellOrders.poll();
      System.out.println(sellOrder.getId());
      assertEquals(expectedOrderIdsInOrder.get(i), sellOrder.getId());
    });
  }

  private static List<String> buildSellOrders() {
// The order is build per the expected execution based on orders listed in "src/test/resources/sell-orders.txt"
    List<String> orderIdList = new ArrayList<>();
    orderIdList.add("#3");
    orderIdList.add("#4");
    orderIdList.add("#6");
    orderIdList.add("#5");
    orderIdList.add("#2");
    orderIdList.add("#1");

    return orderIdList;
  }

}