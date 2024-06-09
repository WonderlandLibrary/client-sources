package org.newdawn.slick.tests;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.Game;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.ScalableGame;
import org.newdawn.slick.SlickException;





public class ScalableTest
  extends BasicGame
{
  public ScalableTest()
  {
    super("Scalable Test For Widescreen");
  }
  


  public void init(GameContainer container)
    throws SlickException
  {}
  


  public void update(GameContainer container, int delta)
    throws SlickException
  {}
  

  public void render(GameContainer container, Graphics g)
    throws SlickException
  {
    g.setColor(new Color(0.4F, 0.6F, 0.8F));
    g.fillRect(0.0F, 0.0F, 1024.0F, 568.0F);
    g.setColor(Color.white);
    g.drawRect(5.0F, 5.0F, 1014.0F, 558.0F);
    
    g.setColor(Color.white);
    g.drawString(container.getInput().getMouseX() + "," + container.getInput().getMouseY(), 10.0F, 400.0F);
    g.setColor(Color.red);
    g.fillOval(container.getInput().getMouseX() - 10, container.getInput().getMouseY() - 10, 20.0F, 20.0F);
  }
  





























  public static void main(String[] argv)
  {
    try
    {
      ScalableGame game = new ScalableGame(new ScalableTest(), 1024, 568, true)
      {
        protected void renderOverlay(GameContainer container, Graphics g) {
          g.setColor(Color.white);
          g.drawString("Outside The Game", 350.0F, 10.0F);
          g.drawString(container.getInput().getMouseX() + "," + container.getInput().getMouseY(), 400.0F, 20.0F);
        }
        

      };
      AppGameContainer container = new AppGameContainer(game);
      container.setDisplayMode(800, 600, false);
      container.start();
    } catch (SlickException e) {
      e.printStackTrace();
    }
  }
}
