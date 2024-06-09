/*     */ package org.newdawn.slick;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class BasicGame
/*     */   implements Game, InputListener
/*     */ {
/*     */   private static final int MAX_CONTROLLERS = 20;
/*     */   private static final int MAX_CONTROLLER_BUTTONS = 100;
/*     */   private String title;
/*  17 */   protected boolean[] controllerLeft = new boolean[20];
/*     */   
/*  19 */   protected boolean[] controllerRight = new boolean[20];
/*     */   
/*  21 */   protected boolean[] controllerUp = new boolean[20];
/*     */   
/*  23 */   protected boolean[] controllerDown = new boolean[20];
/*     */   
/*  25 */   protected boolean[][] controllerButton = new boolean[20][100];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BasicGame(String title) {
/*  33 */     this.title = title;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setInput(Input input) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean closeRequested() {
/*  46 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTitle() {
/*  53 */     return this.title;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void init(GameContainer paramGameContainer) throws SlickException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void keyPressed(int key, char c) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void keyReleased(int key, char c) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mouseMoved(int oldx, int oldy, int newx, int newy) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mouseDragged(int oldx, int oldy, int newx, int newy) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mouseClicked(int button, int x, int y, int clickCount) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mousePressed(int button, int x, int y) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void controllerButtonPressed(int controller, int button) {
/* 102 */     this.controllerButton[controller][button] = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void controllerButtonReleased(int controller, int button) {
/* 109 */     this.controllerButton[controller][button] = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void controllerDownPressed(int controller) {
/* 116 */     this.controllerDown[controller] = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void controllerDownReleased(int controller) {
/* 123 */     this.controllerDown[controller] = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void controllerLeftPressed(int controller) {
/* 130 */     this.controllerLeft[controller] = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void controllerLeftReleased(int controller) {
/* 137 */     this.controllerLeft[controller] = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void controllerRightPressed(int controller) {
/* 144 */     this.controllerRight[controller] = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void controllerRightReleased(int controller) {
/* 151 */     this.controllerRight[controller] = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void controllerUpPressed(int controller) {
/* 158 */     this.controllerUp[controller] = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void controllerUpReleased(int controller) {
/* 165 */     this.controllerUp[controller] = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mouseReleased(int button, int x, int y) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void update(GameContainer paramGameContainer, int paramInt) throws SlickException;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mouseWheelMoved(int change) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAcceptingInput() {
/* 189 */     return true;
/*     */   }
/*     */   
/*     */   public void inputEnded() {}
/*     */   
/*     */   public void inputStarted() {}
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\BasicGame.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */