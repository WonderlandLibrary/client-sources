/*  1:   */ package net.minecraft.block;
/*  2:   */ 
/*  3:   */ import net.minecraft.block.material.Material;
/*  4:   */ import net.minecraft.world.IBlockAccess;
/*  5:   */ 
/*  6:   */ public class BlockLeavesBase
/*  7:   */   extends Block
/*  8:   */ {
/*  9:   */   protected boolean field_150121_P;
/* 10:   */   private static final String __OBFID = "CL_00000326";
/* 11:   */   
/* 12:   */   protected BlockLeavesBase(Material p_i45433_1_, boolean p_i45433_2_)
/* 13:   */   {
/* 14:13 */     super(p_i45433_1_);
/* 15:14 */     this.field_150121_P = p_i45433_2_;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public boolean isOpaqueCube()
/* 19:   */   {
/* 20:19 */     return false;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public boolean shouldSideBeRendered(IBlockAccess p_149646_1_, int p_149646_2_, int p_149646_3_, int p_149646_4_, int p_149646_5_)
/* 24:   */   {
/* 25:24 */     Block var6 = p_149646_1_.getBlock(p_149646_2_, p_149646_3_, p_149646_4_);
/* 26:25 */     return (!this.field_150121_P) && (var6 == this) ? false : super.shouldSideBeRendered(p_149646_1_, p_149646_2_, p_149646_3_, p_149646_4_, p_149646_5_);
/* 27:   */   }
/* 28:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockLeavesBase
 * JD-Core Version:    0.7.0.1
 */