package de.metamorphant.examples.grpcticker;

import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.grpc.stub.StreamObserver;

public class TickSubscriber implements Subscriber<TickReply> {
  private static final Logger logger = LoggerFactory.getLogger(TickSubscriber.class.getName());

  private AtomicInteger count = new AtomicInteger(0);
  private Subscription subscription;

  private StreamObserver<TickReply> observer;

  public TickSubscriber(StreamObserver<TickReply> observer) {
    this.count = new AtomicInteger(0);
    this.observer = observer;
  }

  @Override
  public void onComplete() {
    logger.debug("Subscriber has been completed.");
  }

  @Override
  public void onError(Throwable t) {
    logger.debug("A problem occured in subscriber: ", t);
  }

  @Override
  public void onNext(TickReply reply) {
    logger.debug("Subscriber received next tick: " + reply.getTick());
    observer.onNext(reply);
    Integer currentCount = count.incrementAndGet();
    if (currentCount > 100) {
      this.subscription.cancel();
      observer.onCompleted();
    } else {
      this.subscription.request(1);
    }
  }

  @Override
  public void onSubscribe(Subscription subscription) {
    logger.debug("Subscribing subscriber");
    this.subscription = subscription;
    this.subscription.request(1);
  }
}
