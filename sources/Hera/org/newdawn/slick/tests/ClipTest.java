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
/*     */ 
/*     */ public class ClipTest
/*     */   extends BasicGame
/*     */ {
/*  19 */   private float ang = 0.0F;
/*     */ 
/*     */   
/*     */   private boolean world;
/*     */ 
/*     */   
/*     */   private boolean clip;
/*     */ 
/*     */   
/*     */   public ClipTest() {
/*  29 */     super("Clip Test");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void init(GameContainer container) throws SlickException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update(GameContainer container, int delta) throws SlickException {
/*  43 */     this.ang += delta * 0.01F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void render(GameContainer container, Graphics g) throws SlickException {
/*  51 */     g.setColor(Color.white);
/*  52 */     g.drawString("1 - No Clipping", 100.0F, 10.0F);
/*  53 */     g.drawString("2 - Screen Clipping", 100.0F, 30.0F);
/*  54 */     g.drawString("3 - World Clipping", 100.0F, 50.0F);
/*     */     
/*  56 */     if (this.world) {
/*  57 */       g.drawString("WORLD CLIPPING ENABLED", 200.0F, 80.0F);
/*     */     }
/*  59 */     if (this.clip) {
/*  60 */       g.drawString("SCREEN CLIPPING ENABLED", 200.0F, 80.0F);
/*     */     }
/*     */     
/*  63 */     g.rotate(400.0F, 400.0F, this.ang);
/*  64 */     if (this.world) {
/*  65 */       g.setWorldClip(350.0F, 302.0F, 100.0F, 196.0F);
/*     */     }
/*  67 */     if (this.clip) {
/*  68 */       g.setClip(350, 302, 100, 196);
/*     */     }
/*  70 */     g.setColor(Color.red);
/*  71 */     g.fillOval(300.0F, 300.0F, 200.0F, 200.0F);
/*  72 */     g.setColor(Color.blue);
/*  73 */     g.fillRect(390.0F, 200.0F, 20.0F, 400.0F);
/*     */     
/*  75 */     g.clearClip();
/*  76 */     g.clearWorldClip();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void keyPressed(int key, char c) {
/*  83 */     if (key == 2) {
/*  84 */       this.world = false;
/*  85 */       this.clip = false;
/*     */     } 
/*  87 */     if (key == 3) {
/*  88 */       this.world = false;
/*  89 */       this.clip = true;
/*     */     } 
/*  91 */     if (key == 4) {
/*  92 */       this.world = true;
/*  93 */       this.clip = false;
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
/* 104 */       AppGameContainer container = new AppGameContainer((Game)new ClipTest());
/* 105 */       container.setDisplayMode(800, 600, false);
/* 106 */       container.start();
/* 107 */     } catch (SlickException e) {
/* 108 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\tests\ClipTest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */