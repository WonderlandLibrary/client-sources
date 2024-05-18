package net.minecraft.src;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptionButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.GameSettings.Options;

public class GuiPerformanceSettingsOF extends GuiScreen
{
  private GuiScreen prevScreen;
  protected String title = "Performance Settings";
  private GameSettings settings;
  private static GameSettings.Options[] enumOptions = { GameSettings.Options.FAST_MATH, GameSettings.Options.SMOOTH_WORLD, GameSettings.Options.LOAD_FAR, GameSettings.Options.PRELOADED_CHUNKS, GameSettings.Options.CHUNK_UPDATES, GameSettings.Options.CHUNK_UPDATES_DYNAMIC, GameSettings.Options.FAST_RENDER, GameSettings.Options.LAZY_CHUNK_LOADING };
  private int lastMouseX = 0;
  private int lastMouseY = 0;
  private long mouseStillTime = 0L;
  
  public GuiPerformanceSettingsOF(GuiScreen guiscreen, GameSettings gamesettings)
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
    return btnName.equals("Fast Render") ? new String[] { "Fast Render", " OFF - standard rendering (default)", " ON - faster rendering", "Uses optimized rendering algorithm which decreases", "the GPU load and may substantionally increase the FPS.", "You can turn if OFF if you notice flickering textures", "on some blocks." } : btnName.equals("Fast Math") ? new String[] { "Fast Math", " OFF - standard math (default)", " ON - faster math", "Uses optimized sin() and cos() functions which can", "better utilize the CPU cache and increase the FPS." } : btnName.equals("Lazy Chunk Loading") ? new String[] { "Lazy Chunk Loading", " OFF - default server chunk loading", " ON - lazy server chunk loading (smoother)", "Smooths the integrated server chunk loading by", "distributing the chunks over several ticks.", "Turn it OFF if parts of the world do not load correctly.", "Effective only for local worlds and single-core CPU." } : btnName.equals("Dynamic Updates") ? new String[] { "Dynamic chunk updates", " OFF - (default) standard chunk updates per frame", " ON - more updates while the player is standing still", "Dynamic updates force more chunk updates while", "the player is standing still to load the world faster." } : btnName.equals("Chunk Updates") ? new String[] { "Chunk updates", " 1 - (default) slower world loading, higher FPS", " 3 - faster world loading, lower FPS", " 5 - fastest world loading, lowest FPS", "Number of chunk updates per rendered frame,", "higher values may destabilize the framerate." } : btnName.equals("Preloaded Chunks") ? new String[] { "Defines an area in which no chunks will be loaded", "  OFF - after 5m new chunks will be loaded", "  2 - after 32m  new chunks will be loaded", "  8 - after 128m new chunks will be loaded", "Higher values need more time to load all the chunks", "and may decrease the FPS." } : btnName.equals("Load Far") ? new String[] { "Loads the world chunks at distance Far.", "Switching the render distance does not cause all chunks ", "to be loaded again.", "  OFF - world chunks loaded up to render distance", "  ON - world chunks loaded at distance Far, allows", "       fast render distance switching" } : btnName.equals("Smooth World") ? new String[] { "Removes lag spikes caused by the internal server.", "  OFF - no stabilization, FPS may fluctuate", "  ON - FPS stabilization", "Stabilizes FPS by distributing the internal server load.", "Effective only for local worlds (single player)." } : btnName.equals("Smooth FPS") ? new String[] { "Stabilizes FPS by flushing the graphic driver buffers", "  OFF - no stabilization, FPS may fluctuate", "  ON - FPS stabilization", "This option is graphics driver dependant and its effect", "is not always visible" } : null;
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
