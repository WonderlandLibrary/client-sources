/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyBool;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.stats.StatList;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumWorldBlockLayer;
/*     */ import net.minecraft.world.ColorizerFoliage;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockVine
/*     */   extends Block {
/*  28 */   public static final PropertyBool UP = PropertyBool.create("up");
/*  29 */   public static final PropertyBool NORTH = PropertyBool.create("north");
/*  30 */   public static final PropertyBool EAST = PropertyBool.create("east");
/*  31 */   public static final PropertyBool SOUTH = PropertyBool.create("south");
/*  32 */   public static final PropertyBool WEST = PropertyBool.create("west");
/*  33 */   public static final PropertyBool[] ALL_FACES = new PropertyBool[] { UP, NORTH, SOUTH, WEST, EAST };
/*     */ 
/*     */   
/*     */   public BlockVine() {
/*  37 */     super(Material.vine);
/*  38 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)UP, Boolean.valueOf(false)).withProperty((IProperty)NORTH, Boolean.valueOf(false)).withProperty((IProperty)EAST, Boolean.valueOf(false)).withProperty((IProperty)SOUTH, Boolean.valueOf(false)).withProperty((IProperty)WEST, Boolean.valueOf(false)));
/*  39 */     setTickRandomly(true);
/*  40 */     setCreativeTab(CreativeTabs.tabDecorations);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
/*  49 */     return state.withProperty((IProperty)UP, Boolean.valueOf(worldIn.getBlockState(pos.up()).getBlock().isBlockNormalCube()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBlockBoundsForItemRender() {
/*  57 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube() {
/*  65 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFullCube() {
/*  70 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isReplaceable(World worldIn, BlockPos pos) {
/*  78 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
/*  83 */     float f = 0.0625F;
/*  84 */     float f1 = 1.0F;
/*  85 */     float f2 = 1.0F;
/*  86 */     float f3 = 1.0F;
/*  87 */     float f4 = 0.0F;
/*  88 */     float f5 = 0.0F;
/*  89 */     float f6 = 0.0F;
/*  90 */     boolean flag = false;
/*     */     
/*  92 */     if (((Boolean)worldIn.getBlockState(pos).getValue((IProperty)WEST)).booleanValue()) {
/*     */       
/*  94 */       f4 = Math.max(f4, 0.0625F);
/*  95 */       f1 = 0.0F;
/*  96 */       f2 = 0.0F;
/*  97 */       f5 = 1.0F;
/*  98 */       f3 = 0.0F;
/*  99 */       f6 = 1.0F;
/* 100 */       flag = true;
/*     */     } 
/*     */     
/* 103 */     if (((Boolean)worldIn.getBlockState(pos).getValue((IProperty)EAST)).booleanValue()) {
/*     */       
/* 105 */       f1 = Math.min(f1, 0.9375F);
/* 106 */       f4 = 1.0F;
/* 107 */       f2 = 0.0F;
/* 108 */       f5 = 1.0F;
/* 109 */       f3 = 0.0F;
/* 110 */       f6 = 1.0F;
/* 111 */       flag = true;
/*     */     } 
/*     */     
/* 114 */     if (((Boolean)worldIn.getBlockState(pos).getValue((IProperty)NORTH)).booleanValue()) {
/*     */       
/* 116 */       f6 = Math.max(f6, 0.0625F);
/* 117 */       f3 = 0.0F;
/* 118 */       f1 = 0.0F;
/* 119 */       f4 = 1.0F;
/* 120 */       f2 = 0.0F;
/* 121 */       f5 = 1.0F;
/* 122 */       flag = true;
/*     */     } 
/*     */     
/* 125 */     if (((Boolean)worldIn.getBlockState(pos).getValue((IProperty)SOUTH)).booleanValue()) {
/*     */       
/* 127 */       f3 = Math.min(f3, 0.9375F);
/* 128 */       f6 = 1.0F;
/* 129 */       f1 = 0.0F;
/* 130 */       f4 = 1.0F;
/* 131 */       f2 = 0.0F;
/* 132 */       f5 = 1.0F;
/* 133 */       flag = true;
/*     */     } 
/*     */     
/* 136 */     if (!flag && canPlaceOn(worldIn.getBlockState(pos.up()).getBlock())) {
/*     */       
/* 138 */       f2 = Math.min(f2, 0.9375F);
/* 139 */       f5 = 1.0F;
/* 140 */       f1 = 0.0F;
/* 141 */       f4 = 1.0F;
/* 142 */       f3 = 0.0F;
/* 143 */       f6 = 1.0F;
/*     */     } 
/*     */     
/* 146 */     setBlockBounds(f1, f2, f3, f4, f5, f6);
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {
/* 151 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side) {
/* 159 */     switch (side) {
/*     */       
/*     */       case UP:
/* 162 */         return canPlaceOn(worldIn.getBlockState(pos.up()).getBlock());
/*     */       
/*     */       case NORTH:
/*     */       case SOUTH:
/*     */       case WEST:
/*     */       case EAST:
/* 168 */         return canPlaceOn(worldIn.getBlockState(pos.offset(side.getOpposite())).getBlock());
/*     */     } 
/*     */     
/* 171 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean canPlaceOn(Block blockIn) {
/* 177 */     return (blockIn.isFullCube() && blockIn.blockMaterial.blocksMovement());
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean recheckGrownSides(World worldIn, BlockPos pos, IBlockState state) {
/* 182 */     IBlockState iblockstate = state;
/*     */     
/* 184 */     for (Object enumfacing0 : EnumFacing.Plane.HORIZONTAL) {
/*     */       
/* 186 */       EnumFacing enumfacing = (EnumFacing)enumfacing0;
/* 187 */       PropertyBool propertybool = getPropertyFor(enumfacing);
/*     */       
/* 189 */       if (((Boolean)state.getValue((IProperty)propertybool)).booleanValue() && !canPlaceOn(worldIn.getBlockState(pos.offset(enumfacing)).getBlock())) {
/*     */         
/* 191 */         IBlockState iblockstate1 = worldIn.getBlockState(pos.up());
/*     */         
/* 193 */         if (iblockstate1.getBlock() != this || !((Boolean)iblockstate1.getValue((IProperty)propertybool)).booleanValue())
/*     */         {
/* 195 */           state = state.withProperty((IProperty)propertybool, Boolean.valueOf(false));
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 200 */     if (getNumGrownFaces(state) == 0)
/*     */     {
/* 202 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 206 */     if (iblockstate != state)
/*     */     {
/* 208 */       worldIn.setBlockState(pos, state, 2);
/*     */     }
/*     */     
/* 211 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getBlockColor() {
/* 217 */     return ColorizerFoliage.getFoliageColorBasic();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getRenderColor(IBlockState state) {
/* 222 */     return ColorizerFoliage.getFoliageColorBasic();
/*     */   }
/*     */ 
/*     */   
/*     */   public int colorMultiplier(IBlockAccess worldIn, BlockPos pos, int renderPass) {
/* 227 */     return worldIn.getBiomeGenForCoords(pos).getFoliageColorAtPos(pos);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
/* 235 */     if (!worldIn.isRemote && !recheckGrownSides(worldIn, pos, state)) {
/*     */       
/* 237 */       dropBlockAsItem(worldIn, pos, state, 0);
/* 238 */       worldIn.setBlockToAir(pos);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/* 244 */     if (!worldIn.isRemote)
/*     */     {
/* 246 */       if (worldIn.rand.nextInt(4) == 0) {
/*     */         
/* 248 */         int i = 4;
/* 249 */         int j = 5;
/* 250 */         boolean flag = false;
/*     */         
/*     */         int k;
/* 253 */         label103: for (k = -i; k <= i; k++) {
/*     */           
/* 255 */           for (int l = -i; l <= i; l++) {
/*     */             
/* 257 */             for (int i1 = -1; i1 <= 1; i1++) {
/*     */               
/* 259 */               if (worldIn.getBlockState(pos.add(k, i1, l)).getBlock() == this) {
/*     */                 
/* 261 */                 j--;
/*     */                 
/* 263 */                 if (j <= 0) {
/*     */                   
/* 265 */                   flag = true;
/*     */                   
/*     */                   break label103;
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/* 273 */         EnumFacing enumfacing1 = EnumFacing.random(rand);
/* 274 */         BlockPos blockpos1 = pos.up();
/*     */         
/* 276 */         if (enumfacing1 == EnumFacing.UP && pos.getY() < 255 && worldIn.isAirBlock(blockpos1)) {
/*     */           
/* 278 */           if (!flag)
/*     */           {
/* 280 */             IBlockState iblockstate2 = state;
/*     */             
/* 282 */             for (Object enumfacing30 : EnumFacing.Plane.HORIZONTAL) {
/*     */               
/* 284 */               EnumFacing enumfacing3 = (EnumFacing)enumfacing30;
/*     */               
/* 286 */               if (rand.nextBoolean() || !canPlaceOn(worldIn.getBlockState(blockpos1.offset(enumfacing3)).getBlock()))
/*     */               {
/* 288 */                 iblockstate2 = iblockstate2.withProperty((IProperty)getPropertyFor(enumfacing3), Boolean.valueOf(false));
/*     */               }
/*     */             } 
/*     */             
/* 292 */             if (((Boolean)iblockstate2.getValue((IProperty)NORTH)).booleanValue() || ((Boolean)iblockstate2.getValue((IProperty)EAST)).booleanValue() || ((Boolean)iblockstate2.getValue((IProperty)SOUTH)).booleanValue() || ((Boolean)iblockstate2.getValue((IProperty)WEST)).booleanValue())
/*     */             {
/* 294 */               worldIn.setBlockState(blockpos1, iblockstate2, 2);
/*     */             }
/*     */           }
/*     */         
/* 298 */         } else if (enumfacing1.getAxis().isHorizontal() && !((Boolean)state.getValue((IProperty)getPropertyFor(enumfacing1))).booleanValue()) {
/*     */           
/* 300 */           if (!flag)
/*     */           {
/* 302 */             BlockPos blockpos3 = pos.offset(enumfacing1);
/* 303 */             Block block1 = worldIn.getBlockState(blockpos3).getBlock();
/*     */             
/* 305 */             if (block1.blockMaterial == Material.air) {
/*     */               
/* 307 */               EnumFacing enumfacing2 = enumfacing1.rotateY();
/* 308 */               EnumFacing enumfacing4 = enumfacing1.rotateYCCW();
/* 309 */               boolean flag1 = ((Boolean)state.getValue((IProperty)getPropertyFor(enumfacing2))).booleanValue();
/* 310 */               boolean flag2 = ((Boolean)state.getValue((IProperty)getPropertyFor(enumfacing4))).booleanValue();
/* 311 */               BlockPos blockpos4 = blockpos3.offset(enumfacing2);
/* 312 */               BlockPos blockpos = blockpos3.offset(enumfacing4);
/*     */               
/* 314 */               if (flag1 && canPlaceOn(worldIn.getBlockState(blockpos4).getBlock()))
/*     */               {
/* 316 */                 worldIn.setBlockState(blockpos3, getDefaultState().withProperty((IProperty)getPropertyFor(enumfacing2), Boolean.valueOf(true)), 2);
/*     */               }
/* 318 */               else if (flag2 && canPlaceOn(worldIn.getBlockState(blockpos).getBlock()))
/*     */               {
/* 320 */                 worldIn.setBlockState(blockpos3, getDefaultState().withProperty((IProperty)getPropertyFor(enumfacing4), Boolean.valueOf(true)), 2);
/*     */               }
/* 322 */               else if (flag1 && worldIn.isAirBlock(blockpos4) && canPlaceOn(worldIn.getBlockState(pos.offset(enumfacing2)).getBlock()))
/*     */               {
/* 324 */                 worldIn.setBlockState(blockpos4, getDefaultState().withProperty((IProperty)getPropertyFor(enumfacing1.getOpposite()), Boolean.valueOf(true)), 2);
/*     */               }
/* 326 */               else if (flag2 && worldIn.isAirBlock(blockpos) && canPlaceOn(worldIn.getBlockState(pos.offset(enumfacing4)).getBlock()))
/*     */               {
/* 328 */                 worldIn.setBlockState(blockpos, getDefaultState().withProperty((IProperty)getPropertyFor(enumfacing1.getOpposite()), Boolean.valueOf(true)), 2);
/*     */               }
/* 330 */               else if (canPlaceOn(worldIn.getBlockState(blockpos3.up()).getBlock()))
/*     */               {
/* 332 */                 worldIn.setBlockState(blockpos3, getDefaultState(), 2);
/*     */               }
/*     */             
/* 335 */             } else if (block1.blockMaterial.isOpaque() && block1.isFullCube()) {
/*     */               
/* 337 */               worldIn.setBlockState(pos, state.withProperty((IProperty)getPropertyFor(enumfacing1), Boolean.valueOf(true)), 2);
/*     */             }
/*     */           
/*     */           }
/*     */         
/*     */         }
/* 343 */         else if (pos.getY() > 1) {
/*     */           
/* 345 */           BlockPos blockpos2 = pos.down();
/* 346 */           IBlockState iblockstate = worldIn.getBlockState(blockpos2);
/* 347 */           Block block = iblockstate.getBlock();
/*     */           
/* 349 */           if (block.blockMaterial == Material.air) {
/*     */             
/* 351 */             IBlockState iblockstate1 = state;
/*     */             
/* 353 */             for (Object enumfacing0 : EnumFacing.Plane.HORIZONTAL) {
/*     */               
/* 355 */               EnumFacing enumfacing = (EnumFacing)enumfacing0;
/*     */               
/* 357 */               if (rand.nextBoolean())
/*     */               {
/* 359 */                 iblockstate1 = iblockstate1.withProperty((IProperty)getPropertyFor(enumfacing), Boolean.valueOf(false));
/*     */               }
/*     */             } 
/*     */             
/* 363 */             if (((Boolean)iblockstate1.getValue((IProperty)NORTH)).booleanValue() || ((Boolean)iblockstate1.getValue((IProperty)EAST)).booleanValue() || ((Boolean)iblockstate1.getValue((IProperty)SOUTH)).booleanValue() || ((Boolean)iblockstate1.getValue((IProperty)WEST)).booleanValue())
/*     */             {
/* 365 */               worldIn.setBlockState(blockpos2, iblockstate1, 2);
/*     */             }
/*     */           }
/* 368 */           else if (block == this) {
/*     */             
/* 370 */             IBlockState iblockstate3 = iblockstate;
/*     */             
/* 372 */             for (Object enumfacing50 : EnumFacing.Plane.HORIZONTAL) {
/*     */               
/* 374 */               EnumFacing enumfacing5 = (EnumFacing)enumfacing50;
/* 375 */               PropertyBool propertybool = getPropertyFor(enumfacing5);
/*     */               
/* 377 */               if (rand.nextBoolean() && ((Boolean)state.getValue((IProperty)propertybool)).booleanValue())
/*     */               {
/* 379 */                 iblockstate3 = iblockstate3.withProperty((IProperty)propertybool, Boolean.valueOf(true));
/*     */               }
/*     */             } 
/*     */             
/* 383 */             if (((Boolean)iblockstate3.getValue((IProperty)NORTH)).booleanValue() || ((Boolean)iblockstate3.getValue((IProperty)EAST)).booleanValue() || ((Boolean)iblockstate3.getValue((IProperty)SOUTH)).booleanValue() || ((Boolean)iblockstate3.getValue((IProperty)WEST)).booleanValue())
/*     */             {
/* 385 */               worldIn.setBlockState(blockpos2, iblockstate3, 2);
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
/* 400 */     IBlockState iblockstate = getDefaultState().withProperty((IProperty)UP, Boolean.valueOf(false)).withProperty((IProperty)NORTH, Boolean.valueOf(false)).withProperty((IProperty)EAST, Boolean.valueOf(false)).withProperty((IProperty)SOUTH, Boolean.valueOf(false)).withProperty((IProperty)WEST, Boolean.valueOf(false));
/* 401 */     return facing.getAxis().isHorizontal() ? iblockstate.withProperty((IProperty)getPropertyFor(facing.getOpposite()), Boolean.valueOf(true)) : iblockstate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/* 409 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int quantityDropped(Random random) {
/* 417 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te) {
/* 422 */     if (!worldIn.isRemote && player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() == Items.shears) {
/*     */       
/* 424 */       player.triggerAchievement(StatList.mineBlockStatArray[Block.getIdFromBlock(this)]);
/* 425 */       spawnAsEntity(worldIn, pos, new ItemStack(Blocks.vine, 1, 0));
/*     */     }
/*     */     else {
/*     */       
/* 429 */       super.harvestBlock(worldIn, player, pos, state, te);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public EnumWorldBlockLayer getBlockLayer() {
/* 435 */     return EnumWorldBlockLayer.CUTOUT;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 443 */     return getDefaultState().withProperty((IProperty)SOUTH, Boolean.valueOf(((meta & 0x1) > 0))).withProperty((IProperty)WEST, Boolean.valueOf(((meta & 0x2) > 0))).withProperty((IProperty)NORTH, Boolean.valueOf(((meta & 0x4) > 0))).withProperty((IProperty)EAST, Boolean.valueOf(((meta & 0x8) > 0)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 451 */     int i = 0;
/*     */     
/* 453 */     if (((Boolean)state.getValue((IProperty)SOUTH)).booleanValue())
/*     */     {
/* 455 */       i |= 0x1;
/*     */     }
/*     */     
/* 458 */     if (((Boolean)state.getValue((IProperty)WEST)).booleanValue())
/*     */     {
/* 460 */       i |= 0x2;
/*     */     }
/*     */     
/* 463 */     if (((Boolean)state.getValue((IProperty)NORTH)).booleanValue())
/*     */     {
/* 465 */       i |= 0x4;
/*     */     }
/*     */     
/* 468 */     if (((Boolean)state.getValue((IProperty)EAST)).booleanValue())
/*     */     {
/* 470 */       i |= 0x8;
/*     */     }
/*     */     
/* 473 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockState createBlockState() {
/* 478 */     return new BlockState(this, new IProperty[] { (IProperty)UP, (IProperty)NORTH, (IProperty)EAST, (IProperty)SOUTH, (IProperty)WEST });
/*     */   }
/*     */ 
/*     */   
/*     */   public static PropertyBool getPropertyFor(EnumFacing side) {
/* 483 */     switch (side) {
/*     */       
/*     */       case UP:
/* 486 */         return UP;
/*     */       
/*     */       case NORTH:
/* 489 */         return NORTH;
/*     */       
/*     */       case SOUTH:
/* 492 */         return SOUTH;
/*     */       
/*     */       case EAST:
/* 495 */         return EAST;
/*     */       
/*     */       case WEST:
/* 498 */         return WEST;
/*     */     } 
/*     */     
/* 501 */     throw new IllegalArgumentException(side + " is an invalid choice");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getNumGrownFaces(IBlockState state) {
/* 507 */     int i = 0; byte b; int j;
/*     */     PropertyBool[] arrayOfPropertyBool;
/* 509 */     for (j = (arrayOfPropertyBool = ALL_FACES).length, b = 0; b < j; ) { PropertyBool propertybool = arrayOfPropertyBool[b];
/*     */       
/* 511 */       if (((Boolean)state.getValue((IProperty)propertybool)).booleanValue())
/*     */       {
/* 513 */         i++;
/*     */       }
/*     */       b++; }
/*     */     
/* 517 */     return i;
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\block\BlockVine.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */