/*    */ package org.newdawn.slick.tests;
/*    */ 
/*    */ import org.newdawn.slick.AppGameContainer;
/*    */ import org.newdawn.slick.BasicGame;
/*    */ import org.newdawn.slick.Color;
/*    */ import org.newdawn.slick.Game;
/*    */ import org.newdawn.slick.GameContainer;
/*    */ import org.newdawn.slick.Graphics;
/*    */ import org.newdawn.slick.SlickException;
/*    */ import org.newdawn.slick.geom.Path;
/*    */ import org.newdawn.slick.geom.Polygon;
/*    */ import org.newdawn.slick.geom.Shape;
/*    */ import org.newdawn.slick.opengl.renderer.Renderer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LineRenderTest
/*    */   extends BasicGame
/*    */ {
/* 21 */   private Polygon polygon = new Polygon();
/*    */   
/* 23 */   private Path path = new Path(100.0F, 100.0F);
/*    */   
/* 25 */   private float width = 10.0F;
/*    */ 
/*    */   
/*    */   private boolean antialias = true;
/*    */ 
/*    */ 
/*    */   
/*    */   public LineRenderTest() {
/* 33 */     super("LineRenderTest");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void init(GameContainer container) throws SlickException {
/* 40 */     this.polygon.addPoint(100.0F, 100.0F);
/* 41 */     this.polygon.addPoint(200.0F, 80.0F);
/* 42 */     this.polygon.addPoint(320.0F, 150.0F);
/* 43 */     this.polygon.addPoint(230.0F, 210.0F);
/* 44 */     this.polygon.addPoint(170.0F, 260.0F);
/*    */     
/* 46 */     this.path.curveTo(200.0F, 200.0F, 200.0F, 100.0F, 100.0F, 200.0F);
/* 47 */     this.path.curveTo(400.0F, 100.0F, 400.0F, 200.0F, 200.0F, 100.0F);
/* 48 */     this.path.curveTo(500.0F, 500.0F, 400.0F, 200.0F, 200.0F, 100.0F);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void update(GameContainer container, int delta) throws SlickException {
/* 55 */     if (container.getInput().isKeyPressed(57)) {
/* 56 */       this.antialias = !this.antialias;
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void render(GameContainer container, Graphics g) throws SlickException {
/* 64 */     g.setAntiAlias(this.antialias);
/* 65 */     g.setLineWidth(50.0F);
/* 66 */     g.setColor(Color.red);
/* 67 */     g.draw((Shape)this.path);
/*    */   }
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
/*    */   public static void main(String[] argv) {
/*    */     try {
/* 92 */       Renderer.setLineStripRenderer(4);
/* 93 */       Renderer.getLineStripRenderer().setLineCaps(true);
/*    */       
/* 95 */       AppGameContainer container = new AppGameContainer((Game)new LineRenderTest());
/* 96 */       container.setDisplayMode(800, 600, false);
/* 97 */       container.start();
/* 98 */     } catch (SlickException e) {
/* 99 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\tests\LineRenderTest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */