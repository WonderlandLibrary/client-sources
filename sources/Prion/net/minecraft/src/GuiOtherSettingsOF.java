package net.minecraft.src;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptionButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiVideoSettings;
import net.minecraft.client.gui.GuiYesNo;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.GameSettings.Options;

public class GuiOtherSettingsOF extends GuiScreen implements net.minecraft.client.gui.GuiYesNoCallback
{
  private GuiScreen prevScreen;
  protected String title = "Other Settings";
  private GameSettings settings;
  private static GameSettings.Options[] enumOptions = { GameSettings.Options.LAGOMETER, GameSettings.Options.PROFILER, GameSettings.Options.WEATHER, GameSettings.Options.TIME, GameSettings.Options.USE_FULLSCREEN, GameSettings.Options.FULLSCREEN_MODE, GameSettings.Options.AUTOSAVE_TICKS };
  private int lastMouseX = 0;
  private int lastMouseY = 0;
  private long mouseStillTime = 0L;
  
  public GuiOtherSettingsOF(GuiScreen guiscreen, GameSettings gamesettings)
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
    
    buttonList.add(new GuiButton(210, width / 2 - 100, height / 6 + 168 + 11 - 44, "Reset Video Settings..."));
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
        mc.gameSettings.saveOptions();
        mc.displayGuiScreen(prevScreen);
      }
      
      if (id == 210)
      {
        mc.gameSettings.saveOptions();
        GuiYesNo scaledresolution = new GuiYesNo(this, "Reset all video settings to their default values?", "", 9999);
        mc.displayGuiScreen(scaledresolution);
      }
      
      if (id != GameSettings.Options.CLOUD_HEIGHT.ordinal())
      {
        ScaledResolution scaledresolution1 = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
        int i = scaledresolution1.getScaledWidth();
        int j = scaledresolution1.getScaledHeight();
        setWorldAndResolution(mc, i, j);
      }
    }
  }
  
  public void confirmClicked(boolean flag, int i)
  {
    if (flag)
    {
      mc.gameSettings.resetSettings();
    }
    
    mc.displayGuiScreen(this);
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
    return btnName.equals("3D Anaglyph") ? new String[] { "3D mode used with red-cyan 3D glasses." } : btnName.equals("Fullscreen Mode") ? new String[] { "Fullscreen mode", "  Default - use desktop screen resolution, slower", "  WxH - use custom screen resolution, may be faster", "The selected resolution is used in fullscreen mode (F11).", "Lower resolutions should generally be faster." } : btnName.equals("Fullscreen") ? new String[] { "Fullscreen", "  ON - use fullscreen mode", "  OFF - use window mode", "Fullscreen mode may be faster or slower than", "window mode, depending on the graphics card." } : btnName.equals("Weather") ? new String[] { "Weather", "  ON - weather is active, slower", "  OFF - weather is not active, faster", "The weather controls rain, snow and thunderstorms.", "Weather control is only possible for local worlds." } : btnName.equals("Time") ? new String[] { "Time", " Default - normal day/night cycles", " Day Only - day only", " Night Only - night only", "The time setting is only effective in CREATIVE mode", "and for local worlds." } : btnName.equals("Debug Profiler") ? new String[] { "Debug Profiler", "  ON - debug profiler is active, slower", "  OFF - debug profiler is not active, faster", "The debug profiler collects and shows debug information", "when the debug screen is open (F3)" } : btnName.equals("Lagometer") ? new String[] { "Shows the lagometer on the debug screen (F3).", "* Orange - Memory garbage collection", "* Cyan - Tick", "* Blue - Scheduled executables", "* Purple - Chunk upload", "* Red - Chunk updates", "* Yellow - Visibility check", "* Green - Render terrain" } : btnName.equals("Autosave") ? new String[] { "Autosave interval", "Default autosave interval (2s) is NOT RECOMMENDED.", "Autosave causes the famous Lag Spike of Death." } : null;
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
      int btnWidth = GuiVideoSettings.getButtonWidth(btn);
      int btnHeight = GuiVideoSettings.getButtonHeight(btn);
      boolean flag = (i >= xPosition) && (j >= yPosition) && (i < xPosition + btnWidth) && (j < yPosition + btnHeight);
      
      if (flag)
      {
        return btn;
      }
    }
    
    return null;
  }
}
