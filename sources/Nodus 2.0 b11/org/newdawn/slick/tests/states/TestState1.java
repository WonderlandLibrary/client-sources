/*  1:   */ package org.newdawn.slick.tests.states;
/*  2:   */ 
/*  3:   */ import org.newdawn.slick.AngelCodeFont;
/*  4:   */ import org.newdawn.slick.Color;
/*  5:   */ import org.newdawn.slick.Font;
/*  6:   */ import org.newdawn.slick.GameContainer;
/*  7:   */ import org.newdawn.slick.Graphics;
/*  8:   */ import org.newdawn.slick.SlickException;
/*  9:   */ import org.newdawn.slick.state.BasicGameState;
/* 10:   */ import org.newdawn.slick.state.GameState;
/* 11:   */ import org.newdawn.slick.state.StateBasedGame;
/* 12:   */ import org.newdawn.slick.state.transition.CrossStateTransition;
/* 13:   */ import org.newdawn.slick.state.transition.EmptyTransition;
/* 14:   */ import org.newdawn.slick.state.transition.FadeInTransition;
/* 15:   */ import org.newdawn.slick.state.transition.FadeOutTransition;
/* 16:   */ 
/* 17:   */ public class TestState1
/* 18:   */   extends BasicGameState
/* 19:   */ {
/* 20:   */   public static final int ID = 1;
/* 21:   */   private Font font;
/* 22:   */   private StateBasedGame game;
/* 23:   */   
/* 24:   */   public int getID()
/* 25:   */   {
/* 26:35 */     return 1;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public void init(GameContainer container, StateBasedGame game)
/* 30:   */     throws SlickException
/* 31:   */   {
/* 32:42 */     this.game = game;
/* 33:43 */     this.font = new AngelCodeFont("testdata/demo2.fnt", "testdata/demo2_00.tga");
/* 34:   */   }
/* 35:   */   
/* 36:   */   public void render(GameContainer container, StateBasedGame game, Graphics g)
/* 37:   */   {
/* 38:50 */     g.setFont(this.font);
/* 39:51 */     g.setColor(Color.white);
/* 40:52 */     g.drawString("State Based Game Test", 100.0F, 100.0F);
/* 41:53 */     g.drawString("Numbers 1-3 will switch between states.", 150.0F, 300.0F);
/* 42:54 */     g.setColor(Color.red);
/* 43:55 */     g.drawString("This is State 1", 200.0F, 50.0F);
/* 44:   */   }
/* 45:   */   
/* 46:   */   public void update(GameContainer container, StateBasedGame game, int delta) {}
/* 47:   */   
/* 48:   */   public void keyReleased(int key, char c)
/* 49:   */   {
/* 50:69 */     if (key == 3)
/* 51:   */     {
/* 52:70 */       GameState target = this.game.getState(2);
/* 53:   */       
/* 54:72 */       final long start = System.currentTimeMillis();
/* 55:73 */       CrossStateTransition t = new CrossStateTransition(target)
/* 56:   */       {
/* 57:   */         public boolean isComplete()
/* 58:   */         {
/* 59:75 */           return System.currentTimeMillis() - start > 2000L;
/* 60:   */         }
/* 61:   */         
/* 62:   */         public void init(GameState firstState, GameState secondState) {}
/* 63:81 */       };
/* 64:82 */       this.game.enterState(2, t, new EmptyTransition());
/* 65:   */     }
/* 66:84 */     if (key == 4) {
/* 67:85 */       this.game.enterState(3, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
/* 68:   */     }
/* 69:   */   }
/* 70:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.tests.states.TestState1
 * JD-Core Version:    0.7.0.1
 */