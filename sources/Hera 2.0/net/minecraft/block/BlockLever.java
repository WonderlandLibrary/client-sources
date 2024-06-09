/*     */ package net.minecraft.block;
/*     */ 
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyBool;
/*     */ import net.minecraft.block.properties.PropertyEnum;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.IStringSerializable;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockLever
/*     */   extends Block {
/*  21 */   public static final PropertyEnum<EnumOrientation> FACING = PropertyEnum.create("facing", EnumOrientation.class);
/*  22 */   public static final PropertyBool POWERED = PropertyBool.create("powered");
/*     */ 
/*     */   
/*     */   protected BlockLever() {
/*  26 */     super(Material.circuits);
/*  27 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)FACING, EnumOrientation.NORTH).withProperty((IProperty)POWERED, Boolean.valueOf(false)));
/*  28 */     setCreativeTab(CreativeTabs.tabRedstone);
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {
/*  33 */     return null;
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
/*     */   public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side) {
/*  54 */     return func_181090_a(worldIn, pos, side.getOpposite());
/*     */   } public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
/*     */     byte b;
/*     */     int i;
/*     */     EnumFacing[] arrayOfEnumFacing;
/*  59 */     for (i = (arrayOfEnumFacing = EnumFacing.values()).length, b = 0; b < i; ) { EnumFacing enumfacing = arrayOfEnumFacing[b];
/*     */       
/*  61 */       if (func_181090_a(worldIn, pos, enumfacing))
/*     */       {
/*  63 */         return true;
/*     */       }
/*     */       b++; }
/*     */     
/*  67 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected static boolean func_181090_a(World p_181090_0_, BlockPos p_181090_1_, EnumFacing p_181090_2_) {
/*  72 */     return BlockButton.func_181088_a(p_181090_0_, p_181090_1_, p_181090_2_);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
/*  81 */     IBlockState iblockstate = getDefaultState().withProperty((IProperty)POWERED, Boolean.valueOf(false));
/*     */     
/*  83 */     if (func_181090_a(worldIn, pos, facing.getOpposite()))
/*     */     {
/*  85 */       return iblockstate.withProperty((IProperty)FACING, EnumOrientation.forFacings(facing, placer.getHorizontalFacing()));
/*     */     }
/*     */ 
/*     */     
/*  89 */     for (Object enumfacing0 : EnumFacing.Plane.HORIZONTAL) {
/*     */       
/*  91 */       EnumFacing enumfacing = (EnumFacing)enumfacing0;
/*     */       
/*  93 */       if (enumfacing != facing && func_181090_a(worldIn, pos, enumfacing.getOpposite()))
/*     */       {
/*  95 */         return iblockstate.withProperty((IProperty)FACING, EnumOrientation.forFacings(enumfacing, placer.getHorizontalFacing()));
/*     */       }
/*     */     } 
/*     */     
/*  99 */     if (World.doesBlockHaveSolidTopSurface((IBlockAccess)worldIn, pos.down()))
/*     */     {
/* 101 */       return iblockstate.withProperty((IProperty)FACING, EnumOrientation.forFacings(EnumFacing.UP, placer.getHorizontalFacing()));
/*     */     }
/*     */ 
/*     */     
/* 105 */     return iblockstate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getMetadataForFacing(EnumFacing facing) {
/* 112 */     switch (facing) {
/*     */       
/*     */       case null:
/* 115 */         return 0;
/*     */       
/*     */       case UP:
/* 118 */         return 5;
/*     */       
/*     */       case NORTH:
/* 121 */         return 4;
/*     */       
/*     */       case SOUTH:
/* 124 */         return 3;
/*     */       
/*     */       case WEST:
/* 127 */         return 2;
/*     */       
/*     */       case EAST:
/* 130 */         return 1;
/*     */     } 
/*     */     
/* 133 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
/* 142 */     if (func_181091_e(worldIn, pos, state) && !func_181090_a(worldIn, pos, ((EnumOrientation)state.getValue((IProperty)FACING)).getFacing().getOpposite())) {
/*     */       
/* 144 */       dropBlockAsItem(worldIn, pos, state, 0);
/* 145 */       worldIn.setBlockToAir(pos);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean func_181091_e(World p_181091_1_, BlockPos p_181091_2_, IBlockState p_181091_3_) {
/* 151 */     if (canPlaceBlockAt(p_181091_1_, p_181091_2_))
/*     */     {
/* 153 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 157 */     dropBlockAsItem(p_181091_1_, p_181091_2_, p_181091_3_, 0);
/* 158 */     p_181091_1_.setBlockToAir(p_181091_2_);
/* 159 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
/* 165 */     float f = 0.1875F;
/*     */     
/* 167 */     switch ((EnumOrientation)worldIn.getBlockState(pos).getValue((IProperty)FACING)) {
/*     */       
/*     */       case EAST:
/* 170 */         setBlockBounds(0.0F, 0.2F, 0.5F - f, f * 2.0F, 0.8F, 0.5F + f);
/*     */         break;
/*     */       
/*     */       case WEST:
/* 174 */         setBlockBounds(1.0F - f * 2.0F, 0.2F, 0.5F - f, 1.0F, 0.8F, 0.5F + f);
/*     */         break;
/*     */       
/*     */       case SOUTH:
/* 178 */         setBlockBounds(0.5F - f, 0.2F, 0.0F, 0.5F + f, 0.8F, f * 2.0F);
/*     */         break;
/*     */       
/*     */       case NORTH:
/* 182 */         setBlockBounds(0.5F - f, 0.2F, 1.0F - f * 2.0F, 0.5F + f, 0.8F, 1.0F);
/*     */         break;
/*     */       
/*     */       case UP_Z:
/*     */       case UP_X:
/* 187 */         f = 0.25F;
/* 188 */         setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 0.6F, 0.5F + f);
/*     */         break;
/*     */       
/*     */       case null:
/*     */       case DOWN_Z:
/* 193 */         f = 0.25F;
/* 194 */         setBlockBounds(0.5F - f, 0.4F, 0.5F - f, 0.5F + f, 1.0F, 0.5F + f);
/*     */         break;
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
/* 200 */     if (worldIn.isRemote)
/*     */     {
/* 202 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 206 */     state = state.cycleProperty((IProperty)POWERED);
/* 207 */     worldIn.setBlockState(pos, state, 3);
/* 208 */     worldIn.playSoundEffect(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, "random.click", 0.3F, ((Boolean)state.getValue((IProperty)POWERED)).booleanValue() ? 0.6F : 0.5F);
/* 209 */     worldIn.notifyNeighborsOfStateChange(pos, this);
/* 210 */     EnumFacing enumfacing = ((EnumOrientation)state.getValue((IProperty)FACING)).getFacing();
/* 211 */     worldIn.notifyNeighborsOfStateChange(pos.offset(enumfacing.getOpposite()), this);
/* 212 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
/* 218 */     if (((Boolean)state.getValue((IProperty)POWERED)).booleanValue()) {
/*     */       
/* 220 */       worldIn.notifyNeighborsOfStateChange(pos, this);
/* 221 */       EnumFacing enumfacing = ((EnumOrientation)state.getValue((IProperty)FACING)).getFacing();
/* 222 */       worldIn.notifyNeighborsOfStateChange(pos.offset(enumfacing.getOpposite()), this);
/*     */     } 
/*     */     
/* 225 */     super.breakBlock(worldIn, pos, state);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getWeakPower(IBlockAccess worldIn, BlockPos pos, IBlockState state, EnumFacing side) {
/* 230 */     return ((Boolean)state.getValue((IProperty)POWERED)).booleanValue() ? 15 : 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getStrongPower(IBlockAccess worldIn, BlockPos pos, IBlockState state, EnumFacing side) {
/* 235 */     return !((Boolean)state.getValue((IProperty)POWERED)).booleanValue() ? 0 : ((((EnumOrientation)state.getValue((IProperty)FACING)).getFacing() == side) ? 15 : 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canProvidePower() {
/* 243 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 251 */     return getDefaultState().withProperty((IProperty)FACING, EnumOrientation.byMetadata(meta & 0x7)).withProperty((IProperty)POWERED, Boolean.valueOf(((meta & 0x8) > 0)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 259 */     int i = 0;
/* 260 */     i |= ((EnumOrientation)state.getValue((IProperty)FACING)).getMetadata();
/*     */     
/* 262 */     if (((Boolean)state.getValue((IProperty)POWERED)).booleanValue())
/*     */     {
/* 264 */       i |= 0x8;
/*     */     }
/*     */     
/* 267 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockState createBlockState() {
/* 272 */     return new BlockState(this, new IProperty[] { (IProperty)FACING, (IProperty)POWERED });
/*     */   }
/*     */   
/*     */   public enum EnumOrientation
/*     */     implements IStringSerializable {
/* 277 */     DOWN_X(0, "down_x", EnumFacing.DOWN),
/* 278 */     EAST(1, "east", EnumFacing.EAST),
/* 279 */     WEST(2, "west", EnumFacing.WEST),
/* 280 */     SOUTH(3, "south", EnumFacing.SOUTH),
/* 281 */     NORTH(4, "north", EnumFacing.NORTH),
/* 282 */     UP_Z(5, "up_z", EnumFacing.UP),
/* 283 */     UP_X(6, "up_x", EnumFacing.UP),
/* 284 */     DOWN_Z(7, "down_z", EnumFacing.DOWN);
/*     */     
/* 286 */     private static final EnumOrientation[] META_LOOKUP = new EnumOrientation[(values()).length];
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final int meta;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final String name;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final EnumFacing facing;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static {
/*     */       byte b;
/*     */       int i;
/*     */       EnumOrientation[] arrayOfEnumOrientation;
/* 376 */       for (i = (arrayOfEnumOrientation = values()).length, b = 0; b < i; ) { EnumOrientation blocklever$enumorientation = arrayOfEnumOrientation[b];
/*     */         
/* 378 */         META_LOOKUP[blocklever$enumorientation.getMetadata()] = blocklever$enumorientation;
/*     */         b++; }
/*     */     
/*     */     }
/*     */     
/*     */     EnumOrientation(int meta, String name, EnumFacing facing) {
/*     */       this.meta = meta;
/*     */       this.name = name;
/*     */       this.facing = facing;
/*     */     }
/*     */     
/*     */     public int getMetadata() {
/*     */       return this.meta;
/*     */     }
/*     */     
/*     */     public EnumFacing getFacing() {
/*     */       return this.facing;
/*     */     }
/*     */     
/*     */     public String toString() {
/*     */       return this.name;
/*     */     }
/*     */     
/*     */     public static EnumOrientation byMetadata(int meta) {
/*     */       if (meta < 0 || meta >= META_LOOKUP.length)
/*     */         meta = 0; 
/*     */       return META_LOOKUP[meta];
/*     */     }
/*     */     
/*     */     public static EnumOrientation forFacings(EnumFacing clickedSide, EnumFacing entityFacing) {
/*     */       switch (clickedSide) {
/*     */         case null:
/*     */           switch (entityFacing.getAxis()) {
/*     */             case null:
/*     */               return DOWN_X;
/*     */             case Z:
/*     */               return DOWN_Z;
/*     */           } 
/*     */           throw new IllegalArgumentException("Invalid entityFacing " + entityFacing + " for facing " + clickedSide);
/*     */         case UP:
/*     */           switch (entityFacing.getAxis()) {
/*     */             case null:
/*     */               return UP_X;
/*     */             case Z:
/*     */               return UP_Z;
/*     */           } 
/*     */           throw new IllegalArgumentException("Invalid entityFacing " + entityFacing + " for facing " + clickedSide);
/*     */         case NORTH:
/*     */           return NORTH;
/*     */         case SOUTH:
/*     */           return SOUTH;
/*     */         case WEST:
/*     */           return WEST;
/*     */         case EAST:
/*     */           return EAST;
/*     */       } 
/*     */       throw new IllegalArgumentException("Invalid facing: " + clickedSide);
/*     */     }
/*     */     
/*     */     public String getName() {
/*     */       return this.name;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\block\BlockLever.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */