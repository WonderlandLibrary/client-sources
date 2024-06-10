/*  1:   */ package org.newdawn.slick.opengl;
/*  2:   */ 
/*  3:   */ import org.newdawn.slick.opengl.renderer.Renderer;
/*  4:   */ import org.newdawn.slick.opengl.renderer.SGL;
/*  5:   */ 
/*  6:   */ public final class GLUtils
/*  7:   */ {
/*  8:   */   public static void checkGLContext()
/*  9:   */   {
/* 10:   */     try
/* 11:   */     {
/* 12:17 */       Renderer.get().glGetError();
/* 13:   */     }
/* 14:   */     catch (NullPointerException e)
/* 15:   */     {
/* 16:19 */       throw new RuntimeException("OpenGL based resources (images, fonts, sprites etc) must be loaded as part of init() or the game loop. They cannot be loaded before initialisation.");
/* 17:   */     }
/* 18:   */   }
/* 19:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.opengl.GLUtils
 * JD-Core Version:    0.7.0.1
 */