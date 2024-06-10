/*  1:   */ package org.newdawn.slick.tests;
/*  2:   */ 
/*  3:   */ import org.newdawn.slick.AppGameContainer;
/*  4:   */ import org.newdawn.slick.BasicGame;
/*  5:   */ import org.newdawn.slick.GameContainer;
/*  6:   */ import org.newdawn.slick.Graphics;
/*  7:   */ import org.newdawn.slick.Image;
/*  8:   */ import org.newdawn.slick.ImageBuffer;
/*  9:   */ import org.newdawn.slick.SlickException;
/* 10:   */ 
/* 11:   */ public class ImageBufferTest
/* 12:   */   extends BasicGame
/* 13:   */ {
/* 14:   */   private Image image;
/* 15:   */   
/* 16:   */   public ImageBufferTest()
/* 17:   */   {
/* 18:25 */     super("Image Buffer Test");
/* 19:   */   }
/* 20:   */   
/* 21:   */   public void init(GameContainer container)
/* 22:   */     throws SlickException
/* 23:   */   {
/* 24:32 */     ImageBuffer buffer = new ImageBuffer(320, 200);
/* 25:33 */     for (int x = 0; x < 320; x++) {
/* 26:34 */       for (int y = 0; y < 200; y++) {
/* 27:35 */         if (y == 20) {
/* 28:36 */           buffer.setRGBA(x, y, 255, 255, 255, 255);
/* 29:   */         } else {
/* 30:38 */           buffer.setRGBA(x, y, x, y, 0, 255);
/* 31:   */         }
/* 32:   */       }
/* 33:   */     }
/* 34:42 */     this.image = buffer.getImage();
/* 35:   */   }
/* 36:   */   
/* 37:   */   public void render(GameContainer container, Graphics g)
/* 38:   */   {
/* 39:49 */     this.image.draw(50.0F, 50.0F);
/* 40:   */   }
/* 41:   */   
/* 42:   */   public void update(GameContainer container, int delta) {}
/* 43:   */   
/* 44:   */   public void keyPressed(int key, char c)
/* 45:   */   {
/* 46:62 */     if (key == 1) {
/* 47:63 */       System.exit(0);
/* 48:   */     }
/* 49:   */   }
/* 50:   */   
/* 51:   */   public static void main(String[] argv)
/* 52:   */   {
/* 53:   */     try
/* 54:   */     {
/* 55:74 */       AppGameContainer container = new AppGameContainer(new ImageBufferTest());
/* 56:75 */       container.setDisplayMode(800, 600, false);
/* 57:76 */       container.start();
/* 58:   */     }
/* 59:   */     catch (SlickException e)
/* 60:   */     {
/* 61:78 */       e.printStackTrace();
/* 62:   */     }
/* 63:   */   }
/* 64:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.tests.ImageBufferTest
 * JD-Core Version:    0.7.0.1
 */