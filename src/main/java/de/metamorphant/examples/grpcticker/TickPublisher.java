package de.metamorphant.examples.grpcticker;

import java.util.concurrent.SubmissionPublisher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TickPublisher implements Runnable {
  private static final Logger logger = LoggerFactory.getLogger(TickPublisher.class.getName());

  SubmissionPublisher<TickReply> tickPublisher;

  public TickPublisher(SubmissionPublisher<TickReply> tickPublisher) {
    this.tickPublisher = tickPublisher;
  }

  @Override
  public void run() {
    int i = 0;
    while (!Thread.currentThread().isInterrupted()) {
      i++;
      TickReply reply = TickReply.newBuilder().setTick("Blarz " + i).build();
      Integer n = tickPublisher.getNumberOfSubscribers();
      logger.info("Publishing new tick " + i + " to " + n + " subscribers.");
      tickPublisher.submit(reply);
      try {
        Thread.sleep(1000);
      } catch (InterruptedException ex) {
        return;
      }
    }
  }
}
