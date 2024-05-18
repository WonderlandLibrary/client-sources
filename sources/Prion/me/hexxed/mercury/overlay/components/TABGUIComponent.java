package me.hexxed.mercury.overlay.components;

import java.util.ArrayList;
import java.util.List;
import me.hexxed.mercury.modulebase.Module;
import me.hexxed.mercury.modulebase.ModuleCategory;
import me.hexxed.mercury.modulebase.ModuleManager;
import me.hexxed.mercury.overlay.OverlayComponent;
import me.hexxed.mercury.util.ChatColor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.opengl.GL11;


public class TABGUIComponent
  extends OverlayComponent
{
  private String text;
  
  public TABGUIComponent(String text, int x, int y, boolean chat, String xy)
  {
    super("TabGui", x, y, chat, xy);
    this.text = text;
  }
  


  public static int selectedindex = 0;
  
  public static int selectedModuleindex = 0;
  
  public static boolean extended = false;
  
  public void renderComponent()
  {
    int x = getX();
    int y = getY();
    ScaledResolution sr = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
    int width = sr.getScaledWidth();
    int height = sr.getScaledHeight();
    FontRenderer fr = mc.fontRendererObj;
    int displayoffset = selectedindex * 10;
    drawBorderedRect(x - 3, y - 2, x + getMaxLength(fr) + 3, y + getMaxHeight(fr), 1.0F, -15658735, 1881877291);
    Gui.drawRect(x - 2, y + displayoffset - 1, x + getMaxLength(fr) + 2, y + 9 + displayoffset, -257711197);
    int offset = 0;
    for (String cat : getCategoriesNames()) {
      String display = cat;
      if (cat.equalsIgnoreCase("misc")) display = "Miscellaneous";
      fr.func_175063_a(display, x, y + offset, 12895428);
      offset += 10;
    }
    if (extended) {
      int moduleoffset = selectedModuleindex * 10;
      int stringoffset = 0;
      drawBorderedRect(x + getMaxLength(fr) + 5, y - 2, x + getMaxLength(fr) + getMaxLengthModules(fr, (ModuleCategory)getCategories().get(selectedindex)) + 9, y + getMaxHeightModules((ModuleCategory)getCategories().get(selectedindex)), 1.0F, -15658735, 1881877291);
      Gui.drawRect(x + getMaxLength(fr) + 6, y + moduleoffset - 1, x + getMaxLength(fr) + getMaxLengthModules(fr, (ModuleCategory)getCategories().get(selectedindex)) + 8, y + 9 + moduleoffset, -257711197);
      for (String cat : getModules((ModuleCategory)getCategories().get(selectedindex))) {
        String display = cat;
        fr.func_175063_a(ModuleManager.getModByName(display).isEnabled() ? ChatColor.translateAlternateColorCodes('&', text) + display : display, x + getMaxLength(fr) + 7, y + stringoffset, 12895428);
        stringoffset += 10;
      }
    } else {
      selectedModuleindex = 0;
    }
  }
  
  private int getMaxLength(FontRenderer fr) {
    int maxlenght = 0;
    for (ModuleCategory cat : ModuleCategory.values())
      if (!cat.name().equalsIgnoreCase("none")) {
        String name = cat.name().toUpperCase().charAt(0) + cat.name().toLowerCase().substring(1);
        if (cat.name().equalsIgnoreCase("misc")) name = "Miscellaneous";
        if (fr.getStringWidth(name) >= maxlenght) maxlenght = fr.getStringWidth(name);
      }
    return maxlenght;
  }
  
  private int getMaxHeight(FontRenderer fr) {
    int offset = 0;
    for (ModuleCategory cat : ModuleCategory.values()) {
      if (!cat.name().equalsIgnoreCase("none"))
        offset += 10;
    }
    return offset;
  }
  
  private int getMaxLengthModules(FontRenderer fr, ModuleCategory cat) {
    int maxlenght = 0;
    for (Module m : ModuleManager.moduleList) {
      if ((m.getCategory() == cat) && 
        (fr.getStringWidth(m.getModuleDisplayName()) >= maxlenght)) maxlenght = fr.getStringWidth(m.getModuleDisplayName());
    }
    return maxlenght;
  }
  
  private int getMaxHeightModules(ModuleCategory cat) {
    int offset = 0;
    for (Module m : ModuleManager.moduleList) {
      if (m.getCategory() == cat)
        offset += 10;
    }
    return offset;
  }
  
  public static List<String> getCategoriesNames() {
    List<String> categories = new ArrayList();
    for (ModuleCategory cat : ModuleCategory.values())
      if (!cat.name().equalsIgnoreCase("none")) {
        String name = cat.name().toUpperCase().charAt(0) + cat.name().toLowerCase().substring(1);
        categories.add(name);
      }
    return categories;
  }
  
  public static List<ModuleCategory> getCategories() {
    List<ModuleCategory> categories = new ArrayList();
    for (ModuleCategory cat : ModuleCategory.values()) {
      if (cat != ModuleCategory.NONE)
        categories.add(cat);
    }
    return categories;
  }
  
  public static List<String> getModules(ModuleCategory cat) {
    List<String> modules = new ArrayList();
    for (Module m : ModuleManager.moduleList) {
      if (m.getCategory() == cat)
        modules.add(ChatColor.stripColor(m.getModuleDisplayName()));
    }
    return modules;
  }
  
  public static void drawRect(float g, float h, float i, float j, int col1)
  {
    float f = (col1 >> 24 & 0xFF) / 255.0F;
    float f1 = (col1 >> 16 & 0xFF) / 255.0F;
    float f2 = (col1 >> 8 & 0xFF) / 255.0F;
    float f3 = (col1 & 0xFF) / 255.0F;
    
    GL11.glEnable(3042);
    GL11.glDisable(3553);
    GL11.glBlendFunc(770, 771);
    GL11.glEnable(2848);
    
    GL11.glPushMatrix();
    GL11.glColor4f(f1, f2, f3, f);
    GL11.glBegin(7);
    GL11.glVertex2d(i, h);
    GL11.glVertex2d(g, h);
    GL11.glVertex2d(g, j);
    GL11.glVertex2d(i, j);
    GL11.glEnd();
    GL11.glPopMatrix();
    GL11.glColor3f(1.0F, 1.0F, 1.0F);
    GL11.glEnable(3553);
    GL11.glDisable(3042);
    GL11.glDisable(2848);
  }
  

  public static void drawBorderedRect(float x, float y, float x2, float y2, float l1, int col1, int col2)
  {
    drawRect(x, y, x2, y2, col2);
    
    float f = (col1 >> 24 & 0xFF) / 255.0F;
    float f1 = (col1 >> 16 & 0xFF) / 255.0F;
    float f2 = (col1 >> 8 & 0xFF) / 255.0F;
    float f3 = (col1 & 0xFF) / 255.0F;
    
    GL11.glEnable(3042);
    GL11.glDisable(3553);
    GL11.glBlendFunc(770, 771);
    GL11.glEnable(2848);
    
    GL11.glPushMatrix();
    GL11.glColor4f(f1, f2, f3, f);
    GL11.glLineWidth(l1);
    GL11.glBegin(1);
    GL11.glVertex2d(x, y);
    GL11.glVertex2d(x, y2);
    GL11.glVertex2d(x2, y2);
    GL11.glVertex2d(x2, y);
    GL11.glVertex2d(x, y);
    GL11.glVertex2d(x2, y);
    GL11.glVertex2d(x, y2);
    GL11.glVertex2d(x2, y2);
    GL11.glEnd();
    GL11.glPopMatrix();
    
    GL11.glEnable(3553);
    GL11.glDisable(3042);
    GL11.glDisable(2848);
  }
}
