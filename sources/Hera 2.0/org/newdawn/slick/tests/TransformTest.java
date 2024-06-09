/*     */ package org.newdawn.slick.tests;
/*     */ 
/*     */ import org.newdawn.slick.AppGameContainer;
/*     */ import org.newdawn.slick.BasicGame;
/*     */ import org.newdawn.slick.Color;
/*     */ import org.newdawn.slick.Game;
/*     */ import org.newdawn.slick.GameContainer;
/*     */ import org.newdawn.slick.Graphics;
/*     */ import org.newdawn.slick.SlickException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TransformTest
/*     */   extends BasicGame
/*     */ {
/*  18 */   private float scale = 1.0F;
/*     */ 
/*     */   
/*     */   private boolean scaleUp;
/*     */ 
/*     */   
/*     */   private boolean scaleDown;
/*     */ 
/*     */   
/*     */   public TransformTest() {
/*  28 */     super("Transform Test");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void init(GameContainer container) throws SlickException {
/*  35 */     container.setTargetFrameRate(100);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void render(GameContainer contiainer, Graphics g) {
/*  42 */     g.translate(320.0F, 240.0F);
/*  43 */     g.scale(this.scale, this.scale);
/*     */     
/*  45 */     g.setColor(Color.red);
/*  46 */     for (int x = 0; x < 10; x++) {
/*  47 */       for (int y = 0; y < 10; y++) {
/*  48 */         g.fillRect((-500 + x * 100), (-500 + y * 100), 80.0F, 80.0F);
/*     */       }
/*     */     } 
/*     */     
/*  52 */     g.setColor(new Color(1.0F, 1.0F, 1.0F, 0.5F));
/*  53 */     g.fillRect(-320.0F, -240.0F, 640.0F, 480.0F);
/*  54 */     g.setColor(Color.white);
/*  55 */     g.drawRect(-320.0F, -240.0F, 640.0F, 480.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update(GameContainer container, int delta) {
/*  62 */     if (this.scaleUp) {
/*  63 */       this.scale += delta * 0.001F;
/*     */     }
/*  65 */     if (this.scaleDown) {
/*  66 */       this.scale -= delta * 0.001F;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void keyPressed(int key, char c) {
/*  74 */     if (key == 1) {
/*  75 */       System.exit(0);
/*     */     }
/*  77 */     if (key == 16) {
/*  78 */       this.scaleUp = true;
/*     */     }
/*  80 */     if (key == 30) {
/*  81 */       this.scaleDown = true;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void keyReleased(int key, char c) {
/*  89 */     if (key == 16) {
/*  90 */       this.scaleUp = false;
/*     */     }
/*  92 */     if (key == 30) {
/*  93 */       this.scaleDown = false;
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
/* 104 */       AppGameContainer container = new AppGameContainer((Game)new TransformTest());
/* 105 */       container.setDisplayMode(640, 480, false);
/* 106 */       container.start();
/* 107 */     } catch (SlickException e) {
/* 108 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\tests\TransformTest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */