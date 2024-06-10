/*  1:   */ package org.newdawn.slick.util;
/*  2:   */ 
/*  3:   */ import org.newdawn.slick.opengl.renderer.Renderer;
/*  4:   */ import org.newdawn.slick.opengl.renderer.SGL;
/*  5:   */ 
/*  6:   */ public class MaskUtil
/*  7:   */ {
/*  8:13 */   protected static SGL GL = ;
/*  9:   */   
/* 10:   */   public static void defineMask()
/* 11:   */   {
/* 12:20 */     GL.glDepthMask(true);
/* 13:21 */     GL.glClearDepth(1.0F);
/* 14:22 */     GL.glClear(256);
/* 15:23 */     GL.glDepthFunc(519);
/* 16:24 */     GL.glEnable(2929);
/* 17:25 */     GL.glDepthMask(true);
/* 18:26 */     GL.glColorMask(false, false, false, false);
/* 19:   */   }
/* 20:   */   
/* 21:   */   public static void finishDefineMask()
/* 22:   */   {
/* 23:33 */     GL.glDepthMask(false);
/* 24:34 */     GL.glColorMask(true, true, true, true);
/* 25:   */   }
/* 26:   */   
/* 27:   */   public static void drawOnMask()
/* 28:   */   {
/* 29:41 */     GL.glDepthFunc(514);
/* 30:   */   }
/* 31:   */   
/* 32:   */   public static void drawOffMask()
/* 33:   */   {
/* 34:48 */     GL.glDepthFunc(517);
/* 35:   */   }
/* 36:   */   
/* 37:   */   public static void resetMask()
/* 38:   */   {
/* 39:55 */     GL.glDepthMask(true);
/* 40:56 */     GL.glClearDepth(0.0F);
/* 41:57 */     GL.glClear(256);
/* 42:58 */     GL.glDepthMask(false);
/* 43:   */     
/* 44:60 */     GL.glDisable(2929);
/* 45:   */   }
/* 46:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.util.MaskUtil
 * JD-Core Version:    0.7.0.1
 */