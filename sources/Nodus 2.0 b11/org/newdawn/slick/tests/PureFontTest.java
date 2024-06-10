/*  1:   */ package org.newdawn.slick.tests;
/*  2:   */ 
/*  3:   */ import org.newdawn.slick.AngelCodeFont;
/*  4:   */ import org.newdawn.slick.AppGameContainer;
/*  5:   */ import org.newdawn.slick.BasicGame;
/*  6:   */ import org.newdawn.slick.Font;
/*  7:   */ import org.newdawn.slick.GameContainer;
/*  8:   */ import org.newdawn.slick.Graphics;
/*  9:   */ import org.newdawn.slick.Image;
/* 10:   */ import org.newdawn.slick.SlickException;
/* 11:   */ 
/* 12:   */ public class PureFontTest
/* 13:   */   extends BasicGame
/* 14:   */ {
/* 15:   */   private Font font;
/* 16:   */   private Image image;
/* 17:   */   private static AppGameContainer container;
/* 18:   */   
/* 19:   */   public PureFontTest()
/* 20:   */   {
/* 21:28 */     super("Hiero Font Test");
/* 22:   */   }
/* 23:   */   
/* 24:   */   public void init(GameContainer container)
/* 25:   */     throws SlickException
/* 26:   */   {
/* 27:35 */     this.image = new Image("testdata/sky.jpg");
/* 28:36 */     this.font = new AngelCodeFont("testdata/hiero.fnt", "testdata/hiero.png");
/* 29:   */   }
/* 30:   */   
/* 31:   */   public void render(GameContainer container, Graphics g)
/* 32:   */   {
/* 33:43 */     this.image.draw(0.0F, 0.0F, 800.0F, 600.0F);
/* 34:44 */     this.font.drawString(100.0F, 32.0F, "On top of old smokey, all");
/* 35:45 */     this.font.drawString(100.0F, 80.0F, "covered with sand..");
/* 36:   */   }
/* 37:   */   
/* 38:   */   public void update(GameContainer container, int delta)
/* 39:   */     throws SlickException
/* 40:   */   {}
/* 41:   */   
/* 42:   */   public void keyPressed(int key, char c)
/* 43:   */   {
/* 44:58 */     if (key == 1) {
/* 45:59 */       System.exit(0);
/* 46:   */     }
/* 47:   */   }
/* 48:   */   
/* 49:   */   public static void main(String[] argv)
/* 50:   */   {
/* 51:   */     try
/* 52:   */     {
/* 53:73 */       container = new AppGameContainer(new PureFontTest());
/* 54:74 */       container.setDisplayMode(800, 600, false);
/* 55:75 */       container.start();
/* 56:   */     }
/* 57:   */     catch (SlickException e)
/* 58:   */     {
/* 59:77 */       e.printStackTrace();
/* 60:   */     }
/* 61:   */   }
/* 62:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.tests.PureFontTest
 * JD-Core Version:    0.7.0.1
 */