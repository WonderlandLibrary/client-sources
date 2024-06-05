package net.minecraft.network.play.server;

public enum Action
{
    CHANGE("CHANGE", 0), 
    REMOVE("REMOVE", 1);
    
    private static final Action[] $VALUES;
    
    private Action(final String p_i45957_1_, final int p_i45957_2_) {
    }
    
    static {
        $VALUES = new Action[] { Action.CHANGE, Action.REMOVE };
    }
}
