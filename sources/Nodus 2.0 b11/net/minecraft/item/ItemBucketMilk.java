/*  1:   */ package net.minecraft.item;
/*  2:   */ 
/*  3:   */ import net.minecraft.creativetab.CreativeTabs;
/*  4:   */ import net.minecraft.entity.player.EntityPlayer;
/*  5:   */ import net.minecraft.entity.player.PlayerCapabilities;
/*  6:   */ import net.minecraft.init.Items;
/*  7:   */ import net.minecraft.world.World;
/*  8:   */ 
/*  9:   */ public class ItemBucketMilk
/* 10:   */   extends Item
/* 11:   */ {
/* 12:   */   private static final String __OBFID = "CL_00000048";
/* 13:   */   
/* 14:   */   public ItemBucketMilk()
/* 15:   */   {
/* 16:14 */     setMaxStackSize(1);
/* 17:15 */     setCreativeTab(CreativeTabs.tabMisc);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public ItemStack onEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
/* 21:   */   {
/* 22:20 */     if (!par3EntityPlayer.capabilities.isCreativeMode) {
/* 23:22 */       par1ItemStack.stackSize -= 1;
/* 24:   */     }
/* 25:25 */     if (!par2World.isClient) {
/* 26:27 */       par3EntityPlayer.clearActivePotions();
/* 27:   */     }
/* 28:30 */     return par1ItemStack.stackSize <= 0 ? new ItemStack(Items.bucket) : par1ItemStack;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public int getMaxItemUseDuration(ItemStack par1ItemStack)
/* 32:   */   {
/* 33:38 */     return 32;
/* 34:   */   }
/* 35:   */   
/* 36:   */   public EnumAction getItemUseAction(ItemStack par1ItemStack)
/* 37:   */   {
/* 38:46 */     return EnumAction.drink;
/* 39:   */   }
/* 40:   */   
/* 41:   */   public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
/* 42:   */   {
/* 43:54 */     par3EntityPlayer.setItemInUse(par1ItemStack, getMaxItemUseDuration(par1ItemStack));
/* 44:55 */     return par1ItemStack;
/* 45:   */   }
/* 46:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.item.ItemBucketMilk
 * JD-Core Version:    0.7.0.1
 */