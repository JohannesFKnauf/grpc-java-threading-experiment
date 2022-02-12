package de.metamorphant.examples.grpcticker;

import java.util.concurrent.SubmissionPublisher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.grpc.stub.StreamObserver;

public class AsyncTickerService extends StreamingTickerGrpc.StreamingTickerImplBase {
  private static final Logger logger = LoggerFactory.getLogger(AsyncTickerService.class.getName());

  private SubmissionPublisher<TickReply> tickPublisher;

  public AsyncTickerService(SubmissionPublisher<TickReply> tickPublisher) {
    this.tickPublisher = tickPublisher;
  }

  @Override
  public void subscribeTicker(TickRequest request, StreamObserver<TickReply> responseObserver) {
    logger.debug("Registering a new subscriber");
    TickSubscriber tickSubscriber = new TickSubscriber(responseObserver);
    tickPublisher.subscribe(tickSubscriber);
  }
}
