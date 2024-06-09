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
/*    */ public class CopyAreaAlphaTest
/*    */   extends BasicGame
/*    */ {
/*    */   private Image textureMap;
/*    */   private Image copy;
/*    */   
/*    */   public CopyAreaAlphaTest() {
/* 26 */     super("CopyArea Alpha Test");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void init(GameContainer container) throws SlickException {
/* 33 */     this.textureMap = new Image("testdata/grass.png");
/* 34 */     container.getGraphics().setBackground(Color.black);
/* 35 */     this.copy = new Image(100, 100);
/*    */   }
/*    */ 
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
/*    */   
/*    */   public void render(GameContainer container, Graphics g) throws SlickException {
/* 50 */     g.clearAlphaMap();
/* 51 */     g.setDrawMode(Graphics.MODE_NORMAL);
/* 52 */     g.setColor(Color.white);
/* 53 */     g.fillOval(100.0F, 100.0F, 150.0F, 150.0F);
/* 54 */     this.textureMap.draw(10.0F, 50.0F);
/*    */     
/* 56 */     g.copyArea(this.copy, 100, 100);
/* 57 */     g.setColor(Color.red);
/* 58 */     g.fillRect(300.0F, 100.0F, 200.0F, 200.0F);
/* 59 */     this.copy.draw(350.0F, 150.0F);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void keyPressed(int key, char c) {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void main(String[] argv) {
/*    */     try {
/* 75 */       AppGameContainer container = new AppGameContainer((Game)new CopyAreaAlphaTest());
/* 76 */       container.setDisplayMode(800, 600, false);
/* 77 */       container.start();
/* 78 */     } catch (SlickException e) {
/* 79 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\tests\CopyAreaAlphaTest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */