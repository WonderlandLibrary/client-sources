package net.minecraft.client.stream;

public enum IngestServerTester$IngestTestState {
   Uninitalized,
   Starting,
   ConnectingToServer,
   TestingServer,
   DoneTestingServer,
   Finished,
   Cancelling,
   Cancelled,
   Failed;

   private static final IngestServerTester$IngestTestState[] $VALUES = new IngestServerTester$IngestTestState[]{Uninitalized, Starting, ConnectingToServer, TestingServer, DoneTestingServer, Finished, Cancelling, Cancelled, Failed};
}
