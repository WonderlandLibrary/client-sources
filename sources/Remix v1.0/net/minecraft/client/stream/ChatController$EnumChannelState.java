package net.minecraft.client.stream;

public enum EnumChannelState
{
    Created("Created", 0), 
    Connecting("Connecting", 1), 
    Connected("Connected", 2), 
    Disconnecting("Disconnecting", 3), 
    Disconnected("Disconnected", 4);
    
    private static final EnumChannelState[] $VALUES;
    
    private EnumChannelState(final String p_i46062_1_, final int p_i46062_2_) {
    }
    
    static {
        $VALUES = new EnumChannelState[] { EnumChannelState.Created, EnumChannelState.Connecting, EnumChannelState.Connected, EnumChannelState.Disconnecting, EnumChannelState.Disconnected };
    }
}
