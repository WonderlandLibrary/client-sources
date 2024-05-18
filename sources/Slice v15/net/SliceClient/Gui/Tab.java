package net.SliceClient.Gui;

import java.util.ArrayList;
import net.SliceClient.TTF.TTFManager;
import net.SliceClient.TTF.TTFRenderer;
import net.SliceClient.module.Module;

public class Tab
{
  private TabGui gui;
  public ArrayList hacks;
  public String tabName;
  public int selectedItem = 0;
  public int menuHeight = 0;
  public int menuWidth = 0;
  private int colour;
  
  public Tab(TabGui GUI, String TabName)
  {
    tabName = TabName;
    gui = GUI;
    hacks = new ArrayList();
  }
  
  public int RGBtoHEX(int r, int g, int b, int a)
  {
    return (a << 24) + (r << 16) + (g << 8) + b;
  }
  
  public void countMenuSize()
  {
    int maxWidth = 0;
    for (int i = 0; i < hacks.size(); i++) {
      if (TTFManager.getInstance().getLemonMilk().getWidth(((Module)hacks.get(i)).getName() + 4) > maxWidth) {
        maxWidth = (int)(TTFManager.getInstance().getLemonMilk().getWidth(((Module)hacks.get(i)).getName()) + 7.5F);
      }
    }
    menuWidth = maxWidth;
    menuHeight = (hacks.size() * gui.tabHeight - 1);
  }
  
  public void drawTabMenu(int x, int y)
  {
    countMenuSize();
    x += 4;
    y += 2;
    RenderHelper.drawBorderedRect(x - 2, y - 1, x + menuWidth + 3, y + menuHeight - 1, 0.1F, RGBtoHEX(86, 82, 82, 250), RGBtoHEX(86, 82, 82, 250));
    for (int i = 0; i < hacks.size(); i++)
    {
      RenderHelper.drawBorderedRect(x - 3, y + gui.tabHeight * i - 2, x + menuWidth + 4, y + gui.tabHeight * i + 10, 2.0F, i == TabGui.selectedItem ? 0 : 0, i == TabGui.selectedItem ? RGBtoHEX(96, 99, 192, 255) : 0);
      
      TTFManager.getInstance().getLemonMilk().drawStringWithShadow((((Module)hacks.get(i)).isEnabled() ? "§7" : gui.colorNormal) + ((Module)hacks.get(i)).getName(), x + 2, y + gui.tabHeight * i, -739999111);
    }
  }
}
