/*
 * Decompiled with CFR 0_118.
 */
package net.minecraft.item;

import net.minecraft.util.EnumChatFormatting;

public enum EnumRarity {
    COMMON("COMMON", 0, EnumChatFormatting.WHITE, "Common"),
    UNCOMMON("UNCOMMON", 1, EnumChatFormatting.YELLOW, "Uncommon"),
    RARE("RARE", 2, EnumChatFormatting.AQUA, "Rare"),
    EPIC("EPIC", 3, EnumChatFormatting.LIGHT_PURPLE, "Epic");
    
    public final EnumChatFormatting rarityColor;
    public final String rarityName;
    private static final EnumRarity[] $VALUES;
    private static final String __OBFID = "CL_00000056";

    static {
        $VALUES = new EnumRarity[]{COMMON, UNCOMMON, RARE, EPIC};
    }

    private EnumRarity(String p_i45349_1_, int p_i45349_2_, String p_i45349_3_, int p_i45349_4_, EnumChatFormatting enumChatFormatting, String string2) {
        this.rarityColor = p_i45349_3_;
        this.rarityName = (String)p_i45349_4_;
    }
}

