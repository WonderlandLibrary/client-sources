/*    */ package org.newdawn.slick.command;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ abstract class ControllerControl
/*    */   implements Control
/*    */ {
/*    */   protected static final int BUTTON_EVENT = 0;
/*    */   protected static final int LEFT_EVENT = 1;
/*    */   protected static final int RIGHT_EVENT = 2;
/*    */   protected static final int UP_EVENT = 3;
/*    */   protected static final int DOWN_EVENT = 4;
/*    */   private int event;
/*    */   private int button;
/*    */   private int controllerNumber;
/*    */   
/*    */   protected ControllerControl(int controllerNumber, int event, int button) {
/* 36 */     this.event = event;
/* 37 */     this.button = button;
/* 38 */     this.controllerNumber = controllerNumber;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean equals(Object o) {
/* 45 */     if (o == null)
/* 46 */       return false; 
/* 47 */     if (!(o instanceof ControllerControl)) {
/* 48 */       return false;
/*    */     }
/* 50 */     ControllerControl c = (ControllerControl)o;
/*    */     
/* 52 */     return (c.controllerNumber == this.controllerNumber && c.event == this.event && c.button == this.button);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 59 */     return this.event + this.button + this.controllerNumber;
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\command\ControllerControl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */