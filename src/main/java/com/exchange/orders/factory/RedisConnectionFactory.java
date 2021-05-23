package com.exchange.orders.factory;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.resource.ClientResources;

public class RedisConnectionFactory {

  final static ClientResources BUILD = ClientResources.builder().ioThreadPoolSize(40).build();


  public static RedisClient getClient(String host) {
    final RedisURI redisUri = RedisURI.builder().withHost(host)
        .withPort(RedisURI.DEFAULT_REDIS_PORT).withDatabase(0).build();
    return RedisClient.create(BUILD, redisUri);
  }

  public static RedisClient getClient(String host, int database) {
    final RedisURI redisUri = RedisURI.builder().withHost(host)
        .withPort(RedisURI.DEFAULT_REDIS_PORT).withDatabase(database).build();
    return RedisClient.create(BUILD, redisUri);
  }

  public static RedisClient getClient(String host, int port, int database) {
    final RedisURI redisUri = RedisURI.builder().withHost(host)
        .withPort(port).withDatabase(database).build();
    return RedisClient.create(BUILD, redisUri);
  }

  public static void shutdown() {
    BUILD.shutdown();
  }

}
