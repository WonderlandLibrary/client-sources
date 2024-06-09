/*    */ package org.newdawn.slick.tests.xml;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemContainer
/*    */   extends Item
/*    */ {
/* 12 */   private ArrayList items = new ArrayList();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private void add(Item item) {
/* 20 */     this.items.add(item);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private void setName(String name) {
/* 31 */     this.name = name;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private void setCondition(int condition) {
/* 42 */     this.condition = condition;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void dump(String prefix) {
/* 51 */     System.out.println(prefix + "Item Container " + this.name + "," + this.condition);
/* 52 */     for (int i = 0; i < this.items.size(); i++)
/* 53 */       ((Item)this.items.get(i)).dump(prefix + "\t"); 
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\tests\xml\ItemContainer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */