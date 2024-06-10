/*  1:   */ package net.minecraft.util;
/*  2:   */ 
/*  3:   */ import org.lwjgl.input.Mouse;
/*  4:   */ import org.lwjgl.opengl.Display;
/*  5:   */ 
/*  6:   */ public class MouseHelper
/*  7:   */ {
/*  8:   */   public int deltaX;
/*  9:   */   public int deltaY;
/* 10:   */   private static final String __OBFID = "CL_00000648";
/* 11:   */   
/* 12:   */   public void grabMouseCursor()
/* 13:   */   {
/* 14:20 */     Mouse.setGrabbed(true);
/* 15:21 */     this.deltaX = 0;
/* 16:22 */     this.deltaY = 0;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public void ungrabMouseCursor()
/* 20:   */   {
/* 21:30 */     Mouse.setCursorPosition(Display.getWidth() / 2, Display.getHeight() / 2);
/* 22:31 */     Mouse.setGrabbed(false);
/* 23:   */   }
/* 24:   */   
/* 25:   */   public void mouseXYChange()
/* 26:   */   {
/* 27:36 */     this.deltaX = Mouse.getDX();
/* 28:37 */     this.deltaY = Mouse.getDY();
/* 29:   */   }
/* 30:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.util.MouseHelper
 * JD-Core Version:    0.7.0.1
 */