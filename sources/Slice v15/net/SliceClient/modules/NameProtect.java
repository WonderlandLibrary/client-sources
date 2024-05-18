package net.SliceClient.modules;

import net.SliceClient.module.Category;
import net.SliceClient.module.Module;



public class NameProtect
  extends Module
{
  public NameProtect()
  {
    super("NameProtect", Category.EXPLOITS, 16376546);
  }
  
  public static boolean isEnabled = false;
  public static String Name = "********";
  
  public void onEnable()
  {
    isEnabled = true;
  }
  

  public void onDisable()
  {
    isEnabled = false;
  }
}
