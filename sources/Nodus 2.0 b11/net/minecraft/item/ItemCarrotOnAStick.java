/*  1:   */ package net.minecraft.item;
/*  2:   */ 
/*  3:   */ import net.minecraft.creativetab.CreativeTabs;
/*  4:   */ import net.minecraft.entity.ai.EntityAIControlledByPlayer;
/*  5:   */ import net.minecraft.entity.passive.EntityPig;
/*  6:   */ import net.minecraft.entity.player.EntityPlayer;
/*  7:   */ import net.minecraft.init.Items;
/*  8:   */ import net.minecraft.world.World;
/*  9:   */ 
/* 10:   */ public class ItemCarrotOnAStick
/* 11:   */   extends Item
/* 12:   */ {
/* 13:   */   private static final String __OBFID = "CL_00000001";
/* 14:   */   
/* 15:   */   public ItemCarrotOnAStick()
/* 16:   */   {
/* 17:15 */     setCreativeTab(CreativeTabs.tabTransport);
/* 18:16 */     setMaxStackSize(1);
/* 19:17 */     setMaxDamage(25);
/* 20:   */   }
/* 21:   */   
/* 22:   */   public boolean isFull3D()
/* 23:   */   {
/* 24:25 */     return true;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public boolean shouldRotateAroundWhenRendering()
/* 28:   */   {
/* 29:34 */     return true;
/* 30:   */   }
/* 31:   */   
/* 32:   */   public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
/* 33:   */   {
/* 34:42 */     if ((par3EntityPlayer.isRiding()) && ((par3EntityPlayer.ridingEntity instanceof EntityPig)))
/* 35:   */     {
/* 36:44 */       EntityPig var4 = (EntityPig)par3EntityPlayer.ridingEntity;
/* 37:46 */       if ((var4.getAIControlledByPlayer().isControlledByPlayer()) && (par1ItemStack.getMaxDamage() - par1ItemStack.getItemDamage() >= 7))
/* 38:   */       {
/* 39:48 */         var4.getAIControlledByPlayer().boostSpeed();
/* 40:49 */         par1ItemStack.damageItem(7, par3EntityPlayer);
/* 41:51 */         if (par1ItemStack.stackSize == 0)
/* 42:   */         {
/* 43:53 */           ItemStack var5 = new ItemStack(Items.fishing_rod);
/* 44:54 */           var5.setTagCompound(par1ItemStack.stackTagCompound);
/* 45:55 */           return var5;
/* 46:   */         }
/* 47:   */       }
/* 48:   */     }
/* 49:60 */     return par1ItemStack;
/* 50:   */   }
/* 51:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.item.ItemCarrotOnAStick
 * JD-Core Version:    0.7.0.1
 */