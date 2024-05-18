package net.SliceClient.clickgui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import net.SliceClient.module.Category;
import net.SliceClient.module.Module;
import net.SliceClient.module.ModuleManager;

public class ClickGui extends net.minecraft.client.gui.GuiScreen
{
  private static final List<Panel> panels = new CopyOnWriteArrayList();
  
  public static List<Panel> getPanels() {
    return panels;
  }
  
  private int round(double d)
  {
    double dAbs = Math.abs(d);
    int i; double result = dAbs - (i = (int)dAbs);
    if (result < 0.5D) {
      return d < 0.0D ? -i : i;
    }
    return d < 0.0D ? -i + 1 : i + 1;
  }
  
  public ClickGui() {
    width = 100;
    height = 18;
    panels.add(new Panel("Combat", Category.COMBAT, 15, 10, width, height, false) {
      public void setupItems() {
        Iterator var2 = ModuleManager.getModules().iterator();
        while (var2.hasNext()) {
          Module module = (Module)var2.next();
          if (module.getCategory() == Category.COMBAT) {
            getElements().add(new ElementButton(module, Category.COMBAT));
          }
        }
      }
    });
    panels.add(new Panel("Render", Category.RENDER, 125, 10, width, height, false) {
      public void setupItems() {
        Iterator var2 = ModuleManager.getModules().iterator();
        while (var2.hasNext()) {
          Module module = (Module)var2.next();
          if (module.getCategory() == Category.RENDER) {
            getElements().add(new ElementButton(module, Category.RENDER));
          }
        }
      }
    });
    panels.add(new Panel("Movement", Category.MOVEMENT, 235, 10, width, height, false) {
      public void setupItems() {
        Iterator var2 = ModuleManager.getModules().iterator();
        while (var2.hasNext()) {
          Module module = (Module)var2.next();
          if (module.getCategory() == Category.MOVEMENT) {
            getElements().add(new ElementButton(module, Category.MOVEMENT));
          }
        }
      }
    });
    panels.add(new Panel("Player", Category.PLAYER, 345, 10, width, height, false) {
      public void setupItems() {
        Iterator var2 = ModuleManager.getModules().iterator();
        while (var2.hasNext()) {
          Module module = (Module)var2.next();
          if (module.getCategory() == Category.PLAYER) {
            getElements().add(new ElementButton(module, Category.PLAYER));
          }
        }
      }
    });
    panels.add(new Panel("Misc", Category.MISC, 455, 10, width, height, false) {
      public void setupItems() {
        Iterator var2 = ModuleManager.getModules().iterator();
        while (var2.hasNext()) {
          Module module = (Module)var2.next();
          if (module.getCategory() == Category.MISC) {
            getElements().add(new ElementButton(module, Category.MISC));
          }
          
        }
      }
    });
    panels.add(new Panel("Speed", Category.SPEED, 565, 10, width, height, false) {
      public void setupItems() {
        Iterator var2 = ModuleManager.getModules().iterator();
        while (var2.hasNext()) {
          Module module = (Module)var2.next();
          if (module.getCategory() == Category.SPEED) {
            getElements().add(new ElementButton(module, Category.SPEED));
          }
          
        }
      }
    });
    panels.add(new Panel("Exploits", Category.EXPLOITS, 675, 10, width, height, false) {
      public void setupItems() {
        Iterator var2 = ModuleManager.getModules().iterator();
        while (var2.hasNext()) {
          Module module = (Module)var2.next();
          if (module.getCategory() == Category.EXPLOITS) {
            getElements().add(new ElementButton(module, Category.EXPLOITS));
          }
        }
      }
    });
    panels.add(new Panel("World", Category.WORLD, 785, 10, width, height, false) {
      public void setupItems() {
        Iterator var2 = ModuleManager.getModules().iterator();
        while (var2.hasNext()) {
          Module module = (Module)var2.next();
          if (module.getCategory() == Category.WORLD) {
            getElements().add(new ElementButton(module, Category.WORLD));
          }
        }
      }
    });
  }
  
  public void drawScreen(int i, int j, float k) {
    Iterator var5 = panels.iterator();
    while (var5.hasNext()) {
      Panel panel = (Panel)var5.next();
      panel.drawScreen(i, j, k);
    }
  }
  
  public boolean doesGuiPauseGame() {
    return false;
  }
  
  public void mouseClicked(int i, int j, int k) {
    Iterator var5 = panels.iterator();
    while (var5.hasNext()) {
      Panel panel = (Panel)var5.next();
      panel.mouseClicked(i, j, k);
    }
  }
  
  public void mouseReleased(int i, int j, int k) {
    Iterator var5 = panels.iterator();
    while (var5.hasNext()) {
      Panel panel = (Panel)var5.next();
      panel.mouseReleased(i, j, k);
    }
  }
}
