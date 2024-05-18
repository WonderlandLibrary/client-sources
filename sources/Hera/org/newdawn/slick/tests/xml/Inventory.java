/*    */ package org.newdawn.slick.tests.xml;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Inventory
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
/*    */   public void dump(String prefix) {
/* 29 */     System.out.println(prefix + "Inventory");
/* 30 */     for (int i = 0; i < this.items.size(); i++)
/* 31 */       ((Item)this.items.get(i)).dump(prefix + "\t"); 
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\tests\xml\Inventory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */