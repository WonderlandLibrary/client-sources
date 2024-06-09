package me.hexxed.mercury.modules;

import me.hexxed.mercury.modulebase.Module;
import me.hexxed.mercury.modulebase.ModuleCategory;
import me.hexxed.mercury.modulebase.Values;

public class Safewalk
  extends Module
{
  public Safewalk()
  {
    super("Safewalk", 0, true, ModuleCategory.MISC);
  }
  
  public void onEnable()
  {
    getValuessafewalk = true;
  }
  
  public void onDisable()
  {
    getValuessafewalk = false;
  }
}
