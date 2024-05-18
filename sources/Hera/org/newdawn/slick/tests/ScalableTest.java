/*    */ package org.newdawn.slick.tests;
/*    */ 
/*    */ import org.newdawn.slick.AppGameContainer;
/*    */ import org.newdawn.slick.BasicGame;
/*    */ import org.newdawn.slick.Color;
/*    */ import org.newdawn.slick.Game;
/*    */ import org.newdawn.slick.GameContainer;
/*    */ import org.newdawn.slick.Graphics;
/*    */ import org.newdawn.slick.ScalableGame;
/*    */ import org.newdawn.slick.SlickException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ScalableTest
/*    */   extends BasicGame
/*    */ {
/*    */   public ScalableTest() {
/* 22 */     super("Scalable Test For Widescreen");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void init(GameContainer container) throws SlickException {}
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
/*    */   public void render(GameContainer container, Graphics g) throws SlickException {
/* 41 */     g.setColor(new Color(0.4F, 0.6F, 0.8F));
/* 42 */     g.fillRect(0.0F, 0.0F, 1024.0F, 568.0F);
/* 43 */     g.setColor(Color.white);
/* 44 */     g.drawRect(5.0F, 5.0F, 1014.0F, 558.0F);
/*    */     
/* 46 */     g.setColor(Color.white);
/* 47 */     g.drawString(container.getInput().getMouseX() + "," + container.getInput().getMouseY(), 10.0F, 400.0F);
/* 48 */     g.setColor(Color.red);
/* 49 */     g.fillOval((container.getInput().getMouseX() - 10), (container.getInput().getMouseY() - 10), 20.0F, 20.0F);
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
/*    */ 
/*    */ 
/*    */   
/*    */   public static void main(String[] argv) {
/*    */     try {
/* 85 */       ScalableGame game = new ScalableGame((Game)new ScalableTest(), 1024, 568, true)
/*    */         {
/*    */           protected void renderOverlay(GameContainer container, Graphics g) {
/* 88 */             g.setColor(Color.white);
/* 89 */             g.drawString("Outside The Game", 350.0F, 10.0F);
/* 90 */             g.drawString(container.getInput().getMouseX() + "," + container.getInput().getMouseY(), 400.0F, 20.0F);
/*    */           }
/*    */         };
/*    */ 
/*    */       
/* 95 */       AppGameContainer container = new AppGameContainer((Game)game);
/* 96 */       container.setDisplayMode(800, 600, false);
/* 97 */       container.start();
/* 98 */     } catch (SlickException e) {
/* 99 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\tests\ScalableTest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */