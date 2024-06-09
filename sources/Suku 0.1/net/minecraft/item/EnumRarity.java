package net.minecraft.item;

import net.minecraft.util.Formatting;

public enum EnumRarity
{
    COMMON(Formatting.WHITE, "Common"),
    UNCOMMON(Formatting.YELLOW, "Uncommon"),
    RARE(Formatting.AQUA, "Rare"),
    EPIC(Formatting.LIGHT_PURPLE, "Epic");

    public final Formatting rarityColor;
    public final String rarityName;

    private EnumRarity(Formatting color, String name)
    {
        this.rarityColor = color;
        this.rarityName = name;
    }
}
