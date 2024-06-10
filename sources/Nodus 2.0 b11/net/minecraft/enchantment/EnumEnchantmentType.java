/*  1:   */ package net.minecraft.enchantment;
/*  2:   */ 
/*  3:   */ import net.minecraft.item.Item;
/*  4:   */ import net.minecraft.item.ItemArmor;
/*  5:   */ 
/*  6:   */ public enum EnumEnchantmentType
/*  7:   */ {
/*  8:12 */   all,  armor,  armor_feet,  armor_legs,  armor_torso,  armor_head,  weapon,  digger,  fishing_rod,  breakable,  bow;
/*  9:   */   
/* 10:   */   private static final String __OBFID = "CL_00000106";
/* 11:   */   
/* 12:   */   public boolean canEnchantItem(Item par1Item)
/* 13:   */   {
/* 14:30 */     if (this == all) {
/* 15:32 */       return true;
/* 16:   */     }
/* 17:34 */     if ((this == breakable) && (par1Item.isDamageable())) {
/* 18:36 */       return true;
/* 19:   */     }
/* 20:38 */     if ((par1Item instanceof ItemArmor))
/* 21:   */     {
/* 22:40 */       if (this == armor) {
/* 23:42 */         return true;
/* 24:   */       }
/* 25:46 */       ItemArmor var2 = (ItemArmor)par1Item;
/* 26:47 */       return this == armor_head;
/* 27:   */     }
/* 28:52 */     return this == weapon;
/* 29:   */   }
/* 30:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.enchantment.EnumEnchantmentType
 * JD-Core Version:    0.7.0.1
 */