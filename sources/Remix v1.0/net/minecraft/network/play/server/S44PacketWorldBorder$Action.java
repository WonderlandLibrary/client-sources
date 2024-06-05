package net.minecraft.network.play.server;

public enum Action
{
    SET_SIZE("SET_SIZE", 0), 
    LERP_SIZE("LERP_SIZE", 1), 
    SET_CENTER("SET_CENTER", 2), 
    INITIALIZE("INITIALIZE", 3), 
    SET_WARNING_TIME("SET_WARNING_TIME", 4), 
    SET_WARNING_BLOCKS("SET_WARNING_BLOCKS", 5);
    
    private static final Action[] $VALUES;
    
    private Action(final String p_i45961_1_, final int p_i45961_2_) {
    }
    
    static {
        $VALUES = new Action[] { Action.SET_SIZE, Action.LERP_SIZE, Action.SET_CENTER, Action.INITIALIZE, Action.SET_WARNING_TIME, Action.SET_WARNING_BLOCKS };
    }
}
