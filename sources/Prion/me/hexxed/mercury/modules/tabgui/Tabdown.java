package me.hexxed.mercury.modules.tabgui;

import java.util.List;
import me.hexxed.mercury.modulebase.Module;
import me.hexxed.mercury.modulebase.ModuleCategory;
import me.hexxed.mercury.overlay.components.TABGUIComponent;

public class Tabdown extends Module
{
  public Tabdown()
  {
    super("tabdown", 208, true, ModuleCategory.NONE, false);
  }
  
  public void onEnable()
  {
    if (TABGUIComponent.extended) {
      if (TABGUIComponent.selectedModuleindex < TABGUIComponent.getModules((ModuleCategory)TABGUIComponent.getCategories().get(TABGUIComponent.selectedindex)).size() - 1) {
        TABGUIComponent.selectedModuleindex += 1;
      } else {
        TABGUIComponent.selectedModuleindex = 0;
      }
      setStateSilent(false);
    } else {
      if (TABGUIComponent.selectedindex < TABGUIComponent.getCategories().size() - 1) {
        TABGUIComponent.selectedindex += 1;
      } else {
        TABGUIComponent.selectedindex = 0;
      }
      setStateSilent(false);
    }
  }
}
