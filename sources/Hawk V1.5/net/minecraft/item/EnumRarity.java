package net.minecraft.item;

import net.minecraft.util.EnumChatFormatting;

public enum EnumRarity {
   private static final EnumRarity[] $VALUES = new EnumRarity[]{COMMON, UNCOMMON, RARE, EPIC};
   EPIC("EPIC", 3, EnumChatFormatting.LIGHT_PURPLE, "Epic"),
   RARE("RARE", 2, EnumChatFormatting.AQUA, "Rare"),
   UNCOMMON("UNCOMMON", 1, EnumChatFormatting.YELLOW, "Uncommon"),
   COMMON("COMMON", 0, EnumChatFormatting.WHITE, "Common");

   public final String rarityName;
   public final EnumChatFormatting rarityColor;
   private static final EnumRarity[] ENUM$VALUES = new EnumRarity[]{COMMON, UNCOMMON, RARE, EPIC};
   private static final String __OBFID = "CL_00000056";

   private EnumRarity(String var3, int var4, EnumChatFormatting var5, String var6) {
      this.rarityColor = var5;
      this.rarityName = var6;
   }
}
