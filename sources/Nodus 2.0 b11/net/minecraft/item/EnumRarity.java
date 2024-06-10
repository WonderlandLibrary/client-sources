/*  1:   */ package net.minecraft.item;
/*  2:   */ 
/*  3:   */ import net.minecraft.util.EnumChatFormatting;
/*  4:   */ 
/*  5:   */ public enum EnumRarity
/*  6:   */ {
/*  7: 7 */   common(EnumChatFormatting.WHITE, "Common"),  uncommon(EnumChatFormatting.YELLOW, "Uncommon"),  rare(EnumChatFormatting.AQUA, "Rare"),  epic(EnumChatFormatting.LIGHT_PURPLE, "Epic");
/*  8:   */   
/*  9:   */   public final EnumChatFormatting rarityColor;
/* 10:   */   public final String rarityName;
/* 11:   */   private static final String __OBFID = "CL_00000056";
/* 12:   */   
/* 13:   */   private EnumRarity(EnumChatFormatting p_i45349_3_, String p_i45349_4_)
/* 14:   */   {
/* 15:24 */     this.rarityColor = p_i45349_3_;
/* 16:25 */     this.rarityName = p_i45349_4_;
/* 17:   */   }
/* 18:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.item.EnumRarity
 * JD-Core Version:    0.7.0.1
 */