/*  1:   */ package org.newdawn.slick.state;
/*  2:   */ 
/*  3:   */ import org.newdawn.slick.GameContainer;
/*  4:   */ import org.newdawn.slick.Input;
/*  5:   */ import org.newdawn.slick.SlickException;
/*  6:   */ 
/*  7:   */ public abstract class BasicGameState
/*  8:   */   implements GameState
/*  9:   */ {
/* 10:   */   public void inputStarted() {}
/* 11:   */   
/* 12:   */   public boolean isAcceptingInput()
/* 13:   */   {
/* 14:25 */     return true;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public void setInput(Input input) {}
/* 18:   */   
/* 19:   */   public void inputEnded() {}
/* 20:   */   
/* 21:   */   public abstract int getID();
/* 22:   */   
/* 23:   */   public void controllerButtonPressed(int controller, int button) {}
/* 24:   */   
/* 25:   */   public void controllerButtonReleased(int controller, int button) {}
/* 26:   */   
/* 27:   */   public void controllerDownPressed(int controller) {}
/* 28:   */   
/* 29:   */   public void controllerDownReleased(int controller) {}
/* 30:   */   
/* 31:   */   public void controllerLeftPressed(int controller) {}
/* 32:   */   
/* 33:   */   public void controllerLeftReleased(int controller) {}
/* 34:   */   
/* 35:   */   public void controllerRightPressed(int controller) {}
/* 36:   */   
/* 37:   */   public void controllerRightReleased(int controller) {}
/* 38:   */   
/* 39:   */   public void controllerUpPressed(int controller) {}
/* 40:   */   
/* 41:   */   public void controllerUpReleased(int controller) {}
/* 42:   */   
/* 43:   */   public void keyPressed(int key, char c) {}
/* 44:   */   
/* 45:   */   public void keyReleased(int key, char c) {}
/* 46:   */   
/* 47:   */   public void mouseMoved(int oldx, int oldy, int newx, int newy) {}
/* 48:   */   
/* 49:   */   public void mouseDragged(int oldx, int oldy, int newx, int newy) {}
/* 50:   */   
/* 51:   */   public void mouseClicked(int button, int x, int y, int clickCount) {}
/* 52:   */   
/* 53:   */   public void mousePressed(int button, int x, int y) {}
/* 54:   */   
/* 55:   */   public void mouseReleased(int button, int x, int y) {}
/* 56:   */   
/* 57:   */   public void enter(GameContainer container, StateBasedGame game)
/* 58:   */     throws SlickException
/* 59:   */   {}
/* 60:   */   
/* 61:   */   public void leave(GameContainer container, StateBasedGame game)
/* 62:   */     throws SlickException
/* 63:   */   {}
/* 64:   */   
/* 65:   */   public void mouseWheelMoved(int newValue) {}
/* 66:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.state.BasicGameState
 * JD-Core Version:    0.7.0.1
 */