/*     */ package org.newdawn.slick.tests;
/*     */ 
/*     */ import org.newdawn.slick.AngelCodeFont;
/*     */ import org.newdawn.slick.AppGameContainer;
/*     */ import org.newdawn.slick.BasicGame;
/*     */ import org.newdawn.slick.Color;
/*     */ import org.newdawn.slick.Font;
/*     */ import org.newdawn.slick.Game;
/*     */ import org.newdawn.slick.GameContainer;
/*     */ import org.newdawn.slick.Graphics;
/*     */ import org.newdawn.slick.Image;
/*     */ import org.newdawn.slick.SlickException;
/*     */ import org.newdawn.slick.opengl.pbuffer.GraphicsFactory;
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
/*     */ public class ImageGraphicsTest
/*     */   extends BasicGame
/*     */ {
/*     */   private Image preloaded;
/*     */   private Image target;
/*     */   private Image cut;
/*     */   private Graphics gTarget;
/*     */   private Graphics offscreenPreload;
/*     */   private Image testImage;
/*     */   private Font testFont;
/*     */   private float ang;
/*  37 */   private String using = "none";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ImageGraphicsTest() {
/*  43 */     super("Image Graphics Test");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void init(GameContainer container) throws SlickException {
/*  50 */     this.testImage = new Image("testdata/logo.png");
/*  51 */     this.preloaded = new Image("testdata/logo.png");
/*  52 */     this.testFont = (Font)new AngelCodeFont("testdata/hiero.fnt", "testdata/hiero.png");
/*  53 */     this.target = new Image(400, 300);
/*  54 */     this.cut = new Image(100, 100);
/*  55 */     this.gTarget = this.target.getGraphics();
/*  56 */     this.offscreenPreload = this.preloaded.getGraphics();
/*     */     
/*  58 */     this.offscreenPreload.drawString("Drawing over a loaded image", 5.0F, 15.0F);
/*  59 */     this.offscreenPreload.setLineWidth(5.0F);
/*  60 */     this.offscreenPreload.setAntiAlias(true);
/*  61 */     this.offscreenPreload.setColor(Color.blue.brighter());
/*  62 */     this.offscreenPreload.drawOval(200.0F, 30.0F, 50.0F, 50.0F);
/*  63 */     this.offscreenPreload.setColor(Color.white);
/*  64 */     this.offscreenPreload.drawRect(190.0F, 20.0F, 70.0F, 70.0F);
/*  65 */     this.offscreenPreload.flush();
/*     */     
/*  67 */     if (GraphicsFactory.usingFBO()) {
/*  68 */       this.using = "FBO (Frame Buffer Objects)";
/*  69 */     } else if (GraphicsFactory.usingPBuffer()) {
/*  70 */       this.using = "Pbuffer (Pixel Buffers)";
/*     */     } 
/*     */     
/*  73 */     System.out.println(this.preloaded.getColor(50, 50));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void render(GameContainer container, Graphics g) throws SlickException {
/*  83 */     this.gTarget.setBackground(new Color(0, 0, 0, 0));
/*  84 */     this.gTarget.clear();
/*  85 */     this.gTarget.rotate(200.0F, 160.0F, this.ang);
/*  86 */     this.gTarget.setFont(this.testFont);
/*  87 */     this.gTarget.fillRect(10.0F, 10.0F, 50.0F, 50.0F);
/*  88 */     this.gTarget.drawString("HELLO WORLD", 10.0F, 10.0F);
/*     */     
/*  90 */     this.gTarget.drawImage(this.testImage, 100.0F, 150.0F);
/*  91 */     this.gTarget.drawImage(this.testImage, 100.0F, 50.0F);
/*  92 */     this.gTarget.drawImage(this.testImage, 50.0F, 75.0F);
/*     */ 
/*     */ 
/*     */     
/*  96 */     this.gTarget.flush();
/*     */     
/*  98 */     g.setColor(Color.red);
/*  99 */     g.fillRect(250.0F, 50.0F, 200.0F, 200.0F);
/*     */ 
/*     */     
/* 102 */     this.target.draw(300.0F, 100.0F);
/* 103 */     this.target.draw(300.0F, 410.0F, 200.0F, 150.0F);
/* 104 */     this.target.draw(505.0F, 410.0F, 100.0F, 75.0F);
/*     */ 
/*     */ 
/*     */     
/* 108 */     g.setColor(Color.white);
/* 109 */     g.drawString("Testing On Offscreen Buffer", 300.0F, 80.0F);
/* 110 */     g.setColor(Color.green);
/* 111 */     g.drawRect(300.0F, 100.0F, this.target.getWidth(), this.target.getHeight());
/* 112 */     g.drawRect(300.0F, 410.0F, (this.target.getWidth() / 2), (this.target.getHeight() / 2));
/* 113 */     g.drawRect(505.0F, 410.0F, (this.target.getWidth() / 4), (this.target.getHeight() / 4));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 118 */     g.setColor(Color.white);
/* 119 */     g.drawString("Testing Font On Back Buffer", 10.0F, 100.0F);
/* 120 */     g.drawString("Using: " + this.using, 10.0F, 580.0F);
/* 121 */     g.setColor(Color.red);
/* 122 */     g.fillRect(10.0F, 120.0F, 200.0F, 5.0F);
/*     */ 
/*     */     
/* 125 */     int xp = (int)(60.0D + Math.sin((this.ang / 60.0F)) * 50.0D);
/* 126 */     g.copyArea(this.cut, xp, 50);
/*     */ 
/*     */ 
/*     */     
/* 130 */     this.cut.draw(30.0F, 250.0F);
/* 131 */     g.setColor(Color.white);
/* 132 */     g.drawRect(30.0F, 250.0F, this.cut.getWidth(), this.cut.getHeight());
/* 133 */     g.setColor(Color.gray);
/* 134 */     g.drawRect(xp, 50.0F, this.cut.getWidth(), this.cut.getHeight());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 139 */     this.preloaded.draw(2.0F, 400.0F);
/* 140 */     g.setColor(Color.blue);
/* 141 */     g.drawRect(2.0F, 400.0F, this.preloaded.getWidth(), this.preloaded.getHeight());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update(GameContainer container, int delta) {
/* 148 */     this.ang += delta * 0.1F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void main(String[] argv) {
/*     */     try {
/* 158 */       GraphicsFactory.setUseFBO(false);
/*     */       
/* 160 */       AppGameContainer container = new AppGameContainer((Game)new ImageGraphicsTest());
/* 161 */       container.setDisplayMode(800, 600, false);
/* 162 */       container.start();
/* 163 */     } catch (SlickException e) {
/* 164 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\tests\ImageGraphicsTest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */