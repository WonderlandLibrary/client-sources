package org.newdawn.slick.tests;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;






public class TransformTest
  extends BasicGame
{
  private float scale = 1.0F;
  

  private boolean scaleUp;
  
  private boolean scaleDown;
  

  public TransformTest()
  {
    super("Transform Test");
  }
  

  public void init(GameContainer container)
    throws SlickException
  {
    container.setTargetFrameRate(100);
  }
  


  public void render(GameContainer contiainer, Graphics g)
  {
    g.translate(320.0F, 240.0F);
    g.scale(scale, scale);
    
    g.setColor(Color.red);
    for (int x = 0; x < 10; x++) {
      for (int y = 0; y < 10; y++) {
        g.fillRect(65036 + x * 100, 65036 + y * 100, 80.0F, 80.0F);
      }
    }
    
    g.setColor(new Color(1.0F, 1.0F, 1.0F, 0.5F));
    g.fillRect(-320.0F, -240.0F, 640.0F, 480.0F);
    g.setColor(Color.white);
    g.drawRect(-320.0F, -240.0F, 640.0F, 480.0F);
  }
  


  public void update(GameContainer container, int delta)
  {
    if (scaleUp) {
      scale += delta * 0.001F;
    }
    if (scaleDown) {
      scale -= delta * 0.001F;
    }
  }
  


  public void keyPressed(int key, char c)
  {
    if (key == 1) {
      System.exit(0);
    }
    if (key == 16) {
      scaleUp = true;
    }
    if (key == 30) {
      scaleDown = true;
    }
  }
  


  public void keyReleased(int key, char c)
  {
    if (key == 16) {
      scaleUp = false;
    }
    if (key == 30) {
      scaleDown = false;
    }
  }
  



  public static void main(String[] argv)
  {
    try
    {
      AppGameContainer container = new AppGameContainer(new TransformTest());
      container.setDisplayMode(640, 480, false);
      container.start();
    } catch (SlickException e) {
      e.printStackTrace();
    }
  }
}
