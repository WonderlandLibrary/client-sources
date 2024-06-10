/*  1:   */ package org.newdawn.slick.state.transition;
/*  2:   */ 
/*  3:   */ import java.util.ArrayList;
/*  4:   */ import org.newdawn.slick.GameContainer;
/*  5:   */ import org.newdawn.slick.Graphics;
/*  6:   */ import org.newdawn.slick.SlickException;
/*  7:   */ import org.newdawn.slick.state.GameState;
/*  8:   */ import org.newdawn.slick.state.StateBasedGame;
/*  9:   */ 
/* 10:   */ public class CombinedTransition
/* 11:   */   implements Transition
/* 12:   */ {
/* 13:19 */   private ArrayList transitions = new ArrayList();
/* 14:   */   
/* 15:   */   public void addTransition(Transition t)
/* 16:   */   {
/* 17:34 */     this.transitions.add(t);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public boolean isComplete()
/* 21:   */   {
/* 22:41 */     for (int i = 0; i < this.transitions.size(); i++) {
/* 23:42 */       if (!((Transition)this.transitions.get(i)).isComplete()) {
/* 24:43 */         return false;
/* 25:   */       }
/* 26:   */     }
/* 27:47 */     return true;
/* 28:   */   }
/* 29:   */   
/* 30:   */   public void postRender(StateBasedGame game, GameContainer container, Graphics g)
/* 31:   */     throws SlickException
/* 32:   */   {
/* 33:54 */     for (int i = this.transitions.size() - 1; i >= 0; i--) {
/* 34:55 */       ((Transition)this.transitions.get(i)).postRender(game, container, g);
/* 35:   */     }
/* 36:   */   }
/* 37:   */   
/* 38:   */   public void preRender(StateBasedGame game, GameContainer container, Graphics g)
/* 39:   */     throws SlickException
/* 40:   */   {
/* 41:63 */     for (int i = 0; i < this.transitions.size(); i++) {
/* 42:64 */       ((Transition)this.transitions.get(i)).postRender(game, container, g);
/* 43:   */     }
/* 44:   */   }
/* 45:   */   
/* 46:   */   public void update(StateBasedGame game, GameContainer container, int delta)
/* 47:   */     throws SlickException
/* 48:   */   {
/* 49:72 */     for (int i = 0; i < this.transitions.size(); i++)
/* 50:   */     {
/* 51:73 */       Transition t = (Transition)this.transitions.get(i);
/* 52:75 */       if (!t.isComplete()) {
/* 53:76 */         t.update(game, container, delta);
/* 54:   */       }
/* 55:   */     }
/* 56:   */   }
/* 57:   */   
/* 58:   */   public void init(GameState firstState, GameState secondState)
/* 59:   */   {
/* 60:82 */     for (int i = this.transitions.size() - 1; i >= 0; i--) {
/* 61:83 */       ((Transition)this.transitions.get(i)).init(firstState, secondState);
/* 62:   */     }
/* 63:   */   }
/* 64:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.state.transition.CombinedTransition
 * JD-Core Version:    0.7.0.1
 */