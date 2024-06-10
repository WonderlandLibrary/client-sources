/*  1:   */ package org.newdawn.slick.state.transition;
/*  2:   */ 
/*  3:   */ import org.newdawn.slick.Color;
/*  4:   */ import org.newdawn.slick.GameContainer;
/*  5:   */ import org.newdawn.slick.Graphics;
/*  6:   */ import org.newdawn.slick.SlickException;
/*  7:   */ import org.newdawn.slick.state.GameState;
/*  8:   */ import org.newdawn.slick.state.StateBasedGame;
/*  9:   */ 
/* 10:   */ public class RotateTransition
/* 11:   */   implements Transition
/* 12:   */ {
/* 13:   */   private GameState prev;
/* 14:   */   private float ang;
/* 15:   */   private boolean finish;
/* 16:26 */   private float scale = 1.0F;
/* 17:   */   private Color background;
/* 18:   */   
/* 19:   */   public RotateTransition() {}
/* 20:   */   
/* 21:   */   public RotateTransition(Color background)
/* 22:   */   {
/* 23:43 */     this.background = background;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public void init(GameState firstState, GameState secondState)
/* 27:   */   {
/* 28:50 */     this.prev = secondState;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public boolean isComplete()
/* 32:   */   {
/* 33:57 */     return this.finish;
/* 34:   */   }
/* 35:   */   
/* 36:   */   public void postRender(StateBasedGame game, GameContainer container, Graphics g)
/* 37:   */     throws SlickException
/* 38:   */   {
/* 39:64 */     g.translate(container.getWidth() / 2, container.getHeight() / 2);
/* 40:65 */     g.scale(this.scale, this.scale);
/* 41:66 */     g.rotate(0.0F, 0.0F, this.ang);
/* 42:67 */     g.translate(-container.getWidth() / 2, -container.getHeight() / 2);
/* 43:68 */     if (this.background != null)
/* 44:   */     {
/* 45:69 */       Color c = g.getColor();
/* 46:70 */       g.setColor(this.background);
/* 47:71 */       g.fillRect(0.0F, 0.0F, container.getWidth(), container.getHeight());
/* 48:72 */       g.setColor(c);
/* 49:   */     }
/* 50:74 */     this.prev.render(container, game, g);
/* 51:75 */     g.translate(container.getWidth() / 2, container.getHeight() / 2);
/* 52:76 */     g.rotate(0.0F, 0.0F, -this.ang);
/* 53:77 */     g.scale(1.0F / this.scale, 1.0F / this.scale);
/* 54:78 */     g.translate(-container.getWidth() / 2, -container.getHeight() / 2);
/* 55:   */   }
/* 56:   */   
/* 57:   */   public void preRender(StateBasedGame game, GameContainer container, Graphics g)
/* 58:   */     throws SlickException
/* 59:   */   {}
/* 60:   */   
/* 61:   */   public void update(StateBasedGame game, GameContainer container, int delta)
/* 62:   */     throws SlickException
/* 63:   */   {
/* 64:93 */     this.ang += delta * 0.5F;
/* 65:94 */     if (this.ang > 500.0F) {
/* 66:95 */       this.finish = true;
/* 67:   */     }
/* 68:97 */     this.scale -= delta * 0.001F;
/* 69:98 */     if (this.scale < 0.0F) {
/* 70:99 */       this.scale = 0.0F;
/* 71:   */     }
/* 72:   */   }
/* 73:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.state.transition.RotateTransition
 * JD-Core Version:    0.7.0.1
 */