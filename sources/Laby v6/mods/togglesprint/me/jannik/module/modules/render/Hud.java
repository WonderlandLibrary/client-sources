package mods.togglesprint.me.jannik.module.modules.render;

import mods.togglesprint.com.darkmagician6.eventapi.EventTarget;
import mods.togglesprint.me.imfr0zen.guiapi.example.Settings;
import mods.togglesprint.me.jannik.Jannik;
import mods.togglesprint.me.jannik.events.EventDrawScreen;
import mods.togglesprint.me.jannik.module.Category;
import mods.togglesprint.me.jannik.module.Module;
import mods.togglesprint.me.jannik.module.ModuleManager;
import mods.togglesprint.me.jannik.utils.ColorUtils;
import mods.togglesprint.me.jannik.utils.RenderHelper;
import mods.togglesprint.me.jannik.value.Value;
import mods.togglesprint.me.jannik.value.values.Values;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

public class Hud
  extends Module
{
  private static RenderHelper r = new RenderHelper();
  private static FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;
  
  public Hud()
  {
    super("Hud", Category.RENDER);
  }
  
  @EventTarget
  public void onDrawScreen(EventDrawScreen event)
  {
    arrayList();
    clientInfo();
  }
  
  private static void arrayList()
  {
      int index = 0;
      long x = 0;
    int y = (int)Values.hud_y.getFloatValue() + 10;
    for (Module m : Jannik.getModuleManager().getModulesSorted()) {
      if ((m.isEnabled()) && (m.getName() != "Hud") && (m.getName() != "ClickGui"))
      {
        if (Values.hud_drawrect.getBooleanValue()) {
          if (Values.hud_leftarraylist.getBooleanValue()) {
            RenderHelper.drawRect(0.0F, y - 1, fr.getStringWidth(m.getName()) + 5, y + 9, RenderHelper.toRGB(0, 0, 0, 150));
          } else {
            RenderHelper.drawRect(RenderHelper.getScaledWidth(), y - 1, RenderHelper.getScaledWidth() - (fr.getStringWidth(m.getName()) + 4), y + 9, RenderHelper.toRGB(0, 0, 0, 150));
          }
        }
        if (Values.hud_leftarraylist.getBooleanValue()) {
          fr.drawStringWithShadow(m.getName(), 2.0F, y, RenderHelper.toRGB(Settings.ared, Settings.agreen, Settings.ablue, 255));
        } else {
          fr.drawStringWithShadow(m.getName(), RenderHelper.getScaledWidth() - (fr.getStringWidth(m.getName()) + 2), y, ColorUtils.rainbowEffekt(index  + x * 20000000L, 1.0F).getRGB());
        }
        y += 10;
      }
    }
  }
  
  private static void clientInfo()
  {
    int y = (int)Values.hud_y.getFloatValue();
    String text = Jannik.client_Name + " " + Jannik.client_Version;
    if (Values.hud_leftarraylist.getBooleanValue()) {
      fr.drawStringWithShadow(text, .0F, y, RenderHelper.toRGB(255, 255, 255, 255));
    } else {
      fr.drawStringWithShadow(text, RenderHelper.getScaledWidth() - fr.getStringWidth(text + 2), y, RenderHelper.toRGB(255, 255, 255, 255));
    }
  }
}
