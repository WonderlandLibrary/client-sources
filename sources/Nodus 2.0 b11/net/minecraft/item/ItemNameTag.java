/*  1:   */ package net.minecraft.item;
/*  2:   */ 
/*  3:   */ import net.minecraft.creativetab.CreativeTabs;
/*  4:   */ import net.minecraft.entity.EntityLiving;
/*  5:   */ import net.minecraft.entity.EntityLivingBase;
/*  6:   */ import net.minecraft.entity.player.EntityPlayer;
/*  7:   */ 
/*  8:   */ public class ItemNameTag
/*  9:   */   extends Item
/* 10:   */ {
/* 11:   */   private static final String __OBFID = "CL_00000052";
/* 12:   */   
/* 13:   */   public ItemNameTag()
/* 14:   */   {
/* 15:14 */     setCreativeTab(CreativeTabs.tabTools);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public boolean itemInteractionForEntity(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, EntityLivingBase par3EntityLivingBase)
/* 19:   */   {
/* 20:22 */     if (!par1ItemStack.hasDisplayName()) {
/* 21:24 */       return false;
/* 22:   */     }
/* 23:26 */     if ((par3EntityLivingBase instanceof EntityLiving))
/* 24:   */     {
/* 25:28 */       EntityLiving var4 = (EntityLiving)par3EntityLivingBase;
/* 26:29 */       var4.setCustomNameTag(par1ItemStack.getDisplayName());
/* 27:30 */       var4.func_110163_bv();
/* 28:31 */       par1ItemStack.stackSize -= 1;
/* 29:32 */       return true;
/* 30:   */     }
/* 31:36 */     return super.itemInteractionForEntity(par1ItemStack, par2EntityPlayer, par3EntityLivingBase);
/* 32:   */   }
/* 33:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.item.ItemNameTag
 * JD-Core Version:    0.7.0.1
 */