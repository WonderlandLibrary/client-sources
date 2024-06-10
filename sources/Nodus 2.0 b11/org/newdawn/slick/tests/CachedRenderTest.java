/*  1:   */ package org.newdawn.slick.tests;
/*  2:   */ 
/*  3:   */ import org.newdawn.slick.AppGameContainer;
/*  4:   */ import org.newdawn.slick.BasicGame;
/*  5:   */ import org.newdawn.slick.CachedRender;
/*  6:   */ import org.newdawn.slick.Color;
/*  7:   */ import org.newdawn.slick.GameContainer;
/*  8:   */ import org.newdawn.slick.Graphics;
/*  9:   */ import org.newdawn.slick.Input;
/* 10:   */ import org.newdawn.slick.SlickException;
/* 11:   */ 
/* 12:   */ public class CachedRenderTest
/* 13:   */   extends BasicGame
/* 14:   */ {
/* 15:   */   private Runnable operations;
/* 16:   */   private CachedRender cached;
/* 17:   */   private boolean drawCached;
/* 18:   */   
/* 19:   */   public CachedRenderTest()
/* 20:   */   {
/* 21:30 */     super("Cached Render Test");
/* 22:   */   }
/* 23:   */   
/* 24:   */   public void init(final GameContainer container)
/* 25:   */     throws SlickException
/* 26:   */   {
/* 27:37 */     this.operations = new Runnable()
/* 28:   */     {
/* 29:   */       public void run()
/* 30:   */       {
/* 31:39 */         for (int i = 0; i < 100; i++)
/* 32:   */         {
/* 33:40 */           int c = i + 100;
/* 34:41 */           container.getGraphics().setColor(new Color(c, c, c, c));
/* 35:42 */           container.getGraphics().drawOval(i * 5 + 50, i * 3 + 50, 100.0F, 100.0F);
/* 36:   */         }
/* 37:   */       }
/* 38:46 */     };
/* 39:47 */     this.cached = new CachedRender(this.operations);
/* 40:   */   }
/* 41:   */   
/* 42:   */   public void update(GameContainer container, int delta)
/* 43:   */     throws SlickException
/* 44:   */   {
/* 45:54 */     if (container.getInput().isKeyPressed(57)) {
/* 46:55 */       this.drawCached = (!this.drawCached);
/* 47:   */     }
/* 48:   */   }
/* 49:   */   
/* 50:   */   public void render(GameContainer container, Graphics g)
/* 51:   */     throws SlickException
/* 52:   */   {
/* 53:63 */     g.setColor(Color.white);
/* 54:64 */     g.drawString("Press space to toggle caching", 10.0F, 130.0F);
/* 55:65 */     if (this.drawCached)
/* 56:   */     {
/* 57:66 */       g.drawString("Drawing from cache", 10.0F, 100.0F);
/* 58:67 */       this.cached.render();
/* 59:   */     }
/* 60:   */     else
/* 61:   */     {
/* 62:69 */       g.drawString("Drawing direct", 10.0F, 100.0F);
/* 63:70 */       this.operations.run();
/* 64:   */     }
/* 65:   */   }
/* 66:   */   
/* 67:   */   public static void main(String[] argv)
/* 68:   */   {
/* 69:   */     try
/* 70:   */     {
/* 71:81 */       AppGameContainer container = new AppGameContainer(new CachedRenderTest());
/* 72:82 */       container.setDisplayMode(800, 600, false);
/* 73:83 */       container.start();
/* 74:   */     }
/* 75:   */     catch (SlickException e)
/* 76:   */     {
/* 77:85 */       e.printStackTrace();
/* 78:   */     }
/* 79:   */   }
/* 80:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.tests.CachedRenderTest
 * JD-Core Version:    0.7.0.1
 */