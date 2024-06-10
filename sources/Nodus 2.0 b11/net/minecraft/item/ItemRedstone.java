/*  1:   */ package net.minecraft.item;
/*  2:   */ 
/*  3:   */ import net.minecraft.block.BlockRedstoneWire;
/*  4:   */ import net.minecraft.creativetab.CreativeTabs;
/*  5:   */ import net.minecraft.entity.player.EntityPlayer;
/*  6:   */ import net.minecraft.init.Blocks;
/*  7:   */ import net.minecraft.world.World;
/*  8:   */ 
/*  9:   */ public class ItemRedstone
/* 10:   */   extends Item
/* 11:   */ {
/* 12:   */   private static final String __OBFID = "CL_00000058";
/* 13:   */   
/* 14:   */   public ItemRedstone()
/* 15:   */   {
/* 16:14 */     setCreativeTab(CreativeTabs.tabRedstone);
/* 17:   */   }
/* 18:   */   
/* 19:   */   public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
/* 20:   */   {
/* 21:23 */     if (par3World.getBlock(par4, par5, par6) != Blocks.snow_layer)
/* 22:   */     {
/* 23:25 */       if (par7 == 0) {
/* 24:27 */         par5--;
/* 25:   */       }
/* 26:30 */       if (par7 == 1) {
/* 27:32 */         par5++;
/* 28:   */       }
/* 29:35 */       if (par7 == 2) {
/* 30:37 */         par6--;
/* 31:   */       }
/* 32:40 */       if (par7 == 3) {
/* 33:42 */         par6++;
/* 34:   */       }
/* 35:45 */       if (par7 == 4) {
/* 36:47 */         par4--;
/* 37:   */       }
/* 38:50 */       if (par7 == 5) {
/* 39:52 */         par4++;
/* 40:   */       }
/* 41:55 */       if (!par3World.isAirBlock(par4, par5, par6)) {
/* 42:57 */         return false;
/* 43:   */       }
/* 44:   */     }
/* 45:61 */     if (!par2EntityPlayer.canPlayerEdit(par4, par5, par6, par7, par1ItemStack)) {
/* 46:63 */       return false;
/* 47:   */     }
/* 48:67 */     if (Blocks.redstone_wire.canPlaceBlockAt(par3World, par4, par5, par6))
/* 49:   */     {
/* 50:69 */       par1ItemStack.stackSize -= 1;
/* 51:70 */       par3World.setBlock(par4, par5, par6, Blocks.redstone_wire);
/* 52:   */     }
/* 53:73 */     return true;
/* 54:   */   }
/* 55:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.item.ItemRedstone
 * JD-Core Version:    0.7.0.1
 */