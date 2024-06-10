/*  1:   */ package org.newdawn.slick.tests.states;
/*  2:   */ 
/*  3:   */ import org.newdawn.slick.AngelCodeFont;
/*  4:   */ import org.newdawn.slick.Color;
/*  5:   */ import org.newdawn.slick.Font;
/*  6:   */ import org.newdawn.slick.GameContainer;
/*  7:   */ import org.newdawn.slick.Graphics;
/*  8:   */ import org.newdawn.slick.Image;
/*  9:   */ import org.newdawn.slick.SlickException;
/* 10:   */ import org.newdawn.slick.state.BasicGameState;
/* 11:   */ import org.newdawn.slick.state.StateBasedGame;
/* 12:   */ import org.newdawn.slick.state.transition.FadeInTransition;
/* 13:   */ import org.newdawn.slick.state.transition.FadeOutTransition;
/* 14:   */ 
/* 15:   */ public class TestState2
/* 16:   */   extends BasicGameState
/* 17:   */ {
/* 18:   */   public static final int ID = 2;
/* 19:   */   private Font font;
/* 20:   */   private Image image;
/* 21:   */   private float ang;
/* 22:   */   private StateBasedGame game;
/* 23:   */   
/* 24:   */   public int getID()
/* 25:   */   {
/* 26:37 */     return 2;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public void init(GameContainer container, StateBasedGame game)
/* 30:   */     throws SlickException
/* 31:   */   {
/* 32:44 */     this.game = game;
/* 33:45 */     this.font = new AngelCodeFont("testdata/demo2.fnt", "testdata/demo2_00.tga");
/* 34:46 */     this.image = new Image("testdata/logo.tga");
/* 35:   */   }
/* 36:   */   
/* 37:   */   public void render(GameContainer container, StateBasedGame game, Graphics g)
/* 38:   */   {
/* 39:53 */     g.setFont(this.font);
/* 40:54 */     g.setColor(Color.green);
/* 41:55 */     g.drawString("This is State 2", 200.0F, 50.0F);
/* 42:   */     
/* 43:57 */     g.rotate(400.0F, 300.0F, this.ang);
/* 44:58 */     g.drawImage(this.image, 400 - this.image.getWidth() / 2, 300 - this.image.getHeight() / 2);
/* 45:   */   }
/* 46:   */   
/* 47:   */   public void update(GameContainer container, StateBasedGame game, int delta)
/* 48:   */   {
/* 49:65 */     this.ang += delta * 0.1F;
/* 50:   */   }
/* 51:   */   
/* 52:   */   public void keyReleased(int key, char c)
/* 53:   */   {
/* 54:72 */     if (key == 2) {
/* 55:73 */       this.game.enterState(1, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
/* 56:   */     }
/* 57:75 */     if (key == 4) {
/* 58:76 */       this.game.enterState(3, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
/* 59:   */     }
/* 60:   */   }
/* 61:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.tests.states.TestState2
 * JD-Core Version:    0.7.0.1
 */