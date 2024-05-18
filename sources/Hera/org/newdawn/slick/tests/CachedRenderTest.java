/*    */ package org.newdawn.slick.tests;
/*    */ 
/*    */ import org.newdawn.slick.AppGameContainer;
/*    */ import org.newdawn.slick.BasicGame;
/*    */ import org.newdawn.slick.CachedRender;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CachedRenderTest
/*    */   extends BasicGame
/*    */ {
/*    */   private Runnable operations;
/*    */   private CachedRender cached;
/*    */   private boolean drawCached;
/*    */   
/*    */   public CachedRenderTest() {
/* 30 */     super("Cached Render Test");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void init(final GameContainer container) throws SlickException {
/* 37 */     this.operations = new Runnable() {
/*    */         public void run() {
/* 39 */           for (int i = 0; i < 100; i++) {
/* 40 */             int c = i + 100;
/* 41 */             container.getGraphics().setColor(new Color(c, c, c, c));
/* 42 */             container.getGraphics().drawOval((i * 5 + 50), (i * 3 + 50), 100.0F, 100.0F);
/*    */           } 
/*    */         }
/*    */       };
/*    */     
/* 47 */     this.cached = new CachedRender(this.operations);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void update(GameContainer container, int delta) throws SlickException {
/* 54 */     if (container.getInput().isKeyPressed(57)) {
/* 55 */       this.drawCached = !this.drawCached;
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void render(GameContainer container, Graphics g) throws SlickException {
/* 63 */     g.setColor(Color.white);
/* 64 */     g.drawString("Press space to toggle caching", 10.0F, 130.0F);
/* 65 */     if (this.drawCached) {
/* 66 */       g.drawString("Drawing from cache", 10.0F, 100.0F);
/* 67 */       this.cached.render();
/*    */     } else {
/* 69 */       g.drawString("Drawing direct", 10.0F, 100.0F);
/* 70 */       this.operations.run();
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
/* 81 */       AppGameContainer container = new AppGameContainer((Game)new CachedRenderTest());
/* 82 */       container.setDisplayMode(800, 600, false);
/* 83 */       container.start();
/* 84 */     } catch (SlickException e) {
/* 85 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\tests\CachedRenderTest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */