package me.hexxed.mercury.modules.tabgui;

import java.util.List;
import me.hexxed.mercury.modulebase.Module;
import me.hexxed.mercury.modulebase.ModuleCategory;
import me.hexxed.mercury.overlay.components.TABGUIComponent;

public class Tabup extends Module
{
  public Tabup()
  {
    super("tabup", 200, true, ModuleCategory.NONE, false);
  }
  
  public void onEnable()
  {
    if (TABGUIComponent.extended) {
      if (TABGUIComponent.selectedModuleindex > 0) {
        TABGUIComponent.selectedModuleindex -= 1;
      } else {
        TABGUIComponent.selectedModuleindex = TABGUIComponent.getModules((ModuleCategory)TABGUIComponent.getCategories().get(TABGUIComponent.selectedindex)).size() - 1;
      }
      setStateSilent(false);
    } else {
      if (TABGUIComponent.selectedindex > 0) {
        TABGUIComponent.selectedindex -= 1;
      } else {
        TABGUIComponent.selectedindex = TABGUIComponent.getCategories().size() - 1;
      }
      setStateSilent(false);
    }
  }
}
