package org.newdawn.slick.command;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import org.newdawn.slick.Input;
import org.newdawn.slick.util.InputAdapter;










public class InputProvider
{
  private HashMap commands;
  private ArrayList listeners = new ArrayList();
  

  private Input input;
  

  private HashMap commandState = new HashMap();
  

  private boolean active = true;
  






  public InputProvider(Input input)
  {
    this.input = input;
    
    input.addListener(new InputListenerImpl(null));
    commands = new HashMap();
  }
  






  public List getUniqueCommands()
  {
    List uniqueCommands = new ArrayList();
    
    for (Iterator it = commands.values().iterator(); it.hasNext();) {
      Command command = (Command)it.next();
      
      if (!uniqueCommands.contains(command)) {
        uniqueCommands.add(command);
      }
    }
    
    return uniqueCommands;
  }
  







  public List getControlsFor(Command command)
  {
    List controlsForCommand = new ArrayList();
    
    for (Iterator it = commands.entrySet().iterator(); it.hasNext();) {
      Map.Entry entry = (Map.Entry)it.next();
      Control key = (Control)entry.getKey();
      Command value = (Command)entry.getValue();
      
      if (value == command) {
        controlsForCommand.add(key);
      }
    }
    return controlsForCommand;
  }
  





  public void setActive(boolean active)
  {
    this.active = active;
  }
  




  public boolean isActive()
  {
    return active;
  }
  






  public void addListener(InputProviderListener listener)
  {
    listeners.add(listener);
  }
  






  public void removeListener(InputProviderListener listener)
  {
    listeners.remove(listener);
  }
  







  public void bindCommand(Control control, Command command)
  {
    commands.put(control, command);
    
    if (commandState.get(command) == null) {
      commandState.put(command, new CommandState(null));
    }
  }
  




  public void clearCommand(Command command)
  {
    List controls = getControlsFor(command);
    
    for (int i = 0; i < controls.size(); i++) {
      unbindCommand((Control)controls.get(i));
    }
  }
  





  public void unbindCommand(Control control)
  {
    Command command = (Command)commands.remove(control);
    if ((command != null) && 
      (!commands.keySet().contains(command))) {
      commandState.remove(command);
    }
  }
  







  private CommandState getState(Command command)
  {
    return (CommandState)commandState.get(command);
  }
  







  public boolean isCommandControlDown(Command command)
  {
    return getState(command).isDown();
  }
  







  public boolean isCommandControlPressed(Command command)
  {
    return getState(command).isPressed();
  }
  






  protected void firePressed(Command command)
  {
    getStatedown = true;
    getStatepressed = true;
    
    if (!isActive()) {
      return;
    }
    
    for (int i = 0; i < listeners.size(); i++) {
      ((InputProviderListener)listeners.get(i)).controlPressed(command);
    }
  }
  






  protected void fireReleased(Command command)
  {
    getStatedown = false;
    
    if (!isActive()) {
      return;
    }
    
    for (int i = 0; i < listeners.size(); i++) {
      ((InputProviderListener)listeners.get(i)).controlReleased(command);
    }
  }
  



  private class CommandState
  {
    private boolean down;
    


    private boolean pressed;
    


    private CommandState() {}
    


    public boolean isPressed()
    {
      if (pressed) {
        pressed = false;
        return true;
      }
      
      return false;
    }
    




    public boolean isDown()
    {
      return down;
    }
  }
  


  private class InputListenerImpl
    extends InputAdapter
  {
    private InputListenerImpl() {}
    

    public boolean isAcceptingInput()
    {
      return true;
    }
    


    public void keyPressed(int key, char c)
    {
      Command command = (Command)commands.get(new KeyControl(key));
      if (command != null) {
        firePressed(command);
      }
    }
    


    public void keyReleased(int key, char c)
    {
      Command command = (Command)commands.get(new KeyControl(key));
      if (command != null) {
        fireReleased(command);
      }
    }
    


    public void mousePressed(int button, int x, int y)
    {
      Command command = (Command)commands.get(new MouseButtonControl(
        button));
      if (command != null) {
        firePressed(command);
      }
    }
    


    public void mouseReleased(int button, int x, int y)
    {
      Command command = (Command)commands.get(new MouseButtonControl(
        button));
      if (command != null) {
        fireReleased(command);
      }
    }
    


    public void controllerLeftPressed(int controller)
    {
      Command command = 
        (Command)commands.get(new ControllerDirectionControl(controller, 
        ControllerDirectionControl.LEFT));
      if (command != null) {
        firePressed(command);
      }
    }
    


    public void controllerLeftReleased(int controller)
    {
      Command command = 
        (Command)commands.get(new ControllerDirectionControl(controller, 
        ControllerDirectionControl.LEFT));
      if (command != null) {
        fireReleased(command);
      }
    }
    


    public void controllerRightPressed(int controller)
    {
      Command command = 
        (Command)commands.get(new ControllerDirectionControl(controller, 
        ControllerDirectionControl.RIGHT));
      if (command != null) {
        firePressed(command);
      }
    }
    


    public void controllerRightReleased(int controller)
    {
      Command command = 
        (Command)commands.get(new ControllerDirectionControl(controller, 
        ControllerDirectionControl.RIGHT));
      if (command != null) {
        fireReleased(command);
      }
    }
    


    public void controllerUpPressed(int controller)
    {
      Command command = 
        (Command)commands.get(new ControllerDirectionControl(controller, 
        ControllerDirectionControl.UP));
      if (command != null) {
        firePressed(command);
      }
    }
    

    public void controllerUpReleased(int controller)
    {
      Command command = 
        (Command)commands.get(new ControllerDirectionControl(controller, 
        ControllerDirectionControl.UP));
      if (command != null) {
        fireReleased(command);
      }
    }
    


    public void controllerDownPressed(int controller)
    {
      Command command = 
        (Command)commands.get(new ControllerDirectionControl(controller, 
        ControllerDirectionControl.DOWN));
      if (command != null) {
        firePressed(command);
      }
    }
    


    public void controllerDownReleased(int controller)
    {
      Command command = 
        (Command)commands.get(new ControllerDirectionControl(controller, 
        ControllerDirectionControl.DOWN));
      if (command != null) {
        fireReleased(command);
      }
    }
    



    public void controllerButtonPressed(int controller, int button)
    {
      Command command = 
        (Command)commands.get(new ControllerButtonControl(controller, button));
      if (command != null) {
        firePressed(command);
      }
    }
    



    public void controllerButtonReleased(int controller, int button)
    {
      Command command = 
        (Command)commands.get(new ControllerButtonControl(controller, button));
      if (command != null) {
        fireReleased(command);
      }
    }
  }
}
