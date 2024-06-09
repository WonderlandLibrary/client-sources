package org.newdawn.slick.tests.states;

import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.CrossStateTransition;
import org.newdawn.slick.state.transition.EmptyTransition;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;








public class TestState1
  extends BasicGameState
{
  public static final int ID = 1;
  private Font font;
  private StateBasedGame game;
  
  public TestState1() {}
  
  public int getID()
  {
    return 1;
  }
  

  public void init(GameContainer container, StateBasedGame game)
    throws SlickException
  {
    this.game = game;
    font = new AngelCodeFont("testdata/demo2.fnt", "testdata/demo2_00.tga");
  }
  


  public void render(GameContainer container, StateBasedGame game, Graphics g)
  {
    g.setFont(font);
    g.setColor(Color.white);
    g.drawString("State Based Game Test", 100.0F, 100.0F);
    g.drawString("Numbers 1-3 will switch between states.", 150.0F, 300.0F);
    g.setColor(Color.red);
    g.drawString("This is State 1", 200.0F, 50.0F);
  }
  




  public void update(GameContainer container, StateBasedGame game, int delta) {}
  



  public void keyReleased(int key, char c)
  {
    if (key == 3) {
      GameState target = game.getState(2);
      
      final long start = System.currentTimeMillis();
      CrossStateTransition t = new CrossStateTransition(target) {
        public boolean isComplete() {
          return System.currentTimeMillis() - start > 2000L;
        }
        


        public void init(GameState firstState, GameState secondState) {}
      };
      game.enterState(2, t, new EmptyTransition());
    }
    if (key == 4) {
      game.enterState(3, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
    }
  }
}
