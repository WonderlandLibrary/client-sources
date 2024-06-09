/*     */ package org.newdawn.slick.tests;
/*     */ 
/*     */ import org.newdawn.slick.AppGameContainer;
/*     */ import org.newdawn.slick.BasicGame;
/*     */ import org.newdawn.slick.Color;
/*     */ import org.newdawn.slick.Game;
/*     */ import org.newdawn.slick.GameContainer;
/*     */ import org.newdawn.slick.Graphics;
/*     */ import org.newdawn.slick.SlickException;
/*     */ import org.newdawn.slick.geom.Curve;
/*     */ import org.newdawn.slick.geom.Polygon;
/*     */ import org.newdawn.slick.geom.Shape;
/*     */ import org.newdawn.slick.geom.Vector2f;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CurveTest
/*     */   extends BasicGame
/*     */ {
/*     */   private Curve curve;
/*  22 */   private Vector2f p1 = new Vector2f(100.0F, 300.0F);
/*     */   
/*  24 */   private Vector2f c1 = new Vector2f(100.0F, 100.0F);
/*     */   
/*  26 */   private Vector2f c2 = new Vector2f(300.0F, 100.0F);
/*     */   
/*  28 */   private Vector2f p2 = new Vector2f(300.0F, 300.0F);
/*     */ 
/*     */ 
/*     */   
/*     */   private Polygon poly;
/*     */ 
/*     */ 
/*     */   
/*     */   public CurveTest() {
/*  37 */     super("Curve Test");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void init(GameContainer container) throws SlickException {
/*  44 */     container.getGraphics().setBackground(Color.white);
/*     */     
/*  46 */     this.curve = new Curve(this.p2, this.c2, this.c1, this.p1);
/*  47 */     this.poly = new Polygon();
/*  48 */     this.poly.addPoint(500.0F, 200.0F);
/*  49 */     this.poly.addPoint(600.0F, 200.0F);
/*  50 */     this.poly.addPoint(700.0F, 300.0F);
/*  51 */     this.poly.addPoint(400.0F, 300.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update(GameContainer container, int delta) throws SlickException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void drawMarker(Graphics g, Vector2f p) {
/*  67 */     g.drawRect(p.x - 5.0F, p.y - 5.0F, 10.0F, 10.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void render(GameContainer container, Graphics g) throws SlickException {
/*  74 */     g.setColor(Color.gray);
/*  75 */     drawMarker(g, this.p1);
/*  76 */     drawMarker(g, this.p2);
/*  77 */     g.setColor(Color.red);
/*  78 */     drawMarker(g, this.c1);
/*  79 */     drawMarker(g, this.c2);
/*     */     
/*  81 */     g.setColor(Color.black);
/*  82 */     g.draw((Shape)this.curve);
/*  83 */     g.fill((Shape)this.curve);
/*     */     
/*  85 */     g.draw((Shape)this.poly);
/*  86 */     g.fill((Shape)this.poly);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void main(String[] argv) {
/*     */     try {
/*  96 */       AppGameContainer container = new AppGameContainer((Game)new CurveTest());
/*  97 */       container.setDisplayMode(800, 600, false);
/*  98 */       container.start();
/*  99 */     } catch (SlickException e) {
/* 100 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\tests\CurveTest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */