/*    */ package org.newdawn.slick.tests;
/*    */ 
/*    */ import org.newdawn.slick.AppGameContainer;
/*    */ import org.newdawn.slick.BasicGame;
/*    */ import org.newdawn.slick.Color;
/*    */ import org.newdawn.slick.Game;
/*    */ import org.newdawn.slick.GameContainer;
/*    */ import org.newdawn.slick.Graphics;
/*    */ import org.newdawn.slick.SlickException;
/*    */ import org.newdawn.slick.geom.Polygon;
/*    */ import org.newdawn.slick.geom.Shape;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PolygonTest
/*    */   extends BasicGame
/*    */ {
/*    */   private Polygon poly;
/*    */   private boolean in;
/*    */   private float y;
/*    */   
/*    */   public PolygonTest() {
/* 28 */     super("Polygon Test");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void init(GameContainer container) throws SlickException {
/* 35 */     this.poly = new Polygon();
/* 36 */     this.poly.addPoint(300.0F, 100.0F);
/* 37 */     this.poly.addPoint(320.0F, 200.0F);
/* 38 */     this.poly.addPoint(350.0F, 210.0F);
/* 39 */     this.poly.addPoint(280.0F, 250.0F);
/* 40 */     this.poly.addPoint(300.0F, 200.0F);
/* 41 */     this.poly.addPoint(240.0F, 150.0F);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void update(GameContainer container, int delta) throws SlickException {
/* 49 */     this.in = this.poly.contains(container.getInput().getMouseX(), container.getInput().getMouseY());
/*    */     
/* 51 */     this.poly.setCenterY(0.0F);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void render(GameContainer container, Graphics g) throws SlickException {
/* 58 */     if (this.in) {
/* 59 */       g.setColor(Color.red);
/* 60 */       g.fill((Shape)this.poly);
/*    */     } 
/* 62 */     g.setColor(Color.yellow);
/* 63 */     g.fillOval(this.poly.getCenterX() - 3.0F, this.poly.getCenterY() - 3.0F, 6.0F, 6.0F);
/* 64 */     g.setColor(Color.white);
/* 65 */     g.draw((Shape)this.poly);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void main(String[] argv) {
/*    */     try {
/* 75 */       AppGameContainer container = new AppGameContainer((Game)new PolygonTest(), 640, 480, false);
/* 76 */       container.start();
/* 77 */     } catch (Exception e) {
/* 78 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\tests\PolygonTest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */