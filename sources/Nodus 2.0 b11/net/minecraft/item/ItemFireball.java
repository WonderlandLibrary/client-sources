/*  1:   */ package net.minecraft.item;
/*  2:   */ 
/*  3:   */ import java.util.Random;
/*  4:   */ import net.minecraft.block.Block;
/*  5:   */ import net.minecraft.block.material.Material;
/*  6:   */ import net.minecraft.creativetab.CreativeTabs;
/*  7:   */ import net.minecraft.entity.player.EntityPlayer;
/*  8:   */ import net.minecraft.entity.player.PlayerCapabilities;
/*  9:   */ import net.minecraft.init.Blocks;
/* 10:   */ import net.minecraft.world.World;
/* 11:   */ 
/* 12:   */ public class ItemFireball
/* 13:   */   extends Item
/* 14:   */ {
/* 15:   */   private static final String __OBFID = "CL_00000029";
/* 16:   */   
/* 17:   */   public ItemFireball()
/* 18:   */   {
/* 19:15 */     setCreativeTab(CreativeTabs.tabMisc);
/* 20:   */   }
/* 21:   */   
/* 22:   */   public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
/* 23:   */   {
/* 24:24 */     if (par3World.isClient) {
/* 25:26 */       return true;
/* 26:   */     }
/* 27:30 */     if (par7 == 0) {
/* 28:32 */       par5--;
/* 29:   */     }
/* 30:35 */     if (par7 == 1) {
/* 31:37 */       par5++;
/* 32:   */     }
/* 33:40 */     if (par7 == 2) {
/* 34:42 */       par6--;
/* 35:   */     }
/* 36:45 */     if (par7 == 3) {
/* 37:47 */       par6++;
/* 38:   */     }
/* 39:50 */     if (par7 == 4) {
/* 40:52 */       par4--;
/* 41:   */     }
/* 42:55 */     if (par7 == 5) {
/* 43:57 */       par4++;
/* 44:   */     }
/* 45:60 */     if (!par2EntityPlayer.canPlayerEdit(par4, par5, par6, par7, par1ItemStack)) {
/* 46:62 */       return false;
/* 47:   */     }
/* 48:66 */     if (par3World.getBlock(par4, par5, par6).getMaterial() == Material.air)
/* 49:   */     {
/* 50:68 */       par3World.playSoundEffect(par4 + 0.5D, par5 + 0.5D, par6 + 0.5D, "fire.ignite", 1.0F, itemRand.nextFloat() * 0.4F + 0.8F);
/* 51:69 */       par3World.setBlock(par4, par5, par6, Blocks.fire);
/* 52:   */     }
/* 53:72 */     if (!par2EntityPlayer.capabilities.isCreativeMode) {
/* 54:74 */       par1ItemStack.stackSize -= 1;
/* 55:   */     }
/* 56:77 */     return true;
/* 57:   */   }
/* 58:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.item.ItemFireball
 * JD-Core Version:    0.7.0.1
 */