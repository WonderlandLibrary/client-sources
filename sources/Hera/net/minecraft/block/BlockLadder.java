/*     */ package net.minecraft.block;
/*     */ 
/*     */ import com.google.common.base.Predicate;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyDirection;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumWorldBlockLayer;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockLadder extends Block {
/*  19 */   public static final PropertyDirection FACING = PropertyDirection.create("facing", (Predicate)EnumFacing.Plane.HORIZONTAL);
/*     */ 
/*     */   
/*     */   protected BlockLadder() {
/*  23 */     super(Material.circuits);
/*  24 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)FACING, (Comparable)EnumFacing.NORTH));
/*  25 */     setCreativeTab(CreativeTabs.tabDecorations);
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {
/*  30 */     setBlockBoundsBasedOnState((IBlockAccess)worldIn, pos);
/*  31 */     return super.getCollisionBoundingBox(worldIn, pos, state);
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getSelectedBoundingBox(World worldIn, BlockPos pos) {
/*  36 */     setBlockBoundsBasedOnState((IBlockAccess)worldIn, pos);
/*  37 */     return super.getSelectedBoundingBox(worldIn, pos);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
/*  42 */     IBlockState iblockstate = worldIn.getBlockState(pos);
/*     */     
/*  44 */     if (iblockstate.getBlock() == this) {
/*     */       
/*  46 */       float f = 0.125F;
/*     */       
/*  48 */       switch ((EnumFacing)iblockstate.getValue((IProperty)FACING)) {
/*     */         
/*     */         case NORTH:
/*  51 */           setBlockBounds(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F);
/*     */           return;
/*     */         
/*     */         case SOUTH:
/*  55 */           setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f);
/*     */           return;
/*     */         
/*     */         case WEST:
/*  59 */           setBlockBounds(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */           return;
/*     */       } 
/*     */ 
/*     */       
/*  64 */       setBlockBounds(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube() {
/*  74 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFullCube() {
/*  79 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
/*  84 */     return worldIn.getBlockState(pos.west()).getBlock().isNormalCube() ? true : (worldIn.getBlockState(pos.east()).getBlock().isNormalCube() ? true : (worldIn.getBlockState(pos.north()).getBlock().isNormalCube() ? true : worldIn.getBlockState(pos.south()).getBlock().isNormalCube()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
/*  93 */     if (facing.getAxis().isHorizontal() && canBlockStay(worldIn, pos, facing))
/*     */     {
/*  95 */       return getDefaultState().withProperty((IProperty)FACING, (Comparable)facing);
/*     */     }
/*     */ 
/*     */     
/*  99 */     for (Object enumfacing0 : EnumFacing.Plane.HORIZONTAL) {
/*     */       
/* 101 */       EnumFacing enumfacing = (EnumFacing)enumfacing0;
/*     */       
/* 103 */       if (canBlockStay(worldIn, pos, enumfacing))
/*     */       {
/* 105 */         return getDefaultState().withProperty((IProperty)FACING, (Comparable)enumfacing);
/*     */       }
/*     */     } 
/*     */     
/* 109 */     return getDefaultState();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
/* 118 */     EnumFacing enumfacing = (EnumFacing)state.getValue((IProperty)FACING);
/*     */     
/* 120 */     if (!canBlockStay(worldIn, pos, enumfacing)) {
/*     */       
/* 122 */       dropBlockAsItem(worldIn, pos, state, 0);
/* 123 */       worldIn.setBlockToAir(pos);
/*     */     } 
/*     */     
/* 126 */     super.onNeighborBlockChange(worldIn, pos, state, neighborBlock);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean canBlockStay(World worldIn, BlockPos pos, EnumFacing facing) {
/* 131 */     return worldIn.getBlockState(pos.offset(facing.getOpposite())).getBlock().isNormalCube();
/*     */   }
/*     */ 
/*     */   
/*     */   public EnumWorldBlockLayer getBlockLayer() {
/* 136 */     return EnumWorldBlockLayer.CUTOUT;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 144 */     EnumFacing enumfacing = EnumFacing.getFront(meta);
/*     */     
/* 146 */     if (enumfacing.getAxis() == EnumFacing.Axis.Y)
/*     */     {
/* 148 */       enumfacing = EnumFacing.NORTH;
/*     */     }
/*     */     
/* 151 */     return getDefaultState().withProperty((IProperty)FACING, (Comparable)enumfacing);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 159 */     return ((EnumFacing)state.getValue((IProperty)FACING)).getIndex();
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockState createBlockState() {
/* 164 */     return new BlockState(this, new IProperty[] { (IProperty)FACING });
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\block\BlockLadder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */