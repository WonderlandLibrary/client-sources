/*    */ package org.newdawn.slick.tests;
/*    */ 
/*    */ import org.newdawn.slick.AppGameContainer;
/*    */ import org.newdawn.slick.BasicGame;
/*    */ import org.newdawn.slick.Game;
/*    */ import org.newdawn.slick.GameContainer;
/*    */ import org.newdawn.slick.Graphics;
/*    */ import org.newdawn.slick.Input;
/*    */ import org.newdawn.slick.SlickException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class KeyRepeatTest
/*    */   extends BasicGame
/*    */ {
/*    */   private int count;
/*    */   private Input input;
/*    */   
/*    */   public KeyRepeatTest() {
/* 25 */     super("KeyRepeatTest");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void init(GameContainer container) throws SlickException {
/* 32 */     this.input = container.getInput();
/* 33 */     this.input.enableKeyRepeat(300, 100);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void render(GameContainer container, Graphics g) {
/* 40 */     g.drawString("Key Press Count: " + this.count, 100.0F, 100.0F);
/* 41 */     g.drawString("Press Space to Toggle Key Repeat", 100.0F, 150.0F);
/* 42 */     g.drawString("Key Repeat Enabled: " + this.input.isKeyRepeatEnabled(), 100.0F, 200.0F);
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
/* 58 */       AppGameContainer container = new AppGameContainer((Game)new KeyRepeatTest());
/* 59 */       container.setDisplayMode(800, 600, false);
/* 60 */       container.start();
/* 61 */     } catch (SlickException e) {
/* 62 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void keyPressed(int key, char c) {
/* 70 */     this.count++;
/* 71 */     if (key == 57)
/* 72 */       if (this.input.isKeyRepeatEnabled()) {
/* 73 */         this.input.disableKeyRepeat();
/*    */       } else {
/* 75 */         this.input.enableKeyRepeat(300, 100);
/*    */       }  
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\tests\KeyRepeatTest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */