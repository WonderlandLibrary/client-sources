package net.minecraft.network.play.client;

public enum Action
{
    SUCCESSFULLY_LOADED("SUCCESSFULLY_LOADED", 0), 
    DECLINED("DECLINED", 1), 
    FAILED_DOWNLOAD("FAILED_DOWNLOAD", 2), 
    ACCEPTED("ACCEPTED", 3);
    
    private static final Action[] $VALUES;
    
    private Action(final String p_i45934_1_, final int p_i45934_2_) {
    }
    
    static {
        $VALUES = new Action[] { Action.SUCCESSFULLY_LOADED, Action.DECLINED, Action.FAILED_DOWNLOAD, Action.ACCEPTED };
    }
}
