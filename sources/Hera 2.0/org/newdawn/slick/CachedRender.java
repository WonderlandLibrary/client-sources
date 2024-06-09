/*    */ package org.newdawn.slick;
/*    */ 
/*    */ import org.newdawn.slick.opengl.SlickCallable;
/*    */ import org.newdawn.slick.opengl.renderer.Renderer;
/*    */ import org.newdawn.slick.opengl.renderer.SGL;
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
/*    */ public class CachedRender
/*    */ {
/* 20 */   protected static SGL GL = Renderer.get();
/*    */ 
/*    */   
/*    */   private Runnable runnable;
/*    */   
/* 25 */   private int list = -1;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public CachedRender(Runnable runnable) {
/* 34 */     this.runnable = runnable;
/* 35 */     build();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private void build() {
/* 42 */     if (this.list == -1) {
/* 43 */       this.list = GL.glGenLists(1);
/*    */       
/* 45 */       SlickCallable.enterSafeBlock();
/* 46 */       GL.glNewList(this.list, 4864);
/* 47 */       this.runnable.run();
/* 48 */       GL.glEndList();
/* 49 */       SlickCallable.leaveSafeBlock();
/*    */     } else {
/* 51 */       throw new RuntimeException("Attempt to build the display list more than once in CachedRender");
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void render() {
/* 60 */     if (this.list == -1) {
/* 61 */       throw new RuntimeException("Attempt to render cached operations that have been destroyed");
/*    */     }
/*    */     
/* 64 */     SlickCallable.enterSafeBlock();
/* 65 */     GL.glCallList(this.list);
/* 66 */     SlickCallable.leaveSafeBlock();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void destroy() {
/* 73 */     GL.glDeleteLists(this.list, 1);
/* 74 */     this.list = -1;
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\CachedRender.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */