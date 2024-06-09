package org.newdawn.slick.tests;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.command.BasicCommand;
import org.newdawn.slick.command.Command;
import org.newdawn.slick.command.ControllerButtonControl;
import org.newdawn.slick.command.ControllerDirectionControl;
import org.newdawn.slick.command.InputProvider;
import org.newdawn.slick.command.InputProviderListener;
import org.newdawn.slick.command.KeyControl;
import org.newdawn.slick.command.MouseButtonControl;





public class InputProviderTest
  extends BasicGame
  implements InputProviderListener
{
  private Command attack = new BasicCommand("attack");
  
  private Command jump = new BasicCommand("jump");
  
  private Command run = new BasicCommand("run");
  
  private InputProvider provider;
  
  private String message = "";
  


  public InputProviderTest()
  {
    super("InputProvider Test");
  }
  

  public void init(GameContainer container)
    throws SlickException
  {
    provider = new InputProvider(container.getInput());
    provider.addListener(this);
    
    provider.bindCommand(new KeyControl(203), run);
    provider.bindCommand(new KeyControl(30), run);
    provider.bindCommand(new ControllerDirectionControl(0, ControllerDirectionControl.LEFT), run);
    provider.bindCommand(new KeyControl(200), jump);
    provider.bindCommand(new KeyControl(17), jump);
    provider.bindCommand(new ControllerDirectionControl(0, ControllerDirectionControl.UP), jump);
    provider.bindCommand(new KeyControl(57), attack);
    provider.bindCommand(new MouseButtonControl(0), attack);
    provider.bindCommand(new ControllerButtonControl(0, 1), attack);
  }
  


  public void render(GameContainer container, Graphics g)
  {
    g.drawString("Press A, W, Left, Up, space, mouse button 1,and gamepad controls", 10.0F, 50.0F);
    g.drawString(message, 100.0F, 150.0F);
  }
  



  public void update(GameContainer container, int delta) {}
  



  public void controlPressed(Command command)
  {
    message = ("Pressed: " + command);
  }
  


  public void controlReleased(Command command)
  {
    message = ("Released: " + command);
  }
  



  public static void main(String[] argv)
  {
    try
    {
      AppGameContainer container = new AppGameContainer(new InputProviderTest());
      container.setDisplayMode(800, 600, false);
      container.start();
    } catch (SlickException e) {
      e.printStackTrace();
    }
  }
}
