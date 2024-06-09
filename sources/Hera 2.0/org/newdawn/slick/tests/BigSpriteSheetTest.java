/*    */ package org.newdawn.slick.tests;
/*    */ 
/*    */ import org.newdawn.slick.AppGameContainer;
/*    */ import org.newdawn.slick.BasicGame;
/*    */ import org.newdawn.slick.BigImage;
/*    */ import org.newdawn.slick.Game;
/*    */ import org.newdawn.slick.GameContainer;
/*    */ import org.newdawn.slick.Graphics;
/*    */ import org.newdawn.slick.Image;
/*    */ import org.newdawn.slick.SlickException;
/*    */ import org.newdawn.slick.SpriteSheet;
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
/*    */ public class BigSpriteSheetTest
/*    */   extends BasicGame
/*    */ {
/*    */   private Image original;
/*    */   private SpriteSheet bigSheet;
/*    */   private boolean oldMethod = true;
/*    */   
/*    */   public BigSpriteSheetTest() {
/* 30 */     super("Big SpriteSheet Test");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void init(GameContainer container) throws SlickException {
/* 37 */     this.original = (Image)new BigImage("testdata/bigimage.tga", 2, 256);
/* 38 */     this.bigSheet = new SpriteSheet(this.original, 16, 16);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void render(GameContainer container, Graphics g) {
/* 45 */     if (this.oldMethod) {
/* 46 */       for (int x = 0; x < 43; x++) {
/* 47 */         for (int y = 0; y < 27; y++) {
/* 48 */           this.bigSheet.getSprite(x, y).draw((10 + x * 18), (50 + y * 18));
/*    */         }
/*    */       } 
/*    */     } else {
/* 52 */       this.bigSheet.startUse();
/* 53 */       for (int x = 0; x < 43; x++) {
/* 54 */         for (int y = 0; y < 27; y++) {
/* 55 */           this.bigSheet.renderInUse(10 + x * 18, 50 + y * 18, x, y);
/*    */         }
/*    */       } 
/* 58 */       this.bigSheet.endUse();
/*    */     } 
/*    */     
/* 61 */     g.drawString("Press space to toggle rendering method", 10.0F, 30.0F);
/*    */     
/* 63 */     container.getDefaultFont().drawString(10.0F, 100.0F, "TEST");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void main(String[] argv) {
/*    */     try {
/* 73 */       AppGameContainer container = new AppGameContainer((Game)new BigSpriteSheetTest());
/* 74 */       container.setDisplayMode(800, 600, false);
/* 75 */       container.start();
/* 76 */     } catch (SlickException e) {
/* 77 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void update(GameContainer container, int delta) throws SlickException {
/* 85 */     if (container.getInput().isKeyPressed(57))
/* 86 */       this.oldMethod = !this.oldMethod; 
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\tests\BigSpriteSheetTest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */