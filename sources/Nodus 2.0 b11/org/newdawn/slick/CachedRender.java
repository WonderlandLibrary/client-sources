/*  1:   */ package org.newdawn.slick;
/*  2:   */ 
/*  3:   */ import org.newdawn.slick.opengl.SlickCallable;
/*  4:   */ import org.newdawn.slick.opengl.renderer.Renderer;
/*  5:   */ import org.newdawn.slick.opengl.renderer.SGL;
/*  6:   */ 
/*  7:   */ public class CachedRender
/*  8:   */ {
/*  9:20 */   protected static SGL GL = ;
/* 10:   */   private Runnable runnable;
/* 11:25 */   private int list = -1;
/* 12:   */   
/* 13:   */   public CachedRender(Runnable runnable)
/* 14:   */   {
/* 15:34 */     this.runnable = runnable;
/* 16:35 */     build();
/* 17:   */   }
/* 18:   */   
/* 19:   */   private void build()
/* 20:   */   {
/* 21:42 */     if (this.list == -1)
/* 22:   */     {
/* 23:43 */       this.list = GL.glGenLists(1);
/* 24:   */       
/* 25:45 */       SlickCallable.enterSafeBlock();
/* 26:46 */       GL.glNewList(this.list, 4864);
/* 27:47 */       this.runnable.run();
/* 28:48 */       GL.glEndList();
/* 29:49 */       SlickCallable.leaveSafeBlock();
/* 30:   */     }
/* 31:   */     else
/* 32:   */     {
/* 33:51 */       throw new RuntimeException("Attempt to build the display list more than once in CachedRender");
/* 34:   */     }
/* 35:   */   }
/* 36:   */   
/* 37:   */   public void render()
/* 38:   */   {
/* 39:60 */     if (this.list == -1) {
/* 40:61 */       throw new RuntimeException("Attempt to render cached operations that have been destroyed");
/* 41:   */     }
/* 42:64 */     SlickCallable.enterSafeBlock();
/* 43:65 */     GL.glCallList(this.list);
/* 44:66 */     SlickCallable.leaveSafeBlock();
/* 45:   */   }
/* 46:   */   
/* 47:   */   public void destroy()
/* 48:   */   {
/* 49:73 */     GL.glDeleteLists(this.list, 1);
/* 50:74 */     this.list = -1;
/* 51:   */   }
/* 52:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.CachedRender
 * JD-Core Version:    0.7.0.1
 */