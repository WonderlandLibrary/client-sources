package optifine;

import java.util.List;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.GameSettings.Options;

public class GuiQualitySettingsOF extends GuiScreen
{
  private GuiScreen prevScreen;
  protected String title;
  private GameSettings settings;
  private static GameSettings.Options[] enumOptions = { GameSettings.Options.MIPMAP_LEVELS, GameSettings.Options.MIPMAP_TYPE, GameSettings.Options.AF_LEVEL, GameSettings.Options.AA_LEVEL, GameSettings.Options.CLEAR_WATER, GameSettings.Options.RANDOM_MOBS, GameSettings.Options.BETTER_GRASS, GameSettings.Options.BETTER_SNOW, GameSettings.Options.CUSTOM_FONTS, GameSettings.Options.CUSTOM_COLORS, GameSettings.Options.SWAMP_COLORS, GameSettings.Options.SMOOTH_BIOMES, GameSettings.Options.CONNECTED_TEXTURES, GameSettings.Options.NATURAL_TEXTURES, GameSettings.Options.CUSTOM_SKY, GameSettings.Options.CUSTOM_ITEMS, GameSettings.Options.DYNAMIC_LIGHTS };
  private TooltipManager tooltipManager = new TooltipManager(this);
  
  public GuiQualitySettingsOF(GuiScreen guiscreen, GameSettings gamesettings)
  {
    prevScreen = guiscreen;
    settings = gamesettings;
  }
  



  public void initGui()
  {
    title = net.minecraft.client.resources.I18n.format("of.options.qualityTitle", new Object[0]);
    buttonList.clear();
    
    for (int i = 0; i < enumOptions.length; i++)
    {
      GameSettings.Options opt = enumOptions[i];
      int x = width / 2 - 155 + i % 2 * 160;
      int y = height / 6 + 21 * (i / 2) - 12;
      
      if (!opt.getEnumFloat())
      {
        buttonList.add(new GuiOptionButtonOF(opt.returnEnumOrdinal(), x, y, opt, settings.getKeyBinding(opt)));
      }
      else
      {
        buttonList.add(new GuiOptionSliderOF(opt.returnEnumOrdinal(), x, y, opt));
      }
    }
    
    buttonList.add(new GuiButton(200, width / 2 - 100, height / 6 + 168 + 11, net.minecraft.client.resources.I18n.format("gui.done", new Object[0])));
  }
  
  protected void actionPerformed(GuiButton guibutton)
  {
    if (enabled)
    {
      if ((id < 200) && ((guibutton instanceof net.minecraft.client.gui.GuiOptionButton)))
      {
        settings.setOptionValue(((net.minecraft.client.gui.GuiOptionButton)guibutton).returnEnumOptions(), 1);
        displayString = settings.getKeyBinding(GameSettings.Options.getEnumOptions(id));
      }
      
      if (id == 200)
      {
        mc.gameSettings.saveOptions();
        mc.displayGuiScreen(prevScreen);
      }
      
      if (id != GameSettings.Options.AA_LEVEL.ordinal())
      {
        ScaledResolution sr = new ScaledResolution(mc);
        setWorldAndResolution(mc, sr.getScaledWidth(), sr.getScaledHeight());
      }
    }
  }
  



  public void drawScreen(int x, int y, float f)
  {
    drawDefaultBackground();
    drawCenteredString(fontRendererObj, title, width / 2, 15, 16777215);
    super.drawScreen(x, y, f);
    tooltipManager.drawTooltips(x, y, buttonList);
  }
}
