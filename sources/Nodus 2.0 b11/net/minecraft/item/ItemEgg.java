/*  1:   */ package net.minecraft.item;
/*  2:   */ 
/*  3:   */ import java.util.Random;
/*  4:   */ import net.minecraft.creativetab.CreativeTabs;
/*  5:   */ import net.minecraft.entity.player.EntityPlayer;
/*  6:   */ import net.minecraft.entity.player.PlayerCapabilities;
/*  7:   */ import net.minecraft.entity.projectile.EntityEgg;
/*  8:   */ import net.minecraft.world.World;
/*  9:   */ 
/* 10:   */ public class ItemEgg
/* 11:   */   extends Item
/* 12:   */ {
/* 13:   */   private static final String __OBFID = "CL_00000023";
/* 14:   */   
/* 15:   */   public ItemEgg()
/* 16:   */   {
/* 17:14 */     this.maxStackSize = 16;
/* 18:15 */     setCreativeTab(CreativeTabs.tabMaterials);
/* 19:   */   }
/* 20:   */   
/* 21:   */   public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
/* 22:   */   {
/* 23:23 */     if (!par3EntityPlayer.capabilities.isCreativeMode) {
/* 24:25 */       par1ItemStack.stackSize -= 1;
/* 25:   */     }
/* 26:28 */     par2World.playSoundAtEntity(par3EntityPlayer, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
/* 27:30 */     if (!par2World.isClient) {
/* 28:32 */       par2World.spawnEntityInWorld(new EntityEgg(par2World, par3EntityPlayer));
/* 29:   */     }
/* 30:35 */     return par1ItemStack;
/* 31:   */   }
/* 32:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.item.ItemEgg
 * JD-Core Version:    0.7.0.1
 */