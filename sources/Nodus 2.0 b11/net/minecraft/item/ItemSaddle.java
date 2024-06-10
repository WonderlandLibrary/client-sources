/*  1:   */ package net.minecraft.item;
/*  2:   */ 
/*  3:   */ import net.minecraft.creativetab.CreativeTabs;
/*  4:   */ import net.minecraft.entity.EntityLivingBase;
/*  5:   */ import net.minecraft.entity.passive.EntityPig;
/*  6:   */ import net.minecraft.entity.player.EntityPlayer;
/*  7:   */ import net.minecraft.world.World;
/*  8:   */ 
/*  9:   */ public class ItemSaddle
/* 10:   */   extends Item
/* 11:   */ {
/* 12:   */   private static final String __OBFID = "CL_00000059";
/* 13:   */   
/* 14:   */   public ItemSaddle()
/* 15:   */   {
/* 16:14 */     this.maxStackSize = 1;
/* 17:15 */     setCreativeTab(CreativeTabs.tabTransport);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public boolean itemInteractionForEntity(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, EntityLivingBase par3EntityLivingBase)
/* 21:   */   {
/* 22:23 */     if ((par3EntityLivingBase instanceof EntityPig))
/* 23:   */     {
/* 24:25 */       EntityPig var4 = (EntityPig)par3EntityLivingBase;
/* 25:27 */       if ((!var4.getSaddled()) && (!var4.isChild()))
/* 26:   */       {
/* 27:29 */         var4.setSaddled(true);
/* 28:30 */         var4.worldObj.playSoundAtEntity(var4, "mob.horse.leather", 0.5F, 1.0F);
/* 29:31 */         par1ItemStack.stackSize -= 1;
/* 30:   */       }
/* 31:34 */       return true;
/* 32:   */     }
/* 33:38 */     return false;
/* 34:   */   }
/* 35:   */   
/* 36:   */   public boolean hitEntity(ItemStack par1ItemStack, EntityLivingBase par2EntityLivingBase, EntityLivingBase par3EntityLivingBase)
/* 37:   */   {
/* 38:48 */     itemInteractionForEntity(par1ItemStack, null, par2EntityLivingBase);
/* 39:49 */     return true;
/* 40:   */   }
/* 41:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.item.ItemSaddle
 * JD-Core Version:    0.7.0.1
 */