package org.newdawn.slick.tests.states;

import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;








public class TestState3
  extends BasicGameState
{
  public static final int ID = 3;
  private Font font;
  private String[] options = { "Start Game", "Credits", "Highscores", "Instructions", "Exit" };
  
  private int selected;
  
  private StateBasedGame game;
  
  public TestState3() {}
  
  public int getID()
  {
    return 3;
  }
  

  public void init(GameContainer container, StateBasedGame game)
    throws SlickException
  {
    font = new AngelCodeFont("testdata/demo2.fnt", "testdata/demo2_00.tga");
    this.game = game;
  }
  


  public void render(GameContainer container, StateBasedGame game, Graphics g)
  {
    g.setFont(font);
    g.setColor(Color.blue);
    g.drawString("This is State 3", 200.0F, 50.0F);
    g.setColor(Color.white);
    
    for (int i = 0; i < options.length; i++) {
      g.drawString(options[i], 400 - font.getWidth(options[i]) / 2, 200 + i * 50);
      if (selected == i) {
        g.drawRect(200.0F, 190 + i * 50, 400.0F, 50.0F);
      }
    }
  }
  



  public void update(GameContainer container, StateBasedGame game, int delta) {}
  



  public void keyReleased(int key, char c)
  {
    if (key == 208) {
      selected += 1;
      if (selected >= options.length) {
        selected = 0;
      }
    }
    if (key == 200) {
      selected -= 1;
      if (selected < 0) {
        selected = (options.length - 1);
      }
    }
    if (key == 2) {
      game.enterState(1, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
    }
    if (key == 3) {
      game.enterState(2, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
    }
  }
}
