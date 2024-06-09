package org.newdawn.slick.tests;

import java.io.PrintStream;
import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.opengl.pbuffer.GraphicsFactory;












public class ImageGraphicsTest
  extends BasicGame
{
  private Image preloaded;
  private Image target;
  private Image cut;
  private Graphics gTarget;
  private Graphics offscreenPreload;
  private Image testImage;
  private Font testFont;
  private float ang;
  private String using = "none";
  


  public ImageGraphicsTest()
  {
    super("Image Graphics Test");
  }
  

  public void init(GameContainer container)
    throws SlickException
  {
    testImage = new Image("testdata/logo.png");
    preloaded = new Image("testdata/logo.png");
    testFont = new AngelCodeFont("testdata/hiero.fnt", "testdata/hiero.png");
    target = new Image(400, 300);
    cut = new Image(100, 100);
    gTarget = target.getGraphics();
    offscreenPreload = preloaded.getGraphics();
    
    offscreenPreload.drawString("Drawing over a loaded image", 5.0F, 15.0F);
    offscreenPreload.setLineWidth(5.0F);
    offscreenPreload.setAntiAlias(true);
    offscreenPreload.setColor(Color.blue.brighter());
    offscreenPreload.drawOval(200.0F, 30.0F, 50.0F, 50.0F);
    offscreenPreload.setColor(Color.white);
    offscreenPreload.drawRect(190.0F, 20.0F, 70.0F, 70.0F);
    offscreenPreload.flush();
    
    if (GraphicsFactory.usingFBO()) {
      using = "FBO (Frame Buffer Objects)";
    } else if (GraphicsFactory.usingPBuffer()) {
      using = "Pbuffer (Pixel Buffers)";
    }
    
    System.out.println(preloaded.getColor(50, 50));
  }
  




  public void render(GameContainer container, Graphics g)
    throws SlickException
  {
    gTarget.setBackground(new Color(0, 0, 0, 0));
    gTarget.clear();
    gTarget.rotate(200.0F, 160.0F, ang);
    gTarget.setFont(testFont);
    gTarget.fillRect(10.0F, 10.0F, 50.0F, 50.0F);
    gTarget.drawString("HELLO WORLD", 10.0F, 10.0F);
    
    gTarget.drawImage(testImage, 100.0F, 150.0F);
    gTarget.drawImage(testImage, 100.0F, 50.0F);
    gTarget.drawImage(testImage, 50.0F, 75.0F);
    


    gTarget.flush();
    
    g.setColor(Color.red);
    g.fillRect(250.0F, 50.0F, 200.0F, 200.0F);
    

    target.draw(300.0F, 100.0F);
    target.draw(300.0F, 410.0F, 200.0F, 150.0F);
    target.draw(505.0F, 410.0F, 100.0F, 75.0F);
    


    g.setColor(Color.white);
    g.drawString("Testing On Offscreen Buffer", 300.0F, 80.0F);
    g.setColor(Color.green);
    g.drawRect(300.0F, 100.0F, target.getWidth(), target.getHeight());
    g.drawRect(300.0F, 410.0F, target.getWidth() / 2, target.getHeight() / 2);
    g.drawRect(505.0F, 410.0F, target.getWidth() / 4, target.getHeight() / 4);
    



    g.setColor(Color.white);
    g.drawString("Testing Font On Back Buffer", 10.0F, 100.0F);
    g.drawString("Using: " + using, 10.0F, 580.0F);
    g.setColor(Color.red);
    g.fillRect(10.0F, 120.0F, 200.0F, 5.0F);
    

    int xp = (int)(60.0D + Math.sin(ang / 60.0F) * 50.0D);
    g.copyArea(cut, xp, 50);
    


    cut.draw(30.0F, 250.0F);
    g.setColor(Color.white);
    g.drawRect(30.0F, 250.0F, cut.getWidth(), cut.getHeight());
    g.setColor(Color.gray);
    g.drawRect(xp, 50.0F, cut.getWidth(), cut.getHeight());
    



    preloaded.draw(2.0F, 400.0F);
    g.setColor(Color.blue);
    g.drawRect(2.0F, 400.0F, preloaded.getWidth(), preloaded.getHeight());
  }
  


  public void update(GameContainer container, int delta)
  {
    ang += delta * 0.1F;
  }
  



  public static void main(String[] argv)
  {
    try
    {
      GraphicsFactory.setUseFBO(false);
      
      AppGameContainer container = new AppGameContainer(new ImageGraphicsTest());
      container.setDisplayMode(800, 600, false);
      container.start();
    } catch (SlickException e) {
      e.printStackTrace();
    }
  }
}
