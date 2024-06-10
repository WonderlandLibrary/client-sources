/*  1:   */ package org.newdawn.slick.tests;
/*  2:   */ 
/*  3:   */ import org.newdawn.slick.AppGameContainer;
/*  4:   */ import org.newdawn.slick.BasicGame;
/*  5:   */ import org.newdawn.slick.GameContainer;
/*  6:   */ import org.newdawn.slick.Graphics;
/*  7:   */ import org.newdawn.slick.Image;
/*  8:   */ import org.newdawn.slick.SlickException;
/*  9:   */ 
/* 10:   */ public class ImageMemTest
/* 11:   */   extends BasicGame
/* 12:   */ {
/* 13:   */   public ImageMemTest()
/* 14:   */   {
/* 15:21 */     super("Image Memory Test");
/* 16:   */   }
/* 17:   */   
/* 18:   */   public void init(GameContainer container)
/* 19:   */     throws SlickException
/* 20:   */   {
/* 21:   */     try
/* 22:   */     {
/* 23:29 */       Image img = new Image(2400, 2400);
/* 24:30 */       img.getGraphics();
/* 25:31 */       img.destroy();
/* 26:32 */       img = new Image(2400, 2400);
/* 27:33 */       img.getGraphics();
/* 28:   */     }
/* 29:   */     catch (Exception ex)
/* 30:   */     {
/* 31:35 */       ex.printStackTrace();
/* 32:   */     }
/* 33:   */   }
/* 34:   */   
/* 35:   */   public void render(GameContainer container, Graphics g) {}
/* 36:   */   
/* 37:   */   public void update(GameContainer container, int delta) {}
/* 38:   */   
/* 39:   */   public static void main(String[] argv)
/* 40:   */   {
/* 41:   */     try
/* 42:   */     {
/* 43:58 */       AppGameContainer container = new AppGameContainer(new ImageMemTest());
/* 44:59 */       container.setDisplayMode(800, 600, false);
/* 45:60 */       container.start();
/* 46:   */     }
/* 47:   */     catch (SlickException e)
/* 48:   */     {
/* 49:62 */       e.printStackTrace();
/* 50:   */     }
/* 51:   */   }
/* 52:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.tests.ImageMemTest
 * JD-Core Version:    0.7.0.1
 */