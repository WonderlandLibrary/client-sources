/*  1:   */ package org.newdawn.slick.state.transition;
/*  2:   */ 
/*  3:   */ import org.newdawn.slick.GameContainer;
/*  4:   */ import org.newdawn.slick.Graphics;
/*  5:   */ import org.newdawn.slick.SlickException;
/*  6:   */ import org.newdawn.slick.state.GameState;
/*  7:   */ import org.newdawn.slick.state.StateBasedGame;
/*  8:   */ 
/*  9:   */ public abstract class CrossStateTransition
/* 10:   */   implements Transition
/* 11:   */ {
/* 12:   */   private GameState secondState;
/* 13:   */   
/* 14:   */   public CrossStateTransition(GameState secondState)
/* 15:   */   {
/* 16:39 */     this.secondState = secondState;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public abstract boolean isComplete();
/* 20:   */   
/* 21:   */   public void postRender(StateBasedGame game, GameContainer container, Graphics g)
/* 22:   */     throws SlickException
/* 23:   */   {
/* 24:51 */     preRenderSecondState(game, container, g);
/* 25:52 */     this.secondState.render(container, game, g);
/* 26:53 */     postRenderSecondState(game, container, g);
/* 27:   */   }
/* 28:   */   
/* 29:   */   public void preRender(StateBasedGame game, GameContainer container, Graphics g)
/* 30:   */     throws SlickException
/* 31:   */   {
/* 32:60 */     preRenderFirstState(game, container, g);
/* 33:   */   }
/* 34:   */   
/* 35:   */   public void update(StateBasedGame game, GameContainer container, int delta)
/* 36:   */     throws SlickException
/* 37:   */   {}
/* 38:   */   
/* 39:   */   public void preRenderFirstState(StateBasedGame game, GameContainer container, Graphics g)
/* 40:   */     throws SlickException
/* 41:   */   {}
/* 42:   */   
/* 43:   */   public void preRenderSecondState(StateBasedGame game, GameContainer container, Graphics g)
/* 44:   */     throws SlickException
/* 45:   */   {}
/* 46:   */   
/* 47:   */   public void postRenderSecondState(StateBasedGame game, GameContainer container, Graphics g)
/* 48:   */     throws SlickException
/* 49:   */   {}
/* 50:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.state.transition.CrossStateTransition
 * JD-Core Version:    0.7.0.1
 */