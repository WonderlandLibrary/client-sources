package me.hexxed.mercury.modules.tabgui;

import me.hexxed.mercury.modulebase.Module;
import me.hexxed.mercury.modulebase.ModuleCategory;
import me.hexxed.mercury.overlay.components.TABGUIComponent;

public class Tableft
  extends Module
{
  public Tableft()
  {
    super("tableft", 203, true, ModuleCategory.NONE, false);
  }
  
  public void onEnable()
  {
    if (TABGUIComponent.extended) TABGUIComponent.extended = false;
    setStateSilent(false);
  }
}
