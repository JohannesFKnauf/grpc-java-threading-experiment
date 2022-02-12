package de.metamorphant.examples.grpcticker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import picocli.CommandLine.Command;

@Command(name = "server")
public class GrpcStreamingTickerServer implements Runnable {
  private static final Logger logger = LoggerFactory.getLogger(GrpcStreamingTickerServer.class.getName());

  @Override
  public void run() {
    try {
      this.startThreadCountReporter();
      GrpcServer server = new GrpcServer(new SyncTickerService());
      server.start();
      server.blockUntilShutdown();
    } catch (Exception ex) {

    }
  }

  private void startThreadCountReporter() {
    Thread reporter = new Thread(new ThreadCountReporter());
    reporter.start();
  }

}
