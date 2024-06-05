package net.minecraft.network.play.client;

public enum Action
{
    START_SNEAKING("START_SNEAKING", 0), 
    STOP_SNEAKING("STOP_SNEAKING", 1), 
    STOP_SLEEPING("STOP_SLEEPING", 2), 
    START_SPRINTING("START_SPRINTING", 3), 
    STOP_SPRINTING("STOP_SPRINTING", 4), 
    RIDING_JUMP("RIDING_JUMP", 5), 
    OPEN_INVENTORY("OPEN_INVENTORY", 6);
    
    private static final Action[] $VALUES;
    
    private Action(final String p_i45936_1_, final int p_i45936_2_) {
    }
    
    static {
        $VALUES = new Action[] { Action.START_SNEAKING, Action.STOP_SNEAKING, Action.STOP_SLEEPING, Action.START_SPRINTING, Action.STOP_SPRINTING, Action.RIDING_JUMP, Action.OPEN_INVENTORY };
    }
}
