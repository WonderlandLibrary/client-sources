package me.hexxed.mercury.overlay.components;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import me.hexxed.mercury.modulebase.Module;
import me.hexxed.mercury.modulebase.ModuleCategory;
import me.hexxed.mercury.modulebase.Values;
import me.hexxed.mercury.overlay.OverlayComponent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;

public class ARRAYLISTComponent extends OverlayComponent
{
  public ARRAYLISTComponent(int x, int y, boolean chat, String xy)
  {
    super("ArrayList", x, y, chat, xy);
  }
  
  public void renderComponent()
  {
    ScaledResolution sr = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
    int width = sr.getScaledWidth();
    int height = sr.getScaledHeight();
    final FontRenderer fr = mc.fontRendererObj;
    int i = 0;
    List<String> display = new ArrayList();
    for (Module m : me.hexxed.mercury.modulebase.ModuleManager.moduleList) {
      if ((m.isEnabled()) && (visible)) {
        display.add(m.getCategory().getColor() + m.getModuleDisplayName());
      }
    }
    Comparator<String> x1 = new Comparator()
    {

      public int compare(String o1, String o2)
      {
        if (fr.getStringWidth(o1) > fr.getStringWidth(o2)) {
          return -1;
        }
        if (fr.getStringWidth(o2) > fr.getStringWidth(o1)) {
          return 1;
        }
        return 0;
      }
    };
    
    if (getValuessortarraylist) java.util.Collections.sort(display, x1);
    for (String s : display) {
      int mwidth = sr.getScaledWidth() - fr.getStringWidth(s) - 2;
      int mheight = 10 * i + 2;
      fr.func_175063_a(s, mwidth, mheight, 16777215);
      i++;
    }
    display.clear();
  }
}
