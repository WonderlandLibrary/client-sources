/*  1:   */ package org.newdawn.slick.gui;
/*  2:   */ 
/*  3:   */ import org.newdawn.slick.Graphics;
/*  4:   */ import org.newdawn.slick.SlickException;
/*  5:   */ 
/*  6:   */ /**
/*  7:   */  * @deprecated
/*  8:   */  */
/*  9:   */ public abstract class BasicComponent
/* 10:   */   extends AbstractComponent
/* 11:   */ {
/* 12:   */   protected int x;
/* 13:   */   protected int y;
/* 14:   */   protected int width;
/* 15:   */   protected int height;
/* 16:   */   
/* 17:   */   public BasicComponent(GUIContext container)
/* 18:   */   {
/* 19:29 */     super(container);
/* 20:   */   }
/* 21:   */   
/* 22:   */   public int getHeight()
/* 23:   */   {
/* 24:36 */     return this.height;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public int getWidth()
/* 28:   */   {
/* 29:43 */     return this.width;
/* 30:   */   }
/* 31:   */   
/* 32:   */   public int getX()
/* 33:   */   {
/* 34:50 */     return this.x;
/* 35:   */   }
/* 36:   */   
/* 37:   */   public int getY()
/* 38:   */   {
/* 39:57 */     return this.y;
/* 40:   */   }
/* 41:   */   
/* 42:   */   public abstract void renderImpl(GUIContext paramGUIContext, Graphics paramGraphics);
/* 43:   */   
/* 44:   */   public void render(GUIContext container, Graphics g)
/* 45:   */     throws SlickException
/* 46:   */   {
/* 47:72 */     renderImpl(container, g);
/* 48:   */   }
/* 49:   */   
/* 50:   */   public void setLocation(int x, int y)
/* 51:   */   {
/* 52:79 */     this.x = x;
/* 53:80 */     this.y = y;
/* 54:   */   }
/* 55:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.gui.BasicComponent
 * JD-Core Version:    0.7.0.1
 */