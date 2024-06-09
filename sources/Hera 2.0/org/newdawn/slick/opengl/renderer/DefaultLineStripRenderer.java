/*    */ package org.newdawn.slick.opengl.renderer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DefaultLineStripRenderer
/*    */   implements LineStripRenderer
/*    */ {
/* 11 */   private SGL GL = Renderer.get();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void end() {
/* 17 */     this.GL.glEnd();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setAntiAlias(boolean antialias) {
/* 24 */     if (antialias) {
/* 25 */       this.GL.glEnable(2848);
/*    */     } else {
/* 27 */       this.GL.glDisable(2848);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setWidth(float width) {
/* 35 */     this.GL.glLineWidth(width);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void start() {
/* 42 */     this.GL.glBegin(3);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void vertex(float x, float y) {
/* 49 */     this.GL.glVertex2f(x, y);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void color(float r, float g, float b, float a) {
/* 56 */     this.GL.glColor4f(r, g, b, a);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setLineCaps(boolean caps) {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean applyGLLineFixes() {
/* 69 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\opengl\renderer\DefaultLineStripRenderer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */