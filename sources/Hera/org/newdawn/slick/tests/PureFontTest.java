/*    */ package org.newdawn.slick.tests;
/*    */ 
/*    */ import org.newdawn.slick.AngelCodeFont;
/*    */ import org.newdawn.slick.AppGameContainer;
/*    */ import org.newdawn.slick.BasicGame;
/*    */ import org.newdawn.slick.Font;
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
/*    */ public class PureFontTest
/*    */   extends BasicGame
/*    */ {
/*    */   private Font font;
/*    */   private Image image;
/*    */   private static AppGameContainer container;
/*    */   
/*    */   public PureFontTest() {
/* 28 */     super("Hiero Font Test");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void init(GameContainer container) throws SlickException {
/* 35 */     this.image = new Image("testdata/sky.jpg");
/* 36 */     this.font = (Font)new AngelCodeFont("testdata/hiero.fnt", "testdata/hiero.png");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void render(GameContainer container, Graphics g) {
/* 43 */     this.image.draw(0.0F, 0.0F, 800.0F, 600.0F);
/* 44 */     this.font.drawString(100.0F, 32.0F, "On top of old smokey, all");
/* 45 */     this.font.drawString(100.0F, 80.0F, "covered with sand..");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void update(GameContainer container, int delta) throws SlickException {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void keyPressed(int key, char c) {
/* 58 */     if (key == 1) {
/* 59 */       System.exit(0);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void main(String[] argv) {
/*    */     try {
/* 73 */       container = new AppGameContainer((Game)new PureFontTest());
/* 74 */       container.setDisplayMode(800, 600, false);
/* 75 */       container.start();
/* 76 */     } catch (SlickException e) {
/* 77 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\tests\PureFontTest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */