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
/*    */ import org.newdawn.slick.geom.Rectangle;
/*    */ import org.newdawn.slick.geom.Shape;
/*    */ import org.newdawn.slick.geom.ShapeRenderer;
/*    */ import org.newdawn.slick.geom.TexCoordGenerator;
/*    */ import org.newdawn.slick.geom.Vector2f;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TexturePaintTest
/*    */   extends BasicGame
/*    */ {
/* 23 */   private Polygon poly = new Polygon();
/*    */ 
/*    */   
/*    */   private Image image;
/*    */   
/* 28 */   private Rectangle texRect = new Rectangle(50.0F, 50.0F, 100.0F, 100.0F);
/*    */ 
/*    */   
/*    */   private TexCoordGenerator texPaint;
/*    */ 
/*    */ 
/*    */   
/*    */   public TexturePaintTest() {
/* 36 */     super("Texture Paint Test");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void init(GameContainer container) throws SlickException {
/* 43 */     this.poly.addPoint(120.0F, 120.0F);
/* 44 */     this.poly.addPoint(420.0F, 100.0F);
/* 45 */     this.poly.addPoint(620.0F, 420.0F);
/* 46 */     this.poly.addPoint(300.0F, 320.0F);
/*    */     
/* 48 */     this.image = new Image("testdata/rocks.png");
/*    */     
/* 50 */     this.texPaint = new TexCoordGenerator() {
/*    */         public Vector2f getCoordFor(float x, float y) {
/* 52 */           float tx = (TexturePaintTest.this.texRect.getX() - x) / TexturePaintTest.this.texRect.getWidth();
/* 53 */           float ty = (TexturePaintTest.this.texRect.getY() - y) / TexturePaintTest.this.texRect.getHeight();
/*    */           
/* 55 */           return new Vector2f(tx, ty);
/*    */         }
/*    */       };
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
/* 70 */     g.setColor(Color.white);
/* 71 */     g.texture((Shape)this.poly, this.image);
/*    */     
/* 73 */     ShapeRenderer.texture((Shape)this.poly, this.image, this.texPaint);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void main(String[] argv) {
/*    */     try {
/* 83 */       AppGameContainer container = new AppGameContainer((Game)new TexturePaintTest());
/* 84 */       container.setDisplayMode(800, 600, false);
/* 85 */       container.start();
/* 86 */     } catch (SlickException e) {
/* 87 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\tests\TexturePaintTest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */