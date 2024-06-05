package net.minecraft.entity.player;

public enum EnumChatVisibility
{
    FULL("FULL", 0, 0, "options.chat.visibility.full"), 
    SYSTEM("SYSTEM", 1, 1, "options.chat.visibility.system"), 
    HIDDEN("HIDDEN", 2, 2, "options.chat.visibility.hidden");
    
    private static final EnumChatVisibility[] field_151432_d;
    private static final EnumChatVisibility[] $VALUES;
    private final int chatVisibility;
    private final String resourceKey;
    
    private EnumChatVisibility(final String p_i45323_1_, final int p_i45323_2_, final int p_i45323_3_, final String p_i45323_4_) {
        this.chatVisibility = p_i45323_3_;
        this.resourceKey = p_i45323_4_;
    }
    
    public static EnumChatVisibility getEnumChatVisibility(final int p_151426_0_) {
        return EnumChatVisibility.field_151432_d[p_151426_0_ % EnumChatVisibility.field_151432_d.length];
    }
    
    public int getChatVisibility() {
        return this.chatVisibility;
    }
    
    public String getResourceKey() {
        return this.resourceKey;
    }
    
    static {
        field_151432_d = new EnumChatVisibility[values().length];
        $VALUES = new EnumChatVisibility[] { EnumChatVisibility.FULL, EnumChatVisibility.SYSTEM, EnumChatVisibility.HIDDEN };
        for (final EnumChatVisibility var4 : values()) {
            EnumChatVisibility.field_151432_d[var4.chatVisibility] = var4;
        }
    }
}
