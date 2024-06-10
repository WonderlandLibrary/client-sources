/*    1:     */ package org.newdawn.slick;
/*    2:     */ 
/*    3:     */ import java.io.IOException;
/*    4:     */ import java.io.OutputStream;
/*    5:     */ import java.util.ArrayList;
/*    6:     */ import java.util.Arrays;
/*    7:     */ import java.util.HashSet;
/*    8:     */ import java.util.Iterator;
/*    9:     */ import org.lwjgl.LWJGLException;
/*   10:     */ import org.lwjgl.input.Controller;
/*   11:     */ import org.lwjgl.input.Controllers;
/*   12:     */ import org.lwjgl.input.Keyboard;
/*   13:     */ import org.lwjgl.input.Mouse;
/*   14:     */ import org.lwjgl.opengl.Display;
/*   15:     */ import org.newdawn.slick.util.Log;
/*   16:     */ 
/*   17:     */ public class Input
/*   18:     */ {
/*   19:     */   public static final int ANY_CONTROLLER = -1;
/*   20:     */   private static final int MAX_BUTTONS = 100;
/*   21:     */   public static final int KEY_ESCAPE = 1;
/*   22:     */   public static final int KEY_1 = 2;
/*   23:     */   public static final int KEY_2 = 3;
/*   24:     */   public static final int KEY_3 = 4;
/*   25:     */   public static final int KEY_4 = 5;
/*   26:     */   public static final int KEY_5 = 6;
/*   27:     */   public static final int KEY_6 = 7;
/*   28:     */   public static final int KEY_7 = 8;
/*   29:     */   public static final int KEY_8 = 9;
/*   30:     */   public static final int KEY_9 = 10;
/*   31:     */   public static final int KEY_0 = 11;
/*   32:     */   public static final int KEY_MINUS = 12;
/*   33:     */   public static final int KEY_EQUALS = 13;
/*   34:     */   public static final int KEY_BACK = 14;
/*   35:     */   public static final int KEY_TAB = 15;
/*   36:     */   public static final int KEY_Q = 16;
/*   37:     */   public static final int KEY_W = 17;
/*   38:     */   public static final int KEY_E = 18;
/*   39:     */   public static final int KEY_R = 19;
/*   40:     */   public static final int KEY_T = 20;
/*   41:     */   public static final int KEY_Y = 21;
/*   42:     */   public static final int KEY_U = 22;
/*   43:     */   public static final int KEY_I = 23;
/*   44:     */   public static final int KEY_O = 24;
/*   45:     */   public static final int KEY_P = 25;
/*   46:     */   public static final int KEY_LBRACKET = 26;
/*   47:     */   public static final int KEY_RBRACKET = 27;
/*   48:     */   public static final int KEY_RETURN = 28;
/*   49:     */   public static final int KEY_ENTER = 28;
/*   50:     */   public static final int KEY_LCONTROL = 29;
/*   51:     */   public static final int KEY_A = 30;
/*   52:     */   public static final int KEY_S = 31;
/*   53:     */   public static final int KEY_D = 32;
/*   54:     */   public static final int KEY_F = 33;
/*   55:     */   public static final int KEY_G = 34;
/*   56:     */   public static final int KEY_H = 35;
/*   57:     */   public static final int KEY_J = 36;
/*   58:     */   public static final int KEY_K = 37;
/*   59:     */   public static final int KEY_L = 38;
/*   60:     */   public static final int KEY_SEMICOLON = 39;
/*   61:     */   public static final int KEY_APOSTROPHE = 40;
/*   62:     */   public static final int KEY_GRAVE = 41;
/*   63:     */   public static final int KEY_LSHIFT = 42;
/*   64:     */   public static final int KEY_BACKSLASH = 43;
/*   65:     */   public static final int KEY_Z = 44;
/*   66:     */   public static final int KEY_X = 45;
/*   67:     */   public static final int KEY_C = 46;
/*   68:     */   public static final int KEY_V = 47;
/*   69:     */   public static final int KEY_B = 48;
/*   70:     */   public static final int KEY_N = 49;
/*   71:     */   public static final int KEY_M = 50;
/*   72:     */   public static final int KEY_COMMA = 51;
/*   73:     */   public static final int KEY_PERIOD = 52;
/*   74:     */   public static final int KEY_SLASH = 53;
/*   75:     */   public static final int KEY_RSHIFT = 54;
/*   76:     */   public static final int KEY_MULTIPLY = 55;
/*   77:     */   public static final int KEY_LMENU = 56;
/*   78:     */   public static final int KEY_SPACE = 57;
/*   79:     */   public static final int KEY_CAPITAL = 58;
/*   80:     */   public static final int KEY_F1 = 59;
/*   81:     */   public static final int KEY_F2 = 60;
/*   82:     */   public static final int KEY_F3 = 61;
/*   83:     */   public static final int KEY_F4 = 62;
/*   84:     */   public static final int KEY_F5 = 63;
/*   85:     */   public static final int KEY_F6 = 64;
/*   86:     */   public static final int KEY_F7 = 65;
/*   87:     */   public static final int KEY_F8 = 66;
/*   88:     */   public static final int KEY_F9 = 67;
/*   89:     */   public static final int KEY_F10 = 68;
/*   90:     */   public static final int KEY_NUMLOCK = 69;
/*   91:     */   public static final int KEY_SCROLL = 70;
/*   92:     */   public static final int KEY_NUMPAD7 = 71;
/*   93:     */   public static final int KEY_NUMPAD8 = 72;
/*   94:     */   public static final int KEY_NUMPAD9 = 73;
/*   95:     */   public static final int KEY_SUBTRACT = 74;
/*   96:     */   public static final int KEY_NUMPAD4 = 75;
/*   97:     */   public static final int KEY_NUMPAD5 = 76;
/*   98:     */   public static final int KEY_NUMPAD6 = 77;
/*   99:     */   public static final int KEY_ADD = 78;
/*  100:     */   public static final int KEY_NUMPAD1 = 79;
/*  101:     */   public static final int KEY_NUMPAD2 = 80;
/*  102:     */   public static final int KEY_NUMPAD3 = 81;
/*  103:     */   public static final int KEY_NUMPAD0 = 82;
/*  104:     */   public static final int KEY_DECIMAL = 83;
/*  105:     */   public static final int KEY_F11 = 87;
/*  106:     */   public static final int KEY_F12 = 88;
/*  107:     */   public static final int KEY_F13 = 100;
/*  108:     */   public static final int KEY_F14 = 101;
/*  109:     */   public static final int KEY_F15 = 102;
/*  110:     */   public static final int KEY_KANA = 112;
/*  111:     */   public static final int KEY_CONVERT = 121;
/*  112:     */   public static final int KEY_NOCONVERT = 123;
/*  113:     */   public static final int KEY_YEN = 125;
/*  114:     */   public static final int KEY_NUMPADEQUALS = 141;
/*  115:     */   public static final int KEY_CIRCUMFLEX = 144;
/*  116:     */   public static final int KEY_AT = 145;
/*  117:     */   public static final int KEY_COLON = 146;
/*  118:     */   public static final int KEY_UNDERLINE = 147;
/*  119:     */   public static final int KEY_KANJI = 148;
/*  120:     */   public static final int KEY_STOP = 149;
/*  121:     */   public static final int KEY_AX = 150;
/*  122:     */   public static final int KEY_UNLABELED = 151;
/*  123:     */   public static final int KEY_NUMPADENTER = 156;
/*  124:     */   public static final int KEY_RCONTROL = 157;
/*  125:     */   public static final int KEY_NUMPADCOMMA = 179;
/*  126:     */   public static final int KEY_DIVIDE = 181;
/*  127:     */   public static final int KEY_SYSRQ = 183;
/*  128:     */   public static final int KEY_RMENU = 184;
/*  129:     */   public static final int KEY_PAUSE = 197;
/*  130:     */   public static final int KEY_HOME = 199;
/*  131:     */   public static final int KEY_UP = 200;
/*  132:     */   public static final int KEY_PRIOR = 201;
/*  133:     */   public static final int KEY_LEFT = 203;
/*  134:     */   public static final int KEY_RIGHT = 205;
/*  135:     */   public static final int KEY_END = 207;
/*  136:     */   public static final int KEY_DOWN = 208;
/*  137:     */   public static final int KEY_NEXT = 209;
/*  138:     */   public static final int KEY_INSERT = 210;
/*  139:     */   public static final int KEY_DELETE = 211;
/*  140:     */   public static final int KEY_LWIN = 219;
/*  141:     */   public static final int KEY_RWIN = 220;
/*  142:     */   public static final int KEY_APPS = 221;
/*  143:     */   public static final int KEY_POWER = 222;
/*  144:     */   public static final int KEY_SLEEP = 223;
/*  145:     */   public static final int KEY_LALT = 56;
/*  146:     */   public static final int KEY_RALT = 184;
/*  147:     */   private static final int LEFT = 0;
/*  148:     */   private static final int RIGHT = 1;
/*  149:     */   private static final int UP = 2;
/*  150:     */   private static final int DOWN = 3;
/*  151:     */   private static final int BUTTON1 = 4;
/*  152:     */   private static final int BUTTON2 = 5;
/*  153:     */   private static final int BUTTON3 = 6;
/*  154:     */   private static final int BUTTON4 = 7;
/*  155:     */   private static final int BUTTON5 = 8;
/*  156:     */   private static final int BUTTON6 = 9;
/*  157:     */   private static final int BUTTON7 = 10;
/*  158:     */   private static final int BUTTON8 = 11;
/*  159:     */   private static final int BUTTON9 = 12;
/*  160:     */   private static final int BUTTON10 = 13;
/*  161:     */   public static final int MOUSE_LEFT_BUTTON = 0;
/*  162:     */   public static final int MOUSE_RIGHT_BUTTON = 1;
/*  163:     */   public static final int MOUSE_MIDDLE_BUTTON = 2;
/*  164: 321 */   private static boolean controllersInited = false;
/*  165: 323 */   private static ArrayList controllers = new ArrayList();
/*  166:     */   private int lastMouseX;
/*  167:     */   private int lastMouseY;
/*  168: 330 */   protected boolean[] mousePressed = new boolean[10];
/*  169: 332 */   private boolean[][] controllerPressed = new boolean[100][100];
/*  170: 335 */   protected char[] keys = new char[1024];
/*  171: 337 */   protected boolean[] pressed = new boolean[1024];
/*  172: 339 */   protected long[] nextRepeat = new long[1024];
/*  173: 342 */   private boolean[][] controls = new boolean[10][110];
/*  174: 344 */   protected boolean consumed = false;
/*  175: 346 */   protected HashSet allListeners = new HashSet();
/*  176: 348 */   protected ArrayList keyListeners = new ArrayList();
/*  177: 350 */   protected ArrayList keyListenersToAdd = new ArrayList();
/*  178: 352 */   protected ArrayList mouseListeners = new ArrayList();
/*  179: 354 */   protected ArrayList mouseListenersToAdd = new ArrayList();
/*  180: 356 */   protected ArrayList controllerListeners = new ArrayList();
/*  181:     */   private int wheel;
/*  182:     */   private int height;
/*  183: 363 */   private boolean displayActive = true;
/*  184:     */   private boolean keyRepeat;
/*  185:     */   private int keyRepeatInitial;
/*  186:     */   private int keyRepeatInterval;
/*  187:     */   private boolean paused;
/*  188: 375 */   private float scaleX = 1.0F;
/*  189: 377 */   private float scaleY = 1.0F;
/*  190: 379 */   private float xoffset = 0.0F;
/*  191: 381 */   private float yoffset = 0.0F;
/*  192: 384 */   private int doubleClickDelay = 250;
/*  193: 386 */   private long doubleClickTimeout = 0L;
/*  194:     */   private int clickX;
/*  195:     */   private int clickY;
/*  196:     */   private int clickButton;
/*  197: 396 */   private int pressedX = -1;
/*  198: 399 */   private int pressedY = -1;
/*  199: 402 */   private int mouseClickTolerance = 5;
/*  200:     */   
/*  201:     */   public static void disableControllers()
/*  202:     */   {
/*  203: 409 */     controllersInited = true;
/*  204:     */   }
/*  205:     */   
/*  206:     */   public Input(int height)
/*  207:     */   {
/*  208: 418 */     init(height);
/*  209:     */   }
/*  210:     */   
/*  211:     */   public void setDoubleClickInterval(int delay)
/*  212:     */   {
/*  213: 429 */     this.doubleClickDelay = delay;
/*  214:     */   }
/*  215:     */   
/*  216:     */   public void setMouseClickTolerance(int mouseClickTolerance)
/*  217:     */   {
/*  218: 439 */     this.mouseClickTolerance = mouseClickTolerance;
/*  219:     */   }
/*  220:     */   
/*  221:     */   public void setScale(float scaleX, float scaleY)
/*  222:     */   {
/*  223: 449 */     this.scaleX = scaleX;
/*  224: 450 */     this.scaleY = scaleY;
/*  225:     */   }
/*  226:     */   
/*  227:     */   public void setOffset(float xoffset, float yoffset)
/*  228:     */   {
/*  229: 460 */     this.xoffset = xoffset;
/*  230: 461 */     this.yoffset = yoffset;
/*  231:     */   }
/*  232:     */   
/*  233:     */   public void resetInputTransform()
/*  234:     */   {
/*  235: 468 */     setOffset(0.0F, 0.0F);
/*  236: 469 */     setScale(1.0F, 1.0F);
/*  237:     */   }
/*  238:     */   
/*  239:     */   public void addListener(InputListener listener)
/*  240:     */   {
/*  241: 478 */     addKeyListener(listener);
/*  242: 479 */     addMouseListener(listener);
/*  243: 480 */     addControllerListener(listener);
/*  244:     */   }
/*  245:     */   
/*  246:     */   public void addKeyListener(KeyListener listener)
/*  247:     */   {
/*  248: 489 */     this.keyListenersToAdd.add(listener);
/*  249:     */   }
/*  250:     */   
/*  251:     */   private void addKeyListenerImpl(KeyListener listener)
/*  252:     */   {
/*  253: 498 */     if (this.keyListeners.contains(listener)) {
/*  254: 499 */       return;
/*  255:     */     }
/*  256: 501 */     this.keyListeners.add(listener);
/*  257: 502 */     this.allListeners.add(listener);
/*  258:     */   }
/*  259:     */   
/*  260:     */   public void addMouseListener(MouseListener listener)
/*  261:     */   {
/*  262: 511 */     this.mouseListenersToAdd.add(listener);
/*  263:     */   }
/*  264:     */   
/*  265:     */   private void addMouseListenerImpl(MouseListener listener)
/*  266:     */   {
/*  267: 520 */     if (this.mouseListeners.contains(listener)) {
/*  268: 521 */       return;
/*  269:     */     }
/*  270: 523 */     this.mouseListeners.add(listener);
/*  271: 524 */     this.allListeners.add(listener);
/*  272:     */   }
/*  273:     */   
/*  274:     */   public void addControllerListener(ControllerListener listener)
/*  275:     */   {
/*  276: 533 */     if (this.controllerListeners.contains(listener)) {
/*  277: 534 */       return;
/*  278:     */     }
/*  279: 536 */     this.controllerListeners.add(listener);
/*  280: 537 */     this.allListeners.add(listener);
/*  281:     */   }
/*  282:     */   
/*  283:     */   public void removeAllListeners()
/*  284:     */   {
/*  285: 544 */     removeAllKeyListeners();
/*  286: 545 */     removeAllMouseListeners();
/*  287: 546 */     removeAllControllerListeners();
/*  288:     */   }
/*  289:     */   
/*  290:     */   public void removeAllKeyListeners()
/*  291:     */   {
/*  292: 553 */     this.allListeners.removeAll(this.keyListeners);
/*  293: 554 */     this.keyListeners.clear();
/*  294:     */   }
/*  295:     */   
/*  296:     */   public void removeAllMouseListeners()
/*  297:     */   {
/*  298: 561 */     this.allListeners.removeAll(this.mouseListeners);
/*  299: 562 */     this.mouseListeners.clear();
/*  300:     */   }
/*  301:     */   
/*  302:     */   public void removeAllControllerListeners()
/*  303:     */   {
/*  304: 569 */     this.allListeners.removeAll(this.controllerListeners);
/*  305: 570 */     this.controllerListeners.clear();
/*  306:     */   }
/*  307:     */   
/*  308:     */   public void addPrimaryListener(InputListener listener)
/*  309:     */   {
/*  310: 580 */     removeListener(listener);
/*  311:     */     
/*  312: 582 */     this.keyListeners.add(0, listener);
/*  313: 583 */     this.mouseListeners.add(0, listener);
/*  314: 584 */     this.controllerListeners.add(0, listener);
/*  315:     */     
/*  316: 586 */     this.allListeners.add(listener);
/*  317:     */   }
/*  318:     */   
/*  319:     */   public void removeListener(InputListener listener)
/*  320:     */   {
/*  321: 595 */     removeKeyListener(listener);
/*  322: 596 */     removeMouseListener(listener);
/*  323: 597 */     removeControllerListener(listener);
/*  324:     */   }
/*  325:     */   
/*  326:     */   public void removeKeyListener(KeyListener listener)
/*  327:     */   {
/*  328: 606 */     this.keyListeners.remove(listener);
/*  329: 608 */     if ((!this.mouseListeners.contains(listener)) && (!this.controllerListeners.contains(listener))) {
/*  330: 609 */       this.allListeners.remove(listener);
/*  331:     */     }
/*  332:     */   }
/*  333:     */   
/*  334:     */   public void removeControllerListener(ControllerListener listener)
/*  335:     */   {
/*  336: 619 */     this.controllerListeners.remove(listener);
/*  337: 621 */     if ((!this.mouseListeners.contains(listener)) && (!this.keyListeners.contains(listener))) {
/*  338: 622 */       this.allListeners.remove(listener);
/*  339:     */     }
/*  340:     */   }
/*  341:     */   
/*  342:     */   public void removeMouseListener(MouseListener listener)
/*  343:     */   {
/*  344: 632 */     this.mouseListeners.remove(listener);
/*  345: 634 */     if ((!this.controllerListeners.contains(listener)) && (!this.keyListeners.contains(listener))) {
/*  346: 635 */       this.allListeners.remove(listener);
/*  347:     */     }
/*  348:     */   }
/*  349:     */   
/*  350:     */   void init(int height)
/*  351:     */   {
/*  352: 645 */     this.height = height;
/*  353: 646 */     this.lastMouseX = getMouseX();
/*  354: 647 */     this.lastMouseY = getMouseY();
/*  355:     */   }
/*  356:     */   
/*  357:     */   public static String getKeyName(int code)
/*  358:     */   {
/*  359: 657 */     return Keyboard.getKeyName(code);
/*  360:     */   }
/*  361:     */   
/*  362:     */   public boolean isKeyPressed(int code)
/*  363:     */   {
/*  364: 668 */     if (this.pressed[code] != 0)
/*  365:     */     {
/*  366: 669 */       this.pressed[code] = false;
/*  367: 670 */       return true;
/*  368:     */     }
/*  369: 673 */     return false;
/*  370:     */   }
/*  371:     */   
/*  372:     */   public boolean isMousePressed(int button)
/*  373:     */   {
/*  374: 683 */     if (this.mousePressed[button] != 0)
/*  375:     */     {
/*  376: 684 */       this.mousePressed[button] = false;
/*  377: 685 */       return true;
/*  378:     */     }
/*  379: 688 */     return false;
/*  380:     */   }
/*  381:     */   
/*  382:     */   public boolean isControlPressed(int button)
/*  383:     */   {
/*  384: 699 */     return isControlPressed(button, 0);
/*  385:     */   }
/*  386:     */   
/*  387:     */   public boolean isControlPressed(int button, int controller)
/*  388:     */   {
/*  389: 711 */     if (this.controllerPressed[controller][button] != 0)
/*  390:     */     {
/*  391: 712 */       this.controllerPressed[controller][button] = 0;
/*  392: 713 */       return true;
/*  393:     */     }
/*  394: 716 */     return false;
/*  395:     */   }
/*  396:     */   
/*  397:     */   public void clearControlPressedRecord()
/*  398:     */   {
/*  399: 724 */     for (int i = 0; i < controllers.size(); i++) {
/*  400: 725 */       Arrays.fill(this.controllerPressed[i], false);
/*  401:     */     }
/*  402:     */   }
/*  403:     */   
/*  404:     */   public void clearKeyPressedRecord()
/*  405:     */   {
/*  406: 735 */     Arrays.fill(this.pressed, false);
/*  407:     */   }
/*  408:     */   
/*  409:     */   public void clearMousePressedRecord()
/*  410:     */   {
/*  411: 744 */     Arrays.fill(this.mousePressed, false);
/*  412:     */   }
/*  413:     */   
/*  414:     */   public boolean isKeyDown(int code)
/*  415:     */   {
/*  416: 754 */     return Keyboard.isKeyDown(code);
/*  417:     */   }
/*  418:     */   
/*  419:     */   public int getAbsoluteMouseX()
/*  420:     */   {
/*  421: 763 */     return Mouse.getX();
/*  422:     */   }
/*  423:     */   
/*  424:     */   public int getAbsoluteMouseY()
/*  425:     */   {
/*  426: 772 */     return this.height - Mouse.getY();
/*  427:     */   }
/*  428:     */   
/*  429:     */   public int getMouseX()
/*  430:     */   {
/*  431: 781 */     return (int)(Mouse.getX() * this.scaleX + this.xoffset);
/*  432:     */   }
/*  433:     */   
/*  434:     */   public int getMouseY()
/*  435:     */   {
/*  436: 790 */     return (int)((this.height - Mouse.getY()) * this.scaleY + this.yoffset);
/*  437:     */   }
/*  438:     */   
/*  439:     */   public boolean isMouseButtonDown(int button)
/*  440:     */   {
/*  441: 800 */     return Mouse.isButtonDown(button);
/*  442:     */   }
/*  443:     */   
/*  444:     */   private boolean anyMouseDown()
/*  445:     */   {
/*  446: 809 */     for (int i = 0; i < 3; i++) {
/*  447: 810 */       if (Mouse.isButtonDown(i)) {
/*  448: 811 */         return true;
/*  449:     */       }
/*  450:     */     }
/*  451: 815 */     return false;
/*  452:     */   }
/*  453:     */   
/*  454:     */   public int getControllerCount()
/*  455:     */   {
/*  456:     */     try
/*  457:     */     {
/*  458: 825 */       initControllers();
/*  459:     */     }
/*  460:     */     catch (SlickException e)
/*  461:     */     {
/*  462: 827 */       throw new RuntimeException("Failed to initialise controllers");
/*  463:     */     }
/*  464: 830 */     return controllers.size();
/*  465:     */   }
/*  466:     */   
/*  467:     */   public int getAxisCount(int controller)
/*  468:     */   {
/*  469: 840 */     return ((Controller)controllers.get(controller)).getAxisCount();
/*  470:     */   }
/*  471:     */   
/*  472:     */   public float getAxisValue(int controller, int axis)
/*  473:     */   {
/*  474: 851 */     return ((Controller)controllers.get(controller)).getAxisValue(axis);
/*  475:     */   }
/*  476:     */   
/*  477:     */   public String getAxisName(int controller, int axis)
/*  478:     */   {
/*  479: 862 */     return ((Controller)controllers.get(controller)).getAxisName(axis);
/*  480:     */   }
/*  481:     */   
/*  482:     */   public boolean isControllerLeft(int controller)
/*  483:     */   {
/*  484: 872 */     if (controller >= getControllerCount()) {
/*  485: 873 */       return false;
/*  486:     */     }
/*  487: 876 */     if (controller == -1)
/*  488:     */     {
/*  489: 877 */       for (int i = 0; i < controllers.size(); i++) {
/*  490: 878 */         if (isControllerLeft(i)) {
/*  491: 879 */           return true;
/*  492:     */         }
/*  493:     */       }
/*  494: 883 */       return false;
/*  495:     */     }
/*  496: 886 */     return (((Controller)controllers.get(controller)).getXAxisValue() < -0.5F) || (
/*  497: 887 */       ((Controller)controllers.get(controller)).getPovX() < -0.5F);
/*  498:     */   }
/*  499:     */   
/*  500:     */   public boolean isControllerRight(int controller)
/*  501:     */   {
/*  502: 897 */     if (controller >= getControllerCount()) {
/*  503: 898 */       return false;
/*  504:     */     }
/*  505: 901 */     if (controller == -1)
/*  506:     */     {
/*  507: 902 */       for (int i = 0; i < controllers.size(); i++) {
/*  508: 903 */         if (isControllerRight(i)) {
/*  509: 904 */           return true;
/*  510:     */         }
/*  511:     */       }
/*  512: 908 */       return false;
/*  513:     */     }
/*  514: 911 */     return (((Controller)controllers.get(controller)).getXAxisValue() > 0.5F) || (
/*  515: 912 */       ((Controller)controllers.get(controller)).getPovX() > 0.5F);
/*  516:     */   }
/*  517:     */   
/*  518:     */   public boolean isControllerUp(int controller)
/*  519:     */   {
/*  520: 922 */     if (controller >= getControllerCount()) {
/*  521: 923 */       return false;
/*  522:     */     }
/*  523: 926 */     if (controller == -1)
/*  524:     */     {
/*  525: 927 */       for (int i = 0; i < controllers.size(); i++) {
/*  526: 928 */         if (isControllerUp(i)) {
/*  527: 929 */           return true;
/*  528:     */         }
/*  529:     */       }
/*  530: 933 */       return false;
/*  531:     */     }
/*  532: 935 */     return (((Controller)controllers.get(controller)).getYAxisValue() < -0.5F) || (
/*  533: 936 */       ((Controller)controllers.get(controller)).getPovY() < -0.5F);
/*  534:     */   }
/*  535:     */   
/*  536:     */   public boolean isControllerDown(int controller)
/*  537:     */   {
/*  538: 946 */     if (controller >= getControllerCount()) {
/*  539: 947 */       return false;
/*  540:     */     }
/*  541: 950 */     if (controller == -1)
/*  542:     */     {
/*  543: 951 */       for (int i = 0; i < controllers.size(); i++) {
/*  544: 952 */         if (isControllerDown(i)) {
/*  545: 953 */           return true;
/*  546:     */         }
/*  547:     */       }
/*  548: 957 */       return false;
/*  549:     */     }
/*  550: 960 */     return (((Controller)controllers.get(controller)).getYAxisValue() > 0.5F) || (
/*  551: 961 */       ((Controller)controllers.get(controller)).getPovY() > 0.5F);
/*  552:     */   }
/*  553:     */   
/*  554:     */   public boolean isButtonPressed(int index, int controller)
/*  555:     */   {
/*  556: 973 */     if (controller >= getControllerCount()) {
/*  557: 974 */       return false;
/*  558:     */     }
/*  559: 977 */     if (controller == -1)
/*  560:     */     {
/*  561: 978 */       for (int i = 0; i < controllers.size(); i++) {
/*  562: 979 */         if (isButtonPressed(index, i)) {
/*  563: 980 */           return true;
/*  564:     */         }
/*  565:     */       }
/*  566: 984 */       return false;
/*  567:     */     }
/*  568: 987 */     return ((Controller)controllers.get(controller)).isButtonPressed(index);
/*  569:     */   }
/*  570:     */   
/*  571:     */   public boolean isButton1Pressed(int controller)
/*  572:     */   {
/*  573: 997 */     return isButtonPressed(0, controller);
/*  574:     */   }
/*  575:     */   
/*  576:     */   public boolean isButton2Pressed(int controller)
/*  577:     */   {
/*  578:1007 */     return isButtonPressed(1, controller);
/*  579:     */   }
/*  580:     */   
/*  581:     */   public boolean isButton3Pressed(int controller)
/*  582:     */   {
/*  583:1017 */     return isButtonPressed(2, controller);
/*  584:     */   }
/*  585:     */   
/*  586:     */   public void initControllers()
/*  587:     */     throws SlickException
/*  588:     */   {
/*  589:1026 */     if (controllersInited) {
/*  590:1027 */       return;
/*  591:     */     }
/*  592:1030 */     controllersInited = true;
/*  593:     */     try
/*  594:     */     {
/*  595:1032 */       Controllers.create();
/*  596:1033 */       int count = Controllers.getControllerCount();
/*  597:1035 */       for (int i = 0; i < count; i++)
/*  598:     */       {
/*  599:1036 */         Controller controller = Controllers.getController(i);
/*  600:1038 */         if ((controller.getButtonCount() >= 3) && (controller.getButtonCount() < 100)) {
/*  601:1039 */           controllers.add(controller);
/*  602:     */         }
/*  603:     */       }
/*  604:1043 */       Log.info("Found " + controllers.size() + " controllers");
/*  605:1044 */       for (int i = 0; i < controllers.size(); i++) {
/*  606:1045 */         Log.info(i + " : " + ((Controller)controllers.get(i)).getName());
/*  607:     */       }
/*  608:     */     }
/*  609:     */     catch (LWJGLException e)
/*  610:     */     {
/*  611:1048 */       if ((e.getCause() instanceof ClassNotFoundException)) {
/*  612:1049 */         throw new SlickException("Unable to create controller - no jinput found - add jinput.jar to your classpath");
/*  613:     */       }
/*  614:1051 */       throw new SlickException("Unable to create controllers");
/*  615:     */     }
/*  616:     */     catch (NoClassDefFoundError localNoClassDefFoundError) {}
/*  617:     */   }
/*  618:     */   
/*  619:     */   public void consumeEvent()
/*  620:     */   {
/*  621:1061 */     this.consumed = true;
/*  622:     */   }
/*  623:     */   
/*  624:     */   private int resolveEventKey(int key, char c)
/*  625:     */   {
/*  626:1090 */     if ((c == '=') || (key == 0)) {
/*  627:1091 */       return 13;
/*  628:     */     }
/*  629:1094 */     return key;
/*  630:     */   }
/*  631:     */   
/*  632:     */   public void considerDoubleClick(int button, int x, int y)
/*  633:     */   {
/*  634:1106 */     if (this.doubleClickTimeout == 0L)
/*  635:     */     {
/*  636:1107 */       this.clickX = x;
/*  637:1108 */       this.clickY = y;
/*  638:1109 */       this.clickButton = button;
/*  639:1110 */       this.doubleClickTimeout = (System.currentTimeMillis() + this.doubleClickDelay);
/*  640:1111 */       fireMouseClicked(button, x, y, 1);
/*  641:     */     }
/*  642:1113 */     else if ((this.clickButton == button) && 
/*  643:1114 */       (System.currentTimeMillis() < this.doubleClickTimeout))
/*  644:     */     {
/*  645:1115 */       fireMouseClicked(button, x, y, 2);
/*  646:1116 */       this.doubleClickTimeout = 0L;
/*  647:     */     }
/*  648:     */   }
/*  649:     */   
/*  650:     */   public void poll(int width, int height)
/*  651:     */   {
/*  652:1129 */     if (this.paused)
/*  653:     */     {
/*  654:1130 */       clearControlPressedRecord();
/*  655:1131 */       clearKeyPressedRecord();
/*  656:1132 */       clearMousePressedRecord();
/*  657:1134 */       while (Keyboard.next()) {}
/*  658:1135 */       while (Mouse.next()) {}
/*  659:1136 */       return;
/*  660:     */     }
/*  661:1139 */     if (!Display.isActive())
/*  662:     */     {
/*  663:1140 */       clearControlPressedRecord();
/*  664:1141 */       clearKeyPressedRecord();
/*  665:1142 */       clearMousePressedRecord();
/*  666:     */     }
/*  667:1146 */     for (int i = 0; i < this.keyListenersToAdd.size(); i++) {
/*  668:1147 */       addKeyListenerImpl((KeyListener)this.keyListenersToAdd.get(i));
/*  669:     */     }
/*  670:1149 */     this.keyListenersToAdd.clear();
/*  671:1150 */     for (int i = 0; i < this.mouseListenersToAdd.size(); i++) {
/*  672:1151 */       addMouseListenerImpl((MouseListener)this.mouseListenersToAdd.get(i));
/*  673:     */     }
/*  674:1153 */     this.mouseListenersToAdd.clear();
/*  675:1155 */     if ((this.doubleClickTimeout != 0L) && 
/*  676:1156 */       (System.currentTimeMillis() > this.doubleClickTimeout)) {
/*  677:1157 */       this.doubleClickTimeout = 0L;
/*  678:     */     }
/*  679:1161 */     this.height = height;
/*  680:     */     
/*  681:1163 */     Iterator allStarts = this.allListeners.iterator();
/*  682:1164 */     while (allStarts.hasNext())
/*  683:     */     {
/*  684:1165 */       ControlledInputReciever listener = (ControlledInputReciever)allStarts.next();
/*  685:1166 */       listener.inputStarted();
/*  686:     */     }
/*  687:1169 */     while (Keyboard.next()) {
/*  688:1170 */       if (Keyboard.getEventKeyState())
/*  689:     */       {
/*  690:1171 */         int eventKey = resolveEventKey(Keyboard.getEventKey(), Keyboard.getEventCharacter());
/*  691:     */         
/*  692:1173 */         this.keys[eventKey] = Keyboard.getEventCharacter();
/*  693:1174 */         this.pressed[eventKey] = true;
/*  694:1175 */         this.nextRepeat[eventKey] = (System.currentTimeMillis() + this.keyRepeatInitial);
/*  695:     */         
/*  696:1177 */         this.consumed = false;
/*  697:1178 */         for (int i = 0; i < this.keyListeners.size(); i++)
/*  698:     */         {
/*  699:1179 */           KeyListener listener = (KeyListener)this.keyListeners.get(i);
/*  700:1181 */           if (listener.isAcceptingInput())
/*  701:     */           {
/*  702:1182 */             listener.keyPressed(eventKey, Keyboard.getEventCharacter());
/*  703:1183 */             if (this.consumed) {
/*  704:     */               break;
/*  705:     */             }
/*  706:     */           }
/*  707:     */         }
/*  708:     */       }
/*  709:     */       else
/*  710:     */       {
/*  711:1189 */         int eventKey = resolveEventKey(Keyboard.getEventKey(), Keyboard.getEventCharacter());
/*  712:1190 */         this.nextRepeat[eventKey] = 0L;
/*  713:     */         
/*  714:1192 */         this.consumed = false;
/*  715:1193 */         for (int i = 0; i < this.keyListeners.size(); i++)
/*  716:     */         {
/*  717:1194 */           KeyListener listener = (KeyListener)this.keyListeners.get(i);
/*  718:1195 */           if (listener.isAcceptingInput())
/*  719:     */           {
/*  720:1196 */             listener.keyReleased(eventKey, this.keys[eventKey]);
/*  721:1197 */             if (this.consumed) {
/*  722:     */               break;
/*  723:     */             }
/*  724:     */           }
/*  725:     */         }
/*  726:     */       }
/*  727:     */     }
/*  728:1205 */     while (Mouse.next()) {
/*  729:1206 */       if (Mouse.getEventButton() >= 0)
/*  730:     */       {
/*  731:1207 */         if (Mouse.getEventButtonState())
/*  732:     */         {
/*  733:1208 */           this.consumed = false;
/*  734:1209 */           this.mousePressed[Mouse.getEventButton()] = true;
/*  735:     */           
/*  736:1211 */           this.pressedX = ((int)(this.xoffset + Mouse.getEventX() * this.scaleX));
/*  737:1212 */           this.pressedY = ((int)(this.yoffset + (height - Mouse.getEventY()) * this.scaleY));
/*  738:1214 */           for (int i = 0; i < this.mouseListeners.size(); i++)
/*  739:     */           {
/*  740:1215 */             MouseListener listener = (MouseListener)this.mouseListeners.get(i);
/*  741:1216 */             if (listener.isAcceptingInput())
/*  742:     */             {
/*  743:1217 */               listener.mousePressed(Mouse.getEventButton(), this.pressedX, this.pressedY);
/*  744:1218 */               if (this.consumed) {
/*  745:     */                 break;
/*  746:     */               }
/*  747:     */             }
/*  748:     */           }
/*  749:     */         }
/*  750:     */         else
/*  751:     */         {
/*  752:1224 */           this.consumed = false;
/*  753:1225 */           this.mousePressed[Mouse.getEventButton()] = false;
/*  754:     */           
/*  755:1227 */           int releasedX = (int)(this.xoffset + Mouse.getEventX() * this.scaleX);
/*  756:1228 */           int releasedY = (int)(this.yoffset + (height - Mouse.getEventY()) * this.scaleY);
/*  757:1229 */           if ((this.pressedX != -1) && 
/*  758:1230 */             (this.pressedY != -1) && 
/*  759:1231 */             (Math.abs(this.pressedX - releasedX) < this.mouseClickTolerance) && 
/*  760:1232 */             (Math.abs(this.pressedY - releasedY) < this.mouseClickTolerance))
/*  761:     */           {
/*  762:1233 */             considerDoubleClick(Mouse.getEventButton(), releasedX, releasedY);
/*  763:1234 */             this.pressedX = (this.pressedY = -1);
/*  764:     */           }
/*  765:1237 */           for (int i = 0; i < this.mouseListeners.size(); i++)
/*  766:     */           {
/*  767:1238 */             MouseListener listener = (MouseListener)this.mouseListeners.get(i);
/*  768:1239 */             if (listener.isAcceptingInput())
/*  769:     */             {
/*  770:1240 */               listener.mouseReleased(Mouse.getEventButton(), releasedX, releasedY);
/*  771:1241 */               if (this.consumed) {
/*  772:     */                 break;
/*  773:     */               }
/*  774:     */             }
/*  775:     */           }
/*  776:     */         }
/*  777:     */       }
/*  778:     */       else
/*  779:     */       {
/*  780:1248 */         if ((Mouse.isGrabbed()) && (this.displayActive) && (
/*  781:1249 */           (Mouse.getEventDX() != 0) || (Mouse.getEventDY() != 0)))
/*  782:     */         {
/*  783:1250 */           this.consumed = false;
/*  784:1251 */           for (int i = 0; i < this.mouseListeners.size(); i++)
/*  785:     */           {
/*  786:1252 */             MouseListener listener = (MouseListener)this.mouseListeners.get(i);
/*  787:1253 */             if (listener.isAcceptingInput())
/*  788:     */             {
/*  789:1254 */               if (anyMouseDown()) {
/*  790:1255 */                 listener.mouseDragged(0, 0, Mouse.getEventDX(), -Mouse.getEventDY());
/*  791:     */               } else {
/*  792:1257 */                 listener.mouseMoved(0, 0, Mouse.getEventDX(), -Mouse.getEventDY());
/*  793:     */               }
/*  794:1260 */               if (this.consumed) {
/*  795:     */                 break;
/*  796:     */               }
/*  797:     */             }
/*  798:     */           }
/*  799:     */         }
/*  800:1268 */         int dwheel = Mouse.getEventDWheel();
/*  801:1269 */         this.wheel += dwheel;
/*  802:1270 */         if (dwheel != 0)
/*  803:     */         {
/*  804:1271 */           this.consumed = false;
/*  805:1272 */           for (int i = 0; i < this.mouseListeners.size(); i++)
/*  806:     */           {
/*  807:1273 */             MouseListener listener = (MouseListener)this.mouseListeners.get(i);
/*  808:1274 */             if (listener.isAcceptingInput())
/*  809:     */             {
/*  810:1275 */               listener.mouseWheelMoved(dwheel);
/*  811:1276 */               if (this.consumed) {
/*  812:     */                 break;
/*  813:     */               }
/*  814:     */             }
/*  815:     */           }
/*  816:     */         }
/*  817:     */       }
/*  818:     */     }
/*  819:1285 */     if ((!this.displayActive) || (Mouse.isGrabbed()))
/*  820:     */     {
/*  821:1286 */       this.lastMouseX = getMouseX();
/*  822:1287 */       this.lastMouseY = getMouseY();
/*  823:     */     }
/*  824:1289 */     else if ((this.lastMouseX != getMouseX()) || (this.lastMouseY != getMouseY()))
/*  825:     */     {
/*  826:1290 */       this.consumed = false;
/*  827:1291 */       for (int i = 0; i < this.mouseListeners.size(); i++)
/*  828:     */       {
/*  829:1292 */         MouseListener listener = (MouseListener)this.mouseListeners.get(i);
/*  830:1293 */         if (listener.isAcceptingInput())
/*  831:     */         {
/*  832:1294 */           if (anyMouseDown()) {
/*  833:1295 */             listener.mouseDragged(this.lastMouseX, this.lastMouseY, getMouseX(), getMouseY());
/*  834:     */           } else {
/*  835:1297 */             listener.mouseMoved(this.lastMouseX, this.lastMouseY, getMouseX(), getMouseY());
/*  836:     */           }
/*  837:1299 */           if (this.consumed) {
/*  838:     */             break;
/*  839:     */           }
/*  840:     */         }
/*  841:     */       }
/*  842:1304 */       this.lastMouseX = getMouseX();
/*  843:1305 */       this.lastMouseY = getMouseY();
/*  844:     */     }
/*  845:1309 */     if (controllersInited) {
/*  846:1310 */       for (int i = 0; i < getControllerCount(); i++)
/*  847:     */       {
/*  848:1311 */         int count = ((Controller)controllers.get(i)).getButtonCount() + 3;
/*  849:1312 */         count = Math.min(count, 24);
/*  850:1313 */         for (int c = 0; c <= count; c++) {
/*  851:1314 */           if ((this.controls[i][c] != 0) && (!isControlDwn(c, i)))
/*  852:     */           {
/*  853:1315 */             this.controls[i][c] = 0;
/*  854:1316 */             fireControlRelease(c, i);
/*  855:     */           }
/*  856:1317 */           else if ((this.controls[i][c] == 0) && (isControlDwn(c, i)))
/*  857:     */           {
/*  858:1318 */             this.controllerPressed[i][c] = 1;
/*  859:1319 */             this.controls[i][c] = 1;
/*  860:1320 */             fireControlPress(c, i);
/*  861:     */           }
/*  862:     */         }
/*  863:     */       }
/*  864:     */     }
/*  865:1326 */     if (this.keyRepeat) {
/*  866:1327 */       for (int i = 0; i < 1024; i++) {
/*  867:1328 */         if ((this.pressed[i] != 0) && (this.nextRepeat[i] != 0L) && 
/*  868:1329 */           (System.currentTimeMillis() > this.nextRepeat[i]))
/*  869:     */         {
/*  870:1330 */           this.nextRepeat[i] = (System.currentTimeMillis() + this.keyRepeatInterval);
/*  871:1331 */           this.consumed = false;
/*  872:1332 */           for (int j = 0; j < this.keyListeners.size(); j++)
/*  873:     */           {
/*  874:1333 */             KeyListener listener = (KeyListener)this.keyListeners.get(j);
/*  875:1335 */             if (listener.isAcceptingInput())
/*  876:     */             {
/*  877:1336 */               listener.keyPressed(i, this.keys[i]);
/*  878:1337 */               if (this.consumed) {
/*  879:     */                 break;
/*  880:     */               }
/*  881:     */             }
/*  882:     */           }
/*  883:     */         }
/*  884:     */       }
/*  885:     */     }
/*  886:1348 */     Iterator all = this.allListeners.iterator();
/*  887:1349 */     while (all.hasNext())
/*  888:     */     {
/*  889:1350 */       ControlledInputReciever listener = (ControlledInputReciever)all.next();
/*  890:1351 */       listener.inputEnded();
/*  891:     */     }
/*  892:1354 */     if (Display.isCreated()) {
/*  893:1355 */       this.displayActive = Display.isActive();
/*  894:     */     }
/*  895:     */   }
/*  896:     */   
/*  897:     */   /**
/*  898:     */    * @deprecated
/*  899:     */    */
/*  900:     */   public void enableKeyRepeat(int initial, int interval)
/*  901:     */   {
/*  902:1368 */     Keyboard.enableRepeatEvents(true);
/*  903:     */   }
/*  904:     */   
/*  905:     */   public void enableKeyRepeat()
/*  906:     */   {
/*  907:1376 */     Keyboard.enableRepeatEvents(true);
/*  908:     */   }
/*  909:     */   
/*  910:     */   public void disableKeyRepeat()
/*  911:     */   {
/*  912:1383 */     Keyboard.enableRepeatEvents(false);
/*  913:     */   }
/*  914:     */   
/*  915:     */   public boolean isKeyRepeatEnabled()
/*  916:     */   {
/*  917:1392 */     return Keyboard.areRepeatEventsEnabled();
/*  918:     */   }
/*  919:     */   
/*  920:     */   private void fireControlPress(int index, int controllerIndex)
/*  921:     */   {
/*  922:1402 */     this.consumed = false;
/*  923:1403 */     for (int i = 0; i < this.controllerListeners.size(); i++)
/*  924:     */     {
/*  925:1404 */       ControllerListener listener = (ControllerListener)this.controllerListeners.get(i);
/*  926:1405 */       if (listener.isAcceptingInput())
/*  927:     */       {
/*  928:1406 */         switch (index)
/*  929:     */         {
/*  930:     */         case 0: 
/*  931:1408 */           listener.controllerLeftPressed(controllerIndex);
/*  932:1409 */           break;
/*  933:     */         case 1: 
/*  934:1411 */           listener.controllerRightPressed(controllerIndex);
/*  935:1412 */           break;
/*  936:     */         case 2: 
/*  937:1414 */           listener.controllerUpPressed(controllerIndex);
/*  938:1415 */           break;
/*  939:     */         case 3: 
/*  940:1417 */           listener.controllerDownPressed(controllerIndex);
/*  941:1418 */           break;
/*  942:     */         default: 
/*  943:1421 */           listener.controllerButtonPressed(controllerIndex, index - 4 + 1);
/*  944:     */         }
/*  945:1424 */         if (this.consumed) {
/*  946:     */           break;
/*  947:     */         }
/*  948:     */       }
/*  949:     */     }
/*  950:     */   }
/*  951:     */   
/*  952:     */   private void fireControlRelease(int index, int controllerIndex)
/*  953:     */   {
/*  954:1438 */     this.consumed = false;
/*  955:1439 */     for (int i = 0; i < this.controllerListeners.size(); i++)
/*  956:     */     {
/*  957:1440 */       ControllerListener listener = (ControllerListener)this.controllerListeners.get(i);
/*  958:1441 */       if (listener.isAcceptingInput())
/*  959:     */       {
/*  960:1442 */         switch (index)
/*  961:     */         {
/*  962:     */         case 0: 
/*  963:1444 */           listener.controllerLeftReleased(controllerIndex);
/*  964:1445 */           break;
/*  965:     */         case 1: 
/*  966:1447 */           listener.controllerRightReleased(controllerIndex);
/*  967:1448 */           break;
/*  968:     */         case 2: 
/*  969:1450 */           listener.controllerUpReleased(controllerIndex);
/*  970:1451 */           break;
/*  971:     */         case 3: 
/*  972:1453 */           listener.controllerDownReleased(controllerIndex);
/*  973:1454 */           break;
/*  974:     */         default: 
/*  975:1457 */           listener.controllerButtonReleased(controllerIndex, index - 4 + 1);
/*  976:     */         }
/*  977:1460 */         if (this.consumed) {
/*  978:     */           break;
/*  979:     */         }
/*  980:     */       }
/*  981:     */     }
/*  982:     */   }
/*  983:     */   
/*  984:     */   private boolean isControlDwn(int index, int controllerIndex)
/*  985:     */   {
/*  986:1475 */     switch (index)
/*  987:     */     {
/*  988:     */     case 0: 
/*  989:1477 */       return isControllerLeft(controllerIndex);
/*  990:     */     case 1: 
/*  991:1479 */       return isControllerRight(controllerIndex);
/*  992:     */     case 2: 
/*  993:1481 */       return isControllerUp(controllerIndex);
/*  994:     */     case 3: 
/*  995:1483 */       return isControllerDown(controllerIndex);
/*  996:     */     }
/*  997:1486 */     if (index >= 4) {
/*  998:1487 */       return isButtonPressed(index - 4, controllerIndex);
/*  999:     */     }
/* 1000:1490 */     throw new RuntimeException("Unknown control index");
/* 1001:     */   }
/* 1002:     */   
/* 1003:     */   public void pause()
/* 1004:     */   {
/* 1005:1498 */     this.paused = true;
/* 1006:     */     
/* 1007:     */ 
/* 1008:1501 */     clearKeyPressedRecord();
/* 1009:1502 */     clearMousePressedRecord();
/* 1010:1503 */     clearControlPressedRecord();
/* 1011:     */   }
/* 1012:     */   
/* 1013:     */   public void resume()
/* 1014:     */   {
/* 1015:1510 */     this.paused = false;
/* 1016:     */   }
/* 1017:     */   
/* 1018:     */   private void fireMouseClicked(int button, int x, int y, int clickCount)
/* 1019:     */   {
/* 1020:1522 */     this.consumed = false;
/* 1021:1523 */     for (int i = 0; i < this.mouseListeners.size(); i++)
/* 1022:     */     {
/* 1023:1524 */       MouseListener listener = (MouseListener)this.mouseListeners.get(i);
/* 1024:1525 */       if (listener.isAcceptingInput())
/* 1025:     */       {
/* 1026:1526 */         listener.mouseClicked(button, x, y, clickCount);
/* 1027:1527 */         if (this.consumed) {
/* 1028:     */           break;
/* 1029:     */         }
/* 1030:     */       }
/* 1031:     */     }
/* 1032:     */   }
/* 1033:     */   
/* 1034:     */   private class NullOutputStream
/* 1035:     */     extends OutputStream
/* 1036:     */   {
/* 1037:     */     private NullOutputStream() {}
/* 1038:     */     
/* 1039:     */     public void write(int b)
/* 1040:     */       throws IOException
/* 1041:     */     {}
/* 1042:     */   }
/* 1043:     */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.Input
 * JD-Core Version:    0.7.0.1
 */