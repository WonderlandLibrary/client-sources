/*    */ package org.newdawn.slick.tests.xml;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GameData
/*    */ {
/* 12 */   private ArrayList entities = new ArrayList();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private void add(Entity entity) {
/* 20 */     this.entities.add(entity);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void dump(String prefix) {
/* 29 */     System.out.println(prefix + "GameData");
/* 30 */     for (int i = 0; i < this.entities.size(); i++)
/* 31 */       ((Entity)this.entities.get(i)).dump(prefix + "\t"); 
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\tests\xml\GameData.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */