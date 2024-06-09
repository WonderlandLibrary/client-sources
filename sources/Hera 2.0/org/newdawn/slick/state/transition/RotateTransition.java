/*    */ package org.newdawn.slick.state.transition;
/*    */ 
/*    */ import org.newdawn.slick.Color;
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
/*    */ public class RotateTransition
/*    */   implements Transition
/*    */ {
/*    */   private GameState prev;
/*    */   private float ang;
/*    */   private boolean finish;
/* 26 */   private float scale = 1.0F;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private Color background;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public RotateTransition() {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public RotateTransition(Color background) {
/* 43 */     this.background = background;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void init(GameState firstState, GameState secondState) {
/* 50 */     this.prev = secondState;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isComplete() {
/* 57 */     return this.finish;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void postRender(StateBasedGame game, GameContainer container, Graphics g) throws SlickException {
/* 64 */     g.translate((container.getWidth() / 2), (container.getHeight() / 2));
/* 65 */     g.scale(this.scale, this.scale);
/* 66 */     g.rotate(0.0F, 0.0F, this.ang);
/* 67 */     g.translate((-container.getWidth() / 2), (-container.getHeight() / 2));
/* 68 */     if (this.background != null) {
/* 69 */       Color c = g.getColor();
/* 70 */       g.setColor(this.background);
/* 71 */       g.fillRect(0.0F, 0.0F, container.getWidth(), container.getHeight());
/* 72 */       g.setColor(c);
/*    */     } 
/* 74 */     this.prev.render(container, game, g);
/* 75 */     g.translate((container.getWidth() / 2), (container.getHeight() / 2));
/* 76 */     g.rotate(0.0F, 0.0F, -this.ang);
/* 77 */     g.scale(1.0F / this.scale, 1.0F / this.scale);
/* 78 */     g.translate((-container.getWidth() / 2), (-container.getHeight() / 2));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void preRender(StateBasedGame game, GameContainer container, Graphics g) throws SlickException {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void update(StateBasedGame game, GameContainer container, int delta) throws SlickException {
/* 93 */     this.ang += delta * 0.5F;
/* 94 */     if (this.ang > 500.0F) {
/* 95 */       this.finish = true;
/*    */     }
/* 97 */     this.scale -= delta * 0.001F;
/* 98 */     if (this.scale < 0.0F)
/* 99 */       this.scale = 0.0F; 
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\state\transition\RotateTransition.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */