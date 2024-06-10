/*  1:   */ package net.minecraft.item;
/*  2:   */ 
/*  3:   */ public class ItemBook
/*  4:   */   extends Item
/*  5:   */ {
/*  6:   */   private static final String __OBFID = "CL_00001775";
/*  7:   */   
/*  8:   */   public boolean isItemTool(ItemStack par1ItemStack)
/*  9:   */   {
/* 10:12 */     return par1ItemStack.stackSize == 1;
/* 11:   */   }
/* 12:   */   
/* 13:   */   public int getItemEnchantability()
/* 14:   */   {
/* 15:20 */     return 1;
/* 16:   */   }
/* 17:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.item.ItemBook
 * JD-Core Version:    0.7.0.1
 */