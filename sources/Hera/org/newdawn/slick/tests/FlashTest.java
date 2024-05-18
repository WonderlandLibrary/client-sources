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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FlashTest
/*    */   extends BasicGame
/*    */ {
/*    */   private Image image;
/*    */   private boolean flash;
/*    */   private GameContainer container;
/*    */   
/*    */   public FlashTest() {
/* 29 */     super("Flash Test");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void init(GameContainer container) throws SlickException {
/* 36 */     this.container = container;
/*    */     
/* 38 */     this.image = new Image("testdata/logo.tga");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void render(GameContainer container, Graphics g) {
/* 45 */     g.drawString("Press space to toggle", 10.0F, 50.0F);
/* 46 */     if (this.flash) {
/* 47 */       this.image.draw(100.0F, 100.0F);
/*    */     } else {
/* 49 */       this.image.drawFlash(100.0F, 100.0F, this.image.getWidth(), this.image.getHeight(), new Color(1.0F, 0.0F, 1.0F, 1.0F));
/*    */     } 
/*    */   }
/*    */ 
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
/* 66 */       AppGameContainer container = new AppGameContainer((Game)new FlashTest());
/* 67 */       container.setDisplayMode(800, 600, false);
/* 68 */       container.start();
/* 69 */     } catch (SlickException e) {
/* 70 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void keyPressed(int key, char c) {
/* 78 */     if (key == 57) {
/* 79 */       this.flash = !this.flash;
/*    */     }
/* 81 */     if (key == 1)
/* 82 */       this.container.exit(); 
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\tests\FlashTest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */