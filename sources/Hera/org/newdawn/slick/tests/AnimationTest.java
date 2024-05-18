/*     */ package org.newdawn.slick.tests;
/*     */ 
/*     */ import org.newdawn.slick.Animation;
/*     */ import org.newdawn.slick.AppGameContainer;
/*     */ import org.newdawn.slick.BasicGame;
/*     */ import org.newdawn.slick.Color;
/*     */ import org.newdawn.slick.Game;
/*     */ import org.newdawn.slick.GameContainer;
/*     */ import org.newdawn.slick.Graphics;
/*     */ import org.newdawn.slick.SlickException;
/*     */ import org.newdawn.slick.SpriteSheet;
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
/*     */ public class AnimationTest
/*     */   extends BasicGame
/*     */ {
/*     */   private Animation animation;
/*     */   private Animation limited;
/*     */   private Animation manual;
/*     */   private Animation pingPong;
/*     */   private GameContainer container;
/*  30 */   private int start = 5000;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AnimationTest() {
/*  36 */     super("Animation Test");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void init(GameContainer container) throws SlickException {
/*  43 */     this.container = container;
/*     */     
/*  45 */     SpriteSheet sheet = new SpriteSheet("testdata/homeranim.png", 36, 65);
/*  46 */     this.animation = new Animation(); int i;
/*  47 */     for (i = 0; i < 8; i++) {
/*  48 */       this.animation.addFrame(sheet.getSprite(i, 0), 150);
/*     */     }
/*  50 */     this.limited = new Animation();
/*  51 */     for (i = 0; i < 8; i++) {
/*  52 */       this.limited.addFrame(sheet.getSprite(i, 0), 150);
/*     */     }
/*  54 */     this.limited.stopAt(7);
/*  55 */     this.manual = new Animation(false);
/*  56 */     for (i = 0; i < 8; i++) {
/*  57 */       this.manual.addFrame(sheet.getSprite(i, 0), 150);
/*     */     }
/*  59 */     this.pingPong = new Animation(sheet, 0, 0, 7, 0, true, 150, true);
/*  60 */     this.pingPong.setPingPong(true);
/*  61 */     container.getGraphics().setBackground(new Color(0.4F, 0.6F, 0.6F));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void render(GameContainer container, Graphics g) {
/*  68 */     g.drawString("Space to restart() animation", 100.0F, 50.0F);
/*  69 */     g.drawString("Til Limited animation: " + this.start, 100.0F, 500.0F);
/*  70 */     g.drawString("Hold 1 to move the manually animated", 100.0F, 70.0F);
/*  71 */     g.drawString("PingPong Frame:" + this.pingPong.getFrame(), 600.0F, 70.0F);
/*     */     
/*  73 */     g.scale(-1.0F, 1.0F);
/*  74 */     this.animation.draw(-100.0F, 100.0F);
/*  75 */     this.animation.draw(-200.0F, 100.0F, 144.0F, 260.0F);
/*  76 */     if (this.start < 0) {
/*  77 */       this.limited.draw(-400.0F, 100.0F, 144.0F, 260.0F);
/*     */     }
/*  79 */     this.manual.draw(-600.0F, 100.0F, 144.0F, 260.0F);
/*  80 */     this.pingPong.draw(-700.0F, 100.0F, 72.0F, 130.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update(GameContainer container, int delta) {
/*  87 */     if (container.getInput().isKeyDown(2)) {
/*  88 */       this.manual.update(delta);
/*     */     }
/*  90 */     if (this.start >= 0) {
/*  91 */       this.start -= delta;
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
/* 102 */       AppGameContainer container = new AppGameContainer((Game)new AnimationTest());
/* 103 */       container.setDisplayMode(800, 600, false);
/* 104 */       container.start();
/* 105 */     } catch (SlickException e) {
/* 106 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void keyPressed(int key, char c) {
/* 114 */     if (key == 1) {
/* 115 */       this.container.exit();
/*     */     }
/* 117 */     if (key == 57)
/* 118 */       this.limited.restart(); 
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\tests\AnimationTest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */