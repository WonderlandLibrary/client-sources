/*    */ package net.minecraft.block;
/*    */ 
/*    */ import net.minecraft.block.material.MapColor;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.world.IBlockAccess;
/*    */ 
/*    */ public class BlockCompressedPowered
/*    */   extends Block
/*    */ {
/*    */   public BlockCompressedPowered(Material p_i46386_1_, MapColor p_i46386_2_) {
/* 14 */     super(p_i46386_1_, p_i46386_2_);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean canProvidePower() {
/* 22 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getWeakPower(IBlockAccess worldIn, BlockPos pos, IBlockState state, EnumFacing side) {
/* 27 */     return 15;
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\block\BlockCompressedPowered.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */