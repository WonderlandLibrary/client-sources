package net.minecraft.network.play.client;

public enum Action
{
    INTERACT("INTERACT", 0), 
    ATTACK("ATTACK", 1), 
    INTERACT_AT("INTERACT_AT", 2);
    
    private static final Action[] $VALUES;
    
    private Action(final String p_i45943_1_, final int p_i45943_2_) {
    }
    
    static {
        $VALUES = new Action[] { Action.INTERACT, Action.ATTACK, Action.INTERACT_AT };
    }
}
