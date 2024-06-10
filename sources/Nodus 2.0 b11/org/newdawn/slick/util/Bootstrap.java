/*  1:   */ package org.newdawn.slick.util;
/*  2:   */ 
/*  3:   */ import org.newdawn.slick.AppGameContainer;
/*  4:   */ import org.newdawn.slick.Game;
/*  5:   */ 
/*  6:   */ public class Bootstrap
/*  7:   */ {
/*  8:   */   public static void runAsApplication(Game game, int width, int height, boolean fullscreen)
/*  9:   */   {
/* 10:   */     try
/* 11:   */     {
/* 12:23 */       AppGameContainer container = new AppGameContainer(game, width, height, fullscreen);
/* 13:24 */       container.start();
/* 14:   */     }
/* 15:   */     catch (Exception e)
/* 16:   */     {
/* 17:26 */       e.printStackTrace();
/* 18:   */     }
/* 19:   */   }
/* 20:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.util.Bootstrap
 * JD-Core Version:    0.7.0.1
 */