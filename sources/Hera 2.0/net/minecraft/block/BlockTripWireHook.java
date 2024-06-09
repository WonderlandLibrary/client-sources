/*     */ package net.minecraft.block;
/*     */ 
/*     */ import com.google.common.base.Objects;
/*     */ import com.google.common.base.Predicate;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyBool;
/*     */ import net.minecraft.block.properties.PropertyDirection;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumWorldBlockLayer;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockTripWireHook extends Block {
/*  24 */   public static final PropertyDirection FACING = PropertyDirection.create("facing", (Predicate)EnumFacing.Plane.HORIZONTAL);
/*  25 */   public static final PropertyBool POWERED = PropertyBool.create("powered");
/*  26 */   public static final PropertyBool ATTACHED = PropertyBool.create("attached");
/*  27 */   public static final PropertyBool SUSPENDED = PropertyBool.create("suspended");
/*     */ 
/*     */   
/*     */   public BlockTripWireHook() {
/*  31 */     super(Material.circuits);
/*  32 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)FACING, (Comparable)EnumFacing.NORTH).withProperty((IProperty)POWERED, Boolean.valueOf(false)).withProperty((IProperty)ATTACHED, Boolean.valueOf(false)).withProperty((IProperty)SUSPENDED, Boolean.valueOf(false)));
/*  33 */     setCreativeTab(CreativeTabs.tabRedstone);
/*  34 */     setTickRandomly(true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
/*  43 */     return state.withProperty((IProperty)SUSPENDED, Boolean.valueOf(!World.doesBlockHaveSolidTopSurface(worldIn, pos.down())));
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {
/*  48 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube() {
/*  56 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFullCube() {
/*  61 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side) {
/*  69 */     return (side.getAxis().isHorizontal() && worldIn.getBlockState(pos.offset(side.getOpposite())).getBlock().isNormalCube());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
/*  74 */     for (Object enumfacing0 : EnumFacing.Plane.HORIZONTAL) {
/*     */       
/*  76 */       EnumFacing enumfacing = (EnumFacing)enumfacing0;
/*     */       
/*  78 */       if (worldIn.getBlockState(pos.offset(enumfacing)).getBlock().isNormalCube())
/*     */       {
/*  80 */         return true;
/*     */       }
/*     */     } 
/*     */     
/*  84 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
/*  93 */     IBlockState iblockstate = getDefaultState().withProperty((IProperty)POWERED, Boolean.valueOf(false)).withProperty((IProperty)ATTACHED, Boolean.valueOf(false)).withProperty((IProperty)SUSPENDED, Boolean.valueOf(false));
/*     */     
/*  95 */     if (facing.getAxis().isHorizontal())
/*     */     {
/*  97 */       iblockstate = iblockstate.withProperty((IProperty)FACING, (Comparable)facing);
/*     */     }
/*     */     
/* 100 */     return iblockstate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
/* 108 */     func_176260_a(worldIn, pos, state, false, false, -1, (IBlockState)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
/* 116 */     if (neighborBlock != this)
/*     */     {
/* 118 */       if (checkForDrop(worldIn, pos, state)) {
/*     */         
/* 120 */         EnumFacing enumfacing = (EnumFacing)state.getValue((IProperty)FACING);
/*     */         
/* 122 */         if (!worldIn.getBlockState(pos.offset(enumfacing.getOpposite())).getBlock().isNormalCube()) {
/*     */           
/* 124 */           dropBlockAsItem(worldIn, pos, state, 0);
/* 125 */           worldIn.setBlockToAir(pos);
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public void func_176260_a(World worldIn, BlockPos pos, IBlockState hookState, boolean p_176260_4_, boolean p_176260_5_, int p_176260_6_, IBlockState p_176260_7_) {
/*     */     int k, m;
/* 133 */     EnumFacing enumfacing = (EnumFacing)hookState.getValue((IProperty)FACING);
/* 134 */     int flag = ((Boolean)hookState.getValue((IProperty)ATTACHED)).booleanValue();
/* 135 */     boolean flag1 = ((Boolean)hookState.getValue((IProperty)POWERED)).booleanValue();
/* 136 */     boolean flag2 = !World.doesBlockHaveSolidTopSurface((IBlockAccess)worldIn, pos.down());
/* 137 */     boolean flag3 = !p_176260_4_;
/* 138 */     boolean flag4 = false;
/* 139 */     int i = 0;
/* 140 */     IBlockState[] aiblockstate = new IBlockState[42];
/*     */     
/* 142 */     for (int j = 1; j < 42; j++) {
/*     */       
/* 144 */       BlockPos blockpos = pos.offset(enumfacing, j);
/* 145 */       IBlockState iblockstate = worldIn.getBlockState(blockpos);
/*     */       
/* 147 */       if (iblockstate.getBlock() == Blocks.tripwire_hook) {
/*     */         
/* 149 */         if (iblockstate.getValue((IProperty)FACING) == enumfacing.getOpposite())
/*     */         {
/* 151 */           i = j;
/*     */         }
/*     */         
/*     */         break;
/*     */       } 
/*     */       
/* 157 */       if (iblockstate.getBlock() != Blocks.tripwire && j != p_176260_6_) {
/*     */         
/* 159 */         aiblockstate[j] = null;
/* 160 */         flag3 = false;
/*     */       }
/*     */       else {
/*     */         
/* 164 */         if (j == p_176260_6_)
/*     */         {
/* 166 */           iblockstate = (IBlockState)Objects.firstNonNull(p_176260_7_, iblockstate);
/*     */         }
/*     */         
/* 169 */         int flag5 = ((Boolean)iblockstate.getValue((IProperty)BlockTripWire.DISARMED)).booleanValue() ? 0 : 1;
/* 170 */         boolean flag6 = ((Boolean)iblockstate.getValue((IProperty)BlockTripWire.POWERED)).booleanValue();
/* 171 */         boolean flag7 = ((Boolean)iblockstate.getValue((IProperty)BlockTripWire.SUSPENDED)).booleanValue();
/* 172 */         int n = flag3 & ((flag7 == flag2) ? 1 : 0);
/* 173 */         m = flag4 | ((flag5 && flag6) ? 1 : 0);
/* 174 */         aiblockstate[j] = iblockstate;
/*     */         
/* 176 */         if (j == p_176260_6_) {
/*     */           
/* 178 */           worldIn.scheduleUpdate(pos, this, tickRate(worldIn));
/* 179 */           k = n & flag5;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 184 */     k &= (i > 1) ? 1 : 0;
/* 185 */     m &= k;
/* 186 */     IBlockState iblockstate1 = getDefaultState().withProperty((IProperty)ATTACHED, Boolean.valueOf(k)).withProperty((IProperty)POWERED, Boolean.valueOf(m));
/*     */     
/* 188 */     if (i > 0) {
/*     */       
/* 190 */       BlockPos blockpos1 = pos.offset(enumfacing, i);
/* 191 */       EnumFacing enumfacing1 = enumfacing.getOpposite();
/* 192 */       worldIn.setBlockState(blockpos1, iblockstate1.withProperty((IProperty)FACING, (Comparable)enumfacing1), 3);
/* 193 */       func_176262_b(worldIn, blockpos1, enumfacing1);
/* 194 */       func_180694_a(worldIn, blockpos1, k, m, flag, flag1);
/*     */     } 
/*     */     
/* 197 */     func_180694_a(worldIn, pos, k, m, flag, flag1);
/*     */     
/* 199 */     if (!p_176260_4_) {
/*     */       
/* 201 */       worldIn.setBlockState(pos, iblockstate1.withProperty((IProperty)FACING, (Comparable)enumfacing), 3);
/*     */       
/* 203 */       if (p_176260_5_)
/*     */       {
/* 205 */         func_176262_b(worldIn, pos, enumfacing);
/*     */       }
/*     */     } 
/*     */     
/* 209 */     if (flag != k)
/*     */     {
/* 211 */       for (int n = 1; n < i; n++) {
/*     */         
/* 213 */         BlockPos blockpos2 = pos.offset(enumfacing, n);
/* 214 */         IBlockState iblockstate2 = aiblockstate[n];
/*     */         
/* 216 */         if (iblockstate2 != null && worldIn.getBlockState(blockpos2).getBlock() != Blocks.air)
/*     */         {
/* 218 */           worldIn.setBlockState(blockpos2, iblockstate2.withProperty((IProperty)ATTACHED, Boolean.valueOf(k)), 3);
/*     */         }
/*     */       } 
/*     */     }
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
/* 233 */     func_176260_a(worldIn, pos, state, false, true, -1, (IBlockState)null);
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_180694_a(World worldIn, BlockPos pos, boolean p_180694_3_, boolean p_180694_4_, boolean p_180694_5_, boolean p_180694_6_) {
/* 238 */     if (p_180694_4_ && !p_180694_6_) {
/*     */       
/* 240 */       worldIn.playSoundEffect(pos.getX() + 0.5D, pos.getY() + 0.1D, pos.getZ() + 0.5D, "random.click", 0.4F, 0.6F);
/*     */     }
/* 242 */     else if (!p_180694_4_ && p_180694_6_) {
/*     */       
/* 244 */       worldIn.playSoundEffect(pos.getX() + 0.5D, pos.getY() + 0.1D, pos.getZ() + 0.5D, "random.click", 0.4F, 0.5F);
/*     */     }
/* 246 */     else if (p_180694_3_ && !p_180694_5_) {
/*     */       
/* 248 */       worldIn.playSoundEffect(pos.getX() + 0.5D, pos.getY() + 0.1D, pos.getZ() + 0.5D, "random.click", 0.4F, 0.7F);
/*     */     }
/* 250 */     else if (!p_180694_3_ && p_180694_5_) {
/*     */       
/* 252 */       worldIn.playSoundEffect(pos.getX() + 0.5D, pos.getY() + 0.1D, pos.getZ() + 0.5D, "random.bowhit", 0.4F, 1.2F / (worldIn.rand.nextFloat() * 0.2F + 0.9F));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_176262_b(World worldIn, BlockPos p_176262_2_, EnumFacing p_176262_3_) {
/* 258 */     worldIn.notifyNeighborsOfStateChange(p_176262_2_, this);
/* 259 */     worldIn.notifyNeighborsOfStateChange(p_176262_2_.offset(p_176262_3_.getOpposite()), this);
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean checkForDrop(World worldIn, BlockPos pos, IBlockState state) {
/* 264 */     if (!canPlaceBlockAt(worldIn, pos)) {
/*     */       
/* 266 */       dropBlockAsItem(worldIn, pos, state, 0);
/* 267 */       worldIn.setBlockToAir(pos);
/* 268 */       return false;
/*     */     } 
/*     */ 
/*     */     
/* 272 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
/* 279 */     float f = 0.1875F;
/*     */     
/* 281 */     switch ((EnumFacing)worldIn.getBlockState(pos).getValue((IProperty)FACING)) {
/*     */       
/*     */       case EAST:
/* 284 */         setBlockBounds(0.0F, 0.2F, 0.5F - f, f * 2.0F, 0.8F, 0.5F + f);
/*     */         break;
/*     */       
/*     */       case WEST:
/* 288 */         setBlockBounds(1.0F - f * 2.0F, 0.2F, 0.5F - f, 1.0F, 0.8F, 0.5F + f);
/*     */         break;
/*     */       
/*     */       case SOUTH:
/* 292 */         setBlockBounds(0.5F - f, 0.2F, 0.0F, 0.5F + f, 0.8F, f * 2.0F);
/*     */         break;
/*     */       
/*     */       case NORTH:
/* 296 */         setBlockBounds(0.5F - f, 0.2F, 1.0F - f * 2.0F, 0.5F + f, 0.8F, 1.0F);
/*     */         break;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
/* 302 */     boolean flag = ((Boolean)state.getValue((IProperty)ATTACHED)).booleanValue();
/* 303 */     boolean flag1 = ((Boolean)state.getValue((IProperty)POWERED)).booleanValue();
/*     */     
/* 305 */     if (flag || flag1)
/*     */     {
/* 307 */       func_176260_a(worldIn, pos, state, true, false, -1, (IBlockState)null);
/*     */     }
/*     */     
/* 310 */     if (flag1) {
/*     */       
/* 312 */       worldIn.notifyNeighborsOfStateChange(pos, this);
/* 313 */       worldIn.notifyNeighborsOfStateChange(pos.offset(((EnumFacing)state.getValue((IProperty)FACING)).getOpposite()), this);
/*     */     } 
/*     */     
/* 316 */     super.breakBlock(worldIn, pos, state);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getWeakPower(IBlockAccess worldIn, BlockPos pos, IBlockState state, EnumFacing side) {
/* 321 */     return ((Boolean)state.getValue((IProperty)POWERED)).booleanValue() ? 15 : 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getStrongPower(IBlockAccess worldIn, BlockPos pos, IBlockState state, EnumFacing side) {
/* 326 */     return !((Boolean)state.getValue((IProperty)POWERED)).booleanValue() ? 0 : ((state.getValue((IProperty)FACING) == side) ? 15 : 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canProvidePower() {
/* 334 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public EnumWorldBlockLayer getBlockLayer() {
/* 339 */     return EnumWorldBlockLayer.CUTOUT_MIPPED;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 347 */     return getDefaultState().withProperty((IProperty)FACING, (Comparable)EnumFacing.getHorizontal(meta & 0x3)).withProperty((IProperty)POWERED, Boolean.valueOf(((meta & 0x8) > 0))).withProperty((IProperty)ATTACHED, Boolean.valueOf(((meta & 0x4) > 0)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 355 */     int i = 0;
/* 356 */     i |= ((EnumFacing)state.getValue((IProperty)FACING)).getHorizontalIndex();
/*     */     
/* 358 */     if (((Boolean)state.getValue((IProperty)POWERED)).booleanValue())
/*     */     {
/* 360 */       i |= 0x8;
/*     */     }
/*     */     
/* 363 */     if (((Boolean)state.getValue((IProperty)ATTACHED)).booleanValue())
/*     */     {
/* 365 */       i |= 0x4;
/*     */     }
/*     */     
/* 368 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockState createBlockState() {
/* 373 */     return new BlockState(this, new IProperty[] { (IProperty)FACING, (IProperty)POWERED, (IProperty)ATTACHED, (IProperty)SUSPENDED });
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\block\BlockTripWireHook.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */