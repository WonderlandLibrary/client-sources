/*    */ package org.newdawn.slick.tests.xml;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Entity
/*    */ {
/*    */   private float x;
/*    */   private float y;
/*    */   private Inventory invent;
/*    */   private Stats stats;
/*    */   
/*    */   private void add(Inventory inventory) {
/* 24 */     this.invent = inventory;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private void add(Stats stats) {
/* 33 */     this.stats = stats;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void dump(String prefix) {
/* 42 */     System.out.println(prefix + "Entity " + this.x + "," + this.y);
/* 43 */     this.invent.dump(prefix + "\t");
/* 44 */     this.stats.dump(prefix + "\t");
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\tests\xml\Entity.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */