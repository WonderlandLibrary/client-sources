/*     */ package org.newdawn.slick.tests;
/*     */ 
/*     */ import org.newdawn.slick.AngelCodeFont;
/*     */ import org.newdawn.slick.AppGameContainer;
/*     */ import org.newdawn.slick.BasicGame;
/*     */ import org.newdawn.slick.Color;
/*     */ import org.newdawn.slick.Game;
/*     */ import org.newdawn.slick.GameContainer;
/*     */ import org.newdawn.slick.Graphics;
/*     */ import org.newdawn.slick.Image;
/*     */ import org.newdawn.slick.SlickException;
/*     */ import org.newdawn.slick.util.Log;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FontTest
/*     */   extends BasicGame
/*     */ {
/*     */   private AngelCodeFont font;
/*     */   private AngelCodeFont font2;
/*     */   private Image image;
/*     */   private static AppGameContainer container;
/*     */   
/*     */   public FontTest() {
/*  31 */     super("Font Test");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void init(GameContainer container) throws SlickException {
/*  38 */     this.font = new AngelCodeFont("testdata/demo2.fnt", "testdata/demo2_00.tga");
/*  39 */     this.font2 = new AngelCodeFont("testdata/hiero.fnt", "testdata/hiero.png");
/*  40 */     this.image = new Image("testdata/demo2_00.tga", false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void render(GameContainer container, Graphics g) {
/*  47 */     this.font.drawString(80.0F, 5.0F, "A Font Example", Color.red);
/*  48 */     this.font.drawString(100.0F, 32.0F, "We - AV - Here is a more complete line that hopefully");
/*  49 */     this.font.drawString(100.0F, (36 + this.font.getHeight("We Here is a more complete line that hopefully")), "will show some kerning.");
/*     */ 
/*     */     
/*  52 */     this.font2.drawString(80.0F, 85.0F, "A Font Example", Color.red);
/*  53 */     this.font2.drawString(100.0F, 132.0F, "We - AV - Here is a more complete line that hopefully");
/*  54 */     this.font2.drawString(100.0F, (136 + this.font2.getHeight("We - Here is a more complete line that hopefully")), "will show some kerning.");
/*     */     
/*  56 */     this.image.draw(100.0F, 400.0F);
/*     */     
/*  58 */     String testStr = "Testing Font";
/*  59 */     this.font2.drawString(100.0F, 300.0F, testStr);
/*  60 */     g.setColor(Color.white);
/*  61 */     g.drawRect(100.0F, (300 + this.font2.getYOffset(testStr)), this.font2.getWidth(testStr), (this.font2.getHeight(testStr) - this.font2.getYOffset(testStr)));
/*  62 */     this.font.drawString(500.0F, 300.0F, testStr);
/*  63 */     g.setColor(Color.white);
/*  64 */     g.drawRect(500.0F, (300 + this.font.getYOffset(testStr)), this.font.getWidth(testStr), (this.font.getHeight(testStr) - this.font.getYOffset(testStr)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update(GameContainer container, int delta) throws SlickException {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void keyPressed(int key, char c) {
/*  77 */     if (key == 1) {
/*  78 */       System.exit(0);
/*     */     }
/*  80 */     if (key == 57) {
/*     */       try {
/*  82 */         container.setDisplayMode(640, 480, false);
/*  83 */       } catch (SlickException e) {
/*  84 */         Log.error((Throwable)e);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void main(String[] argv) {
/*     */     try {
/*  99 */       container = new AppGameContainer((Game)new FontTest());
/* 100 */       container.setDisplayMode(800, 600, false);
/* 101 */       container.start();
/* 102 */     } catch (SlickException e) {
/* 103 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\tests\FontTest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */