/*    */ package org.newdawn.slick.state.transition;
/*    */ 
/*    */ import java.util.ArrayList;
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
/*    */ public class CombinedTransition
/*    */   implements Transition
/*    */ {
/* 19 */   private ArrayList transitions = new ArrayList();
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
/*    */   public void addTransition(Transition t) {
/* 34 */     this.transitions.add(t);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isComplete() {
/* 41 */     for (int i = 0; i < this.transitions.size(); i++) {
/* 42 */       if (!((Transition)this.transitions.get(i)).isComplete()) {
/* 43 */         return false;
/*    */       }
/*    */     } 
/*    */     
/* 47 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void postRender(StateBasedGame game, GameContainer container, Graphics g) throws SlickException {
/* 54 */     for (int i = this.transitions.size() - 1; i >= 0; i--) {
/* 55 */       ((Transition)this.transitions.get(i)).postRender(game, container, g);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void preRender(StateBasedGame game, GameContainer container, Graphics g) throws SlickException {
/* 63 */     for (int i = 0; i < this.transitions.size(); i++) {
/* 64 */       ((Transition)this.transitions.get(i)).postRender(game, container, g);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void update(StateBasedGame game, GameContainer container, int delta) throws SlickException {
/* 72 */     for (int i = 0; i < this.transitions.size(); i++) {
/* 73 */       Transition t = this.transitions.get(i);
/*    */       
/* 75 */       if (!t.isComplete()) {
/* 76 */         t.update(game, container, delta);
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   public void init(GameState firstState, GameState secondState) {
/* 82 */     for (int i = this.transitions.size() - 1; i >= 0; i--)
/* 83 */       ((Transition)this.transitions.get(i)).init(firstState, secondState); 
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\state\transition\CombinedTransition.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */