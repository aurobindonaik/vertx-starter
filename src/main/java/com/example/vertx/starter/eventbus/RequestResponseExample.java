package com.example.vertx.starter.eventbus;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestResponseExample extends AbstractVerticle {
  private static final Logger LOG = LoggerFactory.getLogger(RequestResponseExample.class);

  public static void main(String[] args) {
    var vertx = Vertx.vertx();
    vertx.deployVerticle(new RequestVerticle());
    vertx.deployVerticle(new ResponseVerticle());
  }
  static class RequestVerticle extends AbstractVerticle {
    static final String EVENTBUS_ADDRESS = "my.request.address";
    @Override
    public void start(Promise<Void> startPromise) throws Exception {
      startPromise.complete();
      var eventBus = vertx.eventBus();
      final String message = "Hello World";
      LOG.debug("Sending Message: {}", message);
      eventBus.<String>request(EVENTBUS_ADDRESS, message, reply -> {
        LOG.debug("Response: {}", reply.result().body());
      });
    }
  }

  static class ResponseVerticle extends AbstractVerticle {
    @Override
    public void start(Promise<Void> startPromise) throws Exception {
      startPromise.complete();
      var eventBus = vertx.eventBus();
      eventBus.<String>consumer(RequestVerticle.EVENTBUS_ADDRESS, message -> {
        LOG.debug("Received Message: {}", message.body());
        message.reply("Received your message. Thanks!");
      });
    }
  }
}
