package org.newdawn.slick.tests;

import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.CanvasGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;















public class CanvasContainerTest
  extends BasicGame
{
  private Image tga;
  private Image scaleMe;
  private Image scaled;
  private Image gif;
  private Image image;
  private Image subImage;
  private float rot;
  
  public CanvasContainerTest()
  {
    super("Canvas Container Test");
  }
  

  public void init(GameContainer container)
    throws SlickException
  {
    image = (this.tga = new Image("testdata/logo.tga"));
    scaleMe = new Image("testdata/logo.tga", true, 2);
    gif = new Image("testdata/logo.gif");
    scaled = gif.getScaledCopy(120, 120);
    subImage = image.getSubImage(200, 0, 70, 260);
    rot = 0.0F;
  }
  


  public void render(GameContainer container, Graphics g)
  {
    image.draw(0.0F, 0.0F);
    image.draw(500.0F, 0.0F, 200.0F, 100.0F);
    scaleMe.draw(500.0F, 100.0F, 200.0F, 100.0F);
    scaled.draw(400.0F, 500.0F);
    Image flipped = scaled.getFlippedCopy(true, false);
    flipped.draw(520.0F, 500.0F);
    Image flipped2 = flipped.getFlippedCopy(false, true);
    flipped2.draw(520.0F, 380.0F);
    Image flipped3 = flipped2.getFlippedCopy(true, false);
    flipped3.draw(400.0F, 380.0F);
    
    for (int i = 0; i < 3; i++) {
      subImage.draw(200 + i * 30, 300.0F);
    }
    
    g.translate(500.0F, 200.0F);
    g.rotate(50.0F, 50.0F, rot);
    g.scale(0.3F, 0.3F);
    image.draw();
    g.resetTransform();
  }
  


  public void update(GameContainer container, int delta)
  {
    rot += delta * 0.1F;
    if (rot > 360.0F) {
      rot -= 360.0F;
    }
  }
  



  public static void main(String[] argv)
  {
    try
    {
      CanvasGameContainer container = new CanvasGameContainer(new CanvasContainerTest());
      
      Frame frame = new Frame("Test");
      frame.setLayout(new GridLayout(1, 2));
      frame.setSize(500, 500);
      frame.add(container);
      
      frame.addWindowListener(new WindowAdapter() {
        public void windowClosing(WindowEvent e) {
          System.exit(0);
        }
      });
      frame.setVisible(true);
      container.start();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  


  public void keyPressed(int key, char c)
  {
    if (key == 57) {
      if (image == gif) {
        image = tga;
      } else {
        image = gif;
      }
    }
  }
}
