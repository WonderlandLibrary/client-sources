package net.minecraft.item;

import net.minecraft.util.*;

public enum EnumRarity
{
    COMMON("COMMON", 0, EnumChatFormatting.WHITE, "Common"), 
    UNCOMMON("UNCOMMON", 1, EnumChatFormatting.YELLOW, "Uncommon"), 
    RARE("RARE", 2, EnumChatFormatting.AQUA, "Rare"), 
    EPIC("EPIC", 3, EnumChatFormatting.LIGHT_PURPLE, "Epic");
    
    private static final EnumRarity[] $VALUES;
    public final EnumChatFormatting rarityColor;
    public final String rarityName;
    
    private EnumRarity(final String p_i45349_1_, final int p_i45349_2_, final EnumChatFormatting p_i45349_3_, final String p_i45349_4_) {
        this.rarityColor = p_i45349_3_;
        this.rarityName = p_i45349_4_;
    }
    
    static {
        $VALUES = new EnumRarity[] { EnumRarity.COMMON, EnumRarity.UNCOMMON, EnumRarity.RARE, EnumRarity.EPIC };
    }
}
