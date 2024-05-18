/*     */ package net.minecraft.block;
/*     */ 
/*     */ import com.google.common.base.Predicate;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.MapColor;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyBool;
/*     */ import net.minecraft.block.properties.PropertyDirection;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockEndPortalFrame extends Block {
/*  22 */   public static final PropertyDirection FACING = PropertyDirection.create("facing", (Predicate)EnumFacing.Plane.HORIZONTAL);
/*  23 */   public static final PropertyBool EYE = PropertyBool.create("eye");
/*     */ 
/*     */   
/*     */   public BlockEndPortalFrame() {
/*  27 */     super(Material.rock, MapColor.greenColor);
/*  28 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)FACING, (Comparable)EnumFacing.NORTH).withProperty((IProperty)EYE, Boolean.valueOf(false)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube() {
/*  36 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBlockBoundsForItemRender() {
/*  44 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.8125F, 1.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addCollisionBoxesToList(World worldIn, BlockPos pos, IBlockState state, AxisAlignedBB mask, List<AxisAlignedBB> list, Entity collidingEntity) {
/*  52 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.8125F, 1.0F);
/*  53 */     super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
/*     */     
/*  55 */     if (((Boolean)worldIn.getBlockState(pos).getValue((IProperty)EYE)).booleanValue()) {
/*     */       
/*  57 */       setBlockBounds(0.3125F, 0.8125F, 0.3125F, 0.6875F, 1.0F, 0.6875F);
/*  58 */       super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
/*     */     } 
/*     */     
/*  61 */     setBlockBoundsForItemRender();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/*  69 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
/*  78 */     return getDefaultState().withProperty((IProperty)FACING, (Comparable)placer.getHorizontalFacing().getOpposite()).withProperty((IProperty)EYE, Boolean.valueOf(false));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasComparatorInputOverride() {
/*  83 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getComparatorInputOverride(World worldIn, BlockPos pos) {
/*  88 */     return ((Boolean)worldIn.getBlockState(pos).getValue((IProperty)EYE)).booleanValue() ? 15 : 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/*  96 */     return getDefaultState().withProperty((IProperty)EYE, Boolean.valueOf(((meta & 0x4) != 0))).withProperty((IProperty)FACING, (Comparable)EnumFacing.getHorizontal(meta & 0x3));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 104 */     int i = 0;
/* 105 */     i |= ((EnumFacing)state.getValue((IProperty)FACING)).getHorizontalIndex();
/*     */     
/* 107 */     if (((Boolean)state.getValue((IProperty)EYE)).booleanValue())
/*     */     {
/* 109 */       i |= 0x4;
/*     */     }
/*     */     
/* 112 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockState createBlockState() {
/* 117 */     return new BlockState(this, new IProperty[] { (IProperty)FACING, (IProperty)EYE });
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\block\BlockEndPortalFrame.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */