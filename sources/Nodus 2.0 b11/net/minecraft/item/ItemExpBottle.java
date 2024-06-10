/*  1:   */ package net.minecraft.item;
/*  2:   */ 
/*  3:   */ import java.util.Random;
/*  4:   */ import net.minecraft.creativetab.CreativeTabs;
/*  5:   */ import net.minecraft.entity.item.EntityExpBottle;
/*  6:   */ import net.minecraft.entity.player.EntityPlayer;
/*  7:   */ import net.minecraft.entity.player.PlayerCapabilities;
/*  8:   */ import net.minecraft.world.World;
/*  9:   */ 
/* 10:   */ public class ItemExpBottle
/* 11:   */   extends Item
/* 12:   */ {
/* 13:   */   private static final String __OBFID = "CL_00000028";
/* 14:   */   
/* 15:   */   public ItemExpBottle()
/* 16:   */   {
/* 17:14 */     setCreativeTab(CreativeTabs.tabMisc);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public boolean hasEffect(ItemStack par1ItemStack)
/* 21:   */   {
/* 22:19 */     return true;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
/* 26:   */   {
/* 27:27 */     if (!par3EntityPlayer.capabilities.isCreativeMode) {
/* 28:29 */       par1ItemStack.stackSize -= 1;
/* 29:   */     }
/* 30:32 */     par2World.playSoundAtEntity(par3EntityPlayer, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
/* 31:34 */     if (!par2World.isClient) {
/* 32:36 */       par2World.spawnEntityInWorld(new EntityExpBottle(par2World, par3EntityPlayer));
/* 33:   */     }
/* 34:39 */     return par1ItemStack;
/* 35:   */   }
/* 36:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.item.ItemExpBottle
 * JD-Core Version:    0.7.0.1
 */