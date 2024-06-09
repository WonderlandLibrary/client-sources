/*    */ package org.newdawn.slick.util.pathfinding.navmesh;
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
/*    */ 
/*    */ public class Link
/*    */ {
/*    */   private float px;
/*    */   private float py;
/*    */   private Space target;
/*    */   
/*    */   public Link(float px, float py, Space target) {
/* 24 */     this.px = px;
/* 25 */     this.py = py;
/* 26 */     this.target = target;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public float distance2(float tx, float ty) {
/* 37 */     float dx = tx - this.px;
/* 38 */     float dy = ty - this.py;
/*    */     
/* 40 */     return dx * dx + dy * dy;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public float getX() {
/* 49 */     return this.px;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public float getY() {
/* 58 */     return this.py;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Space getTarget() {
/* 67 */     return this.target;
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slic\\util\pathfinding\navmesh\Link.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */