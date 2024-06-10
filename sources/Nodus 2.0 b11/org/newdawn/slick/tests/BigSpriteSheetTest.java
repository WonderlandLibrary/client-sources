/*  1:   */ package org.newdawn.slick.tests;
/*  2:   */ 
/*  3:   */ import org.newdawn.slick.AppGameContainer;
/*  4:   */ import org.newdawn.slick.BasicGame;
/*  5:   */ import org.newdawn.slick.BigImage;
/*  6:   */ import org.newdawn.slick.Font;
/*  7:   */ import org.newdawn.slick.GameContainer;
/*  8:   */ import org.newdawn.slick.Graphics;
/*  9:   */ import org.newdawn.slick.Image;
/* 10:   */ import org.newdawn.slick.Input;
/* 11:   */ import org.newdawn.slick.SlickException;
/* 12:   */ import org.newdawn.slick.SpriteSheet;
/* 13:   */ 
/* 14:   */ public class BigSpriteSheetTest
/* 15:   */   extends BasicGame
/* 16:   */ {
/* 17:   */   private Image original;
/* 18:   */   private SpriteSheet bigSheet;
/* 19:24 */   private boolean oldMethod = true;
/* 20:   */   
/* 21:   */   public BigSpriteSheetTest()
/* 22:   */   {
/* 23:30 */     super("Big SpriteSheet Test");
/* 24:   */   }
/* 25:   */   
/* 26:   */   public void init(GameContainer container)
/* 27:   */     throws SlickException
/* 28:   */   {
/* 29:37 */     this.original = new BigImage("testdata/bigimage.tga", 2, 256);
/* 30:38 */     this.bigSheet = new SpriteSheet(this.original, 16, 16);
/* 31:   */   }
/* 32:   */   
/* 33:   */   public void render(GameContainer container, Graphics g)
/* 34:   */   {
/* 35:45 */     if (this.oldMethod)
/* 36:   */     {
/* 37:46 */       for (int x = 0; x < 43; x++) {
/* 38:47 */         for (int y = 0; y < 27; y++) {
/* 39:48 */           this.bigSheet.getSprite(x, y).draw(10 + x * 18, 50 + y * 18);
/* 40:   */         }
/* 41:   */       }
/* 42:   */     }
/* 43:   */     else
/* 44:   */     {
/* 45:52 */       this.bigSheet.startUse();
/* 46:53 */       for (int x = 0; x < 43; x++) {
/* 47:54 */         for (int y = 0; y < 27; y++) {
/* 48:55 */           this.bigSheet.renderInUse(10 + x * 18, 50 + y * 18, x, y);
/* 49:   */         }
/* 50:   */       }
/* 51:58 */       this.bigSheet.endUse();
/* 52:   */     }
/* 53:61 */     g.drawString("Press space to toggle rendering method", 10.0F, 30.0F);
/* 54:   */     
/* 55:63 */     container.getDefaultFont().drawString(10.0F, 100.0F, "TEST");
/* 56:   */   }
/* 57:   */   
/* 58:   */   public static void main(String[] argv)
/* 59:   */   {
/* 60:   */     try
/* 61:   */     {
/* 62:73 */       AppGameContainer container = new AppGameContainer(new BigSpriteSheetTest());
/* 63:74 */       container.setDisplayMode(800, 600, false);
/* 64:75 */       container.start();
/* 65:   */     }
/* 66:   */     catch (SlickException e)
/* 67:   */     {
/* 68:77 */       e.printStackTrace();
/* 69:   */     }
/* 70:   */   }
/* 71:   */   
/* 72:   */   public void update(GameContainer container, int delta)
/* 73:   */     throws SlickException
/* 74:   */   {
/* 75:85 */     if (container.getInput().isKeyPressed(57)) {
/* 76:86 */       this.oldMethod = (!this.oldMethod);
/* 77:   */     }
/* 78:   */   }
/* 79:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.tests.BigSpriteSheetTest
 * JD-Core Version:    0.7.0.1
 */