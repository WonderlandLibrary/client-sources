/*    */ package net.minecraft.block;
/*    */ 
/*    */ import net.minecraft.block.material.MapColor;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ 
/*    */ public class BlockHardenedClay
/*    */   extends Block
/*    */ {
/*    */   public BlockHardenedClay() {
/* 12 */     super(Material.rock);
/* 13 */     setCreativeTab(CreativeTabs.tabBlock);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public MapColor getMapColor(IBlockState state) {
/* 21 */     return MapColor.adobeColor;
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\block\BlockHardenedClay.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */