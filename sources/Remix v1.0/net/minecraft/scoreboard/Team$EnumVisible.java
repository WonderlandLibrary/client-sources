package net.minecraft.scoreboard;

import java.util.*;
import com.google.common.collect.*;

public enum EnumVisible
{
    ALWAYS("ALWAYS", 0, "always", 0), 
    NEVER("NEVER", 1, "never", 1), 
    HIDE_FOR_OTHER_TEAMS("HIDE_FOR_OTHER_TEAMS", 2, "hideForOtherTeams", 2), 
    HIDE_FOR_OWN_TEAM("HIDE_FOR_OWN_TEAM", 3, "hideForOwnTeam", 3);
    
    private static final EnumVisible[] $VALUES;
    private static Map field_178828_g;
    public final String field_178830_e;
    public final int field_178827_f;
    
    private EnumVisible(final String p_i45550_1_, final int p_i45550_2_, final String p_i45550_3_, final int p_i45550_4_) {
        this.field_178830_e = p_i45550_3_;
        this.field_178827_f = p_i45550_4_;
    }
    
    public static String[] func_178825_a() {
        return (String[])EnumVisible.field_178828_g.keySet().toArray(new String[EnumVisible.field_178828_g.size()]);
    }
    
    public static EnumVisible func_178824_a(final String p_178824_0_) {
        return EnumVisible.field_178828_g.get(p_178824_0_);
    }
    
    static {
        $VALUES = new EnumVisible[] { EnumVisible.ALWAYS, EnumVisible.NEVER, EnumVisible.HIDE_FOR_OTHER_TEAMS, EnumVisible.HIDE_FOR_OWN_TEAM };
        EnumVisible.field_178828_g = Maps.newHashMap();
        for (final EnumVisible var4 : values()) {
            EnumVisible.field_178828_g.put(var4.field_178830_e, var4);
        }
    }
}
