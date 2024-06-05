package net.minecraft.event;

import java.util.*;
import com.google.common.collect.*;

public enum Action
{
    SHOW_TEXT("SHOW_TEXT", 0, "show_text", true), 
    SHOW_ACHIEVEMENT("SHOW_ACHIEVEMENT", 1, "show_achievement", true), 
    SHOW_ITEM("SHOW_ITEM", 2, "show_item", true), 
    SHOW_ENTITY("SHOW_ENTITY", 3, "show_entity", true);
    
    private static final Map nameMapping;
    private static final Action[] $VALUES;
    private final boolean allowedInChat;
    private final String canonicalName;
    
    private Action(final String p_i45157_1_, final int p_i45157_2_, final String p_i45157_3_, final boolean p_i45157_4_) {
        this.canonicalName = p_i45157_3_;
        this.allowedInChat = p_i45157_4_;
    }
    
    public static Action getValueByCanonicalName(final String p_150684_0_) {
        return Action.nameMapping.get(p_150684_0_);
    }
    
    public boolean shouldAllowInChat() {
        return this.allowedInChat;
    }
    
    public String getCanonicalName() {
        return this.canonicalName;
    }
    
    static {
        nameMapping = Maps.newHashMap();
        $VALUES = new Action[] { Action.SHOW_TEXT, Action.SHOW_ACHIEVEMENT, Action.SHOW_ITEM, Action.SHOW_ENTITY };
        for (final Action var4 : values()) {
            Action.nameMapping.put(var4.getCanonicalName(), var4);
        }
    }
}
