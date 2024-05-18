/*    */ package org.newdawn.slick.state.transition;
/*    */ 
/*    */ import org.newdawn.slick.Color;
/*    */ import org.newdawn.slick.GameContainer;
/*    */ import org.newdawn.slick.Graphics;
/*    */ import org.newdawn.slick.state.GameState;
/*    */ import org.newdawn.slick.state.StateBasedGame;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FadeInTransition
/*    */   implements Transition
/*    */ {
/*    */   private Color color;
/* 18 */   private int fadeTime = 500;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public FadeInTransition() {
/* 24 */     this(Color.black, 500);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public FadeInTransition(Color color) {
/* 33 */     this(color, 500);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public FadeInTransition(Color color, int fadeTime) {
/* 43 */     this.color = new Color(color);
/* 44 */     this.color.a = 1.0F;
/* 45 */     this.fadeTime = fadeTime;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isComplete() {
/* 52 */     return (this.color.a <= 0.0F);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void postRender(StateBasedGame game, GameContainer container, Graphics g) {
/* 59 */     Color old = g.getColor();
/* 60 */     g.setColor(this.color);
/*    */     
/* 62 */     g.fillRect(0.0F, 0.0F, (container.getWidth() * 2), (container.getHeight() * 2));
/* 63 */     g.setColor(old);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void update(StateBasedGame game, GameContainer container, int delta) {
/* 70 */     this.color.a -= delta * 1.0F / this.fadeTime;
/* 71 */     if (this.color.a < 0.0F)
/* 72 */       this.color.a = 0.0F; 
/*    */   }
/*    */   
/*    */   public void preRender(StateBasedGame game, GameContainer container, Graphics g) {}
/*    */   
/*    */   public void init(GameState firstState, GameState secondState) {}
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\state\transition\FadeInTransition.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */