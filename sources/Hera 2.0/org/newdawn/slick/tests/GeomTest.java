/*     */ package org.newdawn.slick.tests;
/*     */ 
/*     */ import org.newdawn.slick.AppGameContainer;
/*     */ import org.newdawn.slick.BasicGame;
/*     */ import org.newdawn.slick.Color;
/*     */ import org.newdawn.slick.Game;
/*     */ import org.newdawn.slick.GameContainer;
/*     */ import org.newdawn.slick.Graphics;
/*     */ import org.newdawn.slick.SlickException;
/*     */ import org.newdawn.slick.geom.Circle;
/*     */ import org.newdawn.slick.geom.Ellipse;
/*     */ import org.newdawn.slick.geom.Rectangle;
/*     */ import org.newdawn.slick.geom.RoundedRectangle;
/*     */ import org.newdawn.slick.geom.Shape;
/*     */ import org.newdawn.slick.geom.Transform;
/*     */ import org.newdawn.slick.opengl.renderer.Renderer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GeomTest
/*     */   extends BasicGame
/*     */ {
/*  25 */   private Shape rect = (Shape)new Rectangle(100.0F, 100.0F, 100.0F, 100.0F);
/*     */   
/*  27 */   private Shape circle = (Shape)new Circle(500.0F, 200.0F, 50.0F);
/*     */   
/*  29 */   private Shape rect1 = (new Rectangle(150.0F, 120.0F, 50.0F, 100.0F)).transform(Transform.createTranslateTransform(50.0F, 50.0F));
/*     */   
/*  31 */   private Shape rect2 = (new Rectangle(310.0F, 210.0F, 50.0F, 100.0F)).transform(Transform.createRotateTransform((float)Math.toRadians(45.0D), 335.0F, 260.0F));
/*     */ 
/*     */   
/*  34 */   private Shape circle1 = (Shape)new Circle(150.0F, 90.0F, 30.0F);
/*     */   
/*  36 */   private Shape circle2 = (Shape)new Circle(310.0F, 110.0F, 70.0F);
/*     */   
/*  38 */   private Shape circle3 = (Shape)new Ellipse(510.0F, 150.0F, 70.0F, 70.0F);
/*     */   
/*  40 */   private Shape circle4 = (new Ellipse(510.0F, 350.0F, 30.0F, 30.0F)).transform(Transform.createTranslateTransform(-510.0F, -350.0F)).transform(Transform.createScaleTransform(2.0F, 2.0F)).transform(Transform.createTranslateTransform(510.0F, 350.0F));
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  45 */   private Shape roundRect = (Shape)new RoundedRectangle(50.0F, 175.0F, 100.0F, 100.0F, 20.0F);
/*     */   
/*  47 */   private Shape roundRect2 = (Shape)new RoundedRectangle(50.0F, 280.0F, 50.0F, 50.0F, 20.0F, 20, 5);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GeomTest() {
/*  53 */     super("Geom Test");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void init(GameContainer container) throws SlickException {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void render(GameContainer container, Graphics g) {
/*  66 */     g.setColor(Color.white);
/*  67 */     g.drawString("Red indicates a collision, green indicates no collision", 50.0F, 420.0F);
/*  68 */     g.drawString("White are the targets", 50.0F, 435.0F);
/*     */     
/*  70 */     g.pushTransform();
/*  71 */     g.translate(100.0F, 100.0F);
/*  72 */     g.pushTransform();
/*  73 */     g.translate(-50.0F, -50.0F);
/*  74 */     g.scale(10.0F, 10.0F);
/*  75 */     g.setColor(Color.red);
/*  76 */     g.fillRect(0.0F, 0.0F, 5.0F, 5.0F);
/*  77 */     g.setColor(Color.white);
/*  78 */     g.drawRect(0.0F, 0.0F, 5.0F, 5.0F);
/*  79 */     g.popTransform();
/*  80 */     g.setColor(Color.green);
/*  81 */     g.fillRect(20.0F, 20.0F, 50.0F, 50.0F);
/*  82 */     g.popTransform();
/*     */     
/*  84 */     g.setColor(Color.white);
/*  85 */     g.draw(this.rect);
/*  86 */     g.draw(this.circle);
/*     */     
/*  88 */     g.setColor(this.rect1.intersects(this.rect) ? Color.red : Color.green);
/*  89 */     g.draw(this.rect1);
/*  90 */     g.setColor(this.rect2.intersects(this.rect) ? Color.red : Color.green);
/*  91 */     g.draw(this.rect2);
/*  92 */     g.setColor(this.roundRect.intersects(this.rect) ? Color.red : Color.green);
/*  93 */     g.draw(this.roundRect);
/*  94 */     g.setColor(this.circle1.intersects(this.rect) ? Color.red : Color.green);
/*  95 */     g.draw(this.circle1);
/*  96 */     g.setColor(this.circle2.intersects(this.rect) ? Color.red : Color.green);
/*  97 */     g.draw(this.circle2);
/*  98 */     g.setColor(this.circle3.intersects(this.circle) ? Color.red : Color.green);
/*  99 */     g.fill(this.circle3);
/* 100 */     g.setColor(this.circle4.intersects(this.circle) ? Color.red : Color.green);
/* 101 */     g.draw(this.circle4);
/*     */     
/* 103 */     g.fill(this.roundRect2);
/* 104 */     g.setColor(Color.blue);
/* 105 */     g.draw(this.roundRect2);
/* 106 */     g.setColor(Color.blue);
/* 107 */     g.draw((Shape)new Circle(100.0F, 100.0F, 50.0F));
/* 108 */     g.drawRect(50.0F, 50.0F, 100.0F, 100.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update(GameContainer container, int delta) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void keyPressed(int key, char c) {
/* 122 */     if (key == 1) {
/* 123 */       System.exit(0);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void main(String[] argv) {
/*     */     try {
/* 134 */       Renderer.setRenderer(2);
/*     */       
/* 136 */       AppGameContainer container = new AppGameContainer((Game)new GeomTest());
/* 137 */       container.setDisplayMode(800, 600, false);
/* 138 */       container.start();
/* 139 */     } catch (SlickException e) {
/* 140 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\tests\GeomTest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */