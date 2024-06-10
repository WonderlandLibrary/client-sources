/*  1:   */ package org.newdawn.slick.opengl.renderer;
/*  2:   */ 
/*  3:   */ public class DefaultLineStripRenderer
/*  4:   */   implements LineStripRenderer
/*  5:   */ {
/*  6:11 */   private SGL GL = Renderer.get();
/*  7:   */   
/*  8:   */   public void end()
/*  9:   */   {
/* 10:17 */     this.GL.glEnd();
/* 11:   */   }
/* 12:   */   
/* 13:   */   public void setAntiAlias(boolean antialias)
/* 14:   */   {
/* 15:24 */     if (antialias) {
/* 16:25 */       this.GL.glEnable(2848);
/* 17:   */     } else {
/* 18:27 */       this.GL.glDisable(2848);
/* 19:   */     }
/* 20:   */   }
/* 21:   */   
/* 22:   */   public void setWidth(float width)
/* 23:   */   {
/* 24:35 */     this.GL.glLineWidth(width);
/* 25:   */   }
/* 26:   */   
/* 27:   */   public void start()
/* 28:   */   {
/* 29:42 */     this.GL.glBegin(3);
/* 30:   */   }
/* 31:   */   
/* 32:   */   public void vertex(float x, float y)
/* 33:   */   {
/* 34:49 */     this.GL.glVertex2f(x, y);
/* 35:   */   }
/* 36:   */   
/* 37:   */   public void color(float r, float g, float b, float a)
/* 38:   */   {
/* 39:56 */     this.GL.glColor4f(r, g, b, a);
/* 40:   */   }
/* 41:   */   
/* 42:   */   public void setLineCaps(boolean caps) {}
/* 43:   */   
/* 44:   */   public boolean applyGLLineFixes()
/* 45:   */   {
/* 46:69 */     return true;
/* 47:   */   }
/* 48:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.opengl.renderer.DefaultLineStripRenderer
 * JD-Core Version:    0.7.0.1
 */