# grpc-java-threading-experiment
A simple gRPC server and client app, demonstrating the threading behaviour for server-side streaming

## How to build?

```console
./gradlew clean check assemble installDist
```

## How to conduct the experiment?

Unpack the distribution package
```console
tar xf build/distributions/grpc-java-threading-experiment.tar
```

### Synchronous (blocking) server

```console
$ grpc-java-threading-experiment/bin/grpc-java-threading-experiment server
```

### Asynchronous (non-blocking) server

```console
$ grpc-java-threading-experiment/bin/grpc-java-threading-experiment async-server
```

### Fire clients

```console
$ grpc-java-threading-experiment/bin/grpc-java-threading-experiment client
```
