/*     */ package org.newdawn.slick.tests;
/*     */ 
/*     */ import org.newdawn.slick.AppGameContainer;
/*     */ import org.newdawn.slick.BasicGame;
/*     */ import org.newdawn.slick.Color;
/*     */ import org.newdawn.slick.Game;
/*     */ import org.newdawn.slick.GameContainer;
/*     */ import org.newdawn.slick.Graphics;
/*     */ import org.newdawn.slick.SlickException;
/*     */ import org.newdawn.slick.opengl.renderer.Renderer;
/*     */ import org.newdawn.slick.svg.InkscapeLoader;
/*     */ import org.newdawn.slick.svg.SimpleDiagramRenderer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class InkscapeTest
/*     */   extends BasicGame
/*     */ {
/*  21 */   private SimpleDiagramRenderer[] renderer = new SimpleDiagramRenderer[5];
/*     */   
/*  23 */   private float zoom = 1.0F;
/*     */ 
/*     */   
/*     */   private float x;
/*     */ 
/*     */   
/*     */   private float y;
/*     */ 
/*     */   
/*     */   public InkscapeTest() {
/*  33 */     super("Inkscape Test");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void init(GameContainer container) throws SlickException {
/*  40 */     container.getGraphics().setBackground(Color.white);
/*     */     
/*  42 */     InkscapeLoader.RADIAL_TRIANGULATION_LEVEL = 2;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  47 */     this.renderer[3] = new SimpleDiagramRenderer(InkscapeLoader.load("testdata/svg/clonetest.svg"));
/*     */ 
/*     */     
/*  50 */     container.getGraphics().setBackground(new Color(0.5F, 0.7F, 1.0F));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update(GameContainer container, int delta) throws SlickException {
/*  57 */     if (container.getInput().isKeyDown(16)) {
/*  58 */       this.zoom += delta * 0.01F;
/*  59 */       if (this.zoom > 10.0F) {
/*  60 */         this.zoom = 10.0F;
/*     */       }
/*     */     } 
/*  63 */     if (container.getInput().isKeyDown(30)) {
/*  64 */       this.zoom -= delta * 0.01F;
/*  65 */       if (this.zoom < 0.1F) {
/*  66 */         this.zoom = 0.1F;
/*     */       }
/*     */     } 
/*  69 */     if (container.getInput().isKeyDown(205)) {
/*  70 */       this.x += delta * 0.1F;
/*     */     }
/*  72 */     if (container.getInput().isKeyDown(203)) {
/*  73 */       this.x -= delta * 0.1F;
/*     */     }
/*  75 */     if (container.getInput().isKeyDown(208)) {
/*  76 */       this.y += delta * 0.1F;
/*     */     }
/*  78 */     if (container.getInput().isKeyDown(200)) {
/*  79 */       this.y -= delta * 0.1F;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void render(GameContainer container, Graphics g) throws SlickException {
/*  87 */     g.scale(this.zoom, this.zoom);
/*  88 */     g.translate(this.x, this.y);
/*  89 */     g.scale(0.3F, 0.3F);
/*     */     
/*  91 */     g.scale(3.3333333F, 3.3333333F);
/*  92 */     g.translate(400.0F, 0.0F);
/*     */     
/*  94 */     g.translate(100.0F, 300.0F);
/*  95 */     g.scale(0.7F, 0.7F);
/*     */     
/*  97 */     g.scale(1.4285715F, 1.4285715F);
/*     */     
/*  99 */     g.scale(0.5F, 0.5F);
/* 100 */     g.translate(-1100.0F, -380.0F);
/* 101 */     this.renderer[3].render(g);
/* 102 */     g.scale(2.0F, 2.0F);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 108 */     g.resetTransform();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void main(String[] argv) {
/*     */     try {
/* 118 */       Renderer.setRenderer(2);
/* 119 */       Renderer.setLineStripRenderer(4);
/*     */       
/* 121 */       AppGameContainer container = new AppGameContainer((Game)new InkscapeTest());
/* 122 */       container.setDisplayMode(800, 600, false);
/* 123 */       container.start();
/* 124 */     } catch (SlickException e) {
/* 125 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\tests\InkscapeTest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */