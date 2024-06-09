/*    */ package org.newdawn.slick.gui;
/*    */ 
/*    */ import org.newdawn.slick.Graphics;
/*    */ import org.newdawn.slick.SlickException;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class BasicComponent
/*    */   extends AbstractComponent
/*    */ {
/*    */   protected int x;
/*    */   protected int y;
/*    */   protected int width;
/*    */   protected int height;
/*    */   
/*    */   public BasicComponent(GUIContext container) {
/* 29 */     super(container);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getHeight() {
/* 36 */     return this.height;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getWidth() {
/* 43 */     return this.width;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getX() {
/* 50 */     return this.x;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getY() {
/* 57 */     return this.y;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public abstract void renderImpl(GUIContext paramGUIContext, Graphics paramGraphics);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void render(GUIContext container, Graphics g) throws SlickException {
/* 72 */     renderImpl(container, g);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setLocation(int x, int y) {
/* 79 */     this.x = x;
/* 80 */     this.y = y;
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\gui\BasicComponent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */