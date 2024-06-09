/*     */ package org.newdawn.slick.tests;
/*     */ 
/*     */ import org.newdawn.slick.AppGameContainer;
/*     */ import org.newdawn.slick.BasicGame;
/*     */ import org.newdawn.slick.Game;
/*     */ import org.newdawn.slick.GameContainer;
/*     */ import org.newdawn.slick.Graphics;
/*     */ import org.newdawn.slick.Image;
/*     */ import org.newdawn.slick.SlickException;
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
/*     */ 
/*     */ public class ImageTest
/*     */   extends BasicGame
/*     */ {
/*     */   private Image tga;
/*     */   private Image scaleMe;
/*     */   private Image scaled;
/*     */   private Image gif;
/*     */   private Image image;
/*     */   private Image subImage;
/*     */   private Image rotImage;
/*     */   private float rot;
/*     */   public static boolean exitMe = true;
/*     */   
/*     */   public ImageTest() {
/*  40 */     super("Image Test");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void init(GameContainer container) throws SlickException {
/*  47 */     this.image = this.tga = new Image("testdata/logo.png");
/*  48 */     this.rotImage = new Image("testdata/logo.png");
/*  49 */     this.rotImage = this.rotImage.getScaledCopy(this.rotImage.getWidth() / 2, this.rotImage.getHeight() / 2);
/*     */ 
/*     */     
/*  52 */     this.scaleMe = new Image("testdata/logo.tga", true, 2);
/*  53 */     this.gif = new Image("testdata/logo.gif");
/*  54 */     this.gif.destroy();
/*  55 */     this.gif = new Image("testdata/logo.gif");
/*  56 */     this.scaled = this.gif.getScaledCopy(120, 120);
/*  57 */     this.subImage = this.image.getSubImage(200, 0, 70, 260);
/*  58 */     this.rot = 0.0F;
/*     */     
/*  60 */     if (exitMe) {
/*  61 */       container.exit();
/*     */     }
/*     */     
/*  64 */     Image test = this.tga.getSubImage(50, 50, 50, 50);
/*  65 */     System.out.println(test.getColor(50, 50));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void render(GameContainer container, Graphics g) {
/*  72 */     g.drawRect(0.0F, 0.0F, this.image.getWidth(), this.image.getHeight());
/*  73 */     this.image.draw(0.0F, 0.0F);
/*  74 */     this.image.draw(500.0F, 0.0F, 200.0F, 100.0F);
/*  75 */     this.scaleMe.draw(500.0F, 100.0F, 200.0F, 100.0F);
/*  76 */     this.scaled.draw(400.0F, 500.0F);
/*  77 */     Image flipped = this.scaled.getFlippedCopy(true, false);
/*  78 */     flipped.draw(520.0F, 500.0F);
/*  79 */     Image flipped2 = flipped.getFlippedCopy(false, true);
/*  80 */     flipped2.draw(520.0F, 380.0F);
/*  81 */     Image flipped3 = flipped2.getFlippedCopy(true, false);
/*  82 */     flipped3.draw(400.0F, 380.0F);
/*     */     
/*  84 */     for (int i = 0; i < 3; i++) {
/*  85 */       this.subImage.draw((200 + i * 30), 300.0F);
/*     */     }
/*     */     
/*  88 */     g.translate(500.0F, 200.0F);
/*  89 */     g.rotate(50.0F, 50.0F, this.rot);
/*  90 */     g.scale(0.3F, 0.3F);
/*  91 */     this.image.draw();
/*  92 */     g.resetTransform();
/*     */     
/*  94 */     this.rotImage.setRotation(this.rot);
/*  95 */     this.rotImage.draw(100.0F, 200.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update(GameContainer container, int delta) {
/* 102 */     this.rot += delta * 0.1F;
/* 103 */     if (this.rot > 360.0F) {
/* 104 */       this.rot -= 360.0F;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void main(String[] argv) {
/* 114 */     boolean sharedContextTest = false;
/*     */     
/*     */     try {
/* 117 */       exitMe = false;
/* 118 */       if (sharedContextTest) {
/* 119 */         GameContainer.enableSharedContext();
/* 120 */         exitMe = true;
/*     */       } 
/*     */       
/* 123 */       AppGameContainer container = new AppGameContainer((Game)new ImageTest());
/* 124 */       container.setForceExit(!sharedContextTest);
/* 125 */       container.setDisplayMode(800, 600, false);
/* 126 */       container.start();
/*     */       
/* 128 */       if (sharedContextTest) {
/* 129 */         System.out.println("Exit first instance");
/* 130 */         exitMe = false;
/* 131 */         container = new AppGameContainer((Game)new ImageTest());
/* 132 */         container.setDisplayMode(800, 600, false);
/* 133 */         container.start();
/*     */       } 
/* 135 */     } catch (SlickException e) {
/* 136 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void keyPressed(int key, char c) {
/* 145 */     if (key == 57)
/* 146 */       if (this.image == this.gif) {
/* 147 */         this.image = this.tga;
/*     */       } else {
/* 149 */         this.image = this.gif;
/*     */       }  
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\tests\ImageTest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */