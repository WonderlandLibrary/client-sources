/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.MapColor;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyInteger;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockNetherWart
/*     */   extends BlockBush
/*     */ {
/*  20 */   public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 3);
/*     */ 
/*     */   
/*     */   protected BlockNetherWart() {
/*  24 */     super(Material.plants, MapColor.redColor);
/*  25 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)AGE, Integer.valueOf(0)));
/*  26 */     setTickRandomly(true);
/*  27 */     float f = 0.5F;
/*  28 */     setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 0.25F, 0.5F + f);
/*  29 */     setCreativeTab(null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean canPlaceBlockOn(Block ground) {
/*  37 */     return (ground == Blocks.soul_sand);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state) {
/*  42 */     return canPlaceBlockOn(worldIn.getBlockState(pos.down()).getBlock());
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/*  47 */     int i = ((Integer)state.getValue((IProperty)AGE)).intValue();
/*     */     
/*  49 */     if (i < 3 && rand.nextInt(10) == 0) {
/*     */       
/*  51 */       state = state.withProperty((IProperty)AGE, Integer.valueOf(i + 1));
/*  52 */       worldIn.setBlockState(pos, state, 2);
/*     */     } 
/*     */     
/*  55 */     super.updateTick(worldIn, pos, state, rand);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune) {
/*  63 */     if (!worldIn.isRemote) {
/*     */       
/*  65 */       int i = 1;
/*     */       
/*  67 */       if (((Integer)state.getValue((IProperty)AGE)).intValue() >= 3) {
/*     */         
/*  69 */         i = 2 + worldIn.rand.nextInt(3);
/*     */         
/*  71 */         if (fortune > 0)
/*     */         {
/*  73 */           i += worldIn.rand.nextInt(fortune + 1);
/*     */         }
/*     */       } 
/*     */       
/*  77 */       for (int j = 0; j < i; j++)
/*     */       {
/*  79 */         spawnAsEntity(worldIn, pos, new ItemStack(Items.nether_wart));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/*  89 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int quantityDropped(Random random) {
/*  97 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public Item getItem(World worldIn, BlockPos pos) {
/* 102 */     return Items.nether_wart;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 110 */     return getDefaultState().withProperty((IProperty)AGE, Integer.valueOf(meta));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 118 */     return ((Integer)state.getValue((IProperty)AGE)).intValue();
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockState createBlockState() {
/* 123 */     return new BlockState(this, new IProperty[] { (IProperty)AGE });
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\block\BlockNetherWart.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */