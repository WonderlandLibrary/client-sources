/*    */ package org.newdawn.slick.state.transition;
/*    */ 
/*    */ import org.newdawn.slick.GameContainer;
/*    */ import org.newdawn.slick.Graphics;
/*    */ import org.newdawn.slick.SlickException;
/*    */ import org.newdawn.slick.state.GameState;
/*    */ import org.newdawn.slick.state.StateBasedGame;
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
/*    */ public abstract class CrossStateTransition
/*    */   implements Transition
/*    */ {
/*    */   private GameState secondState;
/*    */   
/*    */   public CrossStateTransition(GameState secondState) {
/* 39 */     this.secondState = secondState;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public abstract boolean isComplete();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void postRender(StateBasedGame game, GameContainer container, Graphics g) throws SlickException {
/* 51 */     preRenderSecondState(game, container, g);
/* 52 */     this.secondState.render(container, game, g);
/* 53 */     postRenderSecondState(game, container, g);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void preRender(StateBasedGame game, GameContainer container, Graphics g) throws SlickException {
/* 60 */     preRenderFirstState(game, container, g);
/*    */   }
/*    */   
/*    */   public void update(StateBasedGame game, GameContainer container, int delta) throws SlickException {}
/*    */   
/*    */   public void preRenderFirstState(StateBasedGame game, GameContainer container, Graphics g) throws SlickException {}
/*    */   
/*    */   public void preRenderSecondState(StateBasedGame game, GameContainer container, Graphics g) throws SlickException {}
/*    */   
/*    */   public void postRenderSecondState(StateBasedGame game, GameContainer container, Graphics g) throws SlickException {}
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\state\transition\CrossStateTransition.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */