/*    */ package org.newdawn.slick.opengl;
/*    */ 
/*    */ import org.newdawn.slick.opengl.renderer.Renderer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class GLUtils
/*    */ {
/*    */   public static void checkGLContext() {
/*    */     try {
/* 17 */       Renderer.get().glGetError();
/* 18 */     } catch (NullPointerException e) {
/* 19 */       throw new RuntimeException("OpenGL based resources (images, fonts, sprites etc) must be loaded as part of init() or the game loop. They cannot be loaded before initialisation.");
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\opengl\GLUtils.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */