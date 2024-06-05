package net.minecraft.client.stream;

public enum BroadcastState
{
    Uninitialized("Uninitialized", 0), 
    Initialized("Initialized", 1), 
    Authenticating("Authenticating", 2), 
    Authenticated("Authenticated", 3), 
    LoggingIn("LoggingIn", 4), 
    LoggedIn("LoggedIn", 5), 
    FindingIngestServer("FindingIngestServer", 6), 
    ReceivedIngestServers("ReceivedIngestServers", 7), 
    ReadyToBroadcast("ReadyToBroadcast", 8), 
    Starting("Starting", 9), 
    Broadcasting("Broadcasting", 10), 
    Stopping("Stopping", 11), 
    Paused("Paused", 12), 
    IngestTesting("IngestTesting", 13);
    
    private static final BroadcastState[] $VALUES;
    
    private BroadcastState(final String p_i1025_1_, final int p_i1025_2_) {
    }
    
    static {
        $VALUES = new BroadcastState[] { BroadcastState.Uninitialized, BroadcastState.Initialized, BroadcastState.Authenticating, BroadcastState.Authenticated, BroadcastState.LoggingIn, BroadcastState.LoggedIn, BroadcastState.FindingIngestServer, BroadcastState.ReceivedIngestServers, BroadcastState.ReadyToBroadcast, BroadcastState.Starting, BroadcastState.Broadcasting, BroadcastState.Stopping, BroadcastState.Paused, BroadcastState.IngestTesting };
    }
}
