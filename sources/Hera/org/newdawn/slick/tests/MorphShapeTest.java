/*    */ package org.newdawn.slick.tests;
/*    */ 
/*    */ import org.newdawn.slick.AppGameContainer;
/*    */ import org.newdawn.slick.BasicGame;
/*    */ import org.newdawn.slick.Color;
/*    */ import org.newdawn.slick.Game;
/*    */ import org.newdawn.slick.GameContainer;
/*    */ import org.newdawn.slick.Graphics;
/*    */ import org.newdawn.slick.SlickException;
/*    */ import org.newdawn.slick.geom.MorphShape;
/*    */ import org.newdawn.slick.geom.Rectangle;
/*    */ import org.newdawn.slick.geom.Shape;
/*    */ import org.newdawn.slick.geom.Transform;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MorphShapeTest
/*    */   extends BasicGame
/*    */ {
/*    */   private Shape a;
/*    */   private Shape b;
/*    */   private Shape c;
/*    */   private MorphShape morph;
/*    */   private float time;
/*    */   
/*    */   public MorphShapeTest() {
/* 35 */     super("MorphShapeTest");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void init(GameContainer container) throws SlickException {
/* 42 */     this.a = (Shape)new Rectangle(100.0F, 100.0F, 50.0F, 200.0F);
/* 43 */     this.a = this.a.transform(Transform.createRotateTransform(0.1F, 100.0F, 100.0F));
/* 44 */     this.b = (Shape)new Rectangle(200.0F, 100.0F, 50.0F, 200.0F);
/* 45 */     this.b = this.b.transform(Transform.createRotateTransform(-0.6F, 100.0F, 100.0F));
/* 46 */     this.c = (Shape)new Rectangle(300.0F, 100.0F, 50.0F, 200.0F);
/* 47 */     this.c = this.c.transform(Transform.createRotateTransform(-0.2F, 100.0F, 100.0F));
/*    */     
/* 49 */     this.morph = new MorphShape(this.a);
/* 50 */     this.morph.addShape(this.b);
/* 51 */     this.morph.addShape(this.c);
/*    */     
/* 53 */     container.setVSync(true);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void update(GameContainer container, int delta) throws SlickException {
/* 61 */     this.time += delta * 0.001F;
/* 62 */     this.morph.setMorphTime(this.time);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void render(GameContainer container, Graphics g) throws SlickException {
/* 70 */     g.setColor(Color.green);
/* 71 */     g.draw(this.a);
/* 72 */     g.setColor(Color.red);
/* 73 */     g.draw(this.b);
/* 74 */     g.setColor(Color.blue);
/* 75 */     g.draw(this.c);
/* 76 */     g.setColor(Color.white);
/* 77 */     g.draw((Shape)this.morph);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void main(String[] argv) {
/*    */     try {
/* 88 */       AppGameContainer container = new AppGameContainer((Game)new MorphShapeTest());
/*    */       
/* 90 */       container.setDisplayMode(800, 600, false);
/* 91 */       container.start();
/* 92 */     } catch (SlickException e) {
/* 93 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\tests\MorphShapeTest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */