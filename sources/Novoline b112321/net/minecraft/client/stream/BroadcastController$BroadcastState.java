package net.minecraft.client.stream;

public enum BroadcastController$BroadcastState {
   Uninitialized,
   Initialized,
   Authenticating,
   Authenticated,
   LoggingIn,
   LoggedIn,
   FindingIngestServer,
   ReceivedIngestServers,
   ReadyToBroadcast,
   Starting,
   Broadcasting,
   Stopping,
   Paused,
   IngestTesting;

   private static final BroadcastController$BroadcastState[] $VALUES = new BroadcastController$BroadcastState[]{Uninitialized, Initialized, Authenticating, Authenticated, LoggingIn, LoggedIn, FindingIngestServer, ReceivedIngestServers, ReadyToBroadcast, Starting, Broadcasting, Stopping, Paused, IngestTesting};
}
