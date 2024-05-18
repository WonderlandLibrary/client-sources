/*    */ package org.newdawn.slick.tests;
/*    */ 
/*    */ import org.newdawn.slick.AppGameContainer;
/*    */ import org.newdawn.slick.BasicGame;
/*    */ import org.newdawn.slick.Color;
/*    */ import org.newdawn.slick.Game;
/*    */ import org.newdawn.slick.GameContainer;
/*    */ import org.newdawn.slick.Graphics;
/*    */ import org.newdawn.slick.Image;
/*    */ import org.newdawn.slick.SlickException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ImageReadTest
/*    */   extends BasicGame
/*    */ {
/*    */   private Image image;
/* 20 */   private Color[] read = new Color[6];
/*    */ 
/*    */   
/*    */   private Graphics g;
/*    */ 
/*    */ 
/*    */   
/*    */   public ImageReadTest() {
/* 28 */     super("Image Read Test");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void init(GameContainer container) throws SlickException {
/* 35 */     this.image = new Image("testdata/testcard.png");
/* 36 */     this.read[0] = this.image.getColor(0, 0);
/* 37 */     this.read[1] = this.image.getColor(30, 40);
/* 38 */     this.read[2] = this.image.getColor(55, 70);
/* 39 */     this.read[3] = this.image.getColor(80, 90);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void render(GameContainer container, Graphics g) {
/* 46 */     this.g = g;
/*    */     
/* 48 */     this.image.draw(100.0F, 100.0F);
/* 49 */     g.setColor(Color.white);
/* 50 */     g.drawString("Move mouse over test image", 200.0F, 20.0F);
/* 51 */     g.setColor(this.read[0]);
/* 52 */     g.drawString(this.read[0].toString(), 100.0F, 300.0F);
/* 53 */     g.setColor(this.read[1]);
/* 54 */     g.drawString(this.read[1].toString(), 150.0F, 320.0F);
/* 55 */     g.setColor(this.read[2]);
/* 56 */     g.drawString(this.read[2].toString(), 200.0F, 340.0F);
/* 57 */     g.setColor(this.read[3]);
/* 58 */     g.drawString(this.read[3].toString(), 250.0F, 360.0F);
/* 59 */     if (this.read[4] != null) {
/* 60 */       g.setColor(this.read[4]);
/* 61 */       g.drawString("On image: " + this.read[4].toString(), 100.0F, 250.0F);
/*    */     } 
/* 63 */     if (this.read[5] != null) {
/* 64 */       g.setColor(Color.white);
/* 65 */       g.drawString("On screen: " + this.read[5].toString(), 100.0F, 270.0F);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void update(GameContainer container, int delta) {
/* 73 */     int mx = container.getInput().getMouseX();
/* 74 */     int my = container.getInput().getMouseY();
/*    */     
/* 76 */     if (mx >= 100 && my >= 100 && mx < 200 && my < 200) {
/* 77 */       this.read[4] = this.image.getColor(mx - 100, my - 100);
/*    */     } else {
/* 79 */       this.read[4] = Color.black;
/*    */     } 
/*    */     
/* 82 */     this.read[5] = this.g.getPixel(mx, my);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void main(String[] argv) {
/*    */     try {
/* 92 */       AppGameContainer container = new AppGameContainer((Game)new ImageReadTest());
/* 93 */       container.setDisplayMode(800, 600, false);
/* 94 */       container.start();
/* 95 */     } catch (SlickException e) {
/* 96 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\tests\ImageReadTest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */