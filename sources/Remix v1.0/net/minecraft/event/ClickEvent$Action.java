package net.minecraft.event;

import java.util.*;
import com.google.common.collect.*;

public enum Action
{
    OPEN_URL("OPEN_URL", 0, "open_url", true), 
    OPEN_FILE("OPEN_FILE", 1, "open_file", false), 
    RUN_COMMAND("RUN_COMMAND", 2, "run_command", true), 
    TWITCH_USER_INFO("TWITCH_USER_INFO", 3, "twitch_user_info", false), 
    SUGGEST_COMMAND("SUGGEST_COMMAND", 4, "suggest_command", true), 
    CHANGE_PAGE("CHANGE_PAGE", 5, "change_page", true);
    
    private static final Map nameMapping;
    private static final Action[] $VALUES;
    private final boolean allowedInChat;
    private final String canonicalName;
    
    private Action(final String p_i45155_1_, final int p_i45155_2_, final String p_i45155_3_, final boolean p_i45155_4_) {
        this.canonicalName = p_i45155_3_;
        this.allowedInChat = p_i45155_4_;
    }
    
    public static Action getValueByCanonicalName(final String p_150672_0_) {
        return Action.nameMapping.get(p_150672_0_);
    }
    
    public boolean shouldAllowInChat() {
        return this.allowedInChat;
    }
    
    public String getCanonicalName() {
        return this.canonicalName;
    }
    
    static {
        nameMapping = Maps.newHashMap();
        $VALUES = new Action[] { Action.OPEN_URL, Action.OPEN_FILE, Action.RUN_COMMAND, Action.TWITCH_USER_INFO, Action.SUGGEST_COMMAND, Action.CHANGE_PAGE };
        for (final Action var4 : values()) {
            Action.nameMapping.put(var4.getCanonicalName(), var4);
        }
    }
}
