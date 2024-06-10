/*  1:   */ package net.minecraft.item;
/*  2:   */ 
/*  3:   */ import net.minecraft.entity.player.EntityPlayer;
/*  4:   */ import net.minecraft.nbt.NBTTagCompound;
/*  5:   */ import net.minecraft.nbt.NBTTagList;
/*  6:   */ import net.minecraft.world.World;
/*  7:   */ 
/*  8:   */ public class ItemWritableBook
/*  9:   */   extends Item
/* 10:   */ {
/* 11:   */   private static final String __OBFID = "CL_00000076";
/* 12:   */   
/* 13:   */   public ItemWritableBook()
/* 14:   */   {
/* 15:14 */     setMaxStackSize(1);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
/* 19:   */   {
/* 20:22 */     par3EntityPlayer.displayGUIBook(par1ItemStack);
/* 21:23 */     return par1ItemStack;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public boolean getShareTag()
/* 25:   */   {
/* 26:31 */     return true;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public static boolean func_150930_a(NBTTagCompound p_150930_0_)
/* 30:   */   {
/* 31:36 */     if (p_150930_0_ == null) {
/* 32:38 */       return false;
/* 33:   */     }
/* 34:40 */     if (!p_150930_0_.func_150297_b("pages", 9)) {
/* 35:42 */       return false;
/* 36:   */     }
/* 37:46 */     NBTTagList var1 = p_150930_0_.getTagList("pages", 8);
/* 38:48 */     for (int var2 = 0; var2 < var1.tagCount(); var2++)
/* 39:   */     {
/* 40:50 */       String var3 = var1.getStringTagAt(var2);
/* 41:52 */       if (var3 == null) {
/* 42:54 */         return false;
/* 43:   */       }
/* 44:57 */       if (var3.length() > 256) {
/* 45:59 */         return false;
/* 46:   */       }
/* 47:   */     }
/* 48:63 */     return true;
/* 49:   */   }
/* 50:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.item.ItemWritableBook
 * JD-Core Version:    0.7.0.1
 */