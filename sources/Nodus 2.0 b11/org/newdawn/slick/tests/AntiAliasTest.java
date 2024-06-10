/*  1:   */ package org.newdawn.slick.tests;
/*  2:   */ 
/*  3:   */ import org.newdawn.slick.AppGameContainer;
/*  4:   */ import org.newdawn.slick.BasicGame;
/*  5:   */ import org.newdawn.slick.Color;
/*  6:   */ import org.newdawn.slick.GameContainer;
/*  7:   */ import org.newdawn.slick.Graphics;
/*  8:   */ import org.newdawn.slick.SlickException;
/*  9:   */ 
/* 10:   */ public class AntiAliasTest
/* 11:   */   extends BasicGame
/* 12:   */ {
/* 13:   */   public AntiAliasTest()
/* 14:   */   {
/* 15:21 */     super("AntiAlias Test");
/* 16:   */   }
/* 17:   */   
/* 18:   */   public void init(GameContainer container)
/* 19:   */     throws SlickException
/* 20:   */   {
/* 21:28 */     container.getGraphics().setBackground(Color.green);
/* 22:   */   }
/* 23:   */   
/* 24:   */   public void update(GameContainer container, int delta)
/* 25:   */     throws SlickException
/* 26:   */   {}
/* 27:   */   
/* 28:   */   public void render(GameContainer container, Graphics g)
/* 29:   */     throws SlickException
/* 30:   */   {
/* 31:41 */     g.setAntiAlias(true);
/* 32:42 */     g.setColor(Color.red);
/* 33:43 */     g.drawOval(100.0F, 100.0F, 100.0F, 100.0F);
/* 34:44 */     g.fillOval(300.0F, 100.0F, 100.0F, 100.0F);
/* 35:45 */     g.setAntiAlias(false);
/* 36:46 */     g.setColor(Color.red);
/* 37:47 */     g.drawOval(100.0F, 300.0F, 100.0F, 100.0F);
/* 38:48 */     g.fillOval(300.0F, 300.0F, 100.0F, 100.0F);
/* 39:   */   }
/* 40:   */   
/* 41:   */   public static void main(String[] argv)
/* 42:   */   {
/* 43:   */     try
/* 44:   */     {
/* 45:58 */       AppGameContainer container = new AppGameContainer(new AntiAliasTest());
/* 46:59 */       container.setDisplayMode(800, 600, false);
/* 47:60 */       container.start();
/* 48:   */     }
/* 49:   */     catch (SlickException e)
/* 50:   */     {
/* 51:62 */       e.printStackTrace();
/* 52:   */     }
/* 53:   */   }
/* 54:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.tests.AntiAliasTest
 * JD-Core Version:    0.7.0.1
 */