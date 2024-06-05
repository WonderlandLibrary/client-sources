package net.minecraft.client.stream;

public enum ChatState
{
    Uninitialized("Uninitialized", 0), 
    Initializing("Initializing", 1), 
    Initialized("Initialized", 2), 
    ShuttingDown("ShuttingDown", 3);
    
    private static final ChatState[] $VALUES;
    
    private ChatState(final String stateName, final int id) {
    }
    
    static {
        $VALUES = new ChatState[] { ChatState.Uninitialized, ChatState.Initializing, ChatState.Initialized, ChatState.ShuttingDown };
    }
}
