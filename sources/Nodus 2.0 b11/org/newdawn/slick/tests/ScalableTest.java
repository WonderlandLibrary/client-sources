/*  1:   */ package org.newdawn.slick.tests;
/*  2:   */ 
/*  3:   */ import org.newdawn.slick.AppGameContainer;
/*  4:   */ import org.newdawn.slick.BasicGame;
/*  5:   */ import org.newdawn.slick.Color;
/*  6:   */ import org.newdawn.slick.Game;
/*  7:   */ import org.newdawn.slick.GameContainer;
/*  8:   */ import org.newdawn.slick.Graphics;
/*  9:   */ import org.newdawn.slick.Input;
/* 10:   */ import org.newdawn.slick.ScalableGame;
/* 11:   */ import org.newdawn.slick.SlickException;
/* 12:   */ 
/* 13:   */ public class ScalableTest
/* 14:   */   extends BasicGame
/* 15:   */ {
/* 16:   */   public ScalableTest()
/* 17:   */   {
/* 18:22 */     super("Scalable Test For Widescreen");
/* 19:   */   }
/* 20:   */   
/* 21:   */   public void init(GameContainer container)
/* 22:   */     throws SlickException
/* 23:   */   {}
/* 24:   */   
/* 25:   */   public void update(GameContainer container, int delta)
/* 26:   */     throws SlickException
/* 27:   */   {}
/* 28:   */   
/* 29:   */   public void render(GameContainer container, Graphics g)
/* 30:   */     throws SlickException
/* 31:   */   {
/* 32:41 */     g.setColor(new Color(0.4F, 0.6F, 0.8F));
/* 33:42 */     g.fillRect(0.0F, 0.0F, 1024.0F, 568.0F);
/* 34:43 */     g.setColor(Color.white);
/* 35:44 */     g.drawRect(5.0F, 5.0F, 1014.0F, 558.0F);
/* 36:   */     
/* 37:46 */     g.setColor(Color.white);
/* 38:47 */     g.drawString(container.getInput().getMouseX() + "," + container.getInput().getMouseY(), 10.0F, 400.0F);
/* 39:48 */     g.setColor(Color.red);
/* 40:49 */     g.fillOval(container.getInput().getMouseX() - 10, container.getInput().getMouseY() - 10, 20.0F, 20.0F);
/* 41:   */   }
/* 42:   */   
/* 43:   */   public static void main(String[] argv)
/* 44:   */   {
/* 45:   */     try
/* 46:   */     {
/* 47:85 */       ScalableGame game = new ScalableGame(new ScalableTest(), 1024, 568, true)
/* 48:   */       {
/* 49:   */         protected void renderOverlay(GameContainer container, Graphics g)
/* 50:   */         {
/* 51:88 */           g.setColor(Color.white);
/* 52:89 */           g.drawString("Outside The Game", 350.0F, 10.0F);
/* 53:90 */           g.drawString(container.getInput().getMouseX() + "," + container.getInput().getMouseY(), 400.0F, 20.0F);
/* 54:   */         }
/* 55:94 */       };
/* 56:95 */       AppGameContainer container = new AppGameContainer(game);
/* 57:96 */       container.setDisplayMode(800, 600, false);
/* 58:97 */       container.start();
/* 59:   */     }
/* 60:   */     catch (SlickException e)
/* 61:   */     {
/* 62:99 */       e.printStackTrace();
/* 63:   */     }
/* 64:   */   }
/* 65:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.tests.ScalableTest
 * JD-Core Version:    0.7.0.1
 */