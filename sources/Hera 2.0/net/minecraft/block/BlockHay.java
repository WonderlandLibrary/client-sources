/*    */ package net.minecraft.block;
/*    */ 
/*    */ import net.minecraft.block.material.MapColor;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.block.properties.IProperty;
/*    */ import net.minecraft.block.state.BlockState;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class BlockHay
/*    */   extends BlockRotatedPillar
/*    */ {
/*    */   public BlockHay() {
/* 20 */     super(Material.grass, MapColor.yellowColor);
/* 21 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)AXIS, (Comparable)EnumFacing.Axis.Y));
/* 22 */     setCreativeTab(CreativeTabs.tabBlock);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IBlockState getStateFromMeta(int meta) {
/* 30 */     EnumFacing.Axis enumfacing$axis = EnumFacing.Axis.Y;
/* 31 */     int i = meta & 0xC;
/*    */     
/* 33 */     if (i == 4) {
/*    */       
/* 35 */       enumfacing$axis = EnumFacing.Axis.X;
/*    */     }
/* 37 */     else if (i == 8) {
/*    */       
/* 39 */       enumfacing$axis = EnumFacing.Axis.Z;
/*    */     } 
/*    */     
/* 42 */     return getDefaultState().withProperty((IProperty)AXIS, (Comparable)enumfacing$axis);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMetaFromState(IBlockState state) {
/* 50 */     int i = 0;
/* 51 */     EnumFacing.Axis enumfacing$axis = (EnumFacing.Axis)state.getValue((IProperty)AXIS);
/*    */     
/* 53 */     if (enumfacing$axis == EnumFacing.Axis.X) {
/*    */       
/* 55 */       i |= 0x4;
/*    */     }
/* 57 */     else if (enumfacing$axis == EnumFacing.Axis.Z) {
/*    */       
/* 59 */       i |= 0x8;
/*    */     } 
/*    */     
/* 62 */     return i;
/*    */   }
/*    */ 
/*    */   
/*    */   protected BlockState createBlockState() {
/* 67 */     return new BlockState(this, new IProperty[] { (IProperty)AXIS });
/*    */   }
/*    */ 
/*    */   
/*    */   protected ItemStack createStackedBlock(IBlockState state) {
/* 72 */     return new ItemStack(Item.getItemFromBlock(this), 1, 0);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
/* 81 */     return super.onBlockPlaced(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer).withProperty((IProperty)AXIS, (Comparable)facing.getAxis());
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\block\BlockHay.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */