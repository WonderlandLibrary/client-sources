package net.java.games.input;

import net.java.games.util.plugins.Plugin;





























public class AWTEnvironmentPlugin
  extends ControllerEnvironment
  implements Plugin
{
  private final Controller[] controllers;
  
  public AWTEnvironmentPlugin()
  {
    controllers = new Controller[] { new AWTKeyboard(), new AWTMouse() };
  }
  
  public Controller[] getControllers() {
    return controllers;
  }
  
  public boolean isSupported() {
    return true;
  }
}
