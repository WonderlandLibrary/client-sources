/*     */ package org.newdawn.slick.tests;
/*     */ 
/*     */ import org.newdawn.slick.AppGameContainer;
/*     */ import org.newdawn.slick.BasicGame;
/*     */ import org.newdawn.slick.BigImage;
/*     */ import org.newdawn.slick.Color;
/*     */ import org.newdawn.slick.Game;
/*     */ import org.newdawn.slick.GameContainer;
/*     */ import org.newdawn.slick.Graphics;
/*     */ import org.newdawn.slick.Image;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BigImageTest
/*     */   extends BasicGame
/*     */ {
/*     */   private Image original;
/*     */   private Image image;
/*     */   private Image imageX;
/*     */   private Image imageY;
/*     */   private Image sub;
/*     */   private Image scaledSub;
/*     */   private float x;
/*     */   private float y;
/*  37 */   private float ang = 30.0F;
/*     */ 
/*     */   
/*     */   private SpriteSheet bigSheet;
/*     */ 
/*     */ 
/*     */   
/*     */   public BigImageTest() {
/*  45 */     super("Big Image Test");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void init(GameContainer container) throws SlickException {
/*  53 */     this.original = this.image = (Image)new BigImage("testdata/bigimage.tga", 2, 512);
/*  54 */     this.sub = this.image.getSubImage(210, 210, 200, 130);
/*  55 */     this.scaledSub = this.sub.getScaledCopy(2.0F);
/*  56 */     this.image = this.image.getScaledCopy(0.3F);
/*  57 */     this.imageX = this.image.getFlippedCopy(true, false);
/*  58 */     this.imageY = this.imageX.getFlippedCopy(true, true);
/*     */     
/*  60 */     this.bigSheet = new SpriteSheet(this.original, 16, 16);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void render(GameContainer container, Graphics g) {
/*  67 */     this.original.draw(0.0F, 0.0F, new Color(1.0F, 1.0F, 1.0F, 0.4F));
/*     */     
/*  69 */     this.image.draw(this.x, this.y);
/*  70 */     this.imageX.draw(this.x + 400.0F, this.y);
/*  71 */     this.imageY.draw(this.x, this.y + 300.0F);
/*  72 */     this.scaledSub.draw(this.x + 300.0F, this.y + 300.0F);
/*     */     
/*  74 */     this.bigSheet.getSprite(7, 5).draw(50.0F, 10.0F);
/*  75 */     g.setColor(Color.white);
/*  76 */     g.drawRect(50.0F, 10.0F, 64.0F, 64.0F);
/*  77 */     g.rotate(this.x + 400.0F, this.y + 165.0F, this.ang);
/*  78 */     g.drawImage(this.sub, this.x + 300.0F, this.y + 100.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void main(String[] argv) {
/*     */     try {
/*  88 */       AppGameContainer container = new AppGameContainer((Game)new BigImageTest());
/*  89 */       container.setDisplayMode(800, 600, false);
/*  90 */       container.start();
/*  91 */     } catch (SlickException e) {
/*  92 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update(GameContainer container, int delta) throws SlickException {
/* 100 */     this.ang += delta * 0.1F;
/*     */     
/* 102 */     if (container.getInput().isKeyDown(203)) {
/* 103 */       this.x -= delta * 0.1F;
/*     */     }
/* 105 */     if (container.getInput().isKeyDown(205)) {
/* 106 */       this.x += delta * 0.1F;
/*     */     }
/* 108 */     if (container.getInput().isKeyDown(200)) {
/* 109 */       this.y -= delta * 0.1F;
/*     */     }
/* 111 */     if (container.getInput().isKeyDown(208))
/* 112 */       this.y += delta * 0.1F; 
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\tests\BigImageTest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */