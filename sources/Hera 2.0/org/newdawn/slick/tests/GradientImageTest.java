/*     */ package org.newdawn.slick.tests;
/*     */ 
/*     */ import org.newdawn.slick.AppGameContainer;
/*     */ import org.newdawn.slick.BasicGame;
/*     */ import org.newdawn.slick.Color;
/*     */ import org.newdawn.slick.Game;
/*     */ import org.newdawn.slick.GameContainer;
/*     */ import org.newdawn.slick.Graphics;
/*     */ import org.newdawn.slick.Image;
/*     */ import org.newdawn.slick.ShapeFill;
/*     */ import org.newdawn.slick.SlickException;
/*     */ import org.newdawn.slick.fills.GradientFill;
/*     */ import org.newdawn.slick.geom.Polygon;
/*     */ import org.newdawn.slick.geom.Rectangle;
/*     */ import org.newdawn.slick.geom.Shape;
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
/*     */ public class GradientImageTest
/*     */   extends BasicGame
/*     */ {
/*     */   private Image image1;
/*     */   private Image image2;
/*     */   private GradientFill fill;
/*     */   private Shape shape;
/*     */   private Polygon poly;
/*     */   private GameContainer container;
/*     */   private float ang;
/*     */   private boolean rotating = false;
/*     */   
/*     */   public GradientImageTest() {
/*  43 */     super("Gradient Image Test");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void init(GameContainer container) throws SlickException {
/*  50 */     this.container = container;
/*     */     
/*  52 */     this.image1 = new Image("testdata/grass.png");
/*  53 */     this.image2 = new Image("testdata/rocks.png");
/*     */     
/*  55 */     this.fill = new GradientFill(-64.0F, 0.0F, new Color(1.0F, 1.0F, 1.0F, 1.0F), 64.0F, 0.0F, new Color(0, 0, 0, 0));
/*  56 */     this.shape = (Shape)new Rectangle(336.0F, 236.0F, 128.0F, 128.0F);
/*  57 */     this.poly = new Polygon();
/*  58 */     this.poly.addPoint(320.0F, 220.0F);
/*  59 */     this.poly.addPoint(350.0F, 200.0F);
/*  60 */     this.poly.addPoint(450.0F, 200.0F);
/*  61 */     this.poly.addPoint(480.0F, 220.0F);
/*  62 */     this.poly.addPoint(420.0F, 400.0F);
/*  63 */     this.poly.addPoint(400.0F, 390.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void render(GameContainer container, Graphics g) {
/*  70 */     g.drawString("R - Toggle Rotationg", 10.0F, 50.0F);
/*  71 */     g.drawImage(this.image1, 100.0F, 236.0F);
/*  72 */     g.drawImage(this.image2, 600.0F, 236.0F);
/*     */     
/*  74 */     g.translate(0.0F, -150.0F);
/*  75 */     g.rotate(400.0F, 300.0F, this.ang);
/*  76 */     g.texture(this.shape, this.image2);
/*  77 */     g.texture(this.shape, this.image1, (ShapeFill)this.fill);
/*  78 */     g.resetTransform();
/*     */     
/*  80 */     g.translate(0.0F, 150.0F);
/*  81 */     g.rotate(400.0F, 300.0F, this.ang);
/*  82 */     g.texture((Shape)this.poly, this.image2);
/*  83 */     g.texture((Shape)this.poly, this.image1, (ShapeFill)this.fill);
/*  84 */     g.resetTransform();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update(GameContainer container, int delta) {
/*  91 */     if (this.rotating) {
/*  92 */       this.ang += delta * 0.1F;
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
/* 103 */       AppGameContainer container = new AppGameContainer((Game)new GradientImageTest());
/* 104 */       container.setDisplayMode(800, 600, false);
/* 105 */       container.start();
/* 106 */     } catch (SlickException e) {
/* 107 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void keyPressed(int key, char c) {
/* 115 */     if (key == 19) {
/* 116 */       this.rotating = !this.rotating;
/*     */     }
/* 118 */     if (key == 1)
/* 119 */       this.container.exit(); 
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\tests\GradientImageTest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */