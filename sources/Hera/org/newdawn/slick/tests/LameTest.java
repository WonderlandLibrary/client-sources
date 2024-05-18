/*    */ package org.newdawn.slick.tests;
/*    */ 
/*    */ import org.newdawn.slick.AppGameContainer;
/*    */ import org.newdawn.slick.BasicGame;
/*    */ import org.newdawn.slick.Color;
/*    */ import org.newdawn.slick.Game;
/*    */ import org.newdawn.slick.GameContainer;
/*    */ import org.newdawn.slick.Graphics;
/*    */ import org.newdawn.slick.Image;
/*    */ import org.newdawn.slick.SlickException;
/*    */ import org.newdawn.slick.geom.Polygon;
/*    */ import org.newdawn.slick.geom.Shape;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LameTest
/*    */   extends BasicGame
/*    */ {
/* 19 */   private Polygon poly = new Polygon();
/*    */ 
/*    */   
/*    */   private Image image;
/*    */ 
/*    */ 
/*    */   
/*    */   public LameTest() {
/* 27 */     super("Lame Test");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void init(GameContainer container) throws SlickException {
/* 34 */     this.poly.addPoint(100.0F, 100.0F);
/* 35 */     this.poly.addPoint(120.0F, 100.0F);
/* 36 */     this.poly.addPoint(120.0F, 120.0F);
/* 37 */     this.poly.addPoint(100.0F, 120.0F);
/*    */     
/* 39 */     this.image = new Image("testdata/rocks.png");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void update(GameContainer container, int delta) throws SlickException {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void render(GameContainer container, Graphics g) throws SlickException {
/* 52 */     g.setColor(Color.white);
/* 53 */     g.texture((Shape)this.poly, this.image);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void main(String[] argv) {
/*    */     try {
/* 63 */       AppGameContainer container = new AppGameContainer((Game)new LameTest());
/* 64 */       container.setDisplayMode(800, 600, false);
/* 65 */       container.start();
/* 66 */     } catch (SlickException e) {
/* 67 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\tests\LameTest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */