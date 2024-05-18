/*    */ package net.minecraft.block;
/*    */ 
/*    */ import net.minecraft.block.properties.IProperty;
/*    */ import net.minecraft.block.properties.PropertyInteger;
/*    */ import net.minecraft.block.state.BlockState;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class BlockStandingSign
/*    */   extends BlockSign {
/* 12 */   public static final PropertyInteger ROTATION = PropertyInteger.create("rotation", 0, 15);
/*    */ 
/*    */   
/*    */   public BlockStandingSign() {
/* 16 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)ROTATION, Integer.valueOf(0)));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
/* 24 */     if (!worldIn.getBlockState(pos.down()).getBlock().getMaterial().isSolid()) {
/*    */       
/* 26 */       dropBlockAsItem(worldIn, pos, state, 0);
/* 27 */       worldIn.setBlockToAir(pos);
/*    */     } 
/*    */     
/* 30 */     super.onNeighborBlockChange(worldIn, pos, state, neighborBlock);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IBlockState getStateFromMeta(int meta) {
/* 38 */     return getDefaultState().withProperty((IProperty)ROTATION, Integer.valueOf(meta));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMetaFromState(IBlockState state) {
/* 46 */     return ((Integer)state.getValue((IProperty)ROTATION)).intValue();
/*    */   }
/*    */ 
/*    */   
/*    */   protected BlockState createBlockState() {
/* 51 */     return new BlockState(this, new IProperty[] { (IProperty)ROTATION });
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\block\BlockStandingSign.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */