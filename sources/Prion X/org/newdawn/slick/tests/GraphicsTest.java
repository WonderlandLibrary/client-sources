package org.newdawn.slick.tests;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.util.FastTrig;












public class GraphicsTest
  extends BasicGame
{
  private boolean clip;
  private float ang;
  private Image image;
  private Polygon poly;
  private GameContainer container;
  
  public GraphicsTest()
  {
    super("Graphics Test");
  }
  

  public void init(GameContainer container)
    throws SlickException
  {
    this.container = container;
    
    image = new Image("testdata/logo.tga", true);
    
    Image temp = new Image("testdata/palette_tool.png");
    container.setMouseCursor(temp, 0, 0);
    
    container.setIcons(new String[] { "testdata/icon.tga" });
    container.setTargetFrameRate(100);
    
    poly = new Polygon();
    float len = 100.0F;
    
    for (int x = 0; x < 360; x += 30) {
      if (len == 100.0F) {
        len = 50.0F;
      } else {
        len = 100.0F;
      }
      poly.addPoint((float)FastTrig.cos(Math.toRadians(x)) * len, 
        (float)FastTrig.sin(Math.toRadians(x)) * len);
    }
  }
  

  public void render(GameContainer container, Graphics g)
    throws SlickException
  {
    g.setColor(Color.white);
    
    g.setAntiAlias(true);
    for (int x = 0; x < 360; x += 10) {
      g.drawLine(700.0F, 100.0F, (int)(700.0D + Math.cos(Math.toRadians(x)) * 100.0D), 
        (int)(100.0D + Math.sin(Math.toRadians(x)) * 100.0D));
    }
    g.setAntiAlias(false);
    
    g.setColor(Color.yellow);
    g.drawString("The Graphics Test!", 300.0F, 50.0F);
    g.setColor(Color.white);
    g.drawString("Space - Toggles clipping", 400.0F, 80.0F);
    g.drawString("Frame rate capped to 100", 400.0F, 120.0F);
    
    if (clip) {
      g.setColor(Color.gray);
      g.drawRect(100.0F, 260.0F, 400.0F, 100.0F);
      g.setClip(100, 260, 400, 100);
    }
    
    g.setColor(Color.yellow);
    g.translate(100.0F, 120.0F);
    g.fill(poly);
    g.setColor(Color.blue);
    g.setLineWidth(3.0F);
    g.draw(poly);
    g.setLineWidth(1.0F);
    g.translate(0.0F, 230.0F);
    g.draw(poly);
    g.resetTransform();
    
    g.setColor(Color.magenta);
    g.drawRoundRect(10.0F, 10.0F, 100.0F, 100.0F, 10);
    g.fillRoundRect(10.0F, 210.0F, 100.0F, 100.0F, 10);
    
    g.rotate(400.0F, 300.0F, ang);
    g.setColor(Color.green);
    g.drawRect(200.0F, 200.0F, 200.0F, 200.0F);
    g.setColor(Color.blue);
    g.fillRect(250.0F, 250.0F, 100.0F, 100.0F);
    
    g.drawImage(image, 300.0F, 270.0F);
    
    g.setColor(Color.red);
    g.drawOval(100.0F, 100.0F, 200.0F, 200.0F);
    g.setColor(Color.red.darker());
    g.fillOval(300.0F, 300.0F, 150.0F, 100.0F);
    g.setAntiAlias(true);
    g.setColor(Color.white);
    g.setLineWidth(5.0F);
    g.drawOval(300.0F, 300.0F, 150.0F, 100.0F);
    g.setAntiAlias(true);
    g.resetTransform();
    
    if (clip) {
      g.clearClip();
    }
  }
  


  public void update(GameContainer container, int delta)
  {
    ang += delta * 0.1F;
  }
  


  public void keyPressed(int key, char c)
  {
    if (key == 1) {
      System.exit(0);
    }
    if (key == 57) {
      clip = (!clip);
    }
  }
  



  public static void main(String[] argv)
  {
    try
    {
      AppGameContainer container = new AppGameContainer(new GraphicsTest());
      container.setDisplayMode(800, 600, false);
      container.start();
    } catch (SlickException e) {
      e.printStackTrace();
    }
  }
}
