/*     */ package net.minecraft.block;
/*     */ import com.google.common.base.Predicate;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyDirection;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.inventory.InventoryEnderChest;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.stats.StatList;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityEnderChest;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockEnderChest extends BlockContainer {
/*  26 */   public static final PropertyDirection FACING = PropertyDirection.create("facing", (Predicate)EnumFacing.Plane.HORIZONTAL);
/*     */ 
/*     */   
/*     */   protected BlockEnderChest() {
/*  30 */     super(Material.rock);
/*  31 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)FACING, (Comparable)EnumFacing.NORTH));
/*  32 */     setCreativeTab(CreativeTabs.tabDecorations);
/*  33 */     setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube() {
/*  41 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFullCube() {
/*  46 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRenderType() {
/*  54 */     return 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/*  62 */     return Item.getItemFromBlock(Blocks.obsidian);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int quantityDropped(Random random) {
/*  70 */     return 8;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean canSilkHarvest() {
/*  75 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
/*  84 */     return getDefaultState().withProperty((IProperty)FACING, (Comparable)placer.getHorizontalFacing().getOpposite());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
/*  92 */     worldIn.setBlockState(pos, state.withProperty((IProperty)FACING, (Comparable)placer.getHorizontalFacing().getOpposite()), 2);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
/*  97 */     InventoryEnderChest inventoryenderchest = playerIn.getInventoryEnderChest();
/*  98 */     TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */     
/* 100 */     if (inventoryenderchest != null && tileentity instanceof TileEntityEnderChest) {
/*     */       
/* 102 */       if (worldIn.getBlockState(pos.up()).getBlock().isNormalCube())
/*     */       {
/* 104 */         return true;
/*     */       }
/* 106 */       if (worldIn.isRemote)
/*     */       {
/* 108 */         return true;
/*     */       }
/*     */ 
/*     */       
/* 112 */       inventoryenderchest.setChestTileEntity((TileEntityEnderChest)tileentity);
/* 113 */       playerIn.displayGUIChest((IInventory)inventoryenderchest);
/* 114 */       playerIn.triggerAchievement(StatList.field_181738_V);
/* 115 */       return true;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 120 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TileEntity createNewTileEntity(World worldIn, int meta) {
/* 129 */     return (TileEntity)new TileEntityEnderChest();
/*     */   }
/*     */ 
/*     */   
/*     */   public void randomDisplayTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/* 134 */     for (int i = 0; i < 3; i++) {
/*     */       
/* 136 */       int j = rand.nextInt(2) * 2 - 1;
/* 137 */       int k = rand.nextInt(2) * 2 - 1;
/* 138 */       double d0 = pos.getX() + 0.5D + 0.25D * j;
/* 139 */       double d1 = (pos.getY() + rand.nextFloat());
/* 140 */       double d2 = pos.getZ() + 0.5D + 0.25D * k;
/* 141 */       double d3 = (rand.nextFloat() * j);
/* 142 */       double d4 = (rand.nextFloat() - 0.5D) * 0.125D;
/* 143 */       double d5 = (rand.nextFloat() * k);
/* 144 */       worldIn.spawnParticle(EnumParticleTypes.PORTAL, d0, d1, d2, d3, d4, d5, new int[0]);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 153 */     EnumFacing enumfacing = EnumFacing.getFront(meta);
/*     */     
/* 155 */     if (enumfacing.getAxis() == EnumFacing.Axis.Y)
/*     */     {
/* 157 */       enumfacing = EnumFacing.NORTH;
/*     */     }
/*     */     
/* 160 */     return getDefaultState().withProperty((IProperty)FACING, (Comparable)enumfacing);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 168 */     return ((EnumFacing)state.getValue((IProperty)FACING)).getIndex();
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockState createBlockState() {
/* 173 */     return new BlockState(this, new IProperty[] { (IProperty)FACING });
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\block\BlockEnderChest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */