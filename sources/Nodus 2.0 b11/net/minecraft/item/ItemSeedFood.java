/*  1:   */ package net.minecraft.item;
/*  2:   */ 
/*  3:   */ import net.minecraft.block.Block;
/*  4:   */ import net.minecraft.entity.player.EntityPlayer;
/*  5:   */ import net.minecraft.world.World;
/*  6:   */ 
/*  7:   */ public class ItemSeedFood
/*  8:   */   extends ItemFood
/*  9:   */ {
/* 10:   */   private Block field_150908_b;
/* 11:   */   private Block soilId;
/* 12:   */   private static final String __OBFID = "CL_00000060";
/* 13:   */   
/* 14:   */   public ItemSeedFood(int p_i45351_1_, float p_i45351_2_, Block p_i45351_3_, Block p_i45351_4_)
/* 15:   */   {
/* 16:17 */     super(p_i45351_1_, p_i45351_2_, false);
/* 17:18 */     this.field_150908_b = p_i45351_3_;
/* 18:19 */     this.soilId = p_i45351_4_;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
/* 22:   */   {
/* 23:28 */     if (par7 != 1) {
/* 24:30 */       return false;
/* 25:   */     }
/* 26:32 */     if ((par2EntityPlayer.canPlayerEdit(par4, par5, par6, par7, par1ItemStack)) && (par2EntityPlayer.canPlayerEdit(par4, par5 + 1, par6, par7, par1ItemStack)))
/* 27:   */     {
/* 28:34 */       if ((par3World.getBlock(par4, par5, par6) == this.soilId) && (par3World.isAirBlock(par4, par5 + 1, par6)))
/* 29:   */       {
/* 30:36 */         par3World.setBlock(par4, par5 + 1, par6, this.field_150908_b);
/* 31:37 */         par1ItemStack.stackSize -= 1;
/* 32:38 */         return true;
/* 33:   */       }
/* 34:42 */       return false;
/* 35:   */     }
/* 36:47 */     return false;
/* 37:   */   }
/* 38:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.item.ItemSeedFood
 * JD-Core Version:    0.7.0.1
 */