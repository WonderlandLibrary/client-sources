/*      */ package org.newdawn.slick;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.OutputStream;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import org.lwjgl.LWJGLException;
/*      */ import org.lwjgl.input.Controller;
/*      */ import org.lwjgl.input.Controllers;
/*      */ import org.lwjgl.input.Keyboard;
/*      */ import org.lwjgl.input.Mouse;
/*      */ import org.lwjgl.opengl.Display;
/*      */ import org.newdawn.slick.util.Log;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Input
/*      */ {
/*      */   public static final int ANY_CONTROLLER = -1;
/*      */   private static final int MAX_BUTTONS = 100;
/*      */   public static final int KEY_ESCAPE = 1;
/*      */   public static final int KEY_1 = 2;
/*      */   public static final int KEY_2 = 3;
/*      */   public static final int KEY_3 = 4;
/*      */   public static final int KEY_4 = 5;
/*      */   public static final int KEY_5 = 6;
/*      */   public static final int KEY_6 = 7;
/*      */   public static final int KEY_7 = 8;
/*      */   public static final int KEY_8 = 9;
/*      */   public static final int KEY_9 = 10;
/*      */   public static final int KEY_0 = 11;
/*      */   public static final int KEY_MINUS = 12;
/*      */   public static final int KEY_EQUALS = 13;
/*      */   public static final int KEY_BACK = 14;
/*      */   public static final int KEY_TAB = 15;
/*      */   public static final int KEY_Q = 16;
/*      */   public static final int KEY_W = 17;
/*      */   public static final int KEY_E = 18;
/*      */   public static final int KEY_R = 19;
/*      */   public static final int KEY_T = 20;
/*      */   public static final int KEY_Y = 21;
/*      */   public static final int KEY_U = 22;
/*      */   public static final int KEY_I = 23;
/*      */   public static final int KEY_O = 24;
/*      */   public static final int KEY_P = 25;
/*      */   public static final int KEY_LBRACKET = 26;
/*      */   public static final int KEY_RBRACKET = 27;
/*      */   public static final int KEY_RETURN = 28;
/*      */   public static final int KEY_ENTER = 28;
/*      */   public static final int KEY_LCONTROL = 29;
/*      */   public static final int KEY_A = 30;
/*      */   public static final int KEY_S = 31;
/*      */   public static final int KEY_D = 32;
/*      */   public static final int KEY_F = 33;
/*      */   public static final int KEY_G = 34;
/*      */   public static final int KEY_H = 35;
/*      */   public static final int KEY_J = 36;
/*      */   public static final int KEY_K = 37;
/*      */   public static final int KEY_L = 38;
/*      */   public static final int KEY_SEMICOLON = 39;
/*      */   public static final int KEY_APOSTROPHE = 40;
/*      */   public static final int KEY_GRAVE = 41;
/*      */   public static final int KEY_LSHIFT = 42;
/*      */   public static final int KEY_BACKSLASH = 43;
/*      */   public static final int KEY_Z = 44;
/*      */   public static final int KEY_X = 45;
/*      */   public static final int KEY_C = 46;
/*      */   public static final int KEY_V = 47;
/*      */   public static final int KEY_B = 48;
/*      */   public static final int KEY_N = 49;
/*      */   public static final int KEY_M = 50;
/*      */   public static final int KEY_COMMA = 51;
/*      */   public static final int KEY_PERIOD = 52;
/*      */   public static final int KEY_SLASH = 53;
/*      */   public static final int KEY_RSHIFT = 54;
/*      */   public static final int KEY_MULTIPLY = 55;
/*      */   public static final int KEY_LMENU = 56;
/*      */   public static final int KEY_SPACE = 57;
/*      */   public static final int KEY_CAPITAL = 58;
/*      */   public static final int KEY_F1 = 59;
/*      */   public static final int KEY_F2 = 60;
/*      */   public static final int KEY_F3 = 61;
/*      */   public static final int KEY_F4 = 62;
/*      */   public static final int KEY_F5 = 63;
/*      */   public static final int KEY_F6 = 64;
/*      */   public static final int KEY_F7 = 65;
/*      */   public static final int KEY_F8 = 66;
/*      */   public static final int KEY_F9 = 67;
/*      */   public static final int KEY_F10 = 68;
/*      */   public static final int KEY_NUMLOCK = 69;
/*      */   public static final int KEY_SCROLL = 70;
/*      */   public static final int KEY_NUMPAD7 = 71;
/*      */   public static final int KEY_NUMPAD8 = 72;
/*      */   public static final int KEY_NUMPAD9 = 73;
/*      */   public static final int KEY_SUBTRACT = 74;
/*      */   public static final int KEY_NUMPAD4 = 75;
/*      */   public static final int KEY_NUMPAD5 = 76;
/*      */   public static final int KEY_NUMPAD6 = 77;
/*      */   public static final int KEY_ADD = 78;
/*      */   public static final int KEY_NUMPAD1 = 79;
/*      */   public static final int KEY_NUMPAD2 = 80;
/*      */   public static final int KEY_NUMPAD3 = 81;
/*      */   public static final int KEY_NUMPAD0 = 82;
/*      */   public static final int KEY_DECIMAL = 83;
/*      */   public static final int KEY_F11 = 87;
/*      */   public static final int KEY_F12 = 88;
/*      */   public static final int KEY_F13 = 100;
/*      */   public static final int KEY_F14 = 101;
/*      */   public static final int KEY_F15 = 102;
/*      */   public static final int KEY_KANA = 112;
/*      */   public static final int KEY_CONVERT = 121;
/*      */   public static final int KEY_NOCONVERT = 123;
/*      */   public static final int KEY_YEN = 125;
/*      */   public static final int KEY_NUMPADEQUALS = 141;
/*      */   public static final int KEY_CIRCUMFLEX = 144;
/*      */   public static final int KEY_AT = 145;
/*      */   public static final int KEY_COLON = 146;
/*      */   public static final int KEY_UNDERLINE = 147;
/*      */   public static final int KEY_KANJI = 148;
/*      */   public static final int KEY_STOP = 149;
/*      */   public static final int KEY_AX = 150;
/*      */   public static final int KEY_UNLABELED = 151;
/*      */   public static final int KEY_NUMPADENTER = 156;
/*      */   public static final int KEY_RCONTROL = 157;
/*      */   public static final int KEY_NUMPADCOMMA = 179;
/*      */   public static final int KEY_DIVIDE = 181;
/*      */   public static final int KEY_SYSRQ = 183;
/*      */   public static final int KEY_RMENU = 184;
/*      */   public static final int KEY_PAUSE = 197;
/*      */   public static final int KEY_HOME = 199;
/*      */   public static final int KEY_UP = 200;
/*      */   public static final int KEY_PRIOR = 201;
/*      */   public static final int KEY_LEFT = 203;
/*      */   public static final int KEY_RIGHT = 205;
/*      */   public static final int KEY_END = 207;
/*      */   public static final int KEY_DOWN = 208;
/*      */   public static final int KEY_NEXT = 209;
/*      */   public static final int KEY_INSERT = 210;
/*      */   public static final int KEY_DELETE = 211;
/*      */   public static final int KEY_LWIN = 219;
/*      */   public static final int KEY_RWIN = 220;
/*      */   public static final int KEY_APPS = 221;
/*      */   public static final int KEY_POWER = 222;
/*      */   public static final int KEY_SLEEP = 223;
/*      */   public static final int KEY_LALT = 56;
/*      */   public static final int KEY_RALT = 184;
/*      */   private static final int LEFT = 0;
/*      */   private static final int RIGHT = 1;
/*      */   private static final int UP = 2;
/*      */   private static final int DOWN = 3;
/*      */   private static final int BUTTON1 = 4;
/*      */   private static final int BUTTON2 = 5;
/*      */   private static final int BUTTON3 = 6;
/*      */   private static final int BUTTON4 = 7;
/*      */   private static final int BUTTON5 = 8;
/*      */   private static final int BUTTON6 = 9;
/*      */   private static final int BUTTON7 = 10;
/*      */   private static final int BUTTON8 = 11;
/*      */   private static final int BUTTON9 = 12;
/*      */   private static final int BUTTON10 = 13;
/*      */   public static final int MOUSE_LEFT_BUTTON = 0;
/*      */   public static final int MOUSE_RIGHT_BUTTON = 1;
/*      */   public static final int MOUSE_MIDDLE_BUTTON = 2;
/*      */   private static boolean controllersInited = false;
/*  323 */   private static ArrayList controllers = new ArrayList();
/*      */ 
/*      */   
/*      */   private int lastMouseX;
/*      */   
/*      */   private int lastMouseY;
/*      */   
/*  330 */   protected boolean[] mousePressed = new boolean[10];
/*      */   
/*  332 */   private boolean[][] controllerPressed = new boolean[100][100];
/*      */ 
/*      */   
/*  335 */   protected char[] keys = new char[1024];
/*      */   
/*  337 */   protected boolean[] pressed = new boolean[1024];
/*      */   
/*  339 */   protected long[] nextRepeat = new long[1024];
/*      */ 
/*      */   
/*  342 */   private boolean[][] controls = new boolean[10][110];
/*      */   
/*      */   protected boolean consumed = false;
/*      */   
/*  346 */   protected HashSet allListeners = new HashSet();
/*      */   
/*  348 */   protected ArrayList keyListeners = new ArrayList();
/*      */   
/*  350 */   protected ArrayList keyListenersToAdd = new ArrayList();
/*      */   
/*  352 */   protected ArrayList mouseListeners = new ArrayList();
/*      */   
/*  354 */   protected ArrayList mouseListenersToAdd = new ArrayList();
/*      */   
/*  356 */   protected ArrayList controllerListeners = new ArrayList();
/*      */ 
/*      */   
/*      */   private int wheel;
/*      */ 
/*      */   
/*      */   private int height;
/*      */ 
/*      */   
/*      */   private boolean displayActive = true;
/*      */   
/*      */   private boolean keyRepeat;
/*      */   
/*      */   private int keyRepeatInitial;
/*      */   
/*      */   private int keyRepeatInterval;
/*      */   
/*      */   private boolean paused;
/*      */   
/*  375 */   private float scaleX = 1.0F;
/*      */   
/*  377 */   private float scaleY = 1.0F;
/*      */   
/*  379 */   private float xoffset = 0.0F;
/*      */   
/*  381 */   private float yoffset = 0.0F;
/*      */ 
/*      */   
/*  384 */   private int doubleClickDelay = 250;
/*      */   
/*  386 */   private long doubleClickTimeout = 0L;
/*      */ 
/*      */   
/*      */   private int clickX;
/*      */ 
/*      */   
/*      */   private int clickY;
/*      */   
/*      */   private int clickButton;
/*      */   
/*  396 */   private int pressedX = -1;
/*      */ 
/*      */   
/*  399 */   private int pressedY = -1;
/*      */ 
/*      */   
/*  402 */   private int mouseClickTolerance = 5;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void disableControllers() {
/*  409 */     controllersInited = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Input(int height) {
/*  418 */     init(height);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDoubleClickInterval(int delay) {
/*  429 */     this.doubleClickDelay = delay;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setMouseClickTolerance(int mouseClickTolerance) {
/*  439 */     this.mouseClickTolerance = mouseClickTolerance;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setScale(float scaleX, float scaleY) {
/*  449 */     this.scaleX = scaleX;
/*  450 */     this.scaleY = scaleY;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setOffset(float xoffset, float yoffset) {
/*  460 */     this.xoffset = xoffset;
/*  461 */     this.yoffset = yoffset;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void resetInputTransform() {
/*  468 */     setOffset(0.0F, 0.0F);
/*  469 */     setScale(1.0F, 1.0F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addListener(InputListener listener) {
/*  478 */     addKeyListener(listener);
/*  479 */     addMouseListener(listener);
/*  480 */     addControllerListener(listener);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addKeyListener(KeyListener listener) {
/*  489 */     this.keyListenersToAdd.add(listener);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void addKeyListenerImpl(KeyListener listener) {
/*  498 */     if (this.keyListeners.contains(listener)) {
/*      */       return;
/*      */     }
/*  501 */     this.keyListeners.add(listener);
/*  502 */     this.allListeners.add(listener);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addMouseListener(MouseListener listener) {
/*  511 */     this.mouseListenersToAdd.add(listener);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void addMouseListenerImpl(MouseListener listener) {
/*  520 */     if (this.mouseListeners.contains(listener)) {
/*      */       return;
/*      */     }
/*  523 */     this.mouseListeners.add(listener);
/*  524 */     this.allListeners.add(listener);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addControllerListener(ControllerListener listener) {
/*  533 */     if (this.controllerListeners.contains(listener)) {
/*      */       return;
/*      */     }
/*  536 */     this.controllerListeners.add(listener);
/*  537 */     this.allListeners.add(listener);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeAllListeners() {
/*  544 */     removeAllKeyListeners();
/*  545 */     removeAllMouseListeners();
/*  546 */     removeAllControllerListeners();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeAllKeyListeners() {
/*  553 */     this.allListeners.removeAll(this.keyListeners);
/*  554 */     this.keyListeners.clear();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeAllMouseListeners() {
/*  561 */     this.allListeners.removeAll(this.mouseListeners);
/*  562 */     this.mouseListeners.clear();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeAllControllerListeners() {
/*  569 */     this.allListeners.removeAll(this.controllerListeners);
/*  570 */     this.controllerListeners.clear();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addPrimaryListener(InputListener listener) {
/*  580 */     removeListener(listener);
/*      */     
/*  582 */     this.keyListeners.add(0, listener);
/*  583 */     this.mouseListeners.add(0, listener);
/*  584 */     this.controllerListeners.add(0, listener);
/*      */     
/*  586 */     this.allListeners.add(listener);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeListener(InputListener listener) {
/*  595 */     removeKeyListener(listener);
/*  596 */     removeMouseListener(listener);
/*  597 */     removeControllerListener(listener);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeKeyListener(KeyListener listener) {
/*  606 */     this.keyListeners.remove(listener);
/*      */     
/*  608 */     if (!this.mouseListeners.contains(listener) && !this.controllerListeners.contains(listener)) {
/*  609 */       this.allListeners.remove(listener);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeControllerListener(ControllerListener listener) {
/*  619 */     this.controllerListeners.remove(listener);
/*      */     
/*  621 */     if (!this.mouseListeners.contains(listener) && !this.keyListeners.contains(listener)) {
/*  622 */       this.allListeners.remove(listener);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeMouseListener(MouseListener listener) {
/*  632 */     this.mouseListeners.remove(listener);
/*      */     
/*  634 */     if (!this.controllerListeners.contains(listener) && !this.keyListeners.contains(listener)) {
/*  635 */       this.allListeners.remove(listener);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void init(int height) {
/*  645 */     this.height = height;
/*  646 */     this.lastMouseX = getMouseX();
/*  647 */     this.lastMouseY = getMouseY();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getKeyName(int code) {
/*  657 */     return Keyboard.getKeyName(code);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isKeyPressed(int code) {
/*  668 */     if (this.pressed[code]) {
/*  669 */       this.pressed[code] = false;
/*  670 */       return true;
/*      */     } 
/*      */     
/*  673 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isMousePressed(int button) {
/*  683 */     if (this.mousePressed[button]) {
/*  684 */       this.mousePressed[button] = false;
/*  685 */       return true;
/*      */     } 
/*      */     
/*  688 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isControlPressed(int button) {
/*  699 */     return isControlPressed(button, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isControlPressed(int button, int controller) {
/*  711 */     if (this.controllerPressed[controller][button]) {
/*  712 */       this.controllerPressed[controller][button] = false;
/*  713 */       return true;
/*      */     } 
/*      */     
/*  716 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clearControlPressedRecord() {
/*  724 */     for (int i = 0; i < controllers.size(); i++) {
/*  725 */       Arrays.fill(this.controllerPressed[i], false);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clearKeyPressedRecord() {
/*  735 */     Arrays.fill(this.pressed, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clearMousePressedRecord() {
/*  744 */     Arrays.fill(this.mousePressed, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isKeyDown(int code) {
/*  754 */     return Keyboard.isKeyDown(code);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getAbsoluteMouseX() {
/*  763 */     return Mouse.getX();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getAbsoluteMouseY() {
/*  772 */     return this.height - Mouse.getY();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMouseX() {
/*  781 */     return (int)(Mouse.getX() * this.scaleX + this.xoffset);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMouseY() {
/*  790 */     return (int)((this.height - Mouse.getY()) * this.scaleY + this.yoffset);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isMouseButtonDown(int button) {
/*  800 */     return Mouse.isButtonDown(button);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean anyMouseDown() {
/*  809 */     for (int i = 0; i < 3; i++) {
/*  810 */       if (Mouse.isButtonDown(i)) {
/*  811 */         return true;
/*      */       }
/*      */     } 
/*      */     
/*  815 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getControllerCount() {
/*      */     try {
/*  825 */       initControllers();
/*  826 */     } catch (SlickException e) {
/*  827 */       throw new RuntimeException("Failed to initialise controllers");
/*      */     } 
/*      */     
/*  830 */     return controllers.size();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getAxisCount(int controller) {
/*  840 */     return ((Controller)controllers.get(controller)).getAxisCount();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getAxisValue(int controller, int axis) {
/*  851 */     return ((Controller)controllers.get(controller)).getAxisValue(axis);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getAxisName(int controller, int axis) {
/*  862 */     return ((Controller)controllers.get(controller)).getAxisName(axis);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isControllerLeft(int controller) {
/*  872 */     if (controller >= getControllerCount()) {
/*  873 */       return false;
/*      */     }
/*      */     
/*  876 */     if (controller == -1) {
/*  877 */       for (int i = 0; i < controllers.size(); i++) {
/*  878 */         if (isControllerLeft(i)) {
/*  879 */           return true;
/*      */         }
/*      */       } 
/*      */       
/*  883 */       return false;
/*      */     } 
/*      */     
/*  886 */     return (((Controller)controllers.get(controller)).getXAxisValue() < -0.5F || ((Controller)controllers.get(controller)).getPovX() < -0.5F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isControllerRight(int controller) {
/*  897 */     if (controller >= getControllerCount()) {
/*  898 */       return false;
/*      */     }
/*      */     
/*  901 */     if (controller == -1) {
/*  902 */       for (int i = 0; i < controllers.size(); i++) {
/*  903 */         if (isControllerRight(i)) {
/*  904 */           return true;
/*      */         }
/*      */       } 
/*      */       
/*  908 */       return false;
/*      */     } 
/*      */     
/*  911 */     return (((Controller)controllers.get(controller)).getXAxisValue() > 0.5F || ((Controller)controllers.get(controller)).getPovX() > 0.5F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isControllerUp(int controller) {
/*  922 */     if (controller >= getControllerCount()) {
/*  923 */       return false;
/*      */     }
/*      */     
/*  926 */     if (controller == -1) {
/*  927 */       for (int i = 0; i < controllers.size(); i++) {
/*  928 */         if (isControllerUp(i)) {
/*  929 */           return true;
/*      */         }
/*      */       } 
/*      */       
/*  933 */       return false;
/*      */     } 
/*  935 */     return (((Controller)controllers.get(controller)).getYAxisValue() < -0.5F || ((Controller)controllers.get(controller)).getPovY() < -0.5F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isControllerDown(int controller) {
/*  946 */     if (controller >= getControllerCount()) {
/*  947 */       return false;
/*      */     }
/*      */     
/*  950 */     if (controller == -1) {
/*  951 */       for (int i = 0; i < controllers.size(); i++) {
/*  952 */         if (isControllerDown(i)) {
/*  953 */           return true;
/*      */         }
/*      */       } 
/*      */       
/*  957 */       return false;
/*      */     } 
/*      */     
/*  960 */     return (((Controller)controllers.get(controller)).getYAxisValue() > 0.5F || ((Controller)controllers.get(controller)).getPovY() > 0.5F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isButtonPressed(int index, int controller) {
/*  973 */     if (controller >= getControllerCount()) {
/*  974 */       return false;
/*      */     }
/*      */     
/*  977 */     if (controller == -1) {
/*  978 */       for (int i = 0; i < controllers.size(); i++) {
/*  979 */         if (isButtonPressed(index, i)) {
/*  980 */           return true;
/*      */         }
/*      */       } 
/*      */       
/*  984 */       return false;
/*      */     } 
/*      */     
/*  987 */     return ((Controller)controllers.get(controller)).isButtonPressed(index);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isButton1Pressed(int controller) {
/*  997 */     return isButtonPressed(0, controller);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isButton2Pressed(int controller) {
/* 1007 */     return isButtonPressed(1, controller);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isButton3Pressed(int controller) {
/* 1017 */     return isButtonPressed(2, controller);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void initControllers() throws SlickException {
/* 1026 */     if (controllersInited) {
/*      */       return;
/*      */     }
/*      */     
/* 1030 */     controllersInited = true;
/*      */     try {
/* 1032 */       Controllers.create();
/* 1033 */       int count = Controllers.getControllerCount();
/*      */       int i;
/* 1035 */       for (i = 0; i < count; i++) {
/* 1036 */         Controller controller = Controllers.getController(i);
/*      */         
/* 1038 */         if (controller.getButtonCount() >= 3 && controller.getButtonCount() < 100) {
/* 1039 */           controllers.add(controller);
/*      */         }
/*      */       } 
/*      */       
/* 1043 */       Log.info("Found " + controllers.size() + " controllers");
/* 1044 */       for (i = 0; i < controllers.size(); i++) {
/* 1045 */         Log.info(i + " : " + ((Controller)controllers.get(i)).getName());
/*      */       }
/* 1047 */     } catch (LWJGLException e) {
/* 1048 */       if (e.getCause() instanceof ClassNotFoundException) {
/* 1049 */         throw new SlickException("Unable to create controller - no jinput found - add jinput.jar to your classpath");
/*      */       }
/* 1051 */       throw new SlickException("Unable to create controllers");
/* 1052 */     } catch (NoClassDefFoundError e) {}
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void consumeEvent() {
/* 1061 */     this.consumed = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class NullOutputStream
/*      */     extends OutputStream
/*      */   {
/*      */     public void write(int b) throws IOException {}
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int resolveEventKey(int key, char c) {
/* 1090 */     if (c == '=' || key == 0) {
/* 1091 */       return 13;
/*      */     }
/*      */     
/* 1094 */     return key;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void considerDoubleClick(int button, int x, int y) {
/* 1106 */     if (this.doubleClickTimeout == 0L) {
/* 1107 */       this.clickX = x;
/* 1108 */       this.clickY = y;
/* 1109 */       this.clickButton = button;
/* 1110 */       this.doubleClickTimeout = System.currentTimeMillis() + this.doubleClickDelay;
/* 1111 */       fireMouseClicked(button, x, y, 1);
/*      */     }
/* 1113 */     else if (this.clickButton == button && 
/* 1114 */       System.currentTimeMillis() < this.doubleClickTimeout) {
/* 1115 */       fireMouseClicked(button, x, y, 2);
/* 1116 */       this.doubleClickTimeout = 0L;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void poll(int width, int height) {
/* 1129 */     if (this.paused) {
/* 1130 */       clearControlPressedRecord();
/* 1131 */       clearKeyPressedRecord();
/* 1132 */       clearMousePressedRecord();
/*      */       
/* 1134 */       while (Keyboard.next());
/* 1135 */       while (Mouse.next());
/*      */       
/*      */       return;
/*      */     } 
/* 1139 */     if (!Display.isActive()) {
/* 1140 */       clearControlPressedRecord();
/* 1141 */       clearKeyPressedRecord();
/* 1142 */       clearMousePressedRecord();
/*      */     } 
/*      */     
/*      */     int i;
/* 1146 */     for (i = 0; i < this.keyListenersToAdd.size(); i++) {
/* 1147 */       addKeyListenerImpl(this.keyListenersToAdd.get(i));
/*      */     }
/* 1149 */     this.keyListenersToAdd.clear();
/* 1150 */     for (i = 0; i < this.mouseListenersToAdd.size(); i++) {
/* 1151 */       addMouseListenerImpl(this.mouseListenersToAdd.get(i));
/*      */     }
/* 1153 */     this.mouseListenersToAdd.clear();
/*      */     
/* 1155 */     if (this.doubleClickTimeout != 0L && 
/* 1156 */       System.currentTimeMillis() > this.doubleClickTimeout) {
/* 1157 */       this.doubleClickTimeout = 0L;
/*      */     }
/*      */ 
/*      */     
/* 1161 */     this.height = height;
/*      */     
/* 1163 */     Iterator<ControlledInputReciever> allStarts = this.allListeners.iterator();
/* 1164 */     while (allStarts.hasNext()) {
/* 1165 */       ControlledInputReciever listener = allStarts.next();
/* 1166 */       listener.inputStarted();
/*      */     } 
/*      */     
/* 1169 */     while (Keyboard.next()) {
/* 1170 */       if (Keyboard.getEventKeyState()) {
/* 1171 */         int k = resolveEventKey(Keyboard.getEventKey(), Keyboard.getEventCharacter());
/*      */         
/* 1173 */         this.keys[k] = Keyboard.getEventCharacter();
/* 1174 */         this.pressed[k] = true;
/* 1175 */         this.nextRepeat[k] = System.currentTimeMillis() + this.keyRepeatInitial;
/*      */         
/* 1177 */         this.consumed = false;
/* 1178 */         for (int m = 0; m < this.keyListeners.size(); m++) {
/* 1179 */           KeyListener listener = this.keyListeners.get(m);
/*      */           
/* 1181 */           if (listener.isAcceptingInput()) {
/* 1182 */             listener.keyPressed(k, Keyboard.getEventCharacter());
/* 1183 */             if (this.consumed)
/*      */               break; 
/*      */           } 
/*      */         } 
/*      */         continue;
/*      */       } 
/* 1189 */       int eventKey = resolveEventKey(Keyboard.getEventKey(), Keyboard.getEventCharacter());
/* 1190 */       this.nextRepeat[eventKey] = 0L;
/*      */       
/* 1192 */       this.consumed = false;
/* 1193 */       for (int j = 0; j < this.keyListeners.size(); j++) {
/* 1194 */         KeyListener listener = this.keyListeners.get(j);
/* 1195 */         if (listener.isAcceptingInput()) {
/* 1196 */           listener.keyReleased(eventKey, this.keys[eventKey]);
/* 1197 */           if (this.consumed) {
/*      */             break;
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1205 */     while (Mouse.next()) {
/* 1206 */       if (Mouse.getEventButton() >= 0) {
/* 1207 */         if (Mouse.getEventButtonState()) {
/* 1208 */           this.consumed = false;
/* 1209 */           this.mousePressed[Mouse.getEventButton()] = true;
/*      */           
/* 1211 */           this.pressedX = (int)(this.xoffset + Mouse.getEventX() * this.scaleX);
/* 1212 */           this.pressedY = (int)(this.yoffset + (height - Mouse.getEventY()) * this.scaleY);
/*      */           
/* 1214 */           for (int k = 0; k < this.mouseListeners.size(); k++) {
/* 1215 */             MouseListener listener = this.mouseListeners.get(k);
/* 1216 */             if (listener.isAcceptingInput()) {
/* 1217 */               listener.mousePressed(Mouse.getEventButton(), this.pressedX, this.pressedY);
/* 1218 */               if (this.consumed)
/*      */                 break; 
/*      */             } 
/*      */           } 
/*      */           continue;
/*      */         } 
/* 1224 */         this.consumed = false;
/* 1225 */         this.mousePressed[Mouse.getEventButton()] = false;
/*      */         
/* 1227 */         int releasedX = (int)(this.xoffset + Mouse.getEventX() * this.scaleX);
/* 1228 */         int releasedY = (int)(this.yoffset + (height - Mouse.getEventY()) * this.scaleY);
/* 1229 */         if (this.pressedX != -1 && this.pressedY != -1 && Math.abs(this.pressedX - releasedX) < this.mouseClickTolerance && Math.abs(this.pressedY - releasedY) < this.mouseClickTolerance) {
/*      */ 
/*      */ 
/*      */           
/* 1233 */           considerDoubleClick(Mouse.getEventButton(), releasedX, releasedY);
/* 1234 */           this.pressedX = this.pressedY = -1;
/*      */         } 
/*      */         
/* 1237 */         for (int j = 0; j < this.mouseListeners.size(); j++) {
/* 1238 */           MouseListener listener = this.mouseListeners.get(j);
/* 1239 */           if (listener.isAcceptingInput()) {
/* 1240 */             listener.mouseReleased(Mouse.getEventButton(), releasedX, releasedY);
/* 1241 */             if (this.consumed) {
/*      */               break;
/*      */             }
/*      */           } 
/*      */         } 
/*      */         continue;
/*      */       } 
/* 1248 */       if (Mouse.isGrabbed() && this.displayActive && (
/* 1249 */         Mouse.getEventDX() != 0 || Mouse.getEventDY() != 0)) {
/* 1250 */         this.consumed = false;
/* 1251 */         for (int j = 0; j < this.mouseListeners.size(); j++) {
/* 1252 */           MouseListener listener = this.mouseListeners.get(j);
/* 1253 */           if (listener.isAcceptingInput()) {
/* 1254 */             if (anyMouseDown()) {
/* 1255 */               listener.mouseDragged(0, 0, Mouse.getEventDX(), -Mouse.getEventDY());
/*      */             } else {
/* 1257 */               listener.mouseMoved(0, 0, Mouse.getEventDX(), -Mouse.getEventDY());
/*      */             } 
/*      */             
/* 1260 */             if (this.consumed) {
/*      */               break;
/*      */             }
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 1268 */       int dwheel = Mouse.getEventDWheel();
/* 1269 */       this.wheel += dwheel;
/* 1270 */       if (dwheel != 0) {
/* 1271 */         this.consumed = false;
/* 1272 */         for (int j = 0; j < this.mouseListeners.size(); j++) {
/* 1273 */           MouseListener listener = this.mouseListeners.get(j);
/* 1274 */           if (listener.isAcceptingInput()) {
/* 1275 */             listener.mouseWheelMoved(dwheel);
/* 1276 */             if (this.consumed) {
/*      */               break;
/*      */             }
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1285 */     if (!this.displayActive || Mouse.isGrabbed()) {
/* 1286 */       this.lastMouseX = getMouseX();
/* 1287 */       this.lastMouseY = getMouseY();
/*      */     }
/* 1289 */     else if (this.lastMouseX != getMouseX() || this.lastMouseY != getMouseY()) {
/* 1290 */       this.consumed = false;
/* 1291 */       for (int j = 0; j < this.mouseListeners.size(); j++) {
/* 1292 */         MouseListener listener = this.mouseListeners.get(j);
/* 1293 */         if (listener.isAcceptingInput()) {
/* 1294 */           if (anyMouseDown()) {
/* 1295 */             listener.mouseDragged(this.lastMouseX, this.lastMouseY, getMouseX(), getMouseY());
/*      */           } else {
/* 1297 */             listener.mouseMoved(this.lastMouseX, this.lastMouseY, getMouseX(), getMouseY());
/*      */           } 
/* 1299 */           if (this.consumed) {
/*      */             break;
/*      */           }
/*      */         } 
/*      */       } 
/* 1304 */       this.lastMouseX = getMouseX();
/* 1305 */       this.lastMouseY = getMouseY();
/*      */     } 
/*      */ 
/*      */     
/* 1309 */     if (controllersInited) {
/* 1310 */       for (int j = 0; j < getControllerCount(); j++) {
/* 1311 */         int count = ((Controller)controllers.get(j)).getButtonCount() + 3;
/* 1312 */         count = Math.min(count, 24);
/* 1313 */         for (int c = 0; c <= count; c++) {
/* 1314 */           if (this.controls[j][c] && !isControlDwn(c, j)) {
/* 1315 */             this.controls[j][c] = false;
/* 1316 */             fireControlRelease(c, j);
/* 1317 */           } else if (!this.controls[j][c] && isControlDwn(c, j)) {
/* 1318 */             this.controllerPressed[j][c] = true;
/* 1319 */             this.controls[j][c] = true;
/* 1320 */             fireControlPress(c, j);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/* 1326 */     if (this.keyRepeat) {
/* 1327 */       for (int j = 0; j < 1024; j++) {
/* 1328 */         if (this.pressed[j] && this.nextRepeat[j] != 0L && 
/* 1329 */           System.currentTimeMillis() > this.nextRepeat[j]) {
/* 1330 */           this.nextRepeat[j] = System.currentTimeMillis() + this.keyRepeatInterval;
/* 1331 */           this.consumed = false;
/* 1332 */           for (int k = 0; k < this.keyListeners.size(); k++) {
/* 1333 */             KeyListener listener = this.keyListeners.get(k);
/*      */             
/* 1335 */             if (listener.isAcceptingInput()) {
/* 1336 */               listener.keyPressed(j, this.keys[j]);
/* 1337 */               if (this.consumed) {
/*      */                 break;
/*      */               }
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1348 */     Iterator<ControlledInputReciever> all = this.allListeners.iterator();
/* 1349 */     while (all.hasNext()) {
/* 1350 */       ControlledInputReciever listener = all.next();
/* 1351 */       listener.inputEnded();
/*      */     } 
/*      */     
/* 1354 */     if (Display.isCreated()) {
/* 1355 */       this.displayActive = Display.isActive();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void enableKeyRepeat(int initial, int interval) {
/* 1368 */     Keyboard.enableRepeatEvents(true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void enableKeyRepeat() {
/* 1376 */     Keyboard.enableRepeatEvents(true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void disableKeyRepeat() {
/* 1383 */     Keyboard.enableRepeatEvents(false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isKeyRepeatEnabled() {
/* 1392 */     return Keyboard.areRepeatEventsEnabled();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void fireControlPress(int index, int controllerIndex) {
/* 1402 */     this.consumed = false;
/* 1403 */     for (int i = 0; i < this.controllerListeners.size(); i++) {
/* 1404 */       ControllerListener listener = this.controllerListeners.get(i);
/* 1405 */       if (listener.isAcceptingInput()) {
/* 1406 */         switch (index) {
/*      */           case 0:
/* 1408 */             listener.controllerLeftPressed(controllerIndex);
/*      */             break;
/*      */           case 1:
/* 1411 */             listener.controllerRightPressed(controllerIndex);
/*      */             break;
/*      */           case 2:
/* 1414 */             listener.controllerUpPressed(controllerIndex);
/*      */             break;
/*      */           case 3:
/* 1417 */             listener.controllerDownPressed(controllerIndex);
/*      */             break;
/*      */           
/*      */           default:
/* 1421 */             listener.controllerButtonPressed(controllerIndex, index - 4 + 1);
/*      */             break;
/*      */         } 
/* 1424 */         if (this.consumed) {
/*      */           break;
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void fireControlRelease(int index, int controllerIndex) {
/* 1438 */     this.consumed = false;
/* 1439 */     for (int i = 0; i < this.controllerListeners.size(); i++) {
/* 1440 */       ControllerListener listener = this.controllerListeners.get(i);
/* 1441 */       if (listener.isAcceptingInput()) {
/* 1442 */         switch (index) {
/*      */           case 0:
/* 1444 */             listener.controllerLeftReleased(controllerIndex);
/*      */             break;
/*      */           case 1:
/* 1447 */             listener.controllerRightReleased(controllerIndex);
/*      */             break;
/*      */           case 2:
/* 1450 */             listener.controllerUpReleased(controllerIndex);
/*      */             break;
/*      */           case 3:
/* 1453 */             listener.controllerDownReleased(controllerIndex);
/*      */             break;
/*      */           
/*      */           default:
/* 1457 */             listener.controllerButtonReleased(controllerIndex, index - 4 + 1);
/*      */             break;
/*      */         } 
/* 1460 */         if (this.consumed) {
/*      */           break;
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isControlDwn(int index, int controllerIndex) {
/* 1475 */     switch (index) {
/*      */       case 0:
/* 1477 */         return isControllerLeft(controllerIndex);
/*      */       case 1:
/* 1479 */         return isControllerRight(controllerIndex);
/*      */       case 2:
/* 1481 */         return isControllerUp(controllerIndex);
/*      */       case 3:
/* 1483 */         return isControllerDown(controllerIndex);
/*      */     } 
/*      */     
/* 1486 */     if (index >= 4) {
/* 1487 */       return isButtonPressed(index - 4, controllerIndex);
/*      */     }
/*      */     
/* 1490 */     throw new RuntimeException("Unknown control index");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void pause() {
/* 1498 */     this.paused = true;
/*      */ 
/*      */     
/* 1501 */     clearKeyPressedRecord();
/* 1502 */     clearMousePressedRecord();
/* 1503 */     clearControlPressedRecord();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void resume() {
/* 1510 */     this.paused = false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void fireMouseClicked(int button, int x, int y, int clickCount) {
/* 1522 */     this.consumed = false;
/* 1523 */     for (int i = 0; i < this.mouseListeners.size(); i++) {
/* 1524 */       MouseListener listener = this.mouseListeners.get(i);
/* 1525 */       if (listener.isAcceptingInput()) {
/* 1526 */         listener.mouseClicked(button, x, y, clickCount);
/* 1527 */         if (this.consumed)
/*      */           break; 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\Input.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */