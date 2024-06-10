/*   1:    */ package org.newdawn.slick.state;
/*   2:    */ 
/*   3:    */ import java.util.Collection;
/*   4:    */ import java.util.HashMap;
/*   5:    */ import java.util.Iterator;
/*   6:    */ import java.util.Set;
/*   7:    */ import org.newdawn.slick.Game;
/*   8:    */ import org.newdawn.slick.GameContainer;
/*   9:    */ import org.newdawn.slick.Graphics;
/*  10:    */ import org.newdawn.slick.Input;
/*  11:    */ import org.newdawn.slick.InputListener;
/*  12:    */ import org.newdawn.slick.SlickException;
/*  13:    */ import org.newdawn.slick.state.transition.EmptyTransition;
/*  14:    */ import org.newdawn.slick.state.transition.Transition;
/*  15:    */ 
/*  16:    */ public abstract class StateBasedGame
/*  17:    */   implements Game, InputListener
/*  18:    */ {
/*  19: 23 */   private HashMap states = new HashMap();
/*  20:    */   private GameState currentState;
/*  21:    */   private GameState nextState;
/*  22:    */   private GameContainer container;
/*  23:    */   private String title;
/*  24:    */   private Transition enterTransition;
/*  25:    */   private Transition leaveTransition;
/*  26:    */   
/*  27:    */   public StateBasedGame(String name)
/*  28:    */   {
/*  29: 44 */     this.title = name;
/*  30:    */     
/*  31: 46 */     this.currentState = new BasicGameState()
/*  32:    */     {
/*  33:    */       public int getID()
/*  34:    */       {
/*  35: 48 */         return -1;
/*  36:    */       }
/*  37:    */       
/*  38:    */       public void init(GameContainer container, StateBasedGame game)
/*  39:    */         throws SlickException
/*  40:    */       {}
/*  41:    */       
/*  42:    */       public void render(StateBasedGame game, Graphics g)
/*  43:    */         throws SlickException
/*  44:    */       {}
/*  45:    */       
/*  46:    */       public void update(GameContainer container, StateBasedGame game, int delta)
/*  47:    */         throws SlickException
/*  48:    */       {}
/*  49:    */       
/*  50:    */       public void render(GameContainer container, StateBasedGame game, Graphics g)
/*  51:    */         throws SlickException
/*  52:    */       {}
/*  53:    */     };
/*  54:    */   }
/*  55:    */   
/*  56:    */   public void inputStarted() {}
/*  57:    */   
/*  58:    */   public int getStateCount()
/*  59:    */   {
/*  60: 74 */     return this.states.keySet().size();
/*  61:    */   }
/*  62:    */   
/*  63:    */   public int getCurrentStateID()
/*  64:    */   {
/*  65: 83 */     return this.currentState.getID();
/*  66:    */   }
/*  67:    */   
/*  68:    */   public GameState getCurrentState()
/*  69:    */   {
/*  70: 92 */     return this.currentState;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public void setInput(Input input) {}
/*  74:    */   
/*  75:    */   public void addState(GameState state)
/*  76:    */   {
/*  77:108 */     this.states.put(new Integer(state.getID()), state);
/*  78:110 */     if (this.currentState.getID() == -1) {
/*  79:111 */       this.currentState = state;
/*  80:    */     }
/*  81:    */   }
/*  82:    */   
/*  83:    */   public GameState getState(int id)
/*  84:    */   {
/*  85:122 */     return (GameState)this.states.get(new Integer(id));
/*  86:    */   }
/*  87:    */   
/*  88:    */   public void enterState(int id)
/*  89:    */   {
/*  90:131 */     enterState(id, new EmptyTransition(), new EmptyTransition());
/*  91:    */   }
/*  92:    */   
/*  93:    */   public void enterState(int id, Transition leave, Transition enter)
/*  94:    */   {
/*  95:142 */     if (leave == null) {
/*  96:143 */       leave = new EmptyTransition();
/*  97:    */     }
/*  98:145 */     if (enter == null) {
/*  99:146 */       enter = new EmptyTransition();
/* 100:    */     }
/* 101:148 */     this.leaveTransition = leave;
/* 102:149 */     this.enterTransition = enter;
/* 103:    */     
/* 104:151 */     this.nextState = getState(id);
/* 105:152 */     if (this.nextState == null) {
/* 106:153 */       throw new RuntimeException("No game state registered with the ID: " + id);
/* 107:    */     }
/* 108:156 */     this.leaveTransition.init(this.currentState, this.nextState);
/* 109:    */   }
/* 110:    */   
/* 111:    */   public final void init(GameContainer container)
/* 112:    */     throws SlickException
/* 113:    */   {
/* 114:163 */     this.container = container;
/* 115:164 */     initStatesList(container);
/* 116:    */     
/* 117:166 */     Iterator gameStates = this.states.values().iterator();
/* 118:168 */     while (gameStates.hasNext())
/* 119:    */     {
/* 120:169 */       GameState state = (GameState)gameStates.next();
/* 121:    */       
/* 122:171 */       state.init(container, this);
/* 123:    */     }
/* 124:174 */     if (this.currentState != null) {
/* 125:175 */       this.currentState.enter(container, this);
/* 126:    */     }
/* 127:    */   }
/* 128:    */   
/* 129:    */   public abstract void initStatesList(GameContainer paramGameContainer)
/* 130:    */     throws SlickException;
/* 131:    */   
/* 132:    */   public final void render(GameContainer container, Graphics g)
/* 133:    */     throws SlickException
/* 134:    */   {
/* 135:191 */     preRenderState(container, g);
/* 136:193 */     if (this.leaveTransition != null) {
/* 137:194 */       this.leaveTransition.preRender(this, container, g);
/* 138:195 */     } else if (this.enterTransition != null) {
/* 139:196 */       this.enterTransition.preRender(this, container, g);
/* 140:    */     }
/* 141:199 */     this.currentState.render(container, this, g);
/* 142:201 */     if (this.leaveTransition != null) {
/* 143:202 */       this.leaveTransition.postRender(this, container, g);
/* 144:203 */     } else if (this.enterTransition != null) {
/* 145:204 */       this.enterTransition.postRender(this, container, g);
/* 146:    */     }
/* 147:207 */     postRenderState(container, g);
/* 148:    */   }
/* 149:    */   
/* 150:    */   protected void preRenderState(GameContainer container, Graphics g)
/* 151:    */     throws SlickException
/* 152:    */   {}
/* 153:    */   
/* 154:    */   protected void postRenderState(GameContainer container, Graphics g)
/* 155:    */     throws SlickException
/* 156:    */   {}
/* 157:    */   
/* 158:    */   public final void update(GameContainer container, int delta)
/* 159:    */     throws SlickException
/* 160:    */   {
/* 161:238 */     preUpdateState(container, delta);
/* 162:240 */     if (this.leaveTransition != null)
/* 163:    */     {
/* 164:241 */       this.leaveTransition.update(this, container, delta);
/* 165:242 */       if (this.leaveTransition.isComplete())
/* 166:    */       {
/* 167:243 */         this.currentState.leave(container, this);
/* 168:244 */         GameState prevState = this.currentState;
/* 169:245 */         this.currentState = this.nextState;
/* 170:246 */         this.nextState = null;
/* 171:247 */         this.leaveTransition = null;
/* 172:248 */         this.currentState.enter(container, this);
/* 173:249 */         if (this.enterTransition != null) {
/* 174:250 */           this.enterTransition.init(this.currentState, prevState);
/* 175:    */         }
/* 176:    */       }
/* 177:    */       else
/* 178:    */       {
/* 179:253 */         return;
/* 180:    */       }
/* 181:    */     }
/* 182:257 */     if (this.enterTransition != null)
/* 183:    */     {
/* 184:258 */       this.enterTransition.update(this, container, delta);
/* 185:259 */       if (this.enterTransition.isComplete()) {
/* 186:260 */         this.enterTransition = null;
/* 187:    */       } else {
/* 188:262 */         return;
/* 189:    */       }
/* 190:    */     }
/* 191:266 */     this.currentState.update(container, this, delta);
/* 192:    */     
/* 193:268 */     postUpdateState(container, delta);
/* 194:    */   }
/* 195:    */   
/* 196:    */   protected void preUpdateState(GameContainer container, int delta)
/* 197:    */     throws SlickException
/* 198:    */   {}
/* 199:    */   
/* 200:    */   protected void postUpdateState(GameContainer container, int delta)
/* 201:    */     throws SlickException
/* 202:    */   {}
/* 203:    */   
/* 204:    */   private boolean transitioning()
/* 205:    */   {
/* 206:301 */     return (this.leaveTransition != null) || (this.enterTransition != null);
/* 207:    */   }
/* 208:    */   
/* 209:    */   public boolean closeRequested()
/* 210:    */   {
/* 211:308 */     return true;
/* 212:    */   }
/* 213:    */   
/* 214:    */   public String getTitle()
/* 215:    */   {
/* 216:315 */     return this.title;
/* 217:    */   }
/* 218:    */   
/* 219:    */   public GameContainer getContainer()
/* 220:    */   {
/* 221:324 */     return this.container;
/* 222:    */   }
/* 223:    */   
/* 224:    */   public void controllerButtonPressed(int controller, int button)
/* 225:    */   {
/* 226:331 */     if (transitioning()) {
/* 227:332 */       return;
/* 228:    */     }
/* 229:335 */     this.currentState.controllerButtonPressed(controller, button);
/* 230:    */   }
/* 231:    */   
/* 232:    */   public void controllerButtonReleased(int controller, int button)
/* 233:    */   {
/* 234:342 */     if (transitioning()) {
/* 235:343 */       return;
/* 236:    */     }
/* 237:346 */     this.currentState.controllerButtonReleased(controller, button);
/* 238:    */   }
/* 239:    */   
/* 240:    */   public void controllerDownPressed(int controller)
/* 241:    */   {
/* 242:353 */     if (transitioning()) {
/* 243:354 */       return;
/* 244:    */     }
/* 245:357 */     this.currentState.controllerDownPressed(controller);
/* 246:    */   }
/* 247:    */   
/* 248:    */   public void controllerDownReleased(int controller)
/* 249:    */   {
/* 250:364 */     if (transitioning()) {
/* 251:365 */       return;
/* 252:    */     }
/* 253:368 */     this.currentState.controllerDownReleased(controller);
/* 254:    */   }
/* 255:    */   
/* 256:    */   public void controllerLeftPressed(int controller)
/* 257:    */   {
/* 258:375 */     if (transitioning()) {
/* 259:376 */       return;
/* 260:    */     }
/* 261:379 */     this.currentState.controllerLeftPressed(controller);
/* 262:    */   }
/* 263:    */   
/* 264:    */   public void controllerLeftReleased(int controller)
/* 265:    */   {
/* 266:386 */     if (transitioning()) {
/* 267:387 */       return;
/* 268:    */     }
/* 269:390 */     this.currentState.controllerLeftReleased(controller);
/* 270:    */   }
/* 271:    */   
/* 272:    */   public void controllerRightPressed(int controller)
/* 273:    */   {
/* 274:397 */     if (transitioning()) {
/* 275:398 */       return;
/* 276:    */     }
/* 277:401 */     this.currentState.controllerRightPressed(controller);
/* 278:    */   }
/* 279:    */   
/* 280:    */   public void controllerRightReleased(int controller)
/* 281:    */   {
/* 282:408 */     if (transitioning()) {
/* 283:409 */       return;
/* 284:    */     }
/* 285:412 */     this.currentState.controllerRightReleased(controller);
/* 286:    */   }
/* 287:    */   
/* 288:    */   public void controllerUpPressed(int controller)
/* 289:    */   {
/* 290:419 */     if (transitioning()) {
/* 291:420 */       return;
/* 292:    */     }
/* 293:423 */     this.currentState.controllerUpPressed(controller);
/* 294:    */   }
/* 295:    */   
/* 296:    */   public void controllerUpReleased(int controller)
/* 297:    */   {
/* 298:430 */     if (transitioning()) {
/* 299:431 */       return;
/* 300:    */     }
/* 301:434 */     this.currentState.controllerUpReleased(controller);
/* 302:    */   }
/* 303:    */   
/* 304:    */   public void keyPressed(int key, char c)
/* 305:    */   {
/* 306:441 */     if (transitioning()) {
/* 307:442 */       return;
/* 308:    */     }
/* 309:445 */     this.currentState.keyPressed(key, c);
/* 310:    */   }
/* 311:    */   
/* 312:    */   public void keyReleased(int key, char c)
/* 313:    */   {
/* 314:452 */     if (transitioning()) {
/* 315:453 */       return;
/* 316:    */     }
/* 317:456 */     this.currentState.keyReleased(key, c);
/* 318:    */   }
/* 319:    */   
/* 320:    */   public void mouseMoved(int oldx, int oldy, int newx, int newy)
/* 321:    */   {
/* 322:463 */     if (transitioning()) {
/* 323:464 */       return;
/* 324:    */     }
/* 325:467 */     this.currentState.mouseMoved(oldx, oldy, newx, newy);
/* 326:    */   }
/* 327:    */   
/* 328:    */   public void mouseDragged(int oldx, int oldy, int newx, int newy)
/* 329:    */   {
/* 330:474 */     if (transitioning()) {
/* 331:475 */       return;
/* 332:    */     }
/* 333:478 */     this.currentState.mouseDragged(oldx, oldy, newx, newy);
/* 334:    */   }
/* 335:    */   
/* 336:    */   public void mouseClicked(int button, int x, int y, int clickCount)
/* 337:    */   {
/* 338:484 */     if (transitioning()) {
/* 339:485 */       return;
/* 340:    */     }
/* 341:488 */     this.currentState.mouseClicked(button, x, y, clickCount);
/* 342:    */   }
/* 343:    */   
/* 344:    */   public void mousePressed(int button, int x, int y)
/* 345:    */   {
/* 346:495 */     if (transitioning()) {
/* 347:496 */       return;
/* 348:    */     }
/* 349:499 */     this.currentState.mousePressed(button, x, y);
/* 350:    */   }
/* 351:    */   
/* 352:    */   public void mouseReleased(int button, int x, int y)
/* 353:    */   {
/* 354:506 */     if (transitioning()) {
/* 355:507 */       return;
/* 356:    */     }
/* 357:510 */     this.currentState.mouseReleased(button, x, y);
/* 358:    */   }
/* 359:    */   
/* 360:    */   public boolean isAcceptingInput()
/* 361:    */   {
/* 362:517 */     if (transitioning()) {
/* 363:518 */       return false;
/* 364:    */     }
/* 365:521 */     return this.currentState.isAcceptingInput();
/* 366:    */   }
/* 367:    */   
/* 368:    */   public void inputEnded() {}
/* 369:    */   
/* 370:    */   public void mouseWheelMoved(int newValue)
/* 371:    */   {
/* 372:534 */     if (transitioning()) {
/* 373:535 */       return;
/* 374:    */     }
/* 375:538 */     this.currentState.mouseWheelMoved(newValue);
/* 376:    */   }
/* 377:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.state.StateBasedGame
 * JD-Core Version:    0.7.0.1
 */