/*  1:   */ package org.newdawn.slick.opengl;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import java.util.ArrayList;
/*  5:   */ 
/*  6:   */ public class CompositeIOException
/*  7:   */   extends IOException
/*  8:   */ {
/*  9:13 */   private ArrayList exceptions = new ArrayList();
/* 10:   */   
/* 11:   */   public void addException(Exception e)
/* 12:   */   {
/* 13:28 */     this.exceptions.add(e);
/* 14:   */   }
/* 15:   */   
/* 16:   */   public String getMessage()
/* 17:   */   {
/* 18:35 */     String msg = "Composite Exception: \n";
/* 19:36 */     for (int i = 0; i < this.exceptions.size(); i++) {
/* 20:37 */       msg = msg + "\t" + ((IOException)this.exceptions.get(i)).getMessage() + "\n";
/* 21:   */     }
/* 22:40 */     return msg;
/* 23:   */   }
/* 24:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.opengl.CompositeIOException
 * JD-Core Version:    0.7.0.1
 */