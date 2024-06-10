/*  1:   */ package org.newdawn.slick.state.transition;
/*  2:   */ 
/*  3:   */ import org.newdawn.slick.Color;
/*  4:   */ import org.newdawn.slick.GameContainer;
/*  5:   */ import org.newdawn.slick.Graphics;
/*  6:   */ import org.newdawn.slick.state.GameState;
/*  7:   */ import org.newdawn.slick.state.StateBasedGame;
/*  8:   */ 
/*  9:   */ public class FadeInTransition
/* 10:   */   implements Transition
/* 11:   */ {
/* 12:   */   private Color color;
/* 13:18 */   private int fadeTime = 500;
/* 14:   */   
/* 15:   */   public FadeInTransition()
/* 16:   */   {
/* 17:24 */     this(Color.black, 500);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public FadeInTransition(Color color)
/* 21:   */   {
/* 22:33 */     this(color, 500);
/* 23:   */   }
/* 24:   */   
/* 25:   */   public FadeInTransition(Color color, int fadeTime)
/* 26:   */   {
/* 27:43 */     this.color = new Color(color);
/* 28:44 */     this.color.a = 1.0F;
/* 29:45 */     this.fadeTime = fadeTime;
/* 30:   */   }
/* 31:   */   
/* 32:   */   public boolean isComplete()
/* 33:   */   {
/* 34:52 */     return this.color.a <= 0.0F;
/* 35:   */   }
/* 36:   */   
/* 37:   */   public void postRender(StateBasedGame game, GameContainer container, Graphics g)
/* 38:   */   {
/* 39:59 */     Color old = g.getColor();
/* 40:60 */     g.setColor(this.color);
/* 41:   */     
/* 42:62 */     g.fillRect(0.0F, 0.0F, container.getWidth() * 2, container.getHeight() * 2);
/* 43:63 */     g.setColor(old);
/* 44:   */   }
/* 45:   */   
/* 46:   */   public void update(StateBasedGame game, GameContainer container, int delta)
/* 47:   */   {
/* 48:70 */     this.color.a -= delta * (1.0F / this.fadeTime);
/* 49:71 */     if (this.color.a < 0.0F) {
/* 50:72 */       this.color.a = 0.0F;
/* 51:   */     }
/* 52:   */   }
/* 53:   */   
/* 54:   */   public void preRender(StateBasedGame game, GameContainer container, Graphics g) {}
/* 55:   */   
/* 56:   */   public void init(GameState firstState, GameState secondState) {}
/* 57:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.state.transition.FadeInTransition
 * JD-Core Version:    0.7.0.1
 */