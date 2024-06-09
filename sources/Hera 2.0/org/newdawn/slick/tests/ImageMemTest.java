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
/*    */ public class ImageMemTest
/*    */   extends BasicGame
/*    */ {
/*    */   public ImageMemTest() {
/* 21 */     super("Image Memory Test");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void init(GameContainer container) throws SlickException {
/*    */     try {
/* 29 */       Image img = new Image(2400, 2400);
/* 30 */       img.getGraphics();
/* 31 */       img.destroy();
/* 32 */       img = new Image(2400, 2400);
/* 33 */       img.getGraphics();
/* 34 */     } catch (Exception ex) {
/* 35 */       ex.printStackTrace();
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void render(GameContainer container, Graphics g) {}
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
/*    */   
/*    */   public static void main(String[] argv) {
/*    */     try {
/* 58 */       AppGameContainer container = new AppGameContainer((Game)new ImageMemTest());
/* 59 */       container.setDisplayMode(800, 600, false);
/* 60 */       container.start();
/* 61 */     } catch (SlickException e) {
/* 62 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\tests\ImageMemTest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */