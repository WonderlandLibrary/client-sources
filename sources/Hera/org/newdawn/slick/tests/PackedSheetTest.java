/*     */ package org.newdawn.slick.tests;
/*     */ 
/*     */ import org.newdawn.slick.Animation;
/*     */ import org.newdawn.slick.AppGameContainer;
/*     */ import org.newdawn.slick.BasicGame;
/*     */ import org.newdawn.slick.Game;
/*     */ import org.newdawn.slick.GameContainer;
/*     */ import org.newdawn.slick.Graphics;
/*     */ import org.newdawn.slick.Image;
/*     */ import org.newdawn.slick.PackedSpriteSheet;
/*     */ import org.newdawn.slick.SlickException;
/*     */ import org.newdawn.slick.SpriteSheet;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PackedSheetTest
/*     */   extends BasicGame
/*     */ {
/*     */   private PackedSpriteSheet sheet;
/*     */   private GameContainer container;
/*  25 */   private float r = -500.0F;
/*     */ 
/*     */   
/*     */   private Image rocket;
/*     */ 
/*     */   
/*     */   private Animation runner;
/*     */   
/*     */   private float ang;
/*     */ 
/*     */   
/*     */   public PackedSheetTest() {
/*  37 */     super("Packed Sprite Sheet Test");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void init(GameContainer container) throws SlickException {
/*  44 */     this.container = container;
/*     */     
/*  46 */     this.sheet = new PackedSpriteSheet("testdata/testpack.def", 2);
/*  47 */     this.rocket = this.sheet.getSprite("rocket");
/*     */     
/*  49 */     SpriteSheet anim = this.sheet.getSpriteSheet("runner");
/*  50 */     this.runner = new Animation();
/*     */     
/*  52 */     for (int y = 0; y < 2; y++) {
/*  53 */       for (int x = 0; x < 6; x++) {
/*  54 */         this.runner.addFrame(anim.getSprite(x, y), 50);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void render(GameContainer container, Graphics g) {
/*  63 */     this.rocket.draw((int)this.r, 100.0F);
/*  64 */     this.runner.draw(250.0F, 250.0F);
/*  65 */     g.scale(1.2F, 1.2F);
/*  66 */     this.runner.draw(250.0F, 250.0F);
/*  67 */     g.scale(1.2F, 1.2F);
/*  68 */     this.runner.draw(250.0F, 250.0F);
/*  69 */     g.resetTransform();
/*     */     
/*  71 */     g.rotate(670.0F, 470.0F, this.ang);
/*  72 */     this.sheet.getSprite("floppy").draw(600.0F, 400.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update(GameContainer container, int delta) {
/*  79 */     this.r += delta * 0.4F;
/*  80 */     if (this.r > 900.0F) {
/*  81 */       this.r = -500.0F;
/*     */     }
/*     */     
/*  84 */     this.ang += delta * 0.1F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void main(String[] argv) {
/*     */     try {
/*  94 */       AppGameContainer container = new AppGameContainer((Game)new PackedSheetTest());
/*  95 */       container.setDisplayMode(800, 600, false);
/*  96 */       container.start();
/*  97 */     } catch (SlickException e) {
/*  98 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void keyPressed(int key, char c) {
/* 106 */     if (key == 1)
/* 107 */       this.container.exit(); 
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\tests\PackedSheetTest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */