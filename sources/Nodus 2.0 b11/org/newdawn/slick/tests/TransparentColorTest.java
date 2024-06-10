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
/* 11:   */ public class TransparentColorTest
/* 12:   */   extends BasicGame
/* 13:   */ {
/* 14:   */   private Image image;
/* 15:   */   private Image timage;
/* 16:   */   
/* 17:   */   public TransparentColorTest()
/* 18:   */   {
/* 19:26 */     super("Transparent Color Test");
/* 20:   */   }
/* 21:   */   
/* 22:   */   public void init(GameContainer container)
/* 23:   */     throws SlickException
/* 24:   */   {
/* 25:33 */     this.image = new Image("testdata/transtest.png");
/* 26:34 */     this.timage = new Image("testdata/transtest.png", new Color(94, 66, 41, 255));
/* 27:   */   }
/* 28:   */   
/* 29:   */   public void render(GameContainer container, Graphics g)
/* 30:   */   {
/* 31:41 */     g.setBackground(Color.red);
/* 32:42 */     this.image.draw(10.0F, 10.0F);
/* 33:43 */     this.timage.draw(10.0F, 310.0F);
/* 34:   */   }
/* 35:   */   
/* 36:   */   public void update(GameContainer container, int delta) {}
/* 37:   */   
/* 38:   */   public static void main(String[] argv)
/* 39:   */   {
/* 40:   */     try
/* 41:   */     {
/* 42:59 */       AppGameContainer container = new AppGameContainer(new TransparentColorTest());
/* 43:60 */       container.setDisplayMode(800, 600, false);
/* 44:61 */       container.start();
/* 45:   */     }
/* 46:   */     catch (SlickException e)
/* 47:   */     {
/* 48:63 */       e.printStackTrace();
/* 49:   */     }
/* 50:   */   }
/* 51:   */   
/* 52:   */   public void keyPressed(int key, char c) {}
/* 53:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.tests.TransparentColorTest
 * JD-Core Version:    0.7.0.1
 */