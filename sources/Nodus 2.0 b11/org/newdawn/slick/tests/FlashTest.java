/*  1:   */ package org.newdawn.slick.tests;
/*  2:   */ 
/*  3:   */ import org.newdawn.slick.AppGameContainer;
/*  4:   */ import org.newdawn.slick.BasicGame;
/*  5:   */ import org.newdawn.slick.Color;
/*  6:   */ import org.newdawn.slick.GameContainer;
/*  7:   */ import org.newdawn.slick.Graphics;
/*  8:   */ import org.newdawn.slick.Image;
/*  9:   */ import org.newdawn.slick.SlickException;
/* 10:   */ 
/* 11:   */ public class FlashTest
/* 12:   */   extends BasicGame
/* 13:   */ {
/* 14:   */   private Image image;
/* 15:   */   private boolean flash;
/* 16:   */   private GameContainer container;
/* 17:   */   
/* 18:   */   public FlashTest()
/* 19:   */   {
/* 20:29 */     super("Flash Test");
/* 21:   */   }
/* 22:   */   
/* 23:   */   public void init(GameContainer container)
/* 24:   */     throws SlickException
/* 25:   */   {
/* 26:36 */     this.container = container;
/* 27:   */     
/* 28:38 */     this.image = new Image("testdata/logo.tga");
/* 29:   */   }
/* 30:   */   
/* 31:   */   public void render(GameContainer container, Graphics g)
/* 32:   */   {
/* 33:45 */     g.drawString("Press space to toggle", 10.0F, 50.0F);
/* 34:46 */     if (this.flash) {
/* 35:47 */       this.image.draw(100.0F, 100.0F);
/* 36:   */     } else {
/* 37:49 */       this.image.drawFlash(100.0F, 100.0F, this.image.getWidth(), this.image.getHeight(), new Color(1.0F, 0.0F, 1.0F, 1.0F));
/* 38:   */     }
/* 39:   */   }
/* 40:   */   
/* 41:   */   public void update(GameContainer container, int delta) {}
/* 42:   */   
/* 43:   */   public static void main(String[] argv)
/* 44:   */   {
/* 45:   */     try
/* 46:   */     {
/* 47:66 */       AppGameContainer container = new AppGameContainer(new FlashTest());
/* 48:67 */       container.setDisplayMode(800, 600, false);
/* 49:68 */       container.start();
/* 50:   */     }
/* 51:   */     catch (SlickException e)
/* 52:   */     {
/* 53:70 */       e.printStackTrace();
/* 54:   */     }
/* 55:   */   }
/* 56:   */   
/* 57:   */   public void keyPressed(int key, char c)
/* 58:   */   {
/* 59:78 */     if (key == 57) {
/* 60:79 */       this.flash = (!this.flash);
/* 61:   */     }
/* 62:81 */     if (key == 1) {
/* 63:82 */       this.container.exit();
/* 64:   */     }
/* 65:   */   }
/* 66:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.tests.FlashTest
 * JD-Core Version:    0.7.0.1
 */