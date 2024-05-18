/*     */ package org.newdawn.slick.tests;
/*     */ 
/*     */ import org.newdawn.slick.AppGameContainer;
/*     */ import org.newdawn.slick.BasicGame;
/*     */ import org.newdawn.slick.Color;
/*     */ import org.newdawn.slick.Game;
/*     */ import org.newdawn.slick.GameContainer;
/*     */ import org.newdawn.slick.Graphics;
/*     */ import org.newdawn.slick.Image;
/*     */ import org.newdawn.slick.SlickException;
/*     */ import org.newdawn.slick.geom.Polygon;
/*     */ import org.newdawn.slick.geom.Shape;
/*     */ import org.newdawn.slick.util.FastTrig;
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
/*     */ public class GraphicsTest
/*     */   extends BasicGame
/*     */ {
/*     */   private boolean clip;
/*     */   private float ang;
/*     */   private Image image;
/*     */   private Polygon poly;
/*     */   private GameContainer container;
/*     */   
/*     */   public GraphicsTest() {
/*  35 */     super("Graphics Test");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void init(GameContainer container) throws SlickException {
/*  42 */     this.container = container;
/*     */     
/*  44 */     this.image = new Image("testdata/logo.tga", true);
/*     */     
/*  46 */     Image temp = new Image("testdata/palette_tool.png");
/*  47 */     container.setMouseCursor(temp, 0, 0);
/*     */     
/*  49 */     container.setIcons(new String[] { "testdata/icon.tga" });
/*  50 */     container.setTargetFrameRate(100);
/*     */     
/*  52 */     this.poly = new Polygon();
/*  53 */     float len = 100.0F;
/*     */     
/*  55 */     for (int x = 0; x < 360; x += 30) {
/*  56 */       if (len == 100.0F) {
/*  57 */         len = 50.0F;
/*     */       } else {
/*  59 */         len = 100.0F;
/*     */       } 
/*  61 */       this.poly.addPoint((float)FastTrig.cos(Math.toRadians(x)) * len, (float)FastTrig.sin(Math.toRadians(x)) * len);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void render(GameContainer container, Graphics g) throws SlickException {
/*  70 */     g.setColor(Color.white);
/*     */     
/*  72 */     g.setAntiAlias(true);
/*  73 */     for (int x = 0; x < 360; x += 10) {
/*  74 */       g.drawLine(700.0F, 100.0F, (int)(700.0D + Math.cos(Math.toRadians(x)) * 100.0D), (int)(100.0D + Math.sin(Math.toRadians(x)) * 100.0D));
/*     */     }
/*     */     
/*  77 */     g.setAntiAlias(false);
/*     */     
/*  79 */     g.setColor(Color.yellow);
/*  80 */     g.drawString("The Graphics Test!", 300.0F, 50.0F);
/*  81 */     g.setColor(Color.white);
/*  82 */     g.drawString("Space - Toggles clipping", 400.0F, 80.0F);
/*  83 */     g.drawString("Frame rate capped to 100", 400.0F, 120.0F);
/*     */     
/*  85 */     if (this.clip) {
/*  86 */       g.setColor(Color.gray);
/*  87 */       g.drawRect(100.0F, 260.0F, 400.0F, 100.0F);
/*  88 */       g.setClip(100, 260, 400, 100);
/*     */     } 
/*     */     
/*  91 */     g.setColor(Color.yellow);
/*  92 */     g.translate(100.0F, 120.0F);
/*  93 */     g.fill((Shape)this.poly);
/*  94 */     g.setColor(Color.blue);
/*  95 */     g.setLineWidth(3.0F);
/*  96 */     g.draw((Shape)this.poly);
/*  97 */     g.setLineWidth(1.0F);
/*  98 */     g.translate(0.0F, 230.0F);
/*  99 */     g.draw((Shape)this.poly);
/* 100 */     g.resetTransform();
/*     */     
/* 102 */     g.setColor(Color.magenta);
/* 103 */     g.drawRoundRect(10.0F, 10.0F, 100.0F, 100.0F, 10);
/* 104 */     g.fillRoundRect(10.0F, 210.0F, 100.0F, 100.0F, 10);
/*     */     
/* 106 */     g.rotate(400.0F, 300.0F, this.ang);
/* 107 */     g.setColor(Color.green);
/* 108 */     g.drawRect(200.0F, 200.0F, 200.0F, 200.0F);
/* 109 */     g.setColor(Color.blue);
/* 110 */     g.fillRect(250.0F, 250.0F, 100.0F, 100.0F);
/*     */     
/* 112 */     g.drawImage(this.image, 300.0F, 270.0F);
/*     */     
/* 114 */     g.setColor(Color.red);
/* 115 */     g.drawOval(100.0F, 100.0F, 200.0F, 200.0F);
/* 116 */     g.setColor(Color.red.darker());
/* 117 */     g.fillOval(300.0F, 300.0F, 150.0F, 100.0F);
/* 118 */     g.setAntiAlias(true);
/* 119 */     g.setColor(Color.white);
/* 120 */     g.setLineWidth(5.0F);
/* 121 */     g.drawOval(300.0F, 300.0F, 150.0F, 100.0F);
/* 122 */     g.setAntiAlias(true);
/* 123 */     g.resetTransform();
/*     */     
/* 125 */     if (this.clip) {
/* 126 */       g.clearClip();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update(GameContainer container, int delta) {
/* 134 */     this.ang += delta * 0.1F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void keyPressed(int key, char c) {
/* 141 */     if (key == 1) {
/* 142 */       System.exit(0);
/*     */     }
/* 144 */     if (key == 57) {
/* 145 */       this.clip = !this.clip;
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
/* 156 */       AppGameContainer container = new AppGameContainer((Game)new GraphicsTest());
/* 157 */       container.setDisplayMode(800, 600, false);
/* 158 */       container.start();
/* 159 */     } catch (SlickException e) {
/* 160 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\tests\GraphicsTest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */