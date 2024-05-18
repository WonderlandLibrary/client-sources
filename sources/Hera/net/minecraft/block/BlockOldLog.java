/*     */ package net.minecraft.block;
/*     */ 
/*     */ import com.google.common.base.Predicate;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.material.MapColor;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyEnum;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ 
/*     */ public class BlockOldLog
/*     */   extends BlockLog {
/*  16 */   public static final PropertyEnum<BlockPlanks.EnumType> VARIANT = PropertyEnum.create("variant", BlockPlanks.EnumType.class, new Predicate<BlockPlanks.EnumType>()
/*     */       {
/*     */         public boolean apply(BlockPlanks.EnumType p_apply_1_)
/*     */         {
/*  20 */           return (p_apply_1_.getMetadata() < 4);
/*     */         }
/*     */       });
/*     */ 
/*     */   
/*     */   public BlockOldLog() {
/*  26 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)VARIANT, BlockPlanks.EnumType.OAK).withProperty((IProperty)LOG_AXIS, BlockLog.EnumAxis.Y));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MapColor getMapColor(IBlockState state) {
/*  34 */     BlockPlanks.EnumType blockplanks$enumtype = (BlockPlanks.EnumType)state.getValue((IProperty)VARIANT);
/*     */     
/*  36 */     switch ((BlockLog.EnumAxis)state.getValue((IProperty)LOG_AXIS)) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       default:
/*  42 */         switch (blockplanks$enumtype) {
/*     */ 
/*     */           
/*     */           default:
/*  46 */             return BlockPlanks.EnumType.SPRUCE.func_181070_c();
/*     */           
/*     */           case SPRUCE:
/*  49 */             return BlockPlanks.EnumType.DARK_OAK.func_181070_c();
/*     */           
/*     */           case BIRCH:
/*  52 */             return MapColor.quartzColor;
/*     */           case JUNGLE:
/*     */             break;
/*  55 */         }  return BlockPlanks.EnumType.SPRUCE.func_181070_c();
/*     */       case Y:
/*     */         break;
/*     */     } 
/*  59 */     return blockplanks$enumtype.func_181070_c();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list) {
/*  68 */     list.add(new ItemStack(itemIn, 1, BlockPlanks.EnumType.OAK.getMetadata()));
/*  69 */     list.add(new ItemStack(itemIn, 1, BlockPlanks.EnumType.SPRUCE.getMetadata()));
/*  70 */     list.add(new ItemStack(itemIn, 1, BlockPlanks.EnumType.BIRCH.getMetadata()));
/*  71 */     list.add(new ItemStack(itemIn, 1, BlockPlanks.EnumType.JUNGLE.getMetadata()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/*  79 */     IBlockState iblockstate = getDefaultState().withProperty((IProperty)VARIANT, BlockPlanks.EnumType.byMetadata((meta & 0x3) % 4));
/*     */     
/*  81 */     switch (meta & 0xC)
/*     */     
/*     */     { case 0:
/*  84 */         iblockstate = iblockstate.withProperty((IProperty)LOG_AXIS, BlockLog.EnumAxis.Y);
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
/*  99 */         return iblockstate;case 4: iblockstate = iblockstate.withProperty((IProperty)LOG_AXIS, BlockLog.EnumAxis.X); return iblockstate;case 8: iblockstate = iblockstate.withProperty((IProperty)LOG_AXIS, BlockLog.EnumAxis.Z); return iblockstate; }  iblockstate = iblockstate.withProperty((IProperty)LOG_AXIS, BlockLog.EnumAxis.NONE); return iblockstate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 109 */     int i = 0;
/* 110 */     i |= ((BlockPlanks.EnumType)state.getValue((IProperty)VARIANT)).getMetadata();
/*     */     
/* 112 */     switch ((BlockLog.EnumAxis)state.getValue((IProperty)LOG_AXIS)) {
/*     */       
/*     */       case X:
/* 115 */         i |= 0x4;
/*     */         break;
/*     */       
/*     */       case Z:
/* 119 */         i |= 0x8;
/*     */         break;
/*     */       
/*     */       case null:
/* 123 */         i |= 0xC;
/*     */         break;
/*     */     } 
/* 126 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockState createBlockState() {
/* 131 */     return new BlockState(this, new IProperty[] { (IProperty)VARIANT, (IProperty)LOG_AXIS });
/*     */   }
/*     */ 
/*     */   
/*     */   protected ItemStack createStackedBlock(IBlockState state) {
/* 136 */     return new ItemStack(Item.getItemFromBlock(this), 1, ((BlockPlanks.EnumType)state.getValue((IProperty)VARIANT)).getMetadata());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int damageDropped(IBlockState state) {
/* 145 */     return ((BlockPlanks.EnumType)state.getValue((IProperty)VARIANT)).getMetadata();
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\block\BlockOldLog.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */