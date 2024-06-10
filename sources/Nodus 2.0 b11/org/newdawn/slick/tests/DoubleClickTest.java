/*  1:   */ package org.newdawn.slick.tests;
/*  2:   */ 
/*  3:   */ import org.newdawn.slick.AppGameContainer;
/*  4:   */ import org.newdawn.slick.BasicGame;
/*  5:   */ import org.newdawn.slick.GameContainer;
/*  6:   */ import org.newdawn.slick.Graphics;
/*  7:   */ import org.newdawn.slick.SlickException;
/*  8:   */ 
/*  9:   */ public class DoubleClickTest
/* 10:   */   extends BasicGame
/* 11:   */ {
/* 12:   */   public DoubleClickTest()
/* 13:   */   {
/* 14:20 */     super("Double Click Test");
/* 15:   */   }
/* 16:   */   
/* 17:24 */   private String message = "Click or Double Click";
/* 18:   */   
/* 19:   */   public void init(GameContainer container)
/* 20:   */     throws SlickException
/* 21:   */   {}
/* 22:   */   
/* 23:   */   public void update(GameContainer container, int delta)
/* 24:   */     throws SlickException
/* 25:   */   {}
/* 26:   */   
/* 27:   */   public void render(GameContainer container, Graphics g)
/* 28:   */     throws SlickException
/* 29:   */   {
/* 30:42 */     g.drawString(this.message, 100.0F, 100.0F);
/* 31:   */   }
/* 32:   */   
/* 33:   */   public static void main(String[] argv)
/* 34:   */   {
/* 35:   */     try
/* 36:   */     {
/* 37:52 */       AppGameContainer container = new AppGameContainer(new DoubleClickTest());
/* 38:53 */       container.setDisplayMode(800, 600, false);
/* 39:54 */       container.start();
/* 40:   */     }
/* 41:   */     catch (SlickException e)
/* 42:   */     {
/* 43:56 */       e.printStackTrace();
/* 44:   */     }
/* 45:   */   }
/* 46:   */   
/* 47:   */   public void mouseClicked(int button, int x, int y, int clickCount)
/* 48:   */   {
/* 49:64 */     if (clickCount == 1) {
/* 50:65 */       this.message = ("Single Click: " + button + " " + x + "," + y);
/* 51:   */     }
/* 52:67 */     if (clickCount == 2) {
/* 53:68 */       this.message = ("Double Click: " + button + " " + x + "," + y);
/* 54:   */     }
/* 55:   */   }
/* 56:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.tests.DoubleClickTest
 * JD-Core Version:    0.7.0.1
 */