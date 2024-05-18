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
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.MouseOverArea;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.util.Log;





public class GUITest
  extends BasicGame
  implements ComponentListener
{
  private Image image;
  private MouseOverArea[] areas = new MouseOverArea[4];
  
  private GameContainer container;
  
  private String message = "Demo Menu System with stock images";
  

  private TextField field;
  
  private TextField field2;
  
  private Image background;
  
  private Font font;
  
  private AppGameContainer app;
  

  public GUITest()
  {
    super("GUI Test");
  }
  

  public void init(GameContainer container)
    throws SlickException
  {
    if ((container instanceof AppGameContainer)) {
      app = ((AppGameContainer)container);
      app.setIcon("testdata/icon.tga");
    }
    
    font = new AngelCodeFont("testdata/demo2.fnt", "testdata/demo2_00.tga");
    field = new TextField(container, font, 150, 20, 500, 35, new ComponentListener() {
      public void componentActivated(AbstractComponent source) {
        message = ("Entered1: " + field.getText());
        field2.setFocus(true);
      }
    });
    field2 = new TextField(container, font, 150, 70, 500, 35, new ComponentListener() {
      public void componentActivated(AbstractComponent source) {
        message = ("Entered2: " + field2.getText());
        field.setFocus(true);
      }
    });
    field2.setBorderColor(Color.red);
    
    this.container = container;
    
    image = new Image("testdata/logo.tga");
    background = new Image("testdata/dungeontiles.gif");
    container.setMouseCursor("testdata/cursor.tga", 0, 0);
    
    for (int i = 0; i < 4; i++) {
      areas[i] = new MouseOverArea(container, image, 300, 100 + i * 100, 200, 90, this);
      areas[i].setNormalColor(new Color(1.0F, 1.0F, 1.0F, 0.8F));
      areas[i].setMouseOverColor(new Color(1.0F, 1.0F, 1.0F, 0.9F));
    }
  }
  


  public void render(GameContainer container, Graphics g)
  {
    background.draw(0.0F, 0.0F, 800.0F, 500.0F);
    
    for (int i = 0; i < 4; i++) {
      areas[i].render(container, g);
    }
    field.render(container, g);
    field2.render(container, g);
    
    g.setFont(font);
    g.drawString(message, 200.0F, 550.0F);
  }
  



  public void update(GameContainer container, int delta) {}
  



  public void keyPressed(int key, char c)
  {
    if (key == 1) {
      System.exit(0);
    }
    if (key == 60) {
      app.setDefaultMouseCursor();
    }
    if ((key == 59) && 
      (app != null)) {
      try {
        app.setDisplayMode(640, 480, false);
      } catch (SlickException e) {
        Log.error(e);
      }
    }
  }
  




  public static void main(String[] argv)
  {
    try
    {
      AppGameContainer container = new AppGameContainer(new GUITest());
      container.setDisplayMode(800, 600, false);
      container.start();
    } catch (SlickException e) {
      e.printStackTrace();
    }
  }
  


  public void componentActivated(AbstractComponent source)
  {
    System.out.println("ACTIVL : " + source);
    for (int i = 0; i < 4; i++) {
      if (source == areas[i]) {
        message = ("Option " + (i + 1) + " pressed!");
      }
    }
  }
}
