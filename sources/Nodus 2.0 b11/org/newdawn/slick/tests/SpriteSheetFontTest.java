/*  1:   */ package org.newdawn.slick.tests;
/*  2:   */ 
/*  3:   */ import org.newdawn.slick.AppGameContainer;
/*  4:   */ import org.newdawn.slick.BasicGame;
/*  5:   */ import org.newdawn.slick.Color;
/*  6:   */ import org.newdawn.slick.Font;
/*  7:   */ import org.newdawn.slick.GameContainer;
/*  8:   */ import org.newdawn.slick.Graphics;
/*  9:   */ import org.newdawn.slick.SlickException;
/* 10:   */ import org.newdawn.slick.SpriteSheet;
/* 11:   */ import org.newdawn.slick.SpriteSheetFont;
/* 12:   */ import org.newdawn.slick.util.Log;
/* 13:   */ 
/* 14:   */ public class SpriteSheetFontTest
/* 15:   */   extends BasicGame
/* 16:   */ {
/* 17:   */   private Font font;
/* 18:   */   private static AppGameContainer container;
/* 19:   */   
/* 20:   */   public SpriteSheetFontTest()
/* 21:   */   {
/* 22:21 */     super("SpriteSheetFont Test");
/* 23:   */   }
/* 24:   */   
/* 25:   */   public void init(GameContainer container)
/* 26:   */     throws SlickException
/* 27:   */   {
/* 28:28 */     SpriteSheet sheet = new SpriteSheet("testdata/spriteSheetFont.png", 32, 32);
/* 29:29 */     this.font = new SpriteSheetFont(sheet, ' ');
/* 30:   */   }
/* 31:   */   
/* 32:   */   public void render(GameContainer container, Graphics g)
/* 33:   */   {
/* 34:36 */     g.setBackground(Color.gray);
/* 35:37 */     this.font.drawString(80.0F, 5.0F, "A FONT EXAMPLE", Color.red);
/* 36:38 */     this.font.drawString(100.0F, 50.0F, "A MORE COMPLETE LINE");
/* 37:   */   }
/* 38:   */   
/* 39:   */   public void update(GameContainer container, int delta)
/* 40:   */     throws SlickException
/* 41:   */   {}
/* 42:   */   
/* 43:   */   public void keyPressed(int key, char c)
/* 44:   */   {
/* 45:51 */     if (key == 1) {
/* 46:52 */       System.exit(0);
/* 47:   */     }
/* 48:54 */     if (key == 57) {
/* 49:   */       try
/* 50:   */       {
/* 51:56 */         container.setDisplayMode(640, 480, false);
/* 52:   */       }
/* 53:   */       catch (SlickException e)
/* 54:   */       {
/* 55:58 */         Log.error(e);
/* 56:   */       }
/* 57:   */     }
/* 58:   */   }
/* 59:   */   
/* 60:   */   public static void main(String[] argv)
/* 61:   */   {
/* 62:   */     try
/* 63:   */     {
/* 64:75 */       container = new AppGameContainer(new SpriteSheetFontTest());
/* 65:76 */       container.setDisplayMode(800, 600, false);
/* 66:77 */       container.start();
/* 67:   */     }
/* 68:   */     catch (SlickException e)
/* 69:   */     {
/* 70:79 */       e.printStackTrace();
/* 71:   */     }
/* 72:   */   }
/* 73:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.tests.SpriteSheetFontTest
 * JD-Core Version:    0.7.0.1
 */