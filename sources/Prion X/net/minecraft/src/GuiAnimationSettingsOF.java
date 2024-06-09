package net.minecraft.src;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptionButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.GameSettings.Options;

public class GuiAnimationSettingsOF extends GuiScreen
{
  private GuiScreen prevScreen;
  protected String title = "Animation Settings";
  private GameSettings settings;
  private static GameSettings.Options[] enumOptions = { GameSettings.Options.ANIMATED_WATER, GameSettings.Options.ANIMATED_LAVA, GameSettings.Options.ANIMATED_FIRE, GameSettings.Options.ANIMATED_PORTAL, GameSettings.Options.ANIMATED_REDSTONE, GameSettings.Options.ANIMATED_EXPLOSION, GameSettings.Options.ANIMATED_FLAME, GameSettings.Options.ANIMATED_SMOKE, GameSettings.Options.VOID_PARTICLES, GameSettings.Options.WATER_PARTICLES, GameSettings.Options.RAIN_SPLASH, GameSettings.Options.PORTAL_PARTICLES, GameSettings.Options.POTION_PARTICLES, GameSettings.Options.DRIPPING_WATER_LAVA, GameSettings.Options.ANIMATED_TERRAIN, GameSettings.Options.ANIMATED_TEXTURES, GameSettings.Options.PARTICLES };
  
  public GuiAnimationSettingsOF(GuiScreen guiscreen, GameSettings gamesettings)
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
    
    buttonList.add(new GuiButton(210, width / 2 - 155, height / 6 + 168 + 11, 70, 20, "All ON"));
    buttonList.add(new GuiButton(211, width / 2 - 155 + 80, height / 6 + 168 + 11, 70, 20, "All OFF"));
    buttonList.add(new GuiOptionButton(200, width / 2 + 5, height / 6 + 168 + 11, net.minecraft.client.resources.I18n.format("gui.done", new Object[0])));
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
        mc.gameSettings.setAllAnimations(true);
      }
      
      if (id == 211)
      {
        mc.gameSettings.setAllAnimations(false);
      }
      
      if (id != GameSettings.Options.CLOUD_HEIGHT.ordinal())
      {
        net.minecraft.client.gui.ScaledResolution scaledresolution = new net.minecraft.client.gui.ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
        int i = scaledresolution.getScaledWidth();
        int j = scaledresolution.getScaledHeight();
        setWorldAndResolution(mc, i, j);
      }
    }
  }
  



  public void drawScreen(int i, int j, float f)
  {
    drawDefaultBackground();
    drawCenteredString(fontRendererObj, title, width / 2, 20, 16777215);
    super.drawScreen(i, j, f);
  }
}
