/*    */ package org.newdawn.slick.tests;
/*    */ 
/*    */ import org.newdawn.slick.AppGameContainer;
/*    */ import org.newdawn.slick.BasicGame;
/*    */ import org.newdawn.slick.Game;
/*    */ import org.newdawn.slick.GameContainer;
/*    */ import org.newdawn.slick.Graphics;
/*    */ import org.newdawn.slick.Image;
/*    */ import org.newdawn.slick.ImageBuffer;
/*    */ import org.newdawn.slick.SlickException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ImageBufferTest
/*    */   extends BasicGame
/*    */ {
/*    */   private Image image;
/*    */   
/*    */   public ImageBufferTest() {
/* 25 */     super("Image Buffer Test");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void init(GameContainer container) throws SlickException {
/* 32 */     ImageBuffer buffer = new ImageBuffer(320, 200);
/* 33 */     for (int x = 0; x < 320; x++) {
/* 34 */       for (int y = 0; y < 200; y++) {
/* 35 */         if (y == 20) {
/* 36 */           buffer.setRGBA(x, y, 255, 255, 255, 255);
/*    */         } else {
/* 38 */           buffer.setRGBA(x, y, x, y, 0, 255);
/*    */         } 
/*    */       } 
/*    */     } 
/* 42 */     this.image = buffer.getImage();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void render(GameContainer container, Graphics g) {
/* 49 */     this.image.draw(50.0F, 50.0F);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void update(GameContainer container, int delta) {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void keyPressed(int key, char c) {
/* 62 */     if (key == 1) {
/* 63 */       System.exit(0);
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
/* 74 */       AppGameContainer container = new AppGameContainer((Game)new ImageBufferTest());
/* 75 */       container.setDisplayMode(800, 600, false);
/* 76 */       container.start();
/* 77 */     } catch (SlickException e) {
/* 78 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\tests\ImageBufferTest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */