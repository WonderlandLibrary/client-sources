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
/*    */ public class TransparentColorTest
/*    */   extends BasicGame
/*    */ {
/*    */   private Image image;
/*    */   private Image timage;
/*    */   
/*    */   public TransparentColorTest() {
/* 26 */     super("Transparent Color Test");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void init(GameContainer container) throws SlickException {
/* 33 */     this.image = new Image("testdata/transtest.png");
/* 34 */     this.timage = new Image("testdata/transtest.png", new Color(94, 66, 41, 255));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void render(GameContainer container, Graphics g) {
/* 41 */     g.setBackground(Color.red);
/* 42 */     this.image.draw(10.0F, 10.0F);
/* 43 */     this.timage.draw(10.0F, 310.0F);
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
/* 59 */       AppGameContainer container = new AppGameContainer((Game)new TransparentColorTest());
/* 60 */       container.setDisplayMode(800, 600, false);
/* 61 */       container.start();
/* 62 */     } catch (SlickException e) {
/* 63 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */   
/*    */   public void keyPressed(int key, char c) {}
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\tests\TransparentColorTest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */