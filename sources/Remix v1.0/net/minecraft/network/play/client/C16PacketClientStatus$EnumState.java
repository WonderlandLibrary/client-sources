package net.minecraft.network.play.client;

public enum EnumState
{
    PERFORM_RESPAWN("PERFORM_RESPAWN", 0), 
    REQUEST_STATS("REQUEST_STATS", 1), 
    OPEN_INVENTORY_ACHIEVEMENT("OPEN_INVENTORY_ACHIEVEMENT", 2);
    
    private static final EnumState[] $VALUES;
    
    private EnumState(final String p_i45947_1_, final int p_i45947_2_) {
    }
    
    static {
        $VALUES = new EnumState[] { EnumState.PERFORM_RESPAWN, EnumState.REQUEST_STATS, EnumState.OPEN_INVENTORY_ACHIEVEMENT };
    }
}
