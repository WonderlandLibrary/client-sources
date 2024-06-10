/*  1:   */ package org.newdawn.slick.opengl.renderer;
/*  2:   */ 
/*  3:   */ public class Renderer
/*  4:   */ {
/*  5:   */   public static final int IMMEDIATE_RENDERER = 1;
/*  6:   */   public static final int VERTEX_ARRAY_RENDERER = 2;
/*  7:   */   public static final int DEFAULT_LINE_STRIP_RENDERER = 3;
/*  8:   */   public static final int QUAD_BASED_LINE_STRIP_RENDERER = 4;
/*  9:23 */   private static SGL renderer = new ImmediateModeOGLRenderer();
/* 10:25 */   private static LineStripRenderer lineStripRenderer = new DefaultLineStripRenderer();
/* 11:   */   
/* 12:   */   public static void setRenderer(int type)
/* 13:   */   {
/* 14:33 */     switch (type)
/* 15:   */     {
/* 16:   */     case 1: 
/* 17:35 */       setRenderer(new ImmediateModeOGLRenderer());
/* 18:36 */       return;
/* 19:   */     case 2: 
/* 20:38 */       setRenderer(new VAOGLRenderer());
/* 21:39 */       return;
/* 22:   */     }
/* 23:42 */     throw new RuntimeException("Unknown renderer type: " + type);
/* 24:   */   }
/* 25:   */   
/* 26:   */   public static void setLineStripRenderer(int type)
/* 27:   */   {
/* 28:51 */     switch (type)
/* 29:   */     {
/* 30:   */     case 3: 
/* 31:53 */       setLineStripRenderer(new DefaultLineStripRenderer());
/* 32:54 */       return;
/* 33:   */     case 4: 
/* 34:56 */       setLineStripRenderer(new QuadBasedLineStripRenderer());
/* 35:57 */       return;
/* 36:   */     }
/* 37:60 */     throw new RuntimeException("Unknown line strip renderer type: " + type);
/* 38:   */   }
/* 39:   */   
/* 40:   */   public static void setLineStripRenderer(LineStripRenderer renderer)
/* 41:   */   {
/* 42:69 */     lineStripRenderer = renderer;
/* 43:   */   }
/* 44:   */   
/* 45:   */   public static void setRenderer(SGL r)
/* 46:   */   {
/* 47:78 */     renderer = r;
/* 48:   */   }
/* 49:   */   
/* 50:   */   public static SGL get()
/* 51:   */   {
/* 52:87 */     return renderer;
/* 53:   */   }
/* 54:   */   
/* 55:   */   public static LineStripRenderer getLineStripRenderer()
/* 56:   */   {
/* 57:96 */     return lineStripRenderer;
/* 58:   */   }
/* 59:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.opengl.renderer.Renderer
 * JD-Core Version:    0.7.0.1
 */