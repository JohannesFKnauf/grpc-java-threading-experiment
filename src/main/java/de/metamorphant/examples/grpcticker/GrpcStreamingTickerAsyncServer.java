package de.metamorphant.examples.grpcticker;

import java.util.concurrent.SubmissionPublisher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import picocli.CommandLine.Command;

@Command(name = "async-server")
public class GrpcStreamingTickerAsyncServer implements Runnable {
  private static final Logger logger = LoggerFactory.getLogger(GrpcStreamingTickerAsyncServer.class.getName());

  private SubmissionPublisher<TickReply> tickPublisher = new SubmissionPublisher<>();

  @Override
  public void run() {
    try {
      this.startThreadCountReporter();
      this.startPublisher();

      GrpcServer server = new GrpcServer(new AsyncTickerService(tickPublisher));

      server.start();
      server.blockUntilShutdown();
    } catch (Exception ex) {
      return;
    }
  }

  private void startThreadCountReporter() {
    Thread reporter = new Thread(new ThreadCountReporter());
    reporter.start();
  }

  private void startPublisher() {
    Thread publisher = new Thread(new TickPublisher(tickPublisher));
    publisher.start();
  }
}
