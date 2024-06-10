/*  1:   */ package org.newdawn.slick.tests;
/*  2:   */ 
/*  3:   */ import org.newdawn.slick.AppGameContainer;
/*  4:   */ import org.newdawn.slick.BasicGame;
/*  5:   */ import org.newdawn.slick.Color;
/*  6:   */ import org.newdawn.slick.GameContainer;
/*  7:   */ import org.newdawn.slick.Graphics;
/*  8:   */ import org.newdawn.slick.Image;
/*  9:   */ import org.newdawn.slick.SlickException;
/* 10:   */ import org.newdawn.slick.geom.Polygon;
/* 11:   */ 
/* 12:   */ public class LameTest
/* 13:   */   extends BasicGame
/* 14:   */ {
/* 15:19 */   private Polygon poly = new Polygon();
/* 16:   */   private Image image;
/* 17:   */   
/* 18:   */   public LameTest()
/* 19:   */   {
/* 20:27 */     super("Lame Test");
/* 21:   */   }
/* 22:   */   
/* 23:   */   public void init(GameContainer container)
/* 24:   */     throws SlickException
/* 25:   */   {
/* 26:34 */     this.poly.addPoint(100.0F, 100.0F);
/* 27:35 */     this.poly.addPoint(120.0F, 100.0F);
/* 28:36 */     this.poly.addPoint(120.0F, 120.0F);
/* 29:37 */     this.poly.addPoint(100.0F, 120.0F);
/* 30:   */     
/* 31:39 */     this.image = new Image("testdata/rocks.png");
/* 32:   */   }
/* 33:   */   
/* 34:   */   public void update(GameContainer container, int delta)
/* 35:   */     throws SlickException
/* 36:   */   {}
/* 37:   */   
/* 38:   */   public void render(GameContainer container, Graphics g)
/* 39:   */     throws SlickException
/* 40:   */   {
/* 41:52 */     g.setColor(Color.white);
/* 42:53 */     g.texture(this.poly, this.image);
/* 43:   */   }
/* 44:   */   
/* 45:   */   public static void main(String[] argv)
/* 46:   */   {
/* 47:   */     try
/* 48:   */     {
/* 49:63 */       AppGameContainer container = new AppGameContainer(new LameTest());
/* 50:64 */       container.setDisplayMode(800, 600, false);
/* 51:65 */       container.start();
/* 52:   */     }
/* 53:   */     catch (SlickException e)
/* 54:   */     {
/* 55:67 */       e.printStackTrace();
/* 56:   */     }
/* 57:   */   }
/* 58:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.tests.LameTest
 * JD-Core Version:    0.7.0.1
 */