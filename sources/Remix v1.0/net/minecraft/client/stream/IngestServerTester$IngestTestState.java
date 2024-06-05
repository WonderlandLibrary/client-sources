package net.minecraft.client.stream;

public enum IngestTestState
{
    Uninitalized("Uninitalized", 0), 
    Starting("Starting", 1), 
    ConnectingToServer("ConnectingToServer", 2), 
    TestingServer("TestingServer", 3), 
    DoneTestingServer("DoneTestingServer", 4), 
    Finished("Finished", 5), 
    Cancelling("Cancelling", 6), 
    Cancelled("Cancelled", 7), 
    Failed("Failed", 8);
    
    private static final IngestTestState[] $VALUES;
    
    private IngestTestState(final String p_i1016_1_, final int p_i1016_2_) {
    }
    
    static {
        $VALUES = new IngestTestState[] { IngestTestState.Uninitalized, IngestTestState.Starting, IngestTestState.ConnectingToServer, IngestTestState.TestingServer, IngestTestState.DoneTestingServer, IngestTestState.Finished, IngestTestState.Cancelling, IngestTestState.Cancelled, IngestTestState.Failed };
    }
}
