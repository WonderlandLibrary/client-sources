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
/*     */ import org.newdawn.slick.particles.ConfigurableEmitter;
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
/*     */ public class PedigreeTest
/*     */   extends BasicGame
/*     */ {
/*     */   private Image image;
/*     */   private GameContainer container;
/*     */   private ParticleSystem trail;
/*     */   private ParticleSystem fire;
/*     */   private float rx;
/*  33 */   private float ry = 900.0F;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PedigreeTest() {
/*  39 */     super("Pedigree Test");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void init(GameContainer container) throws SlickException {
/*  46 */     this.container = container;
/*     */     
/*     */     try {
/*  49 */       this.fire = ParticleIO.loadConfiguredSystem("testdata/system.xml");
/*  50 */       this.trail = ParticleIO.loadConfiguredSystem("testdata/smoketrail.xml");
/*     */     }
/*  52 */     catch (IOException e) {
/*  53 */       throw new SlickException("Failed to load particle systems", e);
/*     */     } 
/*  55 */     this.image = new Image("testdata/rocket.png");
/*     */     
/*  57 */     spawnRocket();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void spawnRocket() {
/*  64 */     this.ry = 700.0F;
/*  65 */     this.rx = (float)(Math.random() * 600.0D + 100.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void render(GameContainer container, Graphics g) {
/*  72 */     ((ConfigurableEmitter)this.trail.getEmitter(0)).setPosition(this.rx + 14.0F, this.ry + 35.0F);
/*  73 */     this.trail.render();
/*  74 */     this.image.draw((int)this.rx, (int)this.ry);
/*     */     
/*  76 */     g.translate(400.0F, 300.0F);
/*  77 */     this.fire.render();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update(GameContainer container, int delta) {
/*  84 */     this.fire.update(delta);
/*  85 */     this.trail.update(delta);
/*     */     
/*  87 */     this.ry -= delta * 0.25F;
/*  88 */     if (this.ry < -100.0F) {
/*  89 */       spawnRocket();
/*     */     }
/*     */   }
/*     */   
/*     */   public void mousePressed(int button, int x, int y) {
/*  94 */     super.mousePressed(button, x, y);
/*     */     
/*  96 */     for (int i = 0; i < this.fire.getEmitterCount(); i++) {
/*  97 */       ((ConfigurableEmitter)this.fire.getEmitter(i)).setPosition((x - 400), (y - 300), true);
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
/* 108 */       AppGameContainer container = new AppGameContainer((Game)new PedigreeTest());
/* 109 */       container.setDisplayMode(800, 600, false);
/* 110 */       container.start();
/* 111 */     } catch (SlickException e) {
/* 112 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void keyPressed(int key, char c) {
/* 120 */     if (key == 1)
/* 121 */       this.container.exit(); 
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\tests\PedigreeTest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */