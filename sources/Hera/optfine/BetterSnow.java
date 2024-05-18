/*     */ package optfine;
/*     */ 
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockLever;
/*     */ import net.minecraft.block.BlockTorch;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.resources.model.IBakedModel;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BetterSnow
/*     */ {
/*  29 */   private static IBakedModel modelSnowLayer = null;
/*     */ 
/*     */   
/*     */   public static void update() {
/*  33 */     modelSnowLayer = Config.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getModelForState(Blocks.snow_layer.getDefaultState());
/*     */   }
/*     */ 
/*     */   
/*     */   public static IBakedModel getModelSnowLayer() {
/*  38 */     return modelSnowLayer;
/*     */   }
/*     */ 
/*     */   
/*     */   public static IBlockState getStateSnowLayer() {
/*  43 */     return Blocks.snow_layer.getDefaultState();
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean shouldRender(IBlockAccess p_shouldRender_0_, Block p_shouldRender_1_, IBlockState p_shouldRender_2_, BlockPos p_shouldRender_3_) {
/*  48 */     return !checkBlock(p_shouldRender_1_, p_shouldRender_2_) ? false : hasSnowNeighbours(p_shouldRender_0_, p_shouldRender_3_);
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean hasSnowNeighbours(IBlockAccess p_hasSnowNeighbours_0_, BlockPos p_hasSnowNeighbours_1_) {
/*  53 */     Block block = Blocks.snow_layer;
/*  54 */     return (p_hasSnowNeighbours_0_.getBlockState(p_hasSnowNeighbours_1_.north()).getBlock() != block && p_hasSnowNeighbours_0_.getBlockState(p_hasSnowNeighbours_1_.south()).getBlock() != block && p_hasSnowNeighbours_0_.getBlockState(p_hasSnowNeighbours_1_.west()).getBlock() != block && p_hasSnowNeighbours_0_.getBlockState(p_hasSnowNeighbours_1_.east()).getBlock() != block) ? false : p_hasSnowNeighbours_0_.getBlockState(p_hasSnowNeighbours_1_.down()).getBlock().isOpaqueCube();
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean checkBlock(Block p_checkBlock_0_, IBlockState p_checkBlock_1_) {
/*  59 */     if (p_checkBlock_0_.isFullCube())
/*     */     {
/*  61 */       return false;
/*     */     }
/*  63 */     if (p_checkBlock_0_.isOpaqueCube())
/*     */     {
/*  65 */       return false;
/*     */     }
/*  67 */     if (p_checkBlock_0_ instanceof net.minecraft.block.BlockSnow)
/*     */     {
/*  69 */       return false;
/*     */     }
/*  71 */     if (!(p_checkBlock_0_ instanceof net.minecraft.block.BlockBush) || (!(p_checkBlock_0_ instanceof net.minecraft.block.BlockDoublePlant) && !(p_checkBlock_0_ instanceof net.minecraft.block.BlockFlower) && !(p_checkBlock_0_ instanceof net.minecraft.block.BlockMushroom) && !(p_checkBlock_0_ instanceof net.minecraft.block.BlockSapling) && !(p_checkBlock_0_ instanceof net.minecraft.block.BlockTallGrass))) {
/*     */       
/*  73 */       if (!(p_checkBlock_0_ instanceof net.minecraft.block.BlockFence) && !(p_checkBlock_0_ instanceof net.minecraft.block.BlockFenceGate) && !(p_checkBlock_0_ instanceof net.minecraft.block.BlockFlowerPot) && !(p_checkBlock_0_ instanceof net.minecraft.block.BlockPane) && !(p_checkBlock_0_ instanceof net.minecraft.block.BlockReed) && !(p_checkBlock_0_ instanceof net.minecraft.block.BlockWall)) {
/*     */         
/*  75 */         if (p_checkBlock_0_ instanceof net.minecraft.block.BlockRedstoneTorch && p_checkBlock_1_.getValue((IProperty)BlockTorch.FACING) == EnumFacing.UP)
/*     */         {
/*  77 */           return true;
/*     */         }
/*     */ 
/*     */         
/*  81 */         if (p_checkBlock_0_ instanceof BlockLever) {
/*     */           
/*  83 */           Object object = p_checkBlock_1_.getValue((IProperty)BlockLever.FACING);
/*     */           
/*  85 */           if (object == BlockLever.EnumOrientation.UP_X || object == BlockLever.EnumOrientation.UP_Z)
/*     */           {
/*  87 */             return true;
/*     */           }
/*     */         } 
/*     */         
/*  91 */         return false;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/*  96 */       return true;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 101 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\optfine\BetterSnow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */