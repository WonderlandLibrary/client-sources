/*  1:   */ package org.newdawn.slick.tests;
/*  2:   */ 
/*  3:   */ import org.newdawn.slick.AppGameContainer;
/*  4:   */ import org.newdawn.slick.GameContainer;
/*  5:   */ import org.newdawn.slick.SlickException;
/*  6:   */ import org.newdawn.slick.state.StateBasedGame;
/*  7:   */ import org.newdawn.slick.tests.states.TestState1;
/*  8:   */ import org.newdawn.slick.tests.states.TestState2;
/*  9:   */ import org.newdawn.slick.tests.states.TestState3;
/* 10:   */ 
/* 11:   */ public class StateBasedTest
/* 12:   */   extends StateBasedGame
/* 13:   */ {
/* 14:   */   public StateBasedTest()
/* 15:   */   {
/* 16:22 */     super("State Based Test");
/* 17:   */   }
/* 18:   */   
/* 19:   */   public void initStatesList(GameContainer container)
/* 20:   */   {
/* 21:29 */     addState(new TestState1());
/* 22:30 */     addState(new TestState2());
/* 23:31 */     addState(new TestState3());
/* 24:   */   }
/* 25:   */   
/* 26:   */   public static void main(String[] argv)
/* 27:   */   {
/* 28:   */     try
/* 29:   */     {
/* 30:41 */       AppGameContainer container = new AppGameContainer(new StateBasedTest());
/* 31:42 */       container.setDisplayMode(800, 600, false);
/* 32:43 */       container.start();
/* 33:   */     }
/* 34:   */     catch (SlickException e)
/* 35:   */     {
/* 36:45 */       e.printStackTrace();
/* 37:   */     }
/* 38:   */   }
/* 39:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.tests.StateBasedTest
 * JD-Core Version:    0.7.0.1
 */