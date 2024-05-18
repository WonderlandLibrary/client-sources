package me.hexxed.mercury.modules.tabgui;

import java.util.List;
import me.hexxed.mercury.modulebase.Module;
import me.hexxed.mercury.modulebase.ModuleCategory;
import me.hexxed.mercury.modulebase.ModuleManager;
import me.hexxed.mercury.overlay.components.TABGUIComponent;
import me.hexxed.mercury.util.TimeHelper;
import net.minecraft.client.Minecraft;

public class Tabenter extends Module
{
  public Tabenter()
  {
    super("tabenter", 28, true, ModuleCategory.NONE, false);
  }
  
  public static TimeHelper chat = new TimeHelper();
  
  public void onEnable()
  {
    if (!chat.isDelayComplete(125L)) {
      setStateSilent(false);
      return;
    }
    if ((TABGUIComponent.extended) && (mc.currentScreen == null)) {
      ModuleManager.getModByName((String)TABGUIComponent.getModules((ModuleCategory)TABGUIComponent.getCategories().get(TABGUIComponent.selectedindex)).get(TABGUIComponent.selectedModuleindex)).toggle();
      setStateSilent(false);
    }
  }
}
