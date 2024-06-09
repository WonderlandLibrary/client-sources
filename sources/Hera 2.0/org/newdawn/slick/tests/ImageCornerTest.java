/*    */ package org.newdawn.slick.tests;
/*    */ 
/*    */ import org.newdawn.slick.AppGameContainer;
/*    */ import org.newdawn.slick.BasicGame;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ImageCornerTest
/*    */   extends BasicGame
/*    */ {
/*    */   private Image image;
/*    */   private Image[] images;
/*    */   private int width;
/*    */   private int height;
/*    */   
/*    */   public ImageCornerTest() {
/* 29 */     super("Image Corner Test");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void init(GameContainer container) throws SlickException {
/* 36 */     this.image = new Image("testdata/logo.png");
/*    */     
/* 38 */     this.width = this.image.getWidth() / 3;
/* 39 */     this.height = this.image.getHeight() / 3;
/*    */     
/* 41 */     this.images = new Image[] { this.image.getSubImage(0, 0, this.width, this.height), this.image.getSubImage(this.width, 0, this.width, this.height), this.image.getSubImage(this.width * 2, 0, this.width, this.height), this.image.getSubImage(0, this.height, this.width, this.height), this.image.getSubImage(this.width, this.height, this.width, this.height), this.image.getSubImage(this.width * 2, this.height, this.width, this.height), this.image.getSubImage(0, this.height * 2, this.width, this.height), this.image.getSubImage(this.width, this.height * 2, this.width, this.height), this.image.getSubImage(this.width * 2, this.height * 2, this.width, this.height) };
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 47 */     this.images[0].setColor(2, 0.0F, 1.0F, 1.0F, 1.0F);
/* 48 */     this.images[1].setColor(3, 0.0F, 1.0F, 1.0F, 1.0F);
/* 49 */     this.images[1].setColor(2, 0.0F, 1.0F, 1.0F, 1.0F);
/* 50 */     this.images[2].setColor(3, 0.0F, 1.0F, 1.0F, 1.0F);
/* 51 */     this.images[3].setColor(1, 0.0F, 1.0F, 1.0F, 1.0F);
/* 52 */     this.images[3].setColor(2, 0.0F, 1.0F, 1.0F, 1.0F);
/*    */     
/* 54 */     this.images[4].setColor(1, 0.0F, 1.0F, 1.0F, 1.0F);
/* 55 */     this.images[4].setColor(0, 0.0F, 1.0F, 1.0F, 1.0F);
/* 56 */     this.images[4].setColor(3, 0.0F, 1.0F, 1.0F, 1.0F);
/* 57 */     this.images[4].setColor(2, 0.0F, 1.0F, 1.0F, 1.0F);
/* 58 */     this.images[5].setColor(0, 0.0F, 1.0F, 1.0F, 1.0F);
/* 59 */     this.images[5].setColor(3, 0.0F, 1.0F, 1.0F, 1.0F);
/*    */     
/* 61 */     this.images[6].setColor(1, 0.0F, 1.0F, 1.0F, 1.0F);
/* 62 */     this.images[7].setColor(1, 0.0F, 1.0F, 1.0F, 1.0F);
/* 63 */     this.images[7].setColor(0, 0.0F, 1.0F, 1.0F, 1.0F);
/* 64 */     this.images[8].setColor(0, 0.0F, 1.0F, 1.0F, 1.0F);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void render(GameContainer container, Graphics g) {
/* 71 */     for (int x = 0; x < 3; x++) {
/* 72 */       for (int y = 0; y < 3; y++) {
/* 73 */         this.images[x + y * 3].draw((100 + x * this.width), (100 + y * this.height));
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void main(String[] argv) {
/* 84 */     boolean sharedContextTest = false;
/*    */     
/*    */     try {
/* 87 */       AppGameContainer container = new AppGameContainer((Game)new ImageCornerTest());
/* 88 */       container.setDisplayMode(800, 600, false);
/* 89 */       container.start();
/* 90 */     } catch (SlickException e) {
/* 91 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */   
/*    */   public void update(GameContainer container, int delta) throws SlickException {}
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\tests\ImageCornerTest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */