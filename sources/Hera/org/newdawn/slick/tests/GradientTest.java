/*     */ package org.newdawn.slick.tests;
/*     */ 
/*     */ import org.newdawn.slick.AppGameContainer;
/*     */ import org.newdawn.slick.BasicGame;
/*     */ import org.newdawn.slick.Color;
/*     */ import org.newdawn.slick.Game;
/*     */ import org.newdawn.slick.GameContainer;
/*     */ import org.newdawn.slick.Graphics;
/*     */ import org.newdawn.slick.ShapeFill;
/*     */ import org.newdawn.slick.SlickException;
/*     */ import org.newdawn.slick.fills.GradientFill;
/*     */ import org.newdawn.slick.geom.Polygon;
/*     */ import org.newdawn.slick.geom.Rectangle;
/*     */ import org.newdawn.slick.geom.RoundedRectangle;
/*     */ import org.newdawn.slick.geom.Shape;
/*     */ import org.newdawn.slick.opengl.renderer.Renderer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GradientTest
/*     */   extends BasicGame
/*     */ {
/*     */   private GameContainer container;
/*     */   private GradientFill gradient;
/*     */   private GradientFill gradient2;
/*     */   private GradientFill gradient4;
/*     */   private Rectangle rect;
/*     */   private Rectangle center;
/*     */   private RoundedRectangle round;
/*     */   private RoundedRectangle round2;
/*     */   private Polygon poly;
/*     */   private float ang;
/*     */   
/*     */   public GradientTest() {
/*  47 */     super("Gradient Test");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void init(GameContainer container) throws SlickException {
/*  54 */     this.container = container;
/*     */     
/*  56 */     this.rect = new Rectangle(400.0F, 100.0F, 200.0F, 150.0F);
/*  57 */     this.round = new RoundedRectangle(150.0F, 100.0F, 200.0F, 150.0F, 50.0F);
/*  58 */     this.round2 = new RoundedRectangle(150.0F, 300.0F, 200.0F, 150.0F, 50.0F);
/*  59 */     this.center = new Rectangle(350.0F, 250.0F, 100.0F, 100.0F);
/*     */     
/*  61 */     this.poly = new Polygon();
/*  62 */     this.poly.addPoint(400.0F, 350.0F);
/*  63 */     this.poly.addPoint(550.0F, 320.0F);
/*  64 */     this.poly.addPoint(600.0F, 380.0F);
/*  65 */     this.poly.addPoint(620.0F, 450.0F);
/*  66 */     this.poly.addPoint(500.0F, 450.0F);
/*     */     
/*  68 */     this.gradient = new GradientFill(0.0F, -75.0F, Color.red, 0.0F, 75.0F, Color.yellow, true);
/*  69 */     this.gradient2 = new GradientFill(0.0F, -75.0F, Color.blue, 0.0F, 75.0F, Color.white, true);
/*  70 */     this.gradient4 = new GradientFill(-50.0F, -40.0F, Color.green, 50.0F, 40.0F, Color.cyan, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void render(GameContainer container, Graphics g) {
/*  78 */     g.rotate(400.0F, 300.0F, this.ang);
/*  79 */     g.fill((Shape)this.rect, (ShapeFill)this.gradient);
/*  80 */     g.fill((Shape)this.round, (ShapeFill)this.gradient);
/*  81 */     g.fill((Shape)this.poly, (ShapeFill)this.gradient2);
/*  82 */     g.fill((Shape)this.center, (ShapeFill)this.gradient4);
/*     */     
/*  84 */     g.setAntiAlias(true);
/*  85 */     g.setLineWidth(10.0F);
/*  86 */     g.draw((Shape)this.round2, (ShapeFill)this.gradient2);
/*  87 */     g.setLineWidth(2.0F);
/*  88 */     g.draw((Shape)this.poly, (ShapeFill)this.gradient);
/*  89 */     g.setAntiAlias(false);
/*     */     
/*  91 */     g.fill((Shape)this.center, (ShapeFill)this.gradient4);
/*  92 */     g.setAntiAlias(true);
/*  93 */     g.setColor(Color.black);
/*  94 */     g.draw((Shape)this.center);
/*  95 */     g.setAntiAlias(false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update(GameContainer container, int delta) {
/* 102 */     this.ang += delta * 0.01F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void main(String[] argv) {
/*     */     try {
/* 112 */       Renderer.setRenderer(2);
/*     */       
/* 114 */       AppGameContainer container = new AppGameContainer((Game)new GradientTest());
/* 115 */       container.setDisplayMode(800, 600, false);
/* 116 */       container.start();
/* 117 */     } catch (SlickException e) {
/* 118 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void keyPressed(int key, char c) {
/* 126 */     if (key == 1)
/* 127 */       this.container.exit(); 
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\tests\GradientTest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */