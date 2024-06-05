package net.minecraft.client.multiplayer;

import net.minecraft.util.*;

public enum ServerResourceMode
{
    ENABLED("ENABLED", 0, "enabled"), 
    DISABLED("DISABLED", 1, "disabled"), 
    PROMPT("PROMPT", 2, "prompt");
    
    private static final ServerResourceMode[] $VALUES;
    private final IChatComponent motd;
    
    private ServerResourceMode(final String p_i1053_1_, final int p_i1053_2_, final String p_i1053_3_) {
        this.motd = new ChatComponentTranslation("addServer.resourcePack." + p_i1053_3_, new Object[0]);
    }
    
    public IChatComponent getMotd() {
        return this.motd;
    }
    
    static {
        $VALUES = new ServerResourceMode[] { ServerResourceMode.ENABLED, ServerResourceMode.DISABLED, ServerResourceMode.PROMPT };
    }
}
