/*  1:   */ package org.newdawn.slick.tests;
/*  2:   */ 
/*  3:   */ import org.newdawn.slick.AppGameContainer;
/*  4:   */ import org.newdawn.slick.BasicGame;
/*  5:   */ import org.newdawn.slick.Color;
/*  6:   */ import org.newdawn.slick.GameContainer;
/*  7:   */ import org.newdawn.slick.Graphics;
/*  8:   */ import org.newdawn.slick.Input;
/*  9:   */ import org.newdawn.slick.SlickException;
/* 10:   */ import org.newdawn.slick.geom.Polygon;
/* 11:   */ 
/* 12:   */ public class PolygonTest
/* 13:   */   extends BasicGame
/* 14:   */ {
/* 15:   */   private Polygon poly;
/* 16:   */   private boolean in;
/* 17:   */   private float y;
/* 18:   */   
/* 19:   */   public PolygonTest()
/* 20:   */   {
/* 21:28 */     super("Polygon Test");
/* 22:   */   }
/* 23:   */   
/* 24:   */   public void init(GameContainer container)
/* 25:   */     throws SlickException
/* 26:   */   {
/* 27:35 */     this.poly = new Polygon();
/* 28:36 */     this.poly.addPoint(300.0F, 100.0F);
/* 29:37 */     this.poly.addPoint(320.0F, 200.0F);
/* 30:38 */     this.poly.addPoint(350.0F, 210.0F);
/* 31:39 */     this.poly.addPoint(280.0F, 250.0F);
/* 32:40 */     this.poly.addPoint(300.0F, 200.0F);
/* 33:41 */     this.poly.addPoint(240.0F, 150.0F);
/* 34:   */   }
/* 35:   */   
/* 36:   */   public void update(GameContainer container, int delta)
/* 37:   */     throws SlickException
/* 38:   */   {
/* 39:49 */     this.in = this.poly.contains(container.getInput().getMouseX(), container.getInput().getMouseY());
/* 40:   */     
/* 41:51 */     this.poly.setCenterY(0.0F);
/* 42:   */   }
/* 43:   */   
/* 44:   */   public void render(GameContainer container, Graphics g)
/* 45:   */     throws SlickException
/* 46:   */   {
/* 47:58 */     if (this.in)
/* 48:   */     {
/* 49:59 */       g.setColor(Color.red);
/* 50:60 */       g.fill(this.poly);
/* 51:   */     }
/* 52:62 */     g.setColor(Color.yellow);
/* 53:63 */     g.fillOval(this.poly.getCenterX() - 3.0F, this.poly.getCenterY() - 3.0F, 6.0F, 6.0F);
/* 54:64 */     g.setColor(Color.white);
/* 55:65 */     g.draw(this.poly);
/* 56:   */   }
/* 57:   */   
/* 58:   */   public static void main(String[] argv)
/* 59:   */   {
/* 60:   */     try
/* 61:   */     {
/* 62:75 */       AppGameContainer container = new AppGameContainer(new PolygonTest(), 640, 480, false);
/* 63:76 */       container.start();
/* 64:   */     }
/* 65:   */     catch (Exception e)
/* 66:   */     {
/* 67:78 */       e.printStackTrace();
/* 68:   */     }
/* 69:   */   }
/* 70:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.tests.PolygonTest
 * JD-Core Version:    0.7.0.1
 */