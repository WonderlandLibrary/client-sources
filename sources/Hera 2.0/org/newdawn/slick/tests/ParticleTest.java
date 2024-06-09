/*    */ package org.newdawn.slick.tests;
/*    */ 
/*    */ import org.newdawn.slick.AppGameContainer;
/*    */ import org.newdawn.slick.BasicGame;
/*    */ import org.newdawn.slick.Game;
/*    */ import org.newdawn.slick.GameContainer;
/*    */ import org.newdawn.slick.Graphics;
/*    */ import org.newdawn.slick.Image;
/*    */ import org.newdawn.slick.SlickException;
/*    */ import org.newdawn.slick.particles.ParticleEmitter;
/*    */ import org.newdawn.slick.particles.ParticleSystem;
/*    */ import org.newdawn.slick.particles.effects.FireEmitter;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ParticleTest
/*    */   extends BasicGame
/*    */ {
/*    */   private ParticleSystem system;
/* 22 */   private int mode = 2;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ParticleTest() {
/* 28 */     super("Particle Test");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void init(GameContainer container) throws SlickException {
/* 35 */     Image image = new Image("testdata/particle.tga", true);
/* 36 */     this.system = new ParticleSystem(image);
/*    */     
/* 38 */     this.system.addEmitter((ParticleEmitter)new FireEmitter(400, 300, 45.0F));
/* 39 */     this.system.addEmitter((ParticleEmitter)new FireEmitter(200, 300, 60.0F));
/* 40 */     this.system.addEmitter((ParticleEmitter)new FireEmitter(600, 300, 30.0F));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void render(GameContainer container, Graphics g) {
/* 49 */     for (int i = 0; i < 100; i++) {
/* 50 */       g.translate(1.0F, 1.0F);
/* 51 */       this.system.render();
/*    */     } 
/* 53 */     g.resetTransform();
/* 54 */     g.drawString("Press space to toggle blending mode", 200.0F, 500.0F);
/* 55 */     g.drawString("Particle Count: " + (this.system.getParticleCount() * 100), 200.0F, 520.0F);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void update(GameContainer container, int delta) {
/* 62 */     this.system.update(delta);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void keyPressed(int key, char c) {
/* 69 */     if (key == 1) {
/* 70 */       System.exit(0);
/*    */     }
/* 72 */     if (key == 57) {
/* 73 */       this.mode = (1 == this.mode) ? 2 : 1;
/* 74 */       this.system.setBlendingMode(this.mode);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void main(String[] argv) {
/*    */     try {
/* 85 */       AppGameContainer container = new AppGameContainer((Game)new ParticleTest());
/* 86 */       container.setDisplayMode(800, 600, false);
/* 87 */       container.start();
/* 88 */     } catch (SlickException e) {
/* 89 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\tests\ParticleTest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */