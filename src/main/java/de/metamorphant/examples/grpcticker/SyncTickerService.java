package de.metamorphant.examples.grpcticker;

import io.grpc.stub.StreamObserver;

public class SyncTickerService extends StreamingTickerGrpc.StreamingTickerImplBase {
  @Override
  public void subscribeTicker(TickRequest request, StreamObserver<TickReply> responseObserver) {
    TickReply reply = TickReply.newBuilder().setTick("Blarz").build();
    for (int i = 0; i < 100; i++) {
      responseObserver.onNext(reply);
      try {
        Thread.sleep(1000);
      } catch (InterruptedException ex) {
        return;
      }
    }
    responseObserver.onCompleted();
  }
}
