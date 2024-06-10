/*  1:   */ package org.newdawn.slick.state.transition;
/*  2:   */ 
/*  3:   */ import org.newdawn.slick.GameContainer;
/*  4:   */ import org.newdawn.slick.Graphics;
/*  5:   */ import org.newdawn.slick.SlickException;
/*  6:   */ import org.newdawn.slick.state.GameState;
/*  7:   */ import org.newdawn.slick.state.StateBasedGame;
/*  8:   */ 
/*  9:   */ public class EmptyTransition
/* 10:   */   implements Transition
/* 11:   */ {
/* 12:   */   public boolean isComplete()
/* 13:   */   {
/* 14:21 */     return true;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public void postRender(StateBasedGame game, GameContainer container, Graphics g)
/* 18:   */     throws SlickException
/* 19:   */   {}
/* 20:   */   
/* 21:   */   public void preRender(StateBasedGame game, GameContainer container, Graphics g)
/* 22:   */     throws SlickException
/* 23:   */   {}
/* 24:   */   
/* 25:   */   public void update(StateBasedGame game, GameContainer container, int delta)
/* 26:   */     throws SlickException
/* 27:   */   {}
/* 28:   */   
/* 29:   */   public void init(GameState firstState, GameState secondState) {}
/* 30:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.state.transition.EmptyTransition
 * JD-Core Version:    0.7.0.1
 */