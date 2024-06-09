package org.newdawn.slick.tests;

import java.io.PrintStream;
import java.util.ArrayList;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.util.Log;





public class InputTest
  extends BasicGame
{
  private String message = "Press any key, mouse button, or drag the mouse";
  
  private ArrayList lines = new ArrayList();
  
  private boolean buttonDown;
  
  private float x;
  
  private float y;
  
  private Color[] cols = { Color.red, Color.green, Color.blue, Color.white, Color.magenta, Color.cyan };
  

  private int index;
  

  private Input input;
  
  private int ypos;
  
  private AppGameContainer app;
  
  private boolean space;
  
  private boolean lshift;
  
  private boolean rshift;
  

  public InputTest()
  {
    super("Input Test");
  }
  

  public void init(GameContainer container)
    throws SlickException
  {
    if ((container instanceof AppGameContainer)) {
      app = ((AppGameContainer)container);
    }
    
    input = container.getInput();
    x = 300.0F;
    y = 300.0F;
  }
  


  public void render(GameContainer container, Graphics g)
  {
    g.drawString("left shift down: " + lshift, 100.0F, 240.0F);
    g.drawString("right shift down: " + rshift, 100.0F, 260.0F);
    g.drawString("space down: " + space, 100.0F, 280.0F);
    
    g.setColor(Color.white);
    g.drawString(message, 10.0F, 50.0F);
    g.drawString(container.getInput().getMouseY(), 10.0F, 400.0F);
    g.drawString("Use the primary gamepad to control the blob, and hit a gamepad button to change the color", 10.0F, 90.0F);
    
    for (int i = 0; i < lines.size(); i++) {
      Line line = (Line)lines.get(i);
      line.draw(g);
    }
    
    g.setColor(cols[index]);
    g.fillOval((int)x, (int)y, 50.0F, 50.0F);
    g.setColor(Color.yellow);
    g.fillRect(50.0F, 200 + ypos, 40.0F, 40.0F);
  }
  


  public void update(GameContainer container, int delta)
  {
    lshift = container.getInput().isKeyDown(42);
    rshift = container.getInput().isKeyDown(54);
    space = container.getInput().isKeyDown(57);
    
    if (controllerLeft[0] != 0) {
      x -= delta * 0.1F;
    }
    if (controllerRight[0] != 0) {
      x += delta * 0.1F;
    }
    if (controllerUp[0] != 0) {
      y -= delta * 0.1F;
    }
    if (controllerDown[0] != 0) {
      y += delta * 0.1F;
    }
  }
  


  public void keyPressed(int key, char c)
  {
    if (key == 1) {
      System.exit(0);
    }
    if ((key == 59) && 
      (app != null)) {
      try {
        app.setDisplayMode(600, 600, false);
        app.reinit();
      } catch (Exception e) { Log.error(e);
      }
    }
  }
  


  public void keyReleased(int key, char c)
  {
    message = ("You pressed key code " + key + " (character = " + c + ")");
  }
  


  public void mousePressed(int button, int x, int y)
  {
    if (button == 0) {
      buttonDown = true;
    }
    
    message = ("Mouse pressed " + button + " " + x + "," + y);
  }
  


  public void mouseReleased(int button, int x, int y)
  {
    if (button == 0) {
      buttonDown = false;
    }
    
    message = ("Mouse released " + button + " " + x + "," + y);
  }
  


  public void mouseClicked(int button, int x, int y, int clickCount)
  {
    System.out.println("CLICKED:" + x + "," + y + " " + clickCount);
  }
  


  public void mouseWheelMoved(int change)
  {
    message = ("Mouse wheel moved: " + change);
    
    if (change < 0) {
      ypos -= 10;
    }
    if (change > 0) {
      ypos += 10;
    }
  }
  


  public void mouseMoved(int oldx, int oldy, int newx, int newy)
  {
    if (buttonDown) {
      lines.add(new Line(oldx, oldy, newx, newy));
    }
  }
  



  private class Line
  {
    private int oldx;
    


    private int oldy;
    


    private int newx;
    


    private int newy;
    



    public Line(int oldx, int oldy, int newx, int newy)
    {
      this.oldx = oldx;
      this.oldy = oldy;
      this.newx = newx;
      this.newy = newy;
    }
    




    public void draw(Graphics g)
    {
      g.drawLine(oldx, oldy, newx, newy);
    }
  }
  


  public void controllerButtonPressed(int controller, int button)
  {
    super.controllerButtonPressed(controller, button);
    
    index += 1;
    index %= cols.length;
  }
  



  public static void main(String[] argv)
  {
    try
    {
      AppGameContainer container = new AppGameContainer(new InputTest());
      container.setDisplayMode(800, 600, false);
      container.start();
    } catch (SlickException e) {
      e.printStackTrace();
    }
  }
}
