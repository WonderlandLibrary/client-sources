/*  1:   */ package org.newdawn.slick.command;
/*  2:   */ 
/*  3:   */ public class ControllerDirectionControl
/*  4:   */   extends ControllerControl
/*  5:   */ {
/*  6:11 */   public static final Direction LEFT = new Direction(1);
/*  7:13 */   public static final Direction UP = new Direction(3);
/*  8:15 */   public static final Direction DOWN = new Direction(4);
/*  9:17 */   public static final Direction RIGHT = new Direction(2);
/* 10:   */   
/* 11:   */   public ControllerDirectionControl(int controllerIndex, Direction dir)
/* 12:   */   {
/* 13:26 */     super(controllerIndex, dir.event, 0);
/* 14:   */   }
/* 15:   */   
/* 16:   */   private static class Direction
/* 17:   */   {
/* 18:   */     private int event;
/* 19:   */     
/* 20:   */     public Direction(int event)
/* 21:   */     {
/* 22:44 */       this.event = event;
/* 23:   */     }
/* 24:   */   }
/* 25:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.command.ControllerDirectionControl
 * JD-Core Version:    0.7.0.1
 */