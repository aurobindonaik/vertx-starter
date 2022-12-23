package com.example.vertx.starter.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VerticleA extends AbstractVerticle {
  private static final Logger LOG = LoggerFactory.getLogger(VerticleA.class);

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    LOG.debug("Start {}", getClass().getName());
    vertx.deployVerticle(new VerticleAA(), whenDeploy -> {
      LOG.debug("Deployed {}", VerticleAA.class.getName());
      vertx.undeploy(whenDeploy.result());
    });
    vertx.deployVerticle(new VerticleAB(), whenDeploy -> {
      LOG.debug("Deployed {}", VerticleAB.class.getName());
//      vertx.undeploy(whenDeploy.result());
    });
    startPromise.complete();
  }
}
