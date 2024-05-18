/*    */ package net.minecraft.item;
/*    */ 
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.BlockLeaves;
/*    */ 
/*    */ public class ItemLeaves
/*    */   extends ItemBlock {
/*    */   private final BlockLeaves leaves;
/*    */   
/*    */   public ItemLeaves(BlockLeaves block) {
/* 11 */     super((Block)block);
/* 12 */     this.leaves = block;
/* 13 */     setMaxDamage(0);
/* 14 */     setHasSubtypes(true);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMetadata(int damage) {
/* 23 */     return damage | 0x4;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getColorFromItemStack(ItemStack stack, int renderPass) {
/* 28 */     return this.leaves.getRenderColor(this.leaves.getStateFromMeta(stack.getMetadata()));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getUnlocalizedName(ItemStack stack) {
/* 37 */     return String.valueOf(getUnlocalizedName()) + "." + this.leaves.getWoodType(stack.getMetadata()).getUnlocalizedName();
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\item\ItemLeaves.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */