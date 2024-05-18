package org.newdawn.slick.tests;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;








public class AlphaMapTest
  extends BasicGame
{
  private Image alphaMap;
  private Image textureMap;
  
  public AlphaMapTest()
  {
    super("AlphaMap Test");
  }
  

  public void init(GameContainer container)
    throws SlickException
  {
    alphaMap = new Image("testdata/alphamap.png");
    textureMap = new Image("testdata/grass.png");
    container.getGraphics().setBackground(Color.black);
  }
  



  public void update(GameContainer container, int delta)
    throws SlickException
  {}
  


  public void render(GameContainer container, Graphics g)
    throws SlickException
  {
    g.clearAlphaMap();
    g.setDrawMode(Graphics.MODE_NORMAL);
    textureMap.draw(10.0F, 50.0F);
    g.setColor(Color.red);
    g.fillRect(290.0F, 40.0F, 200.0F, 200.0F);
    g.setColor(Color.white);
    
    g.setDrawMode(Graphics.MODE_ALPHA_MAP);
    alphaMap.draw(300.0F, 50.0F);
    g.setDrawMode(Graphics.MODE_ALPHA_BLEND);
    textureMap.draw(300.0F, 50.0F);
    g.setDrawMode(Graphics.MODE_NORMAL);
  }
  




  public void keyPressed(int key, char c) {}
  



  public static void main(String[] argv)
  {
    try
    {
      AppGameContainer container = new AppGameContainer(new AlphaMapTest());
      container.setDisplayMode(800, 600, false);
      container.start();
    } catch (SlickException e) {
      e.printStackTrace();
    }
  }
}
