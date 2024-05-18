package net.SliceClient.modules;

import net.SliceClient.Slice;
import net.SliceClient.clickgui.ClickGui;
import net.SliceClient.clickgui.PanelManager;
import net.SliceClient.module.Category;
import net.SliceClient.module.Module;
import net.minecraft.client.Minecraft;

public class Gui extends Module
{
  private ClickGui gui;
  
  public Gui()
  {
    super("Gui", Category.GUI, 12597547);
  }
  
  private int ticksToDisable = 0;
  
  public void onEnable() {
    if (Slice.gui == null) {
      Slice.gui = new ClickGui();
      Slice.panelManager.setPanels();
    }
    mc.displayGuiScreen(Slice.gui);
    super.onEnable();
  }
}
