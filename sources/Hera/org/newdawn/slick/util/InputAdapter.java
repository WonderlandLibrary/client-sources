/*    */ package org.newdawn.slick.util;
/*    */ 
/*    */ import org.newdawn.slick.Input;
/*    */ import org.newdawn.slick.InputListener;
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
/*    */ public class InputAdapter
/*    */   implements InputListener
/*    */ {
/*    */   private boolean acceptingInput = true;
/*    */   
/*    */   public void controllerButtonPressed(int controller, int button) {}
/*    */   
/*    */   public void controllerButtonReleased(int controller, int button) {}
/*    */   
/*    */   public void controllerDownPressed(int controller) {}
/*    */   
/*    */   public void controllerDownReleased(int controller) {}
/*    */   
/*    */   public void controllerLeftPressed(int controller) {}
/*    */   
/*    */   public void controllerLeftReleased(int controller) {}
/*    */   
/*    */   public void controllerRightPressed(int controller) {}
/*    */   
/*    */   public void controllerRightReleased(int controller) {}
/*    */   
/*    */   public void controllerUpPressed(int controller) {}
/*    */   
/*    */   public void controllerUpReleased(int controller) {}
/*    */   
/*    */   public void inputEnded() {}
/*    */   
/*    */   public boolean isAcceptingInput() {
/* 85 */     return this.acceptingInput;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setAcceptingInput(boolean acceptingInput) {
/* 94 */     this.acceptingInput = acceptingInput;
/*    */   }
/*    */   
/*    */   public void keyPressed(int key, char c) {}
/*    */   
/*    */   public void keyReleased(int key, char c) {}
/*    */   
/*    */   public void mouseMoved(int oldx, int oldy, int newx, int newy) {}
/*    */   
/*    */   public void mousePressed(int button, int x, int y) {}
/*    */   
/*    */   public void mouseReleased(int button, int x, int y) {}
/*    */   
/*    */   public void mouseWheelMoved(int change) {}
/*    */   
/*    */   public void setInput(Input input) {}
/*    */   
/*    */   public void mouseClicked(int button, int x, int y, int clickCount) {}
/*    */   
/*    */   public void mouseDragged(int oldx, int oldy, int newx, int newy) {}
/*    */   
/*    */   public void inputStarted() {}
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slic\\util\InputAdapter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */