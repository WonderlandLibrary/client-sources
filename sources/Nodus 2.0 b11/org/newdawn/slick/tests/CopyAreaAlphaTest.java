/*  1:   */ package org.newdawn.slick.tests;
/*  2:   */ 
/*  3:   */ import org.newdawn.slick.AppGameContainer;
/*  4:   */ import org.newdawn.slick.BasicGame;
/*  5:   */ import org.newdawn.slick.Color;
/*  6:   */ import org.newdawn.slick.GameContainer;
/*  7:   */ import org.newdawn.slick.Graphics;
/*  8:   */ import org.newdawn.slick.Image;
/*  9:   */ import org.newdawn.slick.SlickException;
/* 10:   */ 
/* 11:   */ public class CopyAreaAlphaTest
/* 12:   */   extends BasicGame
/* 13:   */ {
/* 14:   */   private Image textureMap;
/* 15:   */   private Image copy;
/* 16:   */   
/* 17:   */   public CopyAreaAlphaTest()
/* 18:   */   {
/* 19:26 */     super("CopyArea Alpha Test");
/* 20:   */   }
/* 21:   */   
/* 22:   */   public void init(GameContainer container)
/* 23:   */     throws SlickException
/* 24:   */   {
/* 25:33 */     this.textureMap = new Image("testdata/grass.png");
/* 26:34 */     container.getGraphics().setBackground(Color.black);
/* 27:35 */     this.copy = new Image(100, 100);
/* 28:   */   }
/* 29:   */   
/* 30:   */   public void update(GameContainer container, int delta)
/* 31:   */     throws SlickException
/* 32:   */   {}
/* 33:   */   
/* 34:   */   public void render(GameContainer container, Graphics g)
/* 35:   */     throws SlickException
/* 36:   */   {
/* 37:50 */     g.clearAlphaMap();
/* 38:51 */     g.setDrawMode(Graphics.MODE_NORMAL);
/* 39:52 */     g.setColor(Color.white);
/* 40:53 */     g.fillOval(100.0F, 100.0F, 150.0F, 150.0F);
/* 41:54 */     this.textureMap.draw(10.0F, 50.0F);
/* 42:   */     
/* 43:56 */     g.copyArea(this.copy, 100, 100);
/* 44:57 */     g.setColor(Color.red);
/* 45:58 */     g.fillRect(300.0F, 100.0F, 200.0F, 200.0F);
/* 46:59 */     this.copy.draw(350.0F, 150.0F);
/* 47:   */   }
/* 48:   */   
/* 49:   */   public void keyPressed(int key, char c) {}
/* 50:   */   
/* 51:   */   public static void main(String[] argv)
/* 52:   */   {
/* 53:   */     try
/* 54:   */     {
/* 55:75 */       AppGameContainer container = new AppGameContainer(new CopyAreaAlphaTest());
/* 56:76 */       container.setDisplayMode(800, 600, false);
/* 57:77 */       container.start();
/* 58:   */     }
/* 59:   */     catch (SlickException e)
/* 60:   */     {
/* 61:79 */       e.printStackTrace();
/* 62:   */     }
/* 63:   */   }
/* 64:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.tests.CopyAreaAlphaTest
 * JD-Core Version:    0.7.0.1
 */