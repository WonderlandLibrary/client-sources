/*    */ package org.newdawn.slick.util;
/*    */ 
/*    */ import org.newdawn.slick.opengl.renderer.Renderer;
/*    */ import org.newdawn.slick.opengl.renderer.SGL;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MaskUtil
/*    */ {
/* 13 */   protected static SGL GL = Renderer.get();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void defineMask() {
/* 20 */     GL.glDepthMask(true);
/* 21 */     GL.glClearDepth(1.0F);
/* 22 */     GL.glClear(256);
/* 23 */     GL.glDepthFunc(519);
/* 24 */     GL.glEnable(2929);
/* 25 */     GL.glDepthMask(true);
/* 26 */     GL.glColorMask(false, false, false, false);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void finishDefineMask() {
/* 33 */     GL.glDepthMask(false);
/* 34 */     GL.glColorMask(true, true, true, true);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void drawOnMask() {
/* 41 */     GL.glDepthFunc(514);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void drawOffMask() {
/* 48 */     GL.glDepthFunc(517);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void resetMask() {
/* 55 */     GL.glDepthMask(true);
/* 56 */     GL.glClearDepth(0.0F);
/* 57 */     GL.glClear(256);
/* 58 */     GL.glDepthMask(false);
/*    */     
/* 60 */     GL.glDisable(2929);
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slic\\util\MaskUtil.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */