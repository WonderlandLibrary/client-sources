/*  1:   */ package net.minecraft.block;
/*  2:   */ 
/*  3:   */ import net.minecraft.block.material.Material;
/*  4:   */ import net.minecraft.client.renderer.texture.IIconRegister;
/*  5:   */ import net.minecraft.init.Blocks;
/*  6:   */ import net.minecraft.world.IBlockAccess;
/*  7:   */ 
/*  8:   */ public class BlockBreakable
/*  9:   */   extends Block
/* 10:   */ {
/* 11:   */   private boolean field_149996_a;
/* 12:   */   private String field_149995_b;
/* 13:   */   private static final String __OBFID = "CL_00000254";
/* 14:   */   
/* 15:   */   protected BlockBreakable(String p_i45411_1_, Material p_i45411_2_, boolean p_i45411_3_)
/* 16:   */   {
/* 17:17 */     super(p_i45411_2_);
/* 18:18 */     this.field_149996_a = p_i45411_3_;
/* 19:19 */     this.field_149995_b = p_i45411_1_;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public boolean isOpaqueCube()
/* 23:   */   {
/* 24:24 */     return false;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public boolean shouldSideBeRendered(IBlockAccess p_149646_1_, int p_149646_2_, int p_149646_3_, int p_149646_4_, int p_149646_5_)
/* 28:   */   {
/* 29:29 */     Block var6 = p_149646_1_.getBlock(p_149646_2_, p_149646_3_, p_149646_4_);
/* 30:31 */     if ((this == Blocks.glass) || (this == Blocks.stained_glass))
/* 31:   */     {
/* 32:33 */       if (p_149646_1_.getBlockMetadata(p_149646_2_, p_149646_3_, p_149646_4_) != p_149646_1_.getBlockMetadata(p_149646_2_ - net.minecraft.util.Facing.offsetsXForSide[p_149646_5_], p_149646_3_ - net.minecraft.util.Facing.offsetsYForSide[p_149646_5_], p_149646_4_ - net.minecraft.util.Facing.offsetsZForSide[p_149646_5_])) {
/* 33:35 */         return true;
/* 34:   */       }
/* 35:38 */       if (var6 == this) {
/* 36:40 */         return false;
/* 37:   */       }
/* 38:   */     }
/* 39:44 */     return (!this.field_149996_a) && (var6 == this) ? false : super.shouldSideBeRendered(p_149646_1_, p_149646_2_, p_149646_3_, p_149646_4_, p_149646_5_);
/* 40:   */   }
/* 41:   */   
/* 42:   */   public void registerBlockIcons(IIconRegister p_149651_1_)
/* 43:   */   {
/* 44:49 */     this.blockIcon = p_149651_1_.registerIcon(this.field_149995_b);
/* 45:   */   }
/* 46:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockBreakable
 * JD-Core Version:    0.7.0.1
 */