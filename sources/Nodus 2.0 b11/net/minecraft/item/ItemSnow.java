/*  1:   */ package net.minecraft.item;
/*  2:   */ 
/*  3:   */ import net.minecraft.block.Block;
/*  4:   */ import net.minecraft.block.Block.SoundType;
/*  5:   */ import net.minecraft.entity.player.EntityPlayer;
/*  6:   */ import net.minecraft.init.Blocks;
/*  7:   */ import net.minecraft.world.World;
/*  8:   */ 
/*  9:   */ public class ItemSnow
/* 10:   */   extends ItemBlockWithMetadata
/* 11:   */ {
/* 12:   */   private static final String __OBFID = "CL_00000068";
/* 13:   */   
/* 14:   */   public ItemSnow(Block p_i45354_1_, Block p_i45354_2_)
/* 15:   */   {
/* 16:14 */     super(p_i45354_1_, p_i45354_2_);
/* 17:   */   }
/* 18:   */   
/* 19:   */   public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
/* 20:   */   {
/* 21:23 */     if (par1ItemStack.stackSize == 0) {
/* 22:25 */       return false;
/* 23:   */     }
/* 24:27 */     if (!par2EntityPlayer.canPlayerEdit(par4, par5, par6, par7, par1ItemStack)) {
/* 25:29 */       return false;
/* 26:   */     }
/* 27:33 */     Block var11 = par3World.getBlock(par4, par5, par6);
/* 28:35 */     if (var11 == Blocks.snow_layer)
/* 29:   */     {
/* 30:37 */       int var12 = par3World.getBlockMetadata(par4, par5, par6);
/* 31:38 */       int var13 = var12 & 0x7;
/* 32:40 */       if ((var13 <= 6) && (par3World.checkNoEntityCollision(this.field_150939_a.getCollisionBoundingBoxFromPool(par3World, par4, par5, par6))) && (par3World.setBlockMetadataWithNotify(par4, par5, par6, var13 + 1 | var12 & 0xFFFFFFF8, 2)))
/* 33:   */       {
/* 34:42 */         par3World.playSoundEffect(par4 + 0.5F, par5 + 0.5F, par6 + 0.5F, this.field_150939_a.stepSound.func_150496_b(), (this.field_150939_a.stepSound.func_150497_c() + 1.0F) / 2.0F, this.field_150939_a.stepSound.func_150494_d() * 0.8F);
/* 35:43 */         par1ItemStack.stackSize -= 1;
/* 36:44 */         return true;
/* 37:   */       }
/* 38:   */     }
/* 39:48 */     return super.onItemUse(par1ItemStack, par2EntityPlayer, par3World, par4, par5, par6, par7, par8, par9, par10);
/* 40:   */   }
/* 41:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.item.ItemSnow
 * JD-Core Version:    0.7.0.1
 */