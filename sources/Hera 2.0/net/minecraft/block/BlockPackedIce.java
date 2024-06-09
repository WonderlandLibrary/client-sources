/*    */ package net.minecraft.block;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ 
/*    */ public class BlockPackedIce
/*    */   extends Block
/*    */ {
/*    */   public BlockPackedIce() {
/* 11 */     super(Material.packedIce);
/* 12 */     this.slipperiness = 0.98F;
/* 13 */     setCreativeTab(CreativeTabs.tabBlock);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int quantityDropped(Random random) {
/* 21 */     return 0;
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\block\BlockPackedIce.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */