/*    */ package org.newdawn.slick.tests;
/*    */ 
/*    */ import org.newdawn.slick.AppGameContainer;
/*    */ import org.newdawn.slick.BasicGame;
/*    */ import org.newdawn.slick.Color;
/*    */ import org.newdawn.slick.Game;
/*    */ import org.newdawn.slick.GameContainer;
/*    */ import org.newdawn.slick.Graphics;
/*    */ import org.newdawn.slick.SlickException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AntiAliasTest
/*    */   extends BasicGame
/*    */ {
/*    */   public AntiAliasTest() {
/* 21 */     super("AntiAlias Test");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void init(GameContainer container) throws SlickException {
/* 28 */     container.getGraphics().setBackground(Color.green);
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
/*    */   public void render(GameContainer container, Graphics g) throws SlickException {
/* 41 */     g.setAntiAlias(true);
/* 42 */     g.setColor(Color.red);
/* 43 */     g.drawOval(100.0F, 100.0F, 100.0F, 100.0F);
/* 44 */     g.fillOval(300.0F, 100.0F, 100.0F, 100.0F);
/* 45 */     g.setAntiAlias(false);
/* 46 */     g.setColor(Color.red);
/* 47 */     g.drawOval(100.0F, 300.0F, 100.0F, 100.0F);
/* 48 */     g.fillOval(300.0F, 300.0F, 100.0F, 100.0F);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void main(String[] argv) {
/*    */     try {
/* 58 */       AppGameContainer container = new AppGameContainer((Game)new AntiAliasTest());
/* 59 */       container.setDisplayMode(800, 600, false);
/* 60 */       container.start();
/* 61 */     } catch (SlickException e) {
/* 62 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\tests\AntiAliasTest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */