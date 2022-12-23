package com.example.vertx.starter.eventbus.customcodec;

import io.vertx.core.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PingPongExample extends AbstractVerticle {
  private static final Logger LOG = LoggerFactory.getLogger(PingPongExample.class);

  public static void main(String[] args) {
    var vertx = Vertx.vertx();
    vertx.deployVerticle(new PingVerticle(), logOnError("Ping Virticle Error:"));
    vertx.deployVerticle(new PongVerticle(), logOnError("Pong Virticle Error:"));
  }

  private static Handler<AsyncResult<String>> logOnError(String msg) {
    return ar -> {
      if (ar.failed()) {
        LOG.error(msg, ar.cause());
      }
    };
  }

  static class PingVerticle extends AbstractVerticle {
    private static final Logger LOG = LoggerFactory.getLogger(Ping.class);
    static final String EVENTBUS_ADDRESS = PingVerticle.class.getName();
    @Override
    public void start(Promise<Void> startPromise) throws Exception {
      startPromise.complete();
      var eventBus = vertx.eventBus();
      final Ping message = new Ping("Hello", true);
      LOG.debug("Sending Message: {}", message);
      eventBus.<Ping>request(EVENTBUS_ADDRESS, message, reply -> {
        if(reply.failed()) {
          LOG.error("Failed: ", reply.cause());
          return;
        }
        LOG.debug("Response: {}", reply.result().body());
      });
    }
  }

  static class PongVerticle extends AbstractVerticle {
    private static final Logger LOG = LoggerFactory.getLogger(Pong.class);
    @Override
    public void start(Promise<Void> startPromise) throws Exception {
      startPromise.complete();
      var eventBus = vertx.eventBus();
      eventBus.<Ping>consumer(PingVerticle.EVENTBUS_ADDRESS, message -> {
        LOG.debug("Received Message: {}", message.body());
        message.reply(new Pong(0));
      }).exceptionHandler(error -> LOG.error("Error: ", error));
    }
  }
}
