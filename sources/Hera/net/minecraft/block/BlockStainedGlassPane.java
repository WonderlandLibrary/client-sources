/*    */ package net.minecraft.block;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.minecraft.block.material.MapColor;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.block.properties.IProperty;
/*    */ import net.minecraft.block.properties.PropertyEnum;
/*    */ import net.minecraft.block.state.BlockState;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.item.EnumDyeColor;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.EnumWorldBlockLayer;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class BlockStainedGlassPane
/*    */   extends BlockPane {
/* 20 */   public static final PropertyEnum<EnumDyeColor> COLOR = PropertyEnum.create("color", EnumDyeColor.class);
/*    */ 
/*    */   
/*    */   public BlockStainedGlassPane() {
/* 24 */     super(Material.glass, false);
/* 25 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)NORTH, Boolean.valueOf(false)).withProperty((IProperty)EAST, Boolean.valueOf(false)).withProperty((IProperty)SOUTH, Boolean.valueOf(false)).withProperty((IProperty)WEST, Boolean.valueOf(false)).withProperty((IProperty)COLOR, (Comparable)EnumDyeColor.WHITE));
/* 26 */     setCreativeTab(CreativeTabs.tabDecorations);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int damageDropped(IBlockState state) {
/* 35 */     return ((EnumDyeColor)state.getValue((IProperty)COLOR)).getMetadata();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list) {
/* 43 */     for (int i = 0; i < (EnumDyeColor.values()).length; i++)
/*    */     {
/* 45 */       list.add(new ItemStack(itemIn, 1, i));
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public MapColor getMapColor(IBlockState state) {
/* 54 */     return ((EnumDyeColor)state.getValue((IProperty)COLOR)).getMapColor();
/*    */   }
/*    */ 
/*    */   
/*    */   public EnumWorldBlockLayer getBlockLayer() {
/* 59 */     return EnumWorldBlockLayer.TRANSLUCENT;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IBlockState getStateFromMeta(int meta) {
/* 67 */     return getDefaultState().withProperty((IProperty)COLOR, (Comparable)EnumDyeColor.byMetadata(meta));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMetaFromState(IBlockState state) {
/* 75 */     return ((EnumDyeColor)state.getValue((IProperty)COLOR)).getMetadata();
/*    */   }
/*    */ 
/*    */   
/*    */   protected BlockState createBlockState() {
/* 80 */     return new BlockState(this, new IProperty[] { (IProperty)NORTH, (IProperty)EAST, (IProperty)WEST, (IProperty)SOUTH, (IProperty)COLOR });
/*    */   }
/*    */ 
/*    */   
/*    */   public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
/* 85 */     if (!worldIn.isRemote)
/*    */     {
/* 87 */       BlockBeacon.updateColorAsync(worldIn, pos);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
/* 93 */     if (!worldIn.isRemote)
/*    */     {
/* 95 */       BlockBeacon.updateColorAsync(worldIn, pos);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\block\BlockStainedGlassPane.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */