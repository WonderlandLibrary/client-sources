/*  1:   */ package org.newdawn.slick.tests;
/*  2:   */ 
/*  3:   */ import org.newdawn.slick.AppGameContainer;
/*  4:   */ import org.newdawn.slick.BasicGame;
/*  5:   */ import org.newdawn.slick.GameContainer;
/*  6:   */ import org.newdawn.slick.Graphics;
/*  7:   */ import org.newdawn.slick.Input;
/*  8:   */ import org.newdawn.slick.SlickException;
/*  9:   */ 
/* 10:   */ public class KeyRepeatTest
/* 11:   */   extends BasicGame
/* 12:   */ {
/* 13:   */   private int count;
/* 14:   */   private Input input;
/* 15:   */   
/* 16:   */   public KeyRepeatTest()
/* 17:   */   {
/* 18:25 */     super("KeyRepeatTest");
/* 19:   */   }
/* 20:   */   
/* 21:   */   public void init(GameContainer container)
/* 22:   */     throws SlickException
/* 23:   */   {
/* 24:32 */     this.input = container.getInput();
/* 25:33 */     this.input.enableKeyRepeat(300, 100);
/* 26:   */   }
/* 27:   */   
/* 28:   */   public void render(GameContainer container, Graphics g)
/* 29:   */   {
/* 30:40 */     g.drawString("Key Press Count: " + this.count, 100.0F, 100.0F);
/* 31:41 */     g.drawString("Press Space to Toggle Key Repeat", 100.0F, 150.0F);
/* 32:42 */     g.drawString("Key Repeat Enabled: " + this.input.isKeyRepeatEnabled(), 100.0F, 200.0F);
/* 33:   */   }
/* 34:   */   
/* 35:   */   public void update(GameContainer container, int delta) {}
/* 36:   */   
/* 37:   */   public static void main(String[] argv)
/* 38:   */   {
/* 39:   */     try
/* 40:   */     {
/* 41:58 */       AppGameContainer container = new AppGameContainer(new KeyRepeatTest());
/* 42:59 */       container.setDisplayMode(800, 600, false);
/* 43:60 */       container.start();
/* 44:   */     }
/* 45:   */     catch (SlickException e)
/* 46:   */     {
/* 47:62 */       e.printStackTrace();
/* 48:   */     }
/* 49:   */   }
/* 50:   */   
/* 51:   */   public void keyPressed(int key, char c)
/* 52:   */   {
/* 53:70 */     this.count += 1;
/* 54:71 */     if (key == 57) {
/* 55:72 */       if (this.input.isKeyRepeatEnabled()) {
/* 56:73 */         this.input.disableKeyRepeat();
/* 57:   */       } else {
/* 58:75 */         this.input.enableKeyRepeat(300, 100);
/* 59:   */       }
/* 60:   */     }
/* 61:   */   }
/* 62:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.tests.KeyRepeatTest
 * JD-Core Version:    0.7.0.1
 */