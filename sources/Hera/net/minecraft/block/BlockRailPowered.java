/*     */ package net.minecraft.block;
/*     */ 
/*     */ import com.google.common.base.Predicate;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyBool;
/*     */ import net.minecraft.block.properties.PropertyEnum;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockRailPowered
/*     */   extends BlockRailBase {
/*  14 */   public static final PropertyEnum<BlockRailBase.EnumRailDirection> SHAPE = PropertyEnum.create("shape", BlockRailBase.EnumRailDirection.class, new Predicate<BlockRailBase.EnumRailDirection>()
/*     */       {
/*     */         public boolean apply(BlockRailBase.EnumRailDirection p_apply_1_)
/*     */         {
/*  18 */           return (p_apply_1_ != BlockRailBase.EnumRailDirection.NORTH_EAST && p_apply_1_ != BlockRailBase.EnumRailDirection.NORTH_WEST && p_apply_1_ != BlockRailBase.EnumRailDirection.SOUTH_EAST && p_apply_1_ != BlockRailBase.EnumRailDirection.SOUTH_WEST);
/*     */         }
/*     */       });
/*  21 */   public static final PropertyBool POWERED = PropertyBool.create("powered");
/*     */ 
/*     */   
/*     */   protected BlockRailPowered() {
/*  25 */     super(true);
/*  26 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.NORTH_SOUTH).withProperty((IProperty)POWERED, Boolean.valueOf(false)));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean func_176566_a(World worldIn, BlockPos pos, IBlockState state, boolean p_176566_4_, int p_176566_5_) {
/*  32 */     if (p_176566_5_ >= 8)
/*     */     {
/*  34 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  38 */     int i = pos.getX();
/*  39 */     int j = pos.getY();
/*  40 */     int k = pos.getZ();
/*  41 */     boolean flag = true;
/*  42 */     BlockRailBase.EnumRailDirection blockrailbase$enumraildirection = (BlockRailBase.EnumRailDirection)state.getValue((IProperty)SHAPE);
/*     */     
/*  44 */     switch (blockrailbase$enumraildirection) {
/*     */       
/*     */       case NORTH_SOUTH:
/*  47 */         if (p_176566_4_) {
/*     */           
/*  49 */           k++;
/*     */           
/*     */           break;
/*     */         } 
/*  53 */         k--;
/*     */         break;
/*     */ 
/*     */ 
/*     */       
/*     */       case EAST_WEST:
/*  59 */         if (p_176566_4_) {
/*     */           
/*  61 */           i--;
/*     */           
/*     */           break;
/*     */         } 
/*  65 */         i++;
/*     */         break;
/*     */ 
/*     */ 
/*     */       
/*     */       case null:
/*  71 */         if (p_176566_4_) {
/*     */           
/*  73 */           i--;
/*     */         }
/*     */         else {
/*     */           
/*  77 */           i++;
/*  78 */           j++;
/*  79 */           flag = false;
/*     */         } 
/*     */         
/*  82 */         blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.EAST_WEST;
/*     */         break;
/*     */       
/*     */       case ASCENDING_WEST:
/*  86 */         if (p_176566_4_) {
/*     */           
/*  88 */           i--;
/*  89 */           j++;
/*  90 */           flag = false;
/*     */         }
/*     */         else {
/*     */           
/*  94 */           i++;
/*     */         } 
/*     */         
/*  97 */         blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.EAST_WEST;
/*     */         break;
/*     */       
/*     */       case ASCENDING_NORTH:
/* 101 */         if (p_176566_4_) {
/*     */           
/* 103 */           k++;
/*     */         }
/*     */         else {
/*     */           
/* 107 */           k--;
/* 108 */           j++;
/* 109 */           flag = false;
/*     */         } 
/*     */         
/* 112 */         blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.NORTH_SOUTH;
/*     */         break;
/*     */       
/*     */       case ASCENDING_SOUTH:
/* 116 */         if (p_176566_4_) {
/*     */           
/* 118 */           k++;
/* 119 */           j++;
/* 120 */           flag = false;
/*     */         }
/*     */         else {
/*     */           
/* 124 */           k--;
/*     */         } 
/*     */         
/* 127 */         blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.NORTH_SOUTH;
/*     */         break;
/*     */     } 
/* 130 */     return func_176567_a(worldIn, new BlockPos(i, j, k), p_176566_4_, p_176566_5_, blockrailbase$enumraildirection) ? true : ((flag && func_176567_a(worldIn, new BlockPos(i, j - 1, k), p_176566_4_, p_176566_5_, blockrailbase$enumraildirection)));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean func_176567_a(World worldIn, BlockPos p_176567_2_, boolean p_176567_3_, int distance, BlockRailBase.EnumRailDirection p_176567_5_) {
/* 136 */     IBlockState iblockstate = worldIn.getBlockState(p_176567_2_);
/*     */     
/* 138 */     if (iblockstate.getBlock() != this)
/*     */     {
/* 140 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 144 */     BlockRailBase.EnumRailDirection blockrailbase$enumraildirection = (BlockRailBase.EnumRailDirection)iblockstate.getValue((IProperty)SHAPE);
/* 145 */     return (p_176567_5_ != BlockRailBase.EnumRailDirection.EAST_WEST || (blockrailbase$enumraildirection != BlockRailBase.EnumRailDirection.NORTH_SOUTH && blockrailbase$enumraildirection != BlockRailBase.EnumRailDirection.ASCENDING_NORTH && blockrailbase$enumraildirection != BlockRailBase.EnumRailDirection.ASCENDING_SOUTH)) ? ((p_176567_5_ != BlockRailBase.EnumRailDirection.NORTH_SOUTH || (blockrailbase$enumraildirection != BlockRailBase.EnumRailDirection.EAST_WEST && blockrailbase$enumraildirection != BlockRailBase.EnumRailDirection.ASCENDING_EAST && blockrailbase$enumraildirection != BlockRailBase.EnumRailDirection.ASCENDING_WEST)) ? (((Boolean)iblockstate.getValue((IProperty)POWERED)).booleanValue() ? (worldIn.isBlockPowered(p_176567_2_) ? true : func_176566_a(worldIn, p_176567_2_, iblockstate, p_176567_3_, distance + 1)) : false) : false) : false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onNeighborChangedInternal(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
/* 151 */     boolean flag = ((Boolean)state.getValue((IProperty)POWERED)).booleanValue();
/* 152 */     boolean flag1 = !(!worldIn.isBlockPowered(pos) && !func_176566_a(worldIn, pos, state, true, 0) && !func_176566_a(worldIn, pos, state, false, 0));
/*     */     
/* 154 */     if (flag1 != flag) {
/*     */       
/* 156 */       worldIn.setBlockState(pos, state.withProperty((IProperty)POWERED, Boolean.valueOf(flag1)), 3);
/* 157 */       worldIn.notifyNeighborsOfStateChange(pos.down(), this);
/*     */       
/* 159 */       if (((BlockRailBase.EnumRailDirection)state.getValue((IProperty)SHAPE)).isAscending())
/*     */       {
/* 161 */         worldIn.notifyNeighborsOfStateChange(pos.up(), this);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public IProperty<BlockRailBase.EnumRailDirection> getShapeProperty() {
/* 168 */     return (IProperty<BlockRailBase.EnumRailDirection>)SHAPE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 176 */     return getDefaultState().withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.byMetadata(meta & 0x7)).withProperty((IProperty)POWERED, Boolean.valueOf(((meta & 0x8) > 0)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 184 */     int i = 0;
/* 185 */     i |= ((BlockRailBase.EnumRailDirection)state.getValue((IProperty)SHAPE)).getMetadata();
/*     */     
/* 187 */     if (((Boolean)state.getValue((IProperty)POWERED)).booleanValue())
/*     */     {
/* 189 */       i |= 0x8;
/*     */     }
/*     */     
/* 192 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockState createBlockState() {
/* 197 */     return new BlockState(this, new IProperty[] { (IProperty)SHAPE, (IProperty)POWERED });
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\block\BlockRailPowered.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */