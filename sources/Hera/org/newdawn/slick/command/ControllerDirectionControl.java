/*    */ package org.newdawn.slick.command;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ControllerDirectionControl
/*    */   extends ControllerControl
/*    */ {
/* 11 */   public static final Direction LEFT = new Direction(1);
/*    */   
/* 13 */   public static final Direction UP = new Direction(3);
/*    */   
/* 15 */   public static final Direction DOWN = new Direction(4);
/*    */   
/* 17 */   public static final Direction RIGHT = new Direction(2);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ControllerDirectionControl(int controllerIndex, Direction dir) {
/* 26 */     super(controllerIndex, dir.event, 0);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static class Direction
/*    */   {
/*    */     private int event;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     public Direction(int event) {
/* 44 */       this.event = event;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\command\ControllerDirectionControl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */