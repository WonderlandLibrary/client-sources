/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyBool;
/*     */ import net.minecraft.block.properties.PropertyDirection;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.projectile.EntityArrow;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public abstract class BlockButton
/*     */   extends Block {
/*  24 */   public static final PropertyDirection FACING = PropertyDirection.create("facing");
/*  25 */   public static final PropertyBool POWERED = PropertyBool.create("powered");
/*     */   
/*     */   private final boolean wooden;
/*     */   
/*     */   protected BlockButton(boolean wooden) {
/*  30 */     super(Material.circuits);
/*  31 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)FACING, (Comparable)EnumFacing.NORTH).withProperty((IProperty)POWERED, Boolean.valueOf(false)));
/*  32 */     setTickRandomly(true);
/*  33 */     setCreativeTab(CreativeTabs.tabRedstone);
/*  34 */     this.wooden = wooden;
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {
/*  39 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int tickRate(World worldIn) {
/*  47 */     return this.wooden ? 30 : 20;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube() {
/*  55 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFullCube() {
/*  60 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side) {
/*  68 */     return func_181088_a(worldIn, pos, side.getOpposite());
/*     */   } public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
/*     */     byte b;
/*     */     int i;
/*     */     EnumFacing[] arrayOfEnumFacing;
/*  73 */     for (i = (arrayOfEnumFacing = EnumFacing.values()).length, b = 0; b < i; ) { EnumFacing enumfacing = arrayOfEnumFacing[b];
/*     */       
/*  75 */       if (func_181088_a(worldIn, pos, enumfacing))
/*     */       {
/*  77 */         return true;
/*     */       }
/*     */       b++; }
/*     */     
/*  81 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected static boolean func_181088_a(World p_181088_0_, BlockPos p_181088_1_, EnumFacing p_181088_2_) {
/*  86 */     BlockPos blockpos = p_181088_1_.offset(p_181088_2_);
/*  87 */     return (p_181088_2_ == EnumFacing.DOWN) ? World.doesBlockHaveSolidTopSurface((IBlockAccess)p_181088_0_, blockpos) : p_181088_0_.getBlockState(blockpos).getBlock().isNormalCube();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
/*  96 */     return func_181088_a(worldIn, pos, facing.getOpposite()) ? getDefaultState().withProperty((IProperty)FACING, (Comparable)facing).withProperty((IProperty)POWERED, Boolean.valueOf(false)) : getDefaultState().withProperty((IProperty)FACING, (Comparable)EnumFacing.DOWN).withProperty((IProperty)POWERED, Boolean.valueOf(false));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
/* 104 */     if (checkForDrop(worldIn, pos, state) && !func_181088_a(worldIn, pos, ((EnumFacing)state.getValue((IProperty)FACING)).getOpposite())) {
/*     */       
/* 106 */       dropBlockAsItem(worldIn, pos, state, 0);
/* 107 */       worldIn.setBlockToAir(pos);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean checkForDrop(World worldIn, BlockPos pos, IBlockState state) {
/* 113 */     if (canPlaceBlockAt(worldIn, pos))
/*     */     {
/* 115 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 119 */     dropBlockAsItem(worldIn, pos, state, 0);
/* 120 */     worldIn.setBlockToAir(pos);
/* 121 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
/* 127 */     updateBlockBounds(worldIn.getBlockState(pos));
/*     */   }
/*     */ 
/*     */   
/*     */   private void updateBlockBounds(IBlockState state) {
/* 132 */     EnumFacing enumfacing = (EnumFacing)state.getValue((IProperty)FACING);
/* 133 */     boolean flag = ((Boolean)state.getValue((IProperty)POWERED)).booleanValue();
/* 134 */     float f = 0.25F;
/* 135 */     float f1 = 0.375F;
/* 136 */     float f2 = (flag ? true : 2) / 16.0F;
/* 137 */     float f3 = 0.125F;
/* 138 */     float f4 = 0.1875F;
/*     */     
/* 140 */     switch (enumfacing) {
/*     */       
/*     */       case EAST:
/* 143 */         setBlockBounds(0.0F, 0.375F, 0.3125F, f2, 0.625F, 0.6875F);
/*     */         break;
/*     */       
/*     */       case WEST:
/* 147 */         setBlockBounds(1.0F - f2, 0.375F, 0.3125F, 1.0F, 0.625F, 0.6875F);
/*     */         break;
/*     */       
/*     */       case SOUTH:
/* 151 */         setBlockBounds(0.3125F, 0.375F, 0.0F, 0.6875F, 0.625F, f2);
/*     */         break;
/*     */       
/*     */       case NORTH:
/* 155 */         setBlockBounds(0.3125F, 0.375F, 1.0F - f2, 0.6875F, 0.625F, 1.0F);
/*     */         break;
/*     */       
/*     */       case UP:
/* 159 */         setBlockBounds(0.3125F, 0.0F, 0.375F, 0.6875F, 0.0F + f2, 0.625F);
/*     */         break;
/*     */       
/*     */       case null:
/* 163 */         setBlockBounds(0.3125F, 1.0F - f2, 0.375F, 0.6875F, 1.0F, 0.625F);
/*     */         break;
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
/* 169 */     if (((Boolean)state.getValue((IProperty)POWERED)).booleanValue())
/*     */     {
/* 171 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 175 */     worldIn.setBlockState(pos, state.withProperty((IProperty)POWERED, Boolean.valueOf(true)), 3);
/* 176 */     worldIn.markBlockRangeForRenderUpdate(pos, pos);
/* 177 */     worldIn.playSoundEffect(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, "random.click", 0.3F, 0.6F);
/* 178 */     notifyNeighbors(worldIn, pos, (EnumFacing)state.getValue((IProperty)FACING));
/* 179 */     worldIn.scheduleUpdate(pos, this, tickRate(worldIn));
/* 180 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
/* 186 */     if (((Boolean)state.getValue((IProperty)POWERED)).booleanValue())
/*     */     {
/* 188 */       notifyNeighbors(worldIn, pos, (EnumFacing)state.getValue((IProperty)FACING));
/*     */     }
/*     */     
/* 191 */     super.breakBlock(worldIn, pos, state);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getWeakPower(IBlockAccess worldIn, BlockPos pos, IBlockState state, EnumFacing side) {
/* 196 */     return ((Boolean)state.getValue((IProperty)POWERED)).booleanValue() ? 15 : 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getStrongPower(IBlockAccess worldIn, BlockPos pos, IBlockState state, EnumFacing side) {
/* 201 */     return !((Boolean)state.getValue((IProperty)POWERED)).booleanValue() ? 0 : ((state.getValue((IProperty)FACING) == side) ? 15 : 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canProvidePower() {
/* 209 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void randomTick(World worldIn, BlockPos pos, IBlockState state, Random random) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/* 221 */     if (!worldIn.isRemote)
/*     */     {
/* 223 */       if (((Boolean)state.getValue((IProperty)POWERED)).booleanValue())
/*     */       {
/* 225 */         if (this.wooden) {
/*     */           
/* 227 */           checkForArrows(worldIn, pos, state);
/*     */         }
/*     */         else {
/*     */           
/* 231 */           worldIn.setBlockState(pos, state.withProperty((IProperty)POWERED, Boolean.valueOf(false)));
/* 232 */           notifyNeighbors(worldIn, pos, (EnumFacing)state.getValue((IProperty)FACING));
/* 233 */           worldIn.playSoundEffect(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, "random.click", 0.3F, 0.5F);
/* 234 */           worldIn.markBlockRangeForRenderUpdate(pos, pos);
/*     */         } 
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBlockBoundsForItemRender() {
/* 245 */     float f = 0.1875F;
/* 246 */     float f1 = 0.125F;
/* 247 */     float f2 = 0.125F;
/* 248 */     setBlockBounds(0.5F - f, 0.5F - f1, 0.5F - f2, 0.5F + f, 0.5F + f1, 0.5F + f2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
/* 256 */     if (!worldIn.isRemote)
/*     */     {
/* 258 */       if (this.wooden)
/*     */       {
/* 260 */         if (!((Boolean)state.getValue((IProperty)POWERED)).booleanValue())
/*     */         {
/* 262 */           checkForArrows(worldIn, pos, state);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void checkForArrows(World worldIn, BlockPos pos, IBlockState state) {
/* 270 */     updateBlockBounds(state);
/* 271 */     List<? extends Entity> list = worldIn.getEntitiesWithinAABB(EntityArrow.class, new AxisAlignedBB(pos.getX() + this.minX, pos.getY() + this.minY, pos.getZ() + this.minZ, pos.getX() + this.maxX, pos.getY() + this.maxY, pos.getZ() + this.maxZ));
/* 272 */     boolean flag = !list.isEmpty();
/* 273 */     boolean flag1 = ((Boolean)state.getValue((IProperty)POWERED)).booleanValue();
/*     */     
/* 275 */     if (flag && !flag1) {
/*     */       
/* 277 */       worldIn.setBlockState(pos, state.withProperty((IProperty)POWERED, Boolean.valueOf(true)));
/* 278 */       notifyNeighbors(worldIn, pos, (EnumFacing)state.getValue((IProperty)FACING));
/* 279 */       worldIn.markBlockRangeForRenderUpdate(pos, pos);
/* 280 */       worldIn.playSoundEffect(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, "random.click", 0.3F, 0.6F);
/*     */     } 
/*     */     
/* 283 */     if (!flag && flag1) {
/*     */       
/* 285 */       worldIn.setBlockState(pos, state.withProperty((IProperty)POWERED, Boolean.valueOf(false)));
/* 286 */       notifyNeighbors(worldIn, pos, (EnumFacing)state.getValue((IProperty)FACING));
/* 287 */       worldIn.markBlockRangeForRenderUpdate(pos, pos);
/* 288 */       worldIn.playSoundEffect(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, "random.click", 0.3F, 0.5F);
/*     */     } 
/*     */     
/* 291 */     if (flag)
/*     */     {
/* 293 */       worldIn.scheduleUpdate(pos, this, tickRate(worldIn));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void notifyNeighbors(World worldIn, BlockPos pos, EnumFacing facing) {
/* 299 */     worldIn.notifyNeighborsOfStateChange(pos, this);
/* 300 */     worldIn.notifyNeighborsOfStateChange(pos.offset(facing.getOpposite()), this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/*     */     EnumFacing enumfacing;
/* 310 */     switch (meta & 0x7) {
/*     */       
/*     */       case 0:
/* 313 */         enumfacing = EnumFacing.DOWN;
/*     */         break;
/*     */       
/*     */       case 1:
/* 317 */         enumfacing = EnumFacing.EAST;
/*     */         break;
/*     */       
/*     */       case 2:
/* 321 */         enumfacing = EnumFacing.WEST;
/*     */         break;
/*     */       
/*     */       case 3:
/* 325 */         enumfacing = EnumFacing.SOUTH;
/*     */         break;
/*     */       
/*     */       case 4:
/* 329 */         enumfacing = EnumFacing.NORTH;
/*     */         break;
/*     */ 
/*     */       
/*     */       default:
/* 334 */         enumfacing = EnumFacing.UP;
/*     */         break;
/*     */     } 
/* 337 */     return getDefaultState().withProperty((IProperty)FACING, (Comparable)enumfacing).withProperty((IProperty)POWERED, Boolean.valueOf(((meta & 0x8) > 0)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/*     */     int i;
/* 347 */     switch ((EnumFacing)state.getValue((IProperty)FACING)) {
/*     */       
/*     */       case EAST:
/* 350 */         i = 1;
/*     */         break;
/*     */       
/*     */       case WEST:
/* 354 */         i = 2;
/*     */         break;
/*     */       
/*     */       case SOUTH:
/* 358 */         i = 3;
/*     */         break;
/*     */       
/*     */       case NORTH:
/* 362 */         i = 4;
/*     */         break;
/*     */ 
/*     */       
/*     */       default:
/* 367 */         i = 5;
/*     */         break;
/*     */       
/*     */       case null:
/* 371 */         i = 0;
/*     */         break;
/*     */     } 
/* 374 */     if (((Boolean)state.getValue((IProperty)POWERED)).booleanValue())
/*     */     {
/* 376 */       i |= 0x8;
/*     */     }
/*     */     
/* 379 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockState createBlockState() {
/* 384 */     return new BlockState(this, new IProperty[] { (IProperty)FACING, (IProperty)POWERED });
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\block\BlockButton.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */