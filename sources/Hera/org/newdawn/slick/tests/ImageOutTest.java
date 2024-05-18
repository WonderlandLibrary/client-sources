/*     */ package org.newdawn.slick.tests;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import org.newdawn.slick.AppGameContainer;
/*     */ import org.newdawn.slick.BasicGame;
/*     */ import org.newdawn.slick.Game;
/*     */ import org.newdawn.slick.GameContainer;
/*     */ import org.newdawn.slick.Graphics;
/*     */ import org.newdawn.slick.Image;
/*     */ import org.newdawn.slick.SlickException;
/*     */ import org.newdawn.slick.imageout.ImageOut;
/*     */ import org.newdawn.slick.particles.ParticleIO;
/*     */ import org.newdawn.slick.particles.ParticleSystem;
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
/*     */ public class ImageOutTest
/*     */   extends BasicGame
/*     */ {
/*     */   private GameContainer container;
/*     */   private ParticleSystem fire;
/*     */   private Graphics g;
/*     */   private Image copy;
/*     */   private String message;
/*     */   
/*     */   public ImageOutTest() {
/*  37 */     super("Image Out Test");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void init(GameContainer container) throws SlickException {
/*  44 */     this.container = container;
/*     */     
/*     */     try {
/*  47 */       this.fire = ParticleIO.loadConfiguredSystem("testdata/system.xml");
/*  48 */     } catch (IOException e) {
/*  49 */       throw new SlickException("Failed to load particle systems", e);
/*     */     } 
/*     */     
/*  52 */     this.copy = new Image(400, 300);
/*  53 */     String[] formats = ImageOut.getSupportedFormats();
/*  54 */     this.message = "Formats supported: ";
/*  55 */     for (int i = 0; i < formats.length; i++) {
/*  56 */       this.message += formats[i];
/*  57 */       if (i < formats.length - 1) {
/*  58 */         this.message += ",";
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void render(GameContainer container, Graphics g) {
/*  67 */     g.drawString("T - TGA Snapshot", 10.0F, 50.0F);
/*  68 */     g.drawString("J - JPG Snapshot", 10.0F, 70.0F);
/*  69 */     g.drawString("P - PNG Snapshot", 10.0F, 90.0F);
/*     */     
/*  71 */     g.setDrawMode(Graphics.MODE_ADD);
/*  72 */     g.drawImage(this.copy, 200.0F, 300.0F);
/*  73 */     g.setDrawMode(Graphics.MODE_NORMAL);
/*     */     
/*  75 */     g.drawString(this.message, 10.0F, 400.0F);
/*  76 */     g.drawRect(200.0F, 0.0F, 400.0F, 300.0F);
/*  77 */     g.translate(400.0F, 250.0F);
/*  78 */     this.fire.render();
/*  79 */     this.g = g;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeTo(String fname) throws SlickException {
/*  89 */     this.g.copyArea(this.copy, 200, 0);
/*  90 */     ImageOut.write(this.copy, fname);
/*  91 */     this.message = "Written " + fname;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update(GameContainer container, int delta) throws SlickException {
/*  98 */     this.fire.update(delta);
/*     */     
/* 100 */     if (container.getInput().isKeyPressed(25)) {
/* 101 */       writeTo("ImageOutTest.png");
/*     */     }
/* 103 */     if (container.getInput().isKeyPressed(36)) {
/* 104 */       writeTo("ImageOutTest.jpg");
/*     */     }
/* 106 */     if (container.getInput().isKeyPressed(20)) {
/* 107 */       writeTo("ImageOutTest.tga");
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
/* 118 */       AppGameContainer container = new AppGameContainer((Game)new ImageOutTest());
/* 119 */       container.setDisplayMode(800, 600, false);
/* 120 */       container.start();
/* 121 */     } catch (SlickException e) {
/* 122 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void keyPressed(int key, char c) {
/* 130 */     if (key == 1)
/* 131 */       this.container.exit(); 
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\tests\ImageOutTest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */