/*  1:   */ package net.minecraft.item;
/*  2:   */ 
/*  3:   */ import net.minecraft.block.Block;
/*  4:   */ import net.minecraft.block.Block.SoundType;
/*  5:   */ import net.minecraft.entity.player.EntityPlayer;
/*  6:   */ import net.minecraft.init.Blocks;
/*  7:   */ import net.minecraft.world.World;
/*  8:   */ 
/*  9:   */ public class ItemReed
/* 10:   */   extends Item
/* 11:   */ {
/* 12:   */   private Block field_150935_a;
/* 13:   */   private static final String __OBFID = "CL_00001773";
/* 14:   */   
/* 15:   */   public ItemReed(Block p_i45329_1_)
/* 16:   */   {
/* 17:16 */     this.field_150935_a = p_i45329_1_;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
/* 21:   */   {
/* 22:25 */     Block var11 = par3World.getBlock(par4, par5, par6);
/* 23:27 */     if ((var11 == Blocks.snow_layer) && ((par3World.getBlockMetadata(par4, par5, par6) & 0x7) < 1))
/* 24:   */     {
/* 25:29 */       par7 = 1;
/* 26:   */     }
/* 27:31 */     else if ((var11 != Blocks.vine) && (var11 != Blocks.tallgrass) && (var11 != Blocks.deadbush))
/* 28:   */     {
/* 29:33 */       if (par7 == 0) {
/* 30:35 */         par5--;
/* 31:   */       }
/* 32:38 */       if (par7 == 1) {
/* 33:40 */         par5++;
/* 34:   */       }
/* 35:43 */       if (par7 == 2) {
/* 36:45 */         par6--;
/* 37:   */       }
/* 38:48 */       if (par7 == 3) {
/* 39:50 */         par6++;
/* 40:   */       }
/* 41:53 */       if (par7 == 4) {
/* 42:55 */         par4--;
/* 43:   */       }
/* 44:58 */       if (par7 == 5) {
/* 45:60 */         par4++;
/* 46:   */       }
/* 47:   */     }
/* 48:64 */     if (!par2EntityPlayer.canPlayerEdit(par4, par5, par6, par7, par1ItemStack)) {
/* 49:66 */       return false;
/* 50:   */     }
/* 51:68 */     if (par1ItemStack.stackSize == 0) {
/* 52:70 */       return false;
/* 53:   */     }
/* 54:74 */     if (par3World.canPlaceEntityOnSide(this.field_150935_a, par4, par5, par6, false, par7, null, par1ItemStack))
/* 55:   */     {
/* 56:76 */       int var12 = this.field_150935_a.onBlockPlaced(par3World, par4, par5, par6, par7, par8, par9, par10, 0);
/* 57:78 */       if (par3World.setBlock(par4, par5, par6, this.field_150935_a, var12, 3))
/* 58:   */       {
/* 59:80 */         if (par3World.getBlock(par4, par5, par6) == this.field_150935_a)
/* 60:   */         {
/* 61:82 */           this.field_150935_a.onBlockPlacedBy(par3World, par4, par5, par6, par2EntityPlayer, par1ItemStack);
/* 62:83 */           this.field_150935_a.onPostBlockPlaced(par3World, par4, par5, par6, var12);
/* 63:   */         }
/* 64:86 */         par3World.playSoundEffect(par4 + 0.5F, par5 + 0.5F, par6 + 0.5F, this.field_150935_a.stepSound.func_150496_b(), (this.field_150935_a.stepSound.func_150497_c() + 1.0F) / 2.0F, this.field_150935_a.stepSound.func_150494_d() * 0.8F);
/* 65:87 */         par1ItemStack.stackSize -= 1;
/* 66:   */       }
/* 67:   */     }
/* 68:91 */     return true;
/* 69:   */   }
/* 70:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.item.ItemReed
 * JD-Core Version:    0.7.0.1
 */