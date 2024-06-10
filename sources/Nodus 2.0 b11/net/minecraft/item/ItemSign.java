/*  1:   */ package net.minecraft.item;
/*  2:   */ 
/*  3:   */ import net.minecraft.block.Block;
/*  4:   */ import net.minecraft.block.material.Material;
/*  5:   */ import net.minecraft.creativetab.CreativeTabs;
/*  6:   */ import net.minecraft.entity.player.EntityPlayer;
/*  7:   */ import net.minecraft.init.Blocks;
/*  8:   */ import net.minecraft.tileentity.TileEntitySign;
/*  9:   */ import net.minecraft.util.MathHelper;
/* 10:   */ import net.minecraft.world.World;
/* 11:   */ 
/* 12:   */ public class ItemSign
/* 13:   */   extends Item
/* 14:   */ {
/* 15:   */   private static final String __OBFID = "CL_00000064";
/* 16:   */   
/* 17:   */   public ItemSign()
/* 18:   */   {
/* 19:16 */     this.maxStackSize = 16;
/* 20:17 */     setCreativeTab(CreativeTabs.tabDecorations);
/* 21:   */   }
/* 22:   */   
/* 23:   */   public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
/* 24:   */   {
/* 25:26 */     if (par7 == 0) {
/* 26:28 */       return false;
/* 27:   */     }
/* 28:30 */     if (!par3World.getBlock(par4, par5, par6).getMaterial().isSolid()) {
/* 29:32 */       return false;
/* 30:   */     }
/* 31:36 */     if (par7 == 1) {
/* 32:38 */       par5++;
/* 33:   */     }
/* 34:41 */     if (par7 == 2) {
/* 35:43 */       par6--;
/* 36:   */     }
/* 37:46 */     if (par7 == 3) {
/* 38:48 */       par6++;
/* 39:   */     }
/* 40:51 */     if (par7 == 4) {
/* 41:53 */       par4--;
/* 42:   */     }
/* 43:56 */     if (par7 == 5) {
/* 44:58 */       par4++;
/* 45:   */     }
/* 46:61 */     if (!par2EntityPlayer.canPlayerEdit(par4, par5, par6, par7, par1ItemStack)) {
/* 47:63 */       return false;
/* 48:   */     }
/* 49:65 */     if (!Blocks.standing_sign.canPlaceBlockAt(par3World, par4, par5, par6)) {
/* 50:67 */       return false;
/* 51:   */     }
/* 52:69 */     if (par3World.isClient) {
/* 53:71 */       return true;
/* 54:   */     }
/* 55:75 */     if (par7 == 1)
/* 56:   */     {
/* 57:77 */       int var11 = MathHelper.floor_double((par2EntityPlayer.rotationYaw + 180.0F) * 16.0F / 360.0F + 0.5D) & 0xF;
/* 58:78 */       par3World.setBlock(par4, par5, par6, Blocks.standing_sign, var11, 3);
/* 59:   */     }
/* 60:   */     else
/* 61:   */     {
/* 62:82 */       par3World.setBlock(par4, par5, par6, Blocks.wall_sign, par7, 3);
/* 63:   */     }
/* 64:85 */     par1ItemStack.stackSize -= 1;
/* 65:86 */     TileEntitySign var12 = (TileEntitySign)par3World.getTileEntity(par4, par5, par6);
/* 66:88 */     if (var12 != null) {
/* 67:90 */       par2EntityPlayer.func_146100_a(var12);
/* 68:   */     }
/* 69:93 */     return true;
/* 70:   */   }
/* 71:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.item.ItemSign
 * JD-Core Version:    0.7.0.1
 */