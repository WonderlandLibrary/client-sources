/*   1:    */ package org.newdawn.slick;
/*   2:    */ 
/*   3:    */ public abstract class BasicGame
/*   4:    */   implements Game, InputListener
/*   5:    */ {
/*   6:    */   private static final int MAX_CONTROLLERS = 20;
/*   7:    */   private static final int MAX_CONTROLLER_BUTTONS = 100;
/*   8:    */   private String title;
/*   9: 17 */   protected boolean[] controllerLeft = new boolean[20];
/*  10: 19 */   protected boolean[] controllerRight = new boolean[20];
/*  11: 21 */   protected boolean[] controllerUp = new boolean[20];
/*  12: 23 */   protected boolean[] controllerDown = new boolean[20];
/*  13: 25 */   protected boolean[][] controllerButton = new boolean[20][100];
/*  14:    */   
/*  15:    */   public BasicGame(String title)
/*  16:    */   {
/*  17: 33 */     this.title = title;
/*  18:    */   }
/*  19:    */   
/*  20:    */   public void setInput(Input input) {}
/*  21:    */   
/*  22:    */   public boolean closeRequested()
/*  23:    */   {
/*  24: 46 */     return true;
/*  25:    */   }
/*  26:    */   
/*  27:    */   public String getTitle()
/*  28:    */   {
/*  29: 53 */     return this.title;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public abstract void init(GameContainer paramGameContainer)
/*  33:    */     throws SlickException;
/*  34:    */   
/*  35:    */   public void keyPressed(int key, char c) {}
/*  36:    */   
/*  37:    */   public void keyReleased(int key, char c) {}
/*  38:    */   
/*  39:    */   public void mouseMoved(int oldx, int oldy, int newx, int newy) {}
/*  40:    */   
/*  41:    */   public void mouseDragged(int oldx, int oldy, int newx, int newy) {}
/*  42:    */   
/*  43:    */   public void mouseClicked(int button, int x, int y, int clickCount) {}
/*  44:    */   
/*  45:    */   public void mousePressed(int button, int x, int y) {}
/*  46:    */   
/*  47:    */   public void controllerButtonPressed(int controller, int button)
/*  48:    */   {
/*  49:102 */     this.controllerButton[controller][button] = 1;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public void controllerButtonReleased(int controller, int button)
/*  53:    */   {
/*  54:109 */     this.controllerButton[controller][button] = 0;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public void controllerDownPressed(int controller)
/*  58:    */   {
/*  59:116 */     this.controllerDown[controller] = true;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public void controllerDownReleased(int controller)
/*  63:    */   {
/*  64:123 */     this.controllerDown[controller] = false;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public void controllerLeftPressed(int controller)
/*  68:    */   {
/*  69:130 */     this.controllerLeft[controller] = true;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public void controllerLeftReleased(int controller)
/*  73:    */   {
/*  74:137 */     this.controllerLeft[controller] = false;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public void controllerRightPressed(int controller)
/*  78:    */   {
/*  79:144 */     this.controllerRight[controller] = true;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public void controllerRightReleased(int controller)
/*  83:    */   {
/*  84:151 */     this.controllerRight[controller] = false;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public void controllerUpPressed(int controller)
/*  88:    */   {
/*  89:158 */     this.controllerUp[controller] = true;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public void controllerUpReleased(int controller)
/*  93:    */   {
/*  94:165 */     this.controllerUp[controller] = false;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public void mouseReleased(int button, int x, int y) {}
/*  98:    */   
/*  99:    */   public abstract void update(GameContainer paramGameContainer, int paramInt)
/* 100:    */     throws SlickException;
/* 101:    */   
/* 102:    */   public void mouseWheelMoved(int change) {}
/* 103:    */   
/* 104:    */   public boolean isAcceptingInput()
/* 105:    */   {
/* 106:189 */     return true;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public void inputEnded() {}
/* 110:    */   
/* 111:    */   public void inputStarted() {}
/* 112:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.BasicGame
 * JD-Core Version:    0.7.0.1
 */