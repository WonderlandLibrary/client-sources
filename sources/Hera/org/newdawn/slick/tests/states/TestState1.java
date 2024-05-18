/*    */ package org.newdawn.slick.tests.states;
/*    */ 
/*    */ import org.newdawn.slick.AngelCodeFont;
/*    */ import org.newdawn.slick.Color;
/*    */ import org.newdawn.slick.Font;
/*    */ import org.newdawn.slick.GameContainer;
/*    */ import org.newdawn.slick.Graphics;
/*    */ import org.newdawn.slick.SlickException;
/*    */ import org.newdawn.slick.state.BasicGameState;
/*    */ import org.newdawn.slick.state.GameState;
/*    */ import org.newdawn.slick.state.StateBasedGame;
/*    */ import org.newdawn.slick.state.transition.CrossStateTransition;
/*    */ import org.newdawn.slick.state.transition.EmptyTransition;
/*    */ import org.newdawn.slick.state.transition.FadeInTransition;
/*    */ import org.newdawn.slick.state.transition.FadeOutTransition;
/*    */ import org.newdawn.slick.state.transition.Transition;
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
/*    */ public class TestState1
/*    */   extends BasicGameState
/*    */ {
/*    */   public static final int ID = 1;
/*    */   private Font font;
/*    */   private StateBasedGame game;
/*    */   
/*    */   public int getID() {
/* 35 */     return 1;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void init(GameContainer container, StateBasedGame game) throws SlickException {
/* 42 */     this.game = game;
/* 43 */     this.font = (Font)new AngelCodeFont("testdata/demo2.fnt", "testdata/demo2_00.tga");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void render(GameContainer container, StateBasedGame game, Graphics g) {
/* 50 */     g.setFont(this.font);
/* 51 */     g.setColor(Color.white);
/* 52 */     g.drawString("State Based Game Test", 100.0F, 100.0F);
/* 53 */     g.drawString("Numbers 1-3 will switch between states.", 150.0F, 300.0F);
/* 54 */     g.setColor(Color.red);
/* 55 */     g.drawString("This is State 1", 200.0F, 50.0F);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void update(GameContainer container, StateBasedGame game, int delta) {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void keyReleased(int key, char c) {
/* 69 */     if (key == 3) {
/* 70 */       GameState target = this.game.getState(2);
/*    */       
/* 72 */       final long start = System.currentTimeMillis();
/* 73 */       CrossStateTransition t = new CrossStateTransition(target) {
/*    */           public boolean isComplete() {
/* 75 */             return (System.currentTimeMillis() - start > 2000L);
/*    */           }
/*    */ 
/*    */ 
/*    */           
/*    */           public void init(GameState firstState, GameState secondState) {}
/*    */         };
/* 82 */       this.game.enterState(2, (Transition)t, (Transition)new EmptyTransition());
/*    */     } 
/* 84 */     if (key == 4)
/* 85 */       this.game.enterState(3, (Transition)new FadeOutTransition(Color.black), (Transition)new FadeInTransition(Color.black)); 
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\tests\states\TestState1.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */