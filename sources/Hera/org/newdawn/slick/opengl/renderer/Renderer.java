/*    */ package org.newdawn.slick.opengl.renderer;
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
/*    */ public class Renderer
/*    */ {
/*    */   public static final int IMMEDIATE_RENDERER = 1;
/*    */   public static final int VERTEX_ARRAY_RENDERER = 2;
/*    */   public static final int DEFAULT_LINE_STRIP_RENDERER = 3;
/*    */   public static final int QUAD_BASED_LINE_STRIP_RENDERER = 4;
/* 23 */   private static SGL renderer = new ImmediateModeOGLRenderer();
/*    */   
/* 25 */   private static LineStripRenderer lineStripRenderer = new DefaultLineStripRenderer();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void setRenderer(int type) {
/* 33 */     switch (type) {
/*    */       case 1:
/* 35 */         setRenderer(new ImmediateModeOGLRenderer());
/*    */         return;
/*    */       case 2:
/* 38 */         setRenderer(new VAOGLRenderer());
/*    */         return;
/*    */     } 
/*    */     
/* 42 */     throw new RuntimeException("Unknown renderer type: " + type);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void setLineStripRenderer(int type) {
/* 51 */     switch (type) {
/*    */       case 3:
/* 53 */         setLineStripRenderer(new DefaultLineStripRenderer());
/*    */         return;
/*    */       case 4:
/* 56 */         setLineStripRenderer(new QuadBasedLineStripRenderer());
/*    */         return;
/*    */     } 
/*    */     
/* 60 */     throw new RuntimeException("Unknown line strip renderer type: " + type);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void setLineStripRenderer(LineStripRenderer renderer) {
/* 69 */     lineStripRenderer = renderer;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void setRenderer(SGL r) {
/* 78 */     renderer = r;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static SGL get() {
/* 87 */     return renderer;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static LineStripRenderer getLineStripRenderer() {
/* 96 */     return lineStripRenderer;
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\opengl\renderer\Renderer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */