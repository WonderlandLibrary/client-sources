/*    */ package org.newdawn.slick.tests;
/*    */ 
/*    */ import org.newdawn.slick.AppGameContainer;
/*    */ import org.newdawn.slick.Game;
/*    */ import org.newdawn.slick.GameContainer;
/*    */ import org.newdawn.slick.SlickException;
/*    */ import org.newdawn.slick.state.GameState;
/*    */ import org.newdawn.slick.state.StateBasedGame;
/*    */ import org.newdawn.slick.tests.states.TestState1;
/*    */ import org.newdawn.slick.tests.states.TestState2;
/*    */ import org.newdawn.slick.tests.states.TestState3;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class StateBasedTest
/*    */   extends StateBasedGame
/*    */ {
/*    */   public StateBasedTest() {
/* 22 */     super("State Based Test");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void initStatesList(GameContainer container) {
/* 29 */     addState((GameState)new TestState1());
/* 30 */     addState((GameState)new TestState2());
/* 31 */     addState((GameState)new TestState3());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void main(String[] argv) {
/*    */     try {
/* 41 */       AppGameContainer container = new AppGameContainer((Game)new StateBasedTest());
/* 42 */       container.setDisplayMode(800, 600, false);
/* 43 */       container.start();
/* 44 */     } catch (SlickException e) {
/* 45 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\tests\StateBasedTest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */