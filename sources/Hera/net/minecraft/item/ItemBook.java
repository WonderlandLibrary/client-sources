/*    */ package net.minecraft.item;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemBook
/*    */   extends Item
/*    */ {
/*    */   public boolean isItemTool(ItemStack stack) {
/* 10 */     return (stack.stackSize == 1);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getItemEnchantability() {
/* 18 */     return 1;
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\item\ItemBook.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */