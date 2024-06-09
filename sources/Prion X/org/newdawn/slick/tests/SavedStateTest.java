package org.newdawn.slick.tests;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SavedState;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.TextField;






public class SavedStateTest
  extends BasicGame
  implements ComponentListener
{
  private TextField name;
  private TextField age;
  private String nameValue = "none";
  
  private int ageValue = 0;
  
  private SavedState state;
  
  private String message = "Enter a name and age to store";
  
  private static AppGameContainer container;
  
  public SavedStateTest()
  {
    super("Saved State Test");
  }
  

  public void init(GameContainer container)
    throws SlickException
  {
    state = new SavedState("testdata");
    nameValue = state.getString("name", "DefaultName");
    ageValue = ((int)state.getNumber("age", 64.0D));
    
    name = new TextField(container, container.getDefaultFont(), 100, 100, 300, 20, this);
    age = new TextField(container, container.getDefaultFont(), 100, 150, 201, 20, this);
  }
  


  public void render(GameContainer container, Graphics g)
  {
    name.render(container, g);
    age.render(container, g);
    
    container.getDefaultFont().drawString(100.0F, 300.0F, "Stored Name: " + nameValue);
    container.getDefaultFont().drawString(100.0F, 350.0F, "Stored Age: " + ageValue);
    container.getDefaultFont().drawString(200.0F, 500.0F, message);
  }
  


  public void update(GameContainer container, int delta)
    throws SlickException
  {}
  


  public void keyPressed(int key, char c)
  {
    if (key == 1) {
      System.exit(0);
    }
  }
  






  public static void main(String[] argv)
  {
    try
    {
      container = new AppGameContainer(new SavedStateTest());
      container.setDisplayMode(800, 600, false);
      container.start();
    } catch (SlickException e) {
      e.printStackTrace();
    }
  }
  


  public void componentActivated(AbstractComponent source)
  {
    if (source == name) {
      nameValue = name.getText();
      state.setString("name", nameValue);
    }
    if (source == age) {
      try {
        ageValue = Integer.parseInt(age.getText());
        state.setNumber("age", ageValue);
      }
      catch (NumberFormatException localNumberFormatException) {}
    }
    
    try
    {
      state.save();
    } catch (Exception e) {
      message = (System.currentTimeMillis() + " : Failed to save state");
    }
  }
}
