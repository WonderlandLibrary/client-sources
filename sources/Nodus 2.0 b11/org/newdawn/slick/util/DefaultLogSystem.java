/*  1:   */ package org.newdawn.slick.util;
/*  2:   */ 
/*  3:   */ import java.io.PrintStream;
/*  4:   */ import java.util.Date;
/*  5:   */ 
/*  6:   */ public class DefaultLogSystem
/*  7:   */   implements LogSystem
/*  8:   */ {
/*  9:13 */   public static PrintStream out = System.out;
/* 10:   */   
/* 11:   */   public void error(String message, Throwable e)
/* 12:   */   {
/* 13:22 */     error(message);
/* 14:23 */     error(e);
/* 15:   */   }
/* 16:   */   
/* 17:   */   public void error(Throwable e)
/* 18:   */   {
/* 19:32 */     out.println(new Date() + " ERROR:" + e.getMessage());
/* 20:33 */     e.printStackTrace(out);
/* 21:   */   }
/* 22:   */   
/* 23:   */   public void error(String message)
/* 24:   */   {
/* 25:42 */     out.println(new Date() + " ERROR:" + message);
/* 26:   */   }
/* 27:   */   
/* 28:   */   public void warn(String message)
/* 29:   */   {
/* 30:51 */     out.println(new Date() + " WARN:" + message);
/* 31:   */   }
/* 32:   */   
/* 33:   */   public void info(String message)
/* 34:   */   {
/* 35:60 */     out.println(new Date() + " INFO:" + message);
/* 36:   */   }
/* 37:   */   
/* 38:   */   public void debug(String message)
/* 39:   */   {
/* 40:69 */     out.println(new Date() + " DEBUG:" + message);
/* 41:   */   }
/* 42:   */   
/* 43:   */   public void warn(String message, Throwable e)
/* 44:   */   {
/* 45:79 */     warn(message);
/* 46:80 */     e.printStackTrace(out);
/* 47:   */   }
/* 48:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.util.DefaultLogSystem
 * JD-Core Version:    0.7.0.1
 */