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
/*    */ public class AlphaMapTest
/*    */   extends BasicGame
/*    */ {
/*    */   private Image alphaMap;
/*    */   private Image textureMap;
/*    */   
/*    */   public AlphaMapTest() {
/* 26 */     super("AlphaMap Test");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void init(GameContainer container) throws SlickException {
/* 33 */     this.alphaMap = new Image("testdata/alphamap.png");
/* 34 */     this.textureMap = new Image("testdata/grass.png");
/* 35 */     container.getGraphics().setBackground(Color.black);
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
/* 52 */     this.textureMap.draw(10.0F, 50.0F);
/* 53 */     g.setColor(Color.red);
/* 54 */     g.fillRect(290.0F, 40.0F, 200.0F, 200.0F);
/* 55 */     g.setColor(Color.white);
/*    */     
/* 57 */     g.setDrawMode(Graphics.MODE_ALPHA_MAP);
/* 58 */     this.alphaMap.draw(300.0F, 50.0F);
/* 59 */     g.setDrawMode(Graphics.MODE_ALPHA_BLEND);
/* 60 */     this.textureMap.draw(300.0F, 50.0F);
/* 61 */     g.setDrawMode(Graphics.MODE_NORMAL);
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
/* 77 */       AppGameContainer container = new AppGameContainer((Game)new AlphaMapTest());
/* 78 */       container.setDisplayMode(800, 600, false);
/* 79 */       container.start();
/* 80 */     } catch (SlickException e) {
/* 81 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\tests\AlphaMapTest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */