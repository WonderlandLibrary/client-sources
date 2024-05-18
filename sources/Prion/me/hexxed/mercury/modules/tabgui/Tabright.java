package me.hexxed.mercury.modules.tabgui;

import me.hexxed.mercury.modulebase.Module;
import me.hexxed.mercury.modulebase.ModuleCategory;
import me.hexxed.mercury.overlay.components.TABGUIComponent;


public class Tabright
  extends Module
{
  public Tabright()
  {
    super("tabright", 205, true, ModuleCategory.NONE, false);
  }
  
  public void onEnable()
  {
    if (!TABGUIComponent.extended) TABGUIComponent.extended = true;
    setStateSilent(false);
  }
}
