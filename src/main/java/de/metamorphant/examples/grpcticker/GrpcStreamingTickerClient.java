package de.metamorphant.examples.grpcticker;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import picocli.CommandLine.Command;

@Command(name = "client")
public class GrpcStreamingTickerClient implements Runnable {
  private static final Logger logger = LoggerFactory.getLogger(GrpcStreamingTickerClient.class.getName());

  // The countdown latch is used for waiting on all client threads
  CountDownLatch countDownLatch;

  @Override
  public void run() {
    this.countDownLatch = new CountDownLatch(100);
    for (int i = 0; i < 100; i++) {
      startConsumer();
    }
    try {
      this.countDownLatch.await(500, TimeUnit.SECONDS);
    } catch (InterruptedException ex) {
      return;
    }
  }

  private void startConsumer() {
    Thread consumer = new Thread(new TickStreamClient(this.countDownLatch));
    consumer.start();
  }
}
