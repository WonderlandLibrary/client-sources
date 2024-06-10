/*  1:   */ package net.minecraft.item;
/*  2:   */ 
/*  3:   */ import java.util.Random;
/*  4:   */ import net.minecraft.block.Block;
/*  5:   */ import net.minecraft.block.material.Material;
/*  6:   */ import net.minecraft.creativetab.CreativeTabs;
/*  7:   */ import net.minecraft.entity.player.EntityPlayer;
/*  8:   */ import net.minecraft.init.Blocks;
/*  9:   */ import net.minecraft.world.World;
/* 10:   */ 
/* 11:   */ public class ItemFlintAndSteel
/* 12:   */   extends Item
/* 13:   */ {
/* 14:   */   private static final String __OBFID = "CL_00000035";
/* 15:   */   
/* 16:   */   public ItemFlintAndSteel()
/* 17:   */   {
/* 18:15 */     this.maxStackSize = 1;
/* 19:16 */     setMaxDamage(64);
/* 20:17 */     setCreativeTab(CreativeTabs.tabTools);
/* 21:   */   }
/* 22:   */   
/* 23:   */   public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
/* 24:   */   {
/* 25:26 */     if (par7 == 0) {
/* 26:28 */       par5--;
/* 27:   */     }
/* 28:31 */     if (par7 == 1) {
/* 29:33 */       par5++;
/* 30:   */     }
/* 31:36 */     if (par7 == 2) {
/* 32:38 */       par6--;
/* 33:   */     }
/* 34:41 */     if (par7 == 3) {
/* 35:43 */       par6++;
/* 36:   */     }
/* 37:46 */     if (par7 == 4) {
/* 38:48 */       par4--;
/* 39:   */     }
/* 40:51 */     if (par7 == 5) {
/* 41:53 */       par4++;
/* 42:   */     }
/* 43:56 */     if (!par2EntityPlayer.canPlayerEdit(par4, par5, par6, par7, par1ItemStack)) {
/* 44:58 */       return false;
/* 45:   */     }
/* 46:62 */     if (par3World.getBlock(par4, par5, par6).getMaterial() == Material.air)
/* 47:   */     {
/* 48:64 */       par3World.playSoundEffect(par4 + 0.5D, par5 + 0.5D, par6 + 0.5D, "fire.ignite", 1.0F, itemRand.nextFloat() * 0.4F + 0.8F);
/* 49:65 */       par3World.setBlock(par4, par5, par6, Blocks.fire);
/* 50:   */     }
/* 51:68 */     par1ItemStack.damageItem(1, par2EntityPlayer);
/* 52:69 */     return true;
/* 53:   */   }
/* 54:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.item.ItemFlintAndSteel
 * JD-Core Version:    0.7.0.1
 */