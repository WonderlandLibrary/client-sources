package org.newdawn.slick.tests.states;

import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;










public class TestState2
  extends BasicGameState
{
  public static final int ID = 2;
  private Font font;
  private Image image;
  private float ang;
  private StateBasedGame game;
  
  public TestState2() {}
  
  public int getID()
  {
    return 2;
  }
  

  public void init(GameContainer container, StateBasedGame game)
    throws SlickException
  {
    this.game = game;
    font = new AngelCodeFont("testdata/demo2.fnt", "testdata/demo2_00.tga");
    image = new Image("testdata/logo.tga");
  }
  


  public void render(GameContainer container, StateBasedGame game, Graphics g)
  {
    g.setFont(font);
    g.setColor(Color.green);
    g.drawString("This is State 2", 200.0F, 50.0F);
    
    g.rotate(400.0F, 300.0F, ang);
    g.drawImage(image, 400 - image.getWidth() / 2, 300 - image.getHeight() / 2);
  }
  


  public void update(GameContainer container, StateBasedGame game, int delta)
  {
    ang += delta * 0.1F;
  }
  


  public void keyReleased(int key, char c)
  {
    if (key == 2) {
      game.enterState(1, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
    }
    if (key == 4) {
      game.enterState(3, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
    }
  }
}
