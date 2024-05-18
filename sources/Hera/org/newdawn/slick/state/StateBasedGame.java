/*     */ package org.newdawn.slick.state;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import org.newdawn.slick.Game;
/*     */ import org.newdawn.slick.GameContainer;
/*     */ import org.newdawn.slick.Graphics;
/*     */ import org.newdawn.slick.Input;
/*     */ import org.newdawn.slick.InputListener;
/*     */ import org.newdawn.slick.SlickException;
/*     */ import org.newdawn.slick.state.transition.EmptyTransition;
/*     */ import org.newdawn.slick.state.transition.Transition;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class StateBasedGame
/*     */   implements Game, InputListener
/*     */ {
/*  23 */   private HashMap states = new HashMap<Object, Object>();
/*     */ 
/*     */   
/*     */   private GameState currentState;
/*     */ 
/*     */   
/*     */   private GameState nextState;
/*     */ 
/*     */   
/*     */   private GameContainer container;
/*     */ 
/*     */   
/*     */   private String title;
/*     */ 
/*     */   
/*     */   private Transition enterTransition;
/*     */   
/*     */   private Transition leaveTransition;
/*     */ 
/*     */   
/*     */   public StateBasedGame(String name) {
/*  44 */     this.title = name;
/*     */     
/*  46 */     this.currentState = new BasicGameState() {
/*     */         public int getID() {
/*  48 */           return -1;
/*     */         }
/*     */ 
/*     */ 
/*     */         
/*     */         public void init(GameContainer container, StateBasedGame game) throws SlickException {}
/*     */ 
/*     */ 
/*     */         
/*     */         public void render(StateBasedGame game, Graphics g) throws SlickException {}
/*     */ 
/*     */ 
/*     */         
/*     */         public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {}
/*     */ 
/*     */ 
/*     */         
/*     */         public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {}
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public void inputStarted() {}
/*     */ 
/*     */   
/*     */   public int getStateCount() {
/*  74 */     return this.states.keySet().size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCurrentStateID() {
/*  83 */     return this.currentState.getID();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GameState getCurrentState() {
/*  92 */     return this.currentState;
/*     */   }
/*     */ 
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
/*     */ 
/*     */   
/*     */   public void addState(GameState state) {
/* 108 */     this.states.put(new Integer(state.getID()), state);
/*     */     
/* 110 */     if (this.currentState.getID() == -1) {
/* 111 */       this.currentState = state;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GameState getState(int id) {
/* 122 */     return (GameState)this.states.get(new Integer(id));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void enterState(int id) {
/* 131 */     enterState(id, (Transition)new EmptyTransition(), (Transition)new EmptyTransition());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void enterState(int id, Transition leave, Transition enter) {
/*     */     EmptyTransition emptyTransition1, emptyTransition2;
/* 142 */     if (leave == null) {
/* 143 */       emptyTransition1 = new EmptyTransition();
/*     */     }
/* 145 */     if (enter == null) {
/* 146 */       emptyTransition2 = new EmptyTransition();
/*     */     }
/* 148 */     this.leaveTransition = (Transition)emptyTransition1;
/* 149 */     this.enterTransition = (Transition)emptyTransition2;
/*     */     
/* 151 */     this.nextState = getState(id);
/* 152 */     if (this.nextState == null) {
/* 153 */       throw new RuntimeException("No game state registered with the ID: " + id);
/*     */     }
/*     */     
/* 156 */     this.leaveTransition.init(this.currentState, this.nextState);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void init(GameContainer container) throws SlickException {
/* 163 */     this.container = container;
/* 164 */     initStatesList(container);
/*     */     
/* 166 */     Iterator<GameState> gameStates = this.states.values().iterator();
/*     */     
/* 168 */     while (gameStates.hasNext()) {
/* 169 */       GameState state = gameStates.next();
/*     */       
/* 171 */       state.init(container, this);
/*     */     } 
/*     */     
/* 174 */     if (this.currentState != null) {
/* 175 */       this.currentState.enter(container, this);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void initStatesList(GameContainer paramGameContainer) throws SlickException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void render(GameContainer container, Graphics g) throws SlickException {
/* 191 */     preRenderState(container, g);
/*     */     
/* 193 */     if (this.leaveTransition != null) {
/* 194 */       this.leaveTransition.preRender(this, container, g);
/* 195 */     } else if (this.enterTransition != null) {
/* 196 */       this.enterTransition.preRender(this, container, g);
/*     */     } 
/*     */     
/* 199 */     this.currentState.render(container, this, g);
/*     */     
/* 201 */     if (this.leaveTransition != null) {
/* 202 */       this.leaveTransition.postRender(this, container, g);
/* 203 */     } else if (this.enterTransition != null) {
/* 204 */       this.enterTransition.postRender(this, container, g);
/*     */     } 
/*     */     
/* 207 */     postRenderState(container, g);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void preRenderState(GameContainer container, Graphics g) throws SlickException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void postRenderState(GameContainer container, Graphics g) throws SlickException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void update(GameContainer container, int delta) throws SlickException {
/* 238 */     preUpdateState(container, delta);
/*     */     
/* 240 */     if (this.leaveTransition != null) {
/* 241 */       this.leaveTransition.update(this, container, delta);
/* 242 */       if (this.leaveTransition.isComplete()) {
/* 243 */         this.currentState.leave(container, this);
/* 244 */         GameState prevState = this.currentState;
/* 245 */         this.currentState = this.nextState;
/* 246 */         this.nextState = null;
/* 247 */         this.leaveTransition = null;
/* 248 */         this.currentState.enter(container, this);
/* 249 */         if (this.enterTransition != null) {
/* 250 */           this.enterTransition.init(this.currentState, prevState);
/*     */         }
/*     */       } else {
/*     */         return;
/*     */       } 
/*     */     } 
/*     */     
/* 257 */     if (this.enterTransition != null) {
/* 258 */       this.enterTransition.update(this, container, delta);
/* 259 */       if (this.enterTransition.isComplete()) {
/* 260 */         this.enterTransition = null;
/*     */       } else {
/*     */         return;
/*     */       } 
/*     */     } 
/*     */     
/* 266 */     this.currentState.update(container, this, delta);
/*     */     
/* 268 */     postUpdateState(container, delta);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void preUpdateState(GameContainer container, int delta) throws SlickException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void postUpdateState(GameContainer container, int delta) throws SlickException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean transitioning() {
/* 301 */     return (this.leaveTransition != null || this.enterTransition != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean closeRequested() {
/* 308 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTitle() {
/* 315 */     return this.title;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GameContainer getContainer() {
/* 324 */     return this.container;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void controllerButtonPressed(int controller, int button) {
/* 331 */     if (transitioning()) {
/*     */       return;
/*     */     }
/*     */     
/* 335 */     this.currentState.controllerButtonPressed(controller, button);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void controllerButtonReleased(int controller, int button) {
/* 342 */     if (transitioning()) {
/*     */       return;
/*     */     }
/*     */     
/* 346 */     this.currentState.controllerButtonReleased(controller, button);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void controllerDownPressed(int controller) {
/* 353 */     if (transitioning()) {
/*     */       return;
/*     */     }
/*     */     
/* 357 */     this.currentState.controllerDownPressed(controller);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void controllerDownReleased(int controller) {
/* 364 */     if (transitioning()) {
/*     */       return;
/*     */     }
/*     */     
/* 368 */     this.currentState.controllerDownReleased(controller);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void controllerLeftPressed(int controller) {
/* 375 */     if (transitioning()) {
/*     */       return;
/*     */     }
/*     */     
/* 379 */     this.currentState.controllerLeftPressed(controller);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void controllerLeftReleased(int controller) {
/* 386 */     if (transitioning()) {
/*     */       return;
/*     */     }
/*     */     
/* 390 */     this.currentState.controllerLeftReleased(controller);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void controllerRightPressed(int controller) {
/* 397 */     if (transitioning()) {
/*     */       return;
/*     */     }
/*     */     
/* 401 */     this.currentState.controllerRightPressed(controller);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void controllerRightReleased(int controller) {
/* 408 */     if (transitioning()) {
/*     */       return;
/*     */     }
/*     */     
/* 412 */     this.currentState.controllerRightReleased(controller);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void controllerUpPressed(int controller) {
/* 419 */     if (transitioning()) {
/*     */       return;
/*     */     }
/*     */     
/* 423 */     this.currentState.controllerUpPressed(controller);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void controllerUpReleased(int controller) {
/* 430 */     if (transitioning()) {
/*     */       return;
/*     */     }
/*     */     
/* 434 */     this.currentState.controllerUpReleased(controller);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void keyPressed(int key, char c) {
/* 441 */     if (transitioning()) {
/*     */       return;
/*     */     }
/*     */     
/* 445 */     this.currentState.keyPressed(key, c);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void keyReleased(int key, char c) {
/* 452 */     if (transitioning()) {
/*     */       return;
/*     */     }
/*     */     
/* 456 */     this.currentState.keyReleased(key, c);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mouseMoved(int oldx, int oldy, int newx, int newy) {
/* 463 */     if (transitioning()) {
/*     */       return;
/*     */     }
/*     */     
/* 467 */     this.currentState.mouseMoved(oldx, oldy, newx, newy);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mouseDragged(int oldx, int oldy, int newx, int newy) {
/* 474 */     if (transitioning()) {
/*     */       return;
/*     */     }
/*     */     
/* 478 */     this.currentState.mouseDragged(oldx, oldy, newx, newy);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void mouseClicked(int button, int x, int y, int clickCount) {
/* 484 */     if (transitioning()) {
/*     */       return;
/*     */     }
/*     */     
/* 488 */     this.currentState.mouseClicked(button, x, y, clickCount);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mousePressed(int button, int x, int y) {
/* 495 */     if (transitioning()) {
/*     */       return;
/*     */     }
/*     */     
/* 499 */     this.currentState.mousePressed(button, x, y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mouseReleased(int button, int x, int y) {
/* 506 */     if (transitioning()) {
/*     */       return;
/*     */     }
/*     */     
/* 510 */     this.currentState.mouseReleased(button, x, y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAcceptingInput() {
/* 517 */     if (transitioning()) {
/* 518 */       return false;
/*     */     }
/*     */     
/* 521 */     return this.currentState.isAcceptingInput();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void inputEnded() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mouseWheelMoved(int newValue) {
/* 534 */     if (transitioning()) {
/*     */       return;
/*     */     }
/*     */     
/* 538 */     this.currentState.mouseWheelMoved(newValue);
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\state\StateBasedGame.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */