package net.minecraft.src;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptionButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.GameSettings.Options;

public class GuiDetailSettingsOF extends GuiScreen
{
  private GuiScreen prevScreen;
  protected String title = "Detail Settings";
  private GameSettings settings;
  private static GameSettings.Options[] enumOptions = { GameSettings.Options.CLOUDS, GameSettings.Options.CLOUD_HEIGHT, GameSettings.Options.TREES, GameSettings.Options.RAIN, GameSettings.Options.SKY, GameSettings.Options.STARS, GameSettings.Options.SUN_MOON, GameSettings.Options.SHOW_CAPES, GameSettings.Options.TRANSLUCENT_BLOCKS, GameSettings.Options.HELD_ITEM_TOOLTIPS, GameSettings.Options.DROPPED_ITEMS };
  private int lastMouseX = 0;
  private int lastMouseY = 0;
  private long mouseStillTime = 0L;
  
  public GuiDetailSettingsOF(GuiScreen guiscreen, GameSettings gamesettings)
  {
    prevScreen = guiscreen;
    settings = gamesettings;
  }
  



  public void initGui()
  {
    int i = 0;
    GameSettings.Options[] aenumoptions = enumOptions;
    int j = aenumoptions.length;
    
    for (int k = 0; k < j; k++)
    {
      GameSettings.Options enumoptions = aenumoptions[k];
      int x = width / 2 - 155 + i % 2 * 160;
      int y = height / 6 + 21 * (i / 2) - 10;
      
      if (!enumoptions.getEnumFloat())
      {
        buttonList.add(new GuiOptionButton(enumoptions.returnEnumOrdinal(), x, y, enumoptions, settings.getKeyBinding(enumoptions)));
      }
      else
      {
        buttonList.add(new net.minecraft.client.gui.GuiOptionSlider(enumoptions.returnEnumOrdinal(), x, y, enumoptions));
      }
      
      i++;
    }
    
    buttonList.add(new GuiButton(200, width / 2 - 100, height / 6 + 168 + 11, net.minecraft.client.resources.I18n.format("gui.done", new Object[0])));
  }
  
  protected void actionPerformed(GuiButton guibutton)
  {
    if (enabled)
    {
      if ((id < 200) && ((guibutton instanceof GuiOptionButton)))
      {
        settings.setOptionValue(((GuiOptionButton)guibutton).returnEnumOptions(), 1);
        displayString = settings.getKeyBinding(GameSettings.Options.getEnumOptions(id));
      }
      
      if (id == 200)
      {
        Minecraft.gameSettings.saveOptions();
        mc.displayGuiScreen(prevScreen);
      }
      
      if (id != GameSettings.Options.CLOUD_HEIGHT.ordinal())
      {
        ScaledResolution scaledresolution = new ScaledResolution(mc, Minecraft.displayWidth, Minecraft.displayHeight);
        int i = scaledresolution.getScaledWidth();
        int j = scaledresolution.getScaledHeight();
        setWorldAndResolution(mc, i, j);
      }
    }
  }
  



  public void drawScreen(int x, int y, float f)
  {
    drawDefaultBackground();
    drawCenteredString(fontRendererObj, title, width / 2, 20, 16777215);
    super.drawScreen(x, y, f);
    
    if ((Math.abs(x - lastMouseX) <= 5) && (Math.abs(y - lastMouseY) <= 5))
    {
      short activateDelay = 700;
      
      if (System.currentTimeMillis() >= mouseStillTime + activateDelay)
      {
        int x1 = width / 2 - 150;
        int y1 = height / 6 - 5;
        
        if (y <= y1 + 98)
        {
          y1 += 105;
        }
        
        int x2 = x1 + 150 + 150;
        int y2 = y1 + 84 + 10;
        GuiButton btn = getSelectedButton(x, y);
        
        if (btn != null)
        {
          String s = getButtonName(displayString);
          String[] lines = getTooltipLines(s);
          
          if (lines == null)
          {
            return;
          }
          
          drawGradientRect(x1, y1, x2, y2, -536870912, -536870912);
          
          for (int i = 0; i < lines.length; i++)
          {
            String line = lines[i];
            fontRendererObj.func_175063_a(line, x1 + 5, y1 + 5 + i * 11, 14540253);
          }
        }
      }
    }
    else
    {
      lastMouseX = x;
      lastMouseY = y;
      mouseStillTime = System.currentTimeMillis();
    }
  }
  
  private String[] getTooltipLines(String btnName)
  {
    return btnName.equals("Translucent Blocks") ? new String[] { "Translucent Blocks", "  Fancy - correct color blending (default)", "  Fast - fast color blending (faster)", "Controls the color blending of translucent blocks", "with different color (stained glass, water, ice)", "when placed behind each other with air between them." } : btnName.equals("Held Item Tooltips") ? new String[] { "Held item tooltips", "  ON - show tooltips for held items (default)", "  OFF - do not show tooltips for held items" } : btnName.equals("Show Capes") ? new String[] { "Show Capes", "  ON - show player capes (default)", "  OFF - do not show player capes" } : btnName.equals("Depth Fog") ? new String[] { "Depth Fog", "  ON - fog moves closer at bedrock levels (default)", "  OFF - same fog at all levels" } : btnName.equals("Stars") ? new String[] { "Stars", "  ON - stars are visible, slower", "  OFF  - stars are not visible, faster" } : btnName.equals("Sun & Moon") ? new String[] { "Sun & Moon", "  ON - sun and moon are visible (default)", "  OFF  - sun and moon are not visible (faster)" } : btnName.equals("Sky") ? new String[] { "Sky", "  ON - sky is visible, slower", "  OFF  - sky is not visible, faster", "When sky is OFF the moon and sun are still visible." } : btnName.equals("Rain & Snow") ? new String[] { "Rain & Snow", "  Default - as set by setting Graphics", "  Fast  - light rain/snow, faster", "  Fancy - heavy rain/snow, slower", "  OFF - no rain/snow, fastest", "When rain is OFF the splashes and rain sounds", "are still active." } : btnName.equals("Water") ? new String[] { "Water", "  Default - as set by setting Graphics", "  Fast  - lower quality, faster", "  Fancy - higher quality, slower", "Fast water (1 pass) has some visual artifacts", "Fancy water (2 pass) has no visual artifacts" } : btnName.equals("Dropped Items") ? new String[] { "Dropped Items", "  Default - as set by setting Graphics", "  Fast - 2D dropped items, faster", "  Fancy - 3D dropped items, slower" } : btnName.equals("Grass") ? new String[] { "Grass", "  Default - as set by setting Graphics", "  Fast - lower quality, faster", "  Fancy - higher quality, slower", "Fast grass uses default side texture.", "Fancy grass uses biome side texture." } : btnName.equals("Trees") ? new String[] { "Trees", "  Default - as set by setting Graphics", "  Fast - lower quality, faster", "  Fancy - higher quality, slower", "Fast trees have opaque leaves.", "Fancy trees have transparent leaves." } : btnName.equals("Cloud Height") ? new String[] { "Cloud Height", "  OFF - default height", "  100% - above world height limit" } : btnName.equals("Clouds") ? new String[] { "Clouds", "  Default - as set by setting Graphics", "  Fast - lower quality, faster", "  Fancy - higher quality, slower", "  OFF - no clouds, fastest", "Fast clouds are rendered 2D.", "Fancy clouds are rendered 3D." } : null;
  }
  
  private String getButtonName(String displayString)
  {
    int pos = displayString.indexOf(':');
    return pos < 0 ? displayString : displayString.substring(0, pos);
  }
  
  private GuiButton getSelectedButton(int i, int j)
  {
    for (int k = 0; k < buttonList.size(); k++)
    {
      GuiButton btn = (GuiButton)buttonList.get(k);
      int btnWidth = net.minecraft.client.gui.GuiVideoSettings.getButtonWidth(btn);
      int btnHeight = net.minecraft.client.gui.GuiVideoSettings.getButtonHeight(btn);
      boolean flag = (i >= xPosition) && (j >= yPosition) && (i < xPosition + btnWidth) && (j < yPosition + btnHeight);
      
      if (flag)
      {
        return btn;
      }
    }
    
    return null;
  }
}
