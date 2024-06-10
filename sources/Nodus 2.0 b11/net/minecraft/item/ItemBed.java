/*  1:   */ package net.minecraft.item;
/*  2:   */ 
/*  3:   */ import net.minecraft.block.BlockBed;
/*  4:   */ import net.minecraft.creativetab.CreativeTabs;
/*  5:   */ import net.minecraft.entity.player.EntityPlayer;
/*  6:   */ import net.minecraft.init.Blocks;
/*  7:   */ import net.minecraft.util.MathHelper;
/*  8:   */ import net.minecraft.world.World;
/*  9:   */ 
/* 10:   */ public class ItemBed
/* 11:   */   extends Item
/* 12:   */ {
/* 13:   */   private static final String __OBFID = "CL_00001771";
/* 14:   */   
/* 15:   */   public ItemBed()
/* 16:   */   {
/* 17:16 */     setCreativeTab(CreativeTabs.tabDecorations);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
/* 21:   */   {
/* 22:25 */     if (par3World.isClient) {
/* 23:27 */       return true;
/* 24:   */     }
/* 25:29 */     if (par7 != 1) {
/* 26:31 */       return false;
/* 27:   */     }
/* 28:35 */     par5++;
/* 29:36 */     BlockBed var11 = (BlockBed)Blocks.bed;
/* 30:37 */     int var12 = MathHelper.floor_double(par2EntityPlayer.rotationYaw * 4.0F / 360.0F + 0.5D) & 0x3;
/* 31:38 */     byte var13 = 0;
/* 32:39 */     byte var14 = 0;
/* 33:41 */     if (var12 == 0) {
/* 34:43 */       var14 = 1;
/* 35:   */     }
/* 36:46 */     if (var12 == 1) {
/* 37:48 */       var13 = -1;
/* 38:   */     }
/* 39:51 */     if (var12 == 2) {
/* 40:53 */       var14 = -1;
/* 41:   */     }
/* 42:56 */     if (var12 == 3) {
/* 43:58 */       var13 = 1;
/* 44:   */     }
/* 45:61 */     if ((par2EntityPlayer.canPlayerEdit(par4, par5, par6, par7, par1ItemStack)) && (par2EntityPlayer.canPlayerEdit(par4 + var13, par5, par6 + var14, par7, par1ItemStack)))
/* 46:   */     {
/* 47:63 */       if ((par3World.isAirBlock(par4, par5, par6)) && (par3World.isAirBlock(par4 + var13, par5, par6 + var14)) && (World.doesBlockHaveSolidTopSurface(par3World, par4, par5 - 1, par6)) && (World.doesBlockHaveSolidTopSurface(par3World, par4 + var13, par5 - 1, par6 + var14)))
/* 48:   */       {
/* 49:65 */         par3World.setBlock(par4, par5, par6, var11, var12, 3);
/* 50:67 */         if (par3World.getBlock(par4, par5, par6) == var11) {
/* 51:69 */           par3World.setBlock(par4 + var13, par5, par6 + var14, var11, var12 + 8, 3);
/* 52:   */         }
/* 53:72 */         par1ItemStack.stackSize -= 1;
/* 54:73 */         return true;
/* 55:   */       }
/* 56:77 */       return false;
/* 57:   */     }
/* 58:82 */     return false;
/* 59:   */   }
/* 60:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.item.ItemBed
 * JD-Core Version:    0.7.0.1
 */