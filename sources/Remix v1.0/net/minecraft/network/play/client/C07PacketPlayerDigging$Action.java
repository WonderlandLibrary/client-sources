package net.minecraft.network.play.client;

public enum Action
{
    START_DESTROY_BLOCK("START_DESTROY_BLOCK", 0), 
    ABORT_DESTROY_BLOCK("ABORT_DESTROY_BLOCK", 1), 
    STOP_DESTROY_BLOCK("STOP_DESTROY_BLOCK", 2), 
    DROP_ALL_ITEMS("DROP_ALL_ITEMS", 3), 
    DROP_ITEM("DROP_ITEM", 4), 
    RELEASE_USE_ITEM("RELEASE_USE_ITEM", 5);
    
    private static final Action[] $VALUES;
    
    private Action(final String p_i45939_1_, final int p_i45939_2_) {
    }
    
    static {
        $VALUES = new Action[] { Action.START_DESTROY_BLOCK, Action.ABORT_DESTROY_BLOCK, Action.STOP_DESTROY_BLOCK, Action.DROP_ALL_ITEMS, Action.DROP_ITEM, Action.RELEASE_USE_ITEM };
    }
}
