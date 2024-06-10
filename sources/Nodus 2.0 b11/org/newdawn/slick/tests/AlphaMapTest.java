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
/* 11:   */ public class AlphaMapTest
/* 12:   */   extends BasicGame
/* 13:   */ {
/* 14:   */   private Image alphaMap;
/* 15:   */   private Image textureMap;
/* 16:   */   
/* 17:   */   public AlphaMapTest()
/* 18:   */   {
/* 19:26 */     super("AlphaMap Test");
/* 20:   */   }
/* 21:   */   
/* 22:   */   public void init(GameContainer container)
/* 23:   */     throws SlickException
/* 24:   */   {
/* 25:33 */     this.alphaMap = new Image("testdata/alphamap.png");
/* 26:34 */     this.textureMap = new Image("testdata/grass.png");
/* 27:35 */     container.getGraphics().setBackground(Color.black);
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
/* 39:52 */     this.textureMap.draw(10.0F, 50.0F);
/* 40:53 */     g.setColor(Color.red);
/* 41:54 */     g.fillRect(290.0F, 40.0F, 200.0F, 200.0F);
/* 42:55 */     g.setColor(Color.white);
/* 43:   */     
/* 44:57 */     g.setDrawMode(Graphics.MODE_ALPHA_MAP);
/* 45:58 */     this.alphaMap.draw(300.0F, 50.0F);
/* 46:59 */     g.setDrawMode(Graphics.MODE_ALPHA_BLEND);
/* 47:60 */     this.textureMap.draw(300.0F, 50.0F);
/* 48:61 */     g.setDrawMode(Graphics.MODE_NORMAL);
/* 49:   */   }
/* 50:   */   
/* 51:   */   public void keyPressed(int key, char c) {}
/* 52:   */   
/* 53:   */   public static void main(String[] argv)
/* 54:   */   {
/* 55:   */     try
/* 56:   */     {
/* 57:77 */       AppGameContainer container = new AppGameContainer(new AlphaMapTest());
/* 58:78 */       container.setDisplayMode(800, 600, false);
/* 59:79 */       container.start();
/* 60:   */     }
/* 61:   */     catch (SlickException e)
/* 62:   */     {
/* 63:81 */       e.printStackTrace();
/* 64:   */     }
/* 65:   */   }
/* 66:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.tests.AlphaMapTest
 * JD-Core Version:    0.7.0.1
 */