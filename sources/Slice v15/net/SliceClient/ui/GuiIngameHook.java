package net.SliceClient.ui;

import java.awt.Color;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import net.SliceClient.Gui.TabGui;
import net.SliceClient.Slice;
import net.SliceClient.TTF.TTFManager;
import net.SliceClient.TTF.TTFRenderer;
import net.SliceClient.Utils.Rainbow;
import net.SliceClient.Utils.Wrapper;
import net.SliceClient.module.Module;
import net.SliceClient.module.ModuleManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.Item;
import org.lwjgl.opengl.GL11;

public class GuiIngameHook extends GuiIngame
{
  public GuiIngameHook(Minecraft mcIn)
  {
    super(mcIn);
  }
  



  public static int RGBtoHEX(int r, int g, int b, int a)
  {
    return (a << 24) + (r << 16) + (g << 8) + b;
  }
  

  public void func_175180_a(float p_175180_1_)
  {
    super.func_175180_a(p_175180_1_);
    
    ScaledResolution s1 = new ScaledResolution(Wrapper.mc, Minecraft.displayWidth, Minecraft.displayHeight);
    
    ScaledResolution sr = new ScaledResolution(this.mc, Minecraft.displayWidth, Minecraft.displayHeight);
    SimpleDateFormat formatDate = new SimpleDateFormat("hh:mm");
    String timeString = formatDate.format(new Date());
    

    String direction = Wrapper.getDirection();
    
    Wrapper.fr.drawStringWithShadow("§7Time:§f " + timeString, 
      sr.getScaledWidth() - Wrapper.fr.getStringWidth("§7Time§f: " + timeString) - 2, 
      sr.getScaledHeight() - 10, 16777215);
    
    Wrapper.fr.drawStringWithShadow("§7D:§f " + direction, 
      sr.getScaledWidth() - Wrapper.fr.getStringWidth("§7D§f: " + direction) - 2, 
      sr.getScaledHeight() - 20, 16777215);
    
    String xPos = String.format("%.0f", new Object[] { Double.valueOf(thePlayerposX) });
    String yPos = String.format("%.0f", new Object[] { Double.valueOf(thePlayerposY) });
    String zPos = String.format("%.0f", new Object[] { Double.valueOf(thePlayerposZ) });
    
    int ping = 0;
    
    if (Wrapper.mc.isSingleplayer()) {
      ping = 0;
    } else {
      ping = (int)mcgetCurrentServerDatapingToServer;
    }
    
    int x = 95;
    if ((thePlayerposY < 100.0D) || (thePlayerposY > -99.0D)) {
      x = 99;
    } else {
      x = 104;
    }
    
    int y = 1;
    if (thePlayerposX < 100.0D) {
      if (thePlayerposX > 0.0D) {
        y = 70;
      }
      if (thePlayerposX < -99.0D) {
        y = 75;
      }
    } else {
      y = 75;
    }
    
    DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
    
    Date today = Calendar.getInstance().getTime();
    
    String reportDate = df.format(today);
    
    DateFormat dff = new SimpleDateFormat("HH:mm:ss");
    
    Date todayy = Calendar.getInstance().getTime();
    
    String reporttime = dff.format(todayy);
    



    Minecraft.fontRendererObj.drawStringWithShadow("§7FPS: §f" + Minecraft.debugFPS, 17.0F, s1.getScaledHeight() - 20, -1);
    Minecraft.fontRendererObj.drawStringWithShadow("§7Ping: §f" + ping, 67.0F, s1.getScaledHeight() - 20, -1);
    Minecraft.fontRendererObj.drawStringWithShadow("§7X:§f" + xPos, 71.0F, s1.getScaledHeight() - 10, -1);
    Minecraft.fontRendererObj.drawStringWithShadow("§7Y:§f" + yPos, 17.0F, s1.getScaledHeight() - 10, -1);
    Minecraft.fontRendererObj.drawStringWithShadow("§7Z:§f" + zPos, 44.0F, s1.getScaledHeight() - 10, -1);
    
    TTFManager.getInstance().getLemonMilk24().drawStringWithShadow("S", 1.0F, s1.getScaledHeight() - 20, -1);
    Minecraft.fontRendererObj.drawStringWithShadow(reporttime, 910.0F, s1.getScaledHeight() - 20, -1);
    
    int width = sr.getScaledWidth();
    int height = 0;
    drawRect(width, height, 0, 0, Integer.MAX_VALUE);
    ArrayList modules = new ArrayList();
    Minecraft mc = Minecraft.getMinecraft();
    TabGui gui = new TabGui(mc);
    
    double scale = 1.0D;
    GL11.glScaled(1.0D / scale, 1.0D / scale, 1.0D / scale);
    
    TTFManager.getInstance().getLemonMilk12().drawStringWithShadow(Slice.G + "B" + "15", 0.0F, 96.0F, Rainbow.rainbow(1.0F).getRGB());
    gui.drawGui(0, 0, 65);
    
    Minecraft.getMinecraft().getRenderItem().func_180450_b(new net.minecraft.item.ItemStack(Item.getByNameOrId("378")), 52, 94);
    
    if (Minecraft.getMinecraft().isFullScreen()) {
      gameSettingskeyBindInventory.isKeyDown();
    }
    
    for (Module m : ModuleManager.activeModules) {
      modules.add(0, m);
    }
    Collections.sort(modules, new ModuleComperator());
    Collections.reverse(modules);
    int i = 0;
    for (Object o : modules)
    {

      Module m = (Module)o;
      if ((m.getState()) && (!m.isCategory(net.SliceClient.module.Category.GUI)))
      {
        float widthh = sr.getScaledWidth() - TTFManager.getInstance().getLemonMilk().getWidth(m.getName()) - 2.0F;
        int heightt = 9 * i + 2;
        TTFManager.getInstance().getLemonMilk().drawStringWithShadow(m.getName() + " §7|", widthh - 0.0F, heightt, m.getColor());
        i++;
        GL11.glScaled(scale, scale, scale);
      }
    }
  }
  
  public static Color rainbow(float offset)
  {
    float hue = ((float)System.nanoTime() + offset) / 1.0E10F % 1.0F;
    long color = Long.parseLong(Integer.toHexString(Integer.valueOf(Color.HSBtoRGB(hue, 1.0F, 1.0F)).intValue()), 16);
    Color c = new Color((int)color);
    return new Color(c.getRed() / 265.0F, c.getGreen() / 265.0F, c.getBlue() / 265.0F, c.getAlpha() / 265.0F);
  }
}
