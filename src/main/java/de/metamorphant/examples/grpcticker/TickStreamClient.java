package de.metamorphant.examples.grpcticker;

import java.util.Iterator;
import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.metamorphant.examples.grpcticker.StreamingTickerGrpc.StreamingTickerBlockingStub;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;

public class TickStreamClient implements Runnable {
  private static final Integer DEFAULT_PORT = 54321;
  private static final String DEFAULT_HOST = "localhost";

  private static final Logger logger = LoggerFactory.getLogger(TickStreamClient.class.getName());

  CountDownLatch countDownLatch;

  public TickStreamClient(CountDownLatch countDownLatch) {
    this.countDownLatch = countDownLatch;
  }

  @Override
  public void run() {
    ManagedChannel channel = ManagedChannelBuilder.forAddress(DEFAULT_HOST, DEFAULT_PORT).usePlaintext().build();
    StreamingTickerBlockingStub stub = StreamingTickerGrpc.newBlockingStub(channel);

    TickRequest request = TickRequest.newBuilder().build();
    Iterator<TickReply> ticks;
    try {
      ticks = stub.subscribeTicker(request);
    } catch (StatusRuntimeException ex) {
      logger.info("gRPC Client failed: {0}", ex.getStatus());
      return;
    }

    while (ticks.hasNext()) {
      TickReply tick = ticks.next();
      logger.info(tick.getTick());
    }

    // client thread is done, release it
    this.countDownLatch.countDown();
  }
}
