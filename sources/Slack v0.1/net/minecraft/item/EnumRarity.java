package net.minecraft.item;

import net.minecraft.util.ChatFormatting;

public enum EnumRarity {
   COMMON(ChatFormatting.WHITE, "Common"),
   UNCOMMON(ChatFormatting.YELLOW, "Uncommon"),
   RARE(ChatFormatting.AQUA, "Rare"),
   EPIC(ChatFormatting.LIGHT_PURPLE, "Epic");

   public final ChatFormatting rarityColor;
   public final String rarityName;

   private EnumRarity(ChatFormatting color, String name) {
      this.rarityColor = color;
      this.rarityName = name;
   }
}
