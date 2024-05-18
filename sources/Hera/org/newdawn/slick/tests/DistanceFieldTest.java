/*     */ package org.newdawn.slick.tests;
/*     */ 
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import org.newdawn.slick.AngelCodeFont;
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
/*     */ 
/*     */ 
/*     */ public class DistanceFieldTest
/*     */   extends BasicGame
/*     */ {
/*     */   private AngelCodeFont font;
/*     */   
/*     */   public DistanceFieldTest() {
/*  26 */     super("DistanceMapTest Test");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void init(GameContainer container) throws SlickException {
/*  33 */     this.font = new AngelCodeFont("testdata/distance.fnt", "testdata/distance-dis.png");
/*  34 */     container.getGraphics().setBackground(Color.black);
/*     */   }
/*     */ 
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
/*     */   
/*     */   public void render(GameContainer container, Graphics g) throws SlickException {
/*  49 */     String text = "abc";
/*  50 */     this.font.drawString(610.0F, 100.0F, text);
/*     */     
/*  52 */     GL11.glDisable(3042);
/*  53 */     GL11.glEnable(3008);
/*  54 */     GL11.glAlphaFunc(518, 0.5F);
/*  55 */     this.font.drawString(610.0F, 150.0F, text);
/*  56 */     GL11.glDisable(3008);
/*  57 */     GL11.glEnable(3042);
/*     */     
/*  59 */     g.translate(-50.0F, -130.0F);
/*  60 */     g.scale(10.0F, 10.0F);
/*  61 */     this.font.drawString(0.0F, 0.0F, text);
/*     */     
/*  63 */     GL11.glDisable(3042);
/*  64 */     GL11.glEnable(3008);
/*  65 */     GL11.glAlphaFunc(518, 0.5F);
/*  66 */     this.font.drawString(0.0F, 26.0F, text);
/*  67 */     GL11.glDisable(3008);
/*  68 */     GL11.glEnable(3042);
/*     */     
/*  70 */     g.resetTransform();
/*  71 */     g.setColor(Color.lightGray);
/*  72 */     g.drawString("Original Size on Sheet", 620.0F, 210.0F);
/*  73 */     g.drawString("10x Scale Up", 40.0F, 575.0F);
/*     */     
/*  75 */     g.setColor(Color.darkGray);
/*  76 */     g.drawRect(40.0F, 40.0F, 560.0F, 530.0F);
/*  77 */     g.drawRect(610.0F, 105.0F, 150.0F, 100.0F);
/*     */     
/*  79 */     g.setColor(Color.white);
/*  80 */     g.drawString("512x512 Font Sheet", 620.0F, 300.0F);
/*  81 */     g.drawString("NEHE Charset", 620.0F, 320.0F);
/*  82 */     g.drawString("4096x4096 (8x) Source Image", 620.0F, 340.0F);
/*  83 */     g.drawString("ScanSize = 20", 620.0F, 360.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void keyPressed(int key, char c) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void main(String[] argv) {
/*     */     try {
/*  99 */       AppGameContainer container = new AppGameContainer((Game)new DistanceFieldTest());
/* 100 */       container.setDisplayMode(800, 600, false);
/* 101 */       container.start();
/* 102 */     } catch (SlickException e) {
/* 103 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\tests\DistanceFieldTest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */