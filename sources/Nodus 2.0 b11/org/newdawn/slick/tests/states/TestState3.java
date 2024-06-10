/*  1:   */ package org.newdawn.slick.tests.states;
/*  2:   */ 
/*  3:   */ import org.newdawn.slick.AngelCodeFont;
/*  4:   */ import org.newdawn.slick.Color;
/*  5:   */ import org.newdawn.slick.Font;
/*  6:   */ import org.newdawn.slick.GameContainer;
/*  7:   */ import org.newdawn.slick.Graphics;
/*  8:   */ import org.newdawn.slick.SlickException;
/*  9:   */ import org.newdawn.slick.state.BasicGameState;
/* 10:   */ import org.newdawn.slick.state.StateBasedGame;
/* 11:   */ import org.newdawn.slick.state.transition.FadeInTransition;
/* 12:   */ import org.newdawn.slick.state.transition.FadeOutTransition;
/* 13:   */ 
/* 14:   */ public class TestState3
/* 15:   */   extends BasicGameState
/* 16:   */ {
/* 17:   */   public static final int ID = 3;
/* 18:   */   private Font font;
/* 19:26 */   private String[] options = { "Start Game", "Credits", "Highscores", "Instructions", "Exit" };
/* 20:   */   private int selected;
/* 21:   */   private StateBasedGame game;
/* 22:   */   
/* 23:   */   public int getID()
/* 24:   */   {
/* 25:36 */     return 3;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public void init(GameContainer container, StateBasedGame game)
/* 29:   */     throws SlickException
/* 30:   */   {
/* 31:43 */     this.font = new AngelCodeFont("testdata/demo2.fnt", "testdata/demo2_00.tga");
/* 32:44 */     this.game = game;
/* 33:   */   }
/* 34:   */   
/* 35:   */   public void render(GameContainer container, StateBasedGame game, Graphics g)
/* 36:   */   {
/* 37:51 */     g.setFont(this.font);
/* 38:52 */     g.setColor(Color.blue);
/* 39:53 */     g.drawString("This is State 3", 200.0F, 50.0F);
/* 40:54 */     g.setColor(Color.white);
/* 41:56 */     for (int i = 0; i < this.options.length; i++)
/* 42:   */     {
/* 43:57 */       g.drawString(this.options[i], 400 - this.font.getWidth(this.options[i]) / 2, 200 + i * 50);
/* 44:58 */       if (this.selected == i) {
/* 45:59 */         g.drawRect(200.0F, 190 + i * 50, 400.0F, 50.0F);
/* 46:   */       }
/* 47:   */     }
/* 48:   */   }
/* 49:   */   
/* 50:   */   public void update(GameContainer container, StateBasedGame game, int delta) {}
/* 51:   */   
/* 52:   */   public void keyReleased(int key, char c)
/* 53:   */   {
/* 54:74 */     if (key == 208)
/* 55:   */     {
/* 56:75 */       this.selected += 1;
/* 57:76 */       if (this.selected >= this.options.length) {
/* 58:77 */         this.selected = 0;
/* 59:   */       }
/* 60:   */     }
/* 61:80 */     if (key == 200)
/* 62:   */     {
/* 63:81 */       this.selected -= 1;
/* 64:82 */       if (this.selected < 0) {
/* 65:83 */         this.selected = (this.options.length - 1);
/* 66:   */       }
/* 67:   */     }
/* 68:86 */     if (key == 2) {
/* 69:87 */       this.game.enterState(1, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
/* 70:   */     }
/* 71:89 */     if (key == 3) {
/* 72:90 */       this.game.enterState(2, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
/* 73:   */     }
/* 74:   */   }
/* 75:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.tests.states.TestState3
 * JD-Core Version:    0.7.0.1
 */