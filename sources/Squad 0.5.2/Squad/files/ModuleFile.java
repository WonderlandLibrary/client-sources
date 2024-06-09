package Squad.files;

import java.util.ArrayList;

import Squad.base.Module;
import Squad.base.ModuleManager;

public class ModuleFile
{
  private static FileManager ModuleList = new FileManager("ActiveModules", Squad.Squad.ClientName);
  private static ArrayList<String> modulesFromFile = new ArrayList();
  
  public ModuleFile()
  {
    try
    {
      loadActiveModules();
    }
    catch (Exception localException) {}
  }
  
  public static void saveModulesState()
  {
    try
    {
      ModuleList.clear();
      for (Module m : ModuleManager.getModules())
      {
        String line = null;
        if (m.isEnabled())
        {
          line = m.getName();
          ModuleList.write(line);
        }
      }
    }
    catch (Exception localException) {}
  }
  
  public static void loadActiveModules()
  {
    try
    {
      for (String s : ModuleList.read()) {
        modulesFromFile.add(s);
      }
      for (Module m : ModuleManager.getModules()) {
        if (modulesFromFile.contains(m.getName()))
        {
          m.toggle();
          m.setToggled(true);
          m.onEnable();
        }
      }
    }
    catch (Exception localException) {}
  }
}
