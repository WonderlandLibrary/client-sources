/*  1:   */ package org.newdawn.slick.util;
/*  2:   */ 
/*  3:   */ import org.newdawn.slick.Input;
/*  4:   */ import org.newdawn.slick.InputListener;
/*  5:   */ 
/*  6:   */ public class InputAdapter
/*  7:   */   implements InputListener
/*  8:   */ {
/*  9:13 */   private boolean acceptingInput = true;
/* 10:   */   
/* 11:   */   public void controllerButtonPressed(int controller, int button) {}
/* 12:   */   
/* 13:   */   public void controllerButtonReleased(int controller, int button) {}
/* 14:   */   
/* 15:   */   public void controllerDownPressed(int controller) {}
/* 16:   */   
/* 17:   */   public void controllerDownReleased(int controller) {}
/* 18:   */   
/* 19:   */   public void controllerLeftPressed(int controller) {}
/* 20:   */   
/* 21:   */   public void controllerLeftReleased(int controller) {}
/* 22:   */   
/* 23:   */   public void controllerRightPressed(int controller) {}
/* 24:   */   
/* 25:   */   public void controllerRightReleased(int controller) {}
/* 26:   */   
/* 27:   */   public void controllerUpPressed(int controller) {}
/* 28:   */   
/* 29:   */   public void controllerUpReleased(int controller) {}
/* 30:   */   
/* 31:   */   public void inputEnded() {}
/* 32:   */   
/* 33:   */   public boolean isAcceptingInput()
/* 34:   */   {
/* 35:85 */     return this.acceptingInput;
/* 36:   */   }
/* 37:   */   
/* 38:   */   public void setAcceptingInput(boolean acceptingInput)
/* 39:   */   {
/* 40:94 */     this.acceptingInput = acceptingInput;
/* 41:   */   }
/* 42:   */   
/* 43:   */   public void keyPressed(int key, char c) {}
/* 44:   */   
/* 45:   */   public void keyReleased(int key, char c) {}
/* 46:   */   
/* 47:   */   public void mouseMoved(int oldx, int oldy, int newx, int newy) {}
/* 48:   */   
/* 49:   */   public void mousePressed(int button, int x, int y) {}
/* 50:   */   
/* 51:   */   public void mouseReleased(int button, int x, int y) {}
/* 52:   */   
/* 53:   */   public void mouseWheelMoved(int change) {}
/* 54:   */   
/* 55:   */   public void setInput(Input input) {}
/* 56:   */   
/* 57:   */   public void mouseClicked(int button, int x, int y, int clickCount) {}
/* 58:   */   
/* 59:   */   public void mouseDragged(int oldx, int oldy, int newx, int newy) {}
/* 60:   */   
/* 61:   */   public void inputStarted() {}
/* 62:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.util.InputAdapter
 * JD-Core Version:    0.7.0.1
 */