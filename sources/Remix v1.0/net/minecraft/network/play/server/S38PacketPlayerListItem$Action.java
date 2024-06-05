package net.minecraft.network.play.server;

public enum Action
{
    ADD_PLAYER("ADD_PLAYER", 0), 
    UPDATE_GAME_MODE("UPDATE_GAME_MODE", 1), 
    UPDATE_LATENCY("UPDATE_LATENCY", 2), 
    UPDATE_DISPLAY_NAME("UPDATE_DISPLAY_NAME", 3), 
    REMOVE_PLAYER("REMOVE_PLAYER", 4);
    
    private static final Action[] $VALUES;
    
    private Action(final String p_i45966_1_, final int p_i45966_2_) {
    }
    
    static {
        $VALUES = new Action[] { Action.ADD_PLAYER, Action.UPDATE_GAME_MODE, Action.UPDATE_LATENCY, Action.UPDATE_DISPLAY_NAME, Action.REMOVE_PLAYER };
    }
}
