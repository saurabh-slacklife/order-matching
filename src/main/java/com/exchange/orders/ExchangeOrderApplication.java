package com.exchange.orders;

import com.exchange.orders.common.OrderType;
import com.exchange.orders.domain.Order;
import com.exchange.orders.service.OrderBookService;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class ExchangeOrderApplication {

  public static void main(String[] args) {

    if (args.length < 1) {
      log.error("Input file path isn't provided. Application will exit");
      System.exit(1);
    }

    String filePath = args[0];

    final List<Order> orders = new ArrayList<>();

    try (BufferedReader bufferedReader = new BufferedReader(
        new FileReader(filePath))) {

      String line;
      while ((line = bufferedReader.readLine()) != null) {
        log.info("Parsing line={}", line);
        orders.add(parseLineToOrder(line));
      }


    } catch (FileNotFoundException e) {
      log.error("File not found = {}", filePath, e);
      log.error(" Application will exit");
      System.exit(1);
    } catch (IOException e) {
      log.error("File not found = {}", filePath, e);
      log.error(" Application will exit");
      System.exit(1);
    }

    final OrderBookService orderBookService = new OrderBookService();
    orders.stream().forEach(o -> orderBookService.processOrder(o));

  }

  private static Order parseLineToOrder(String line) {
//    #1 09:45 BAC sell 240.12 100
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

}
