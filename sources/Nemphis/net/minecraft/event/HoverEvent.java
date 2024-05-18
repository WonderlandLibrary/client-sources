/*
 * Decompiled with CFR 0_118.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 */
package net.minecraft.event;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.util.IChatComponent;

public class HoverEvent {
    private final Action action;
    private final IChatComponent value;
    private static final String __OBFID = "CL_00001264";

    public HoverEvent(Action p_i45158_1_, IChatComponent p_i45158_2_) {
        this.action = p_i45158_1_;
        this.value = p_i45158_2_;
    }

    public Action getAction() {
        return this.action;
    }

    public IChatComponent getValue() {
        return this.value;
    }

    public boolean equals(Object p_equals_1_) {
        if (this == p_equals_1_) {
            return true;
        }
        if (p_equals_1_ != null && this.getClass() == p_equals_1_.getClass()) {
            HoverEvent var2 = (HoverEvent)p_equals_1_;
            if (this.action != var2.action) {
                return false;
            }
            if (this.value != null ? !this.value.equals(var2.value) : var2.value != null) {
                return false;
            }
            return true;
        }
        return false;
    }

    public String toString() {
        return "HoverEvent{action=" + (Object)((Object)this.action) + ", value='" + this.value + '\'' + '}';
    }

    public int hashCode() {
        int var1 = this.action.hashCode();
        var1 = 31 * var1 + (this.value != null ? this.value.hashCode() : 0);
        return var1;
    }

    public static enum Action {
        SHOW_TEXT("SHOW_TEXT", 0, "show_text", true),
        SHOW_ACHIEVEMENT("SHOW_ACHIEVEMENT", 1, "show_achievement", true),
        SHOW_ITEM("SHOW_ITEM", 2, "show_item", true),
        SHOW_ENTITY("SHOW_ENTITY", 3, "show_entity", true);
        
        private static final Map nameMapping;
        private final boolean allowedInChat;
        private final String canonicalName;
        private static final Action[] $VALUES;
        private static final String __OBFID = "CL_00001265";

        static {
            nameMapping = Maps.newHashMap();
            $VALUES = new Action[]{SHOW_TEXT, SHOW_ACHIEVEMENT, SHOW_ITEM, SHOW_ENTITY};
            Action[] var0 = Action.values();
            int var1 = var0.length;
            int var2 = 0;
            while (var2 < var1) {
                Action var3 = var0[var2];
                nameMapping.put(var3.getCanonicalName(), var3);
                ++var2;
            }
        }

        private Action(String p_i45157_1_, int p_i45157_2_, String p_i45157_3_, int p_i45157_4_, String string2, boolean bl) {
            this.canonicalName = p_i45157_3_;
            this.allowedInChat = p_i45157_4_;
        }

        public boolean shouldAllowInChat() {
            return this.allowedInChat;
        }

        public String getCanonicalName() {
            return this.canonicalName;
        }

        public static Action getValueByCanonicalName(String p_150684_0_) {
            return (Action)((Object)nameMapping.get(p_150684_0_));
        }
    }

}

