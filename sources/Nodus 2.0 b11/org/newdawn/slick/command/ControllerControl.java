/*  1:   */ package org.newdawn.slick.command;
/*  2:   */ 
/*  3:   */ abstract class ControllerControl
/*  4:   */   implements Control
/*  5:   */ {
/*  6:   */   protected static final int BUTTON_EVENT = 0;
/*  7:   */   protected static final int LEFT_EVENT = 1;
/*  8:   */   protected static final int RIGHT_EVENT = 2;
/*  9:   */   protected static final int UP_EVENT = 3;
/* 10:   */   protected static final int DOWN_EVENT = 4;
/* 11:   */   private int event;
/* 12:   */   private int button;
/* 13:   */   private int controllerNumber;
/* 14:   */   
/* 15:   */   protected ControllerControl(int controllerNumber, int event, int button)
/* 16:   */   {
/* 17:36 */     this.event = event;
/* 18:37 */     this.button = button;
/* 19:38 */     this.controllerNumber = controllerNumber;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public boolean equals(Object o)
/* 23:   */   {
/* 24:45 */     if (o == null) {
/* 25:46 */       return false;
/* 26:   */     }
/* 27:47 */     if (!(o instanceof ControllerControl)) {
/* 28:48 */       return false;
/* 29:   */     }
/* 30:50 */     ControllerControl c = (ControllerControl)o;
/* 31:   */     
/* 32:52 */     return (c.controllerNumber == this.controllerNumber) && (c.event == this.event) && (c.button == this.button);
/* 33:   */   }
/* 34:   */   
/* 35:   */   public int hashCode()
/* 36:   */   {
/* 37:59 */     return this.event + this.button + this.controllerNumber;
/* 38:   */   }
/* 39:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.command.ControllerControl
 * JD-Core Version:    0.7.0.1
 */