package net.minecraft.item;

import net.minecraft.util.ChatFormatting;

public enum EnumRarity
{
    COMMON(ChatFormatting.WHITE, "Common"),
    UNCOMMON(ChatFormatting.YELLOW, "Uncommon"),
    RARE(ChatFormatting.AQUA, "Rare"),
    EPIC(ChatFormatting.LIGHT_PURPLE, "Epic");

    /**
     * A decimal representation of the hex color codes of a the color assigned to this rarity type. (13 becomes d as in
     * \247d which is light purple)
     */
    public final ChatFormatting rarityColor;

    /** Rarity name. */
    public final String rarityName;

    private EnumRarity(ChatFormatting color, String name)
    {
        this.rarityColor = color;
        this.rarityName = name;
    }
}
