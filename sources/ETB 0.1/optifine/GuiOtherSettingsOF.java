package optifine;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.GameSettings.Options;

public class GuiOtherSettingsOF extends GuiScreen implements net.minecraft.client.gui.GuiYesNoCallback
{
  private GuiScreen prevScreen;
  protected String title;
  private GameSettings settings;
  private static GameSettings.Options[] enumOptions = { GameSettings.Options.LAGOMETER, GameSettings.Options.PROFILER, GameSettings.Options.WEATHER, GameSettings.Options.TIME, GameSettings.Options.USE_FULLSCREEN, GameSettings.Options.FULLSCREEN_MODE, GameSettings.Options.SHOW_FPS, GameSettings.Options.AUTOSAVE_TICKS, GameSettings.Options.ANAGLYPH };
  private TooltipManager tooltipManager = new TooltipManager(this);
  
  public GuiOtherSettingsOF(GuiScreen guiscreen, GameSettings gamesettings)
  {
    prevScreen = guiscreen;
    settings = gamesettings;
  }
  



  public void initGui()
  {
    title = I18n.format("of.options.otherTitle", new Object[0]);
    buttonList.clear();
    
    for (int i = 0; i < enumOptions.length; i++)
    {
      GameSettings.Options enumoptions = enumOptions[i];
      int x = width / 2 - 155 + i % 2 * 160;
      int y = height / 6 + 21 * (i / 2) - 12;
      
      if (!enumoptions.getEnumFloat())
      {
        buttonList.add(new GuiOptionButtonOF(enumoptions.returnEnumOrdinal(), x, y, enumoptions, settings.getKeyBinding(enumoptions)));
      }
      else
      {
        buttonList.add(new GuiOptionSliderOF(enumoptions.returnEnumOrdinal(), x, y, enumoptions));
      }
    }
    
    buttonList.add(new GuiButton(210, width / 2 - 100, height / 6 + 168 + 11 - 44, I18n.format("of.options.other.reset", new Object[0])));
    buttonList.add(new GuiButton(200, width / 2 - 100, height / 6 + 168 + 11, I18n.format("gui.done", new Object[0])));
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
      
      if (id == 210)
      {
        mc.gameSettings.saveOptions();
        net.minecraft.client.gui.GuiYesNo guiyesno = new net.minecraft.client.gui.GuiYesNo(this, I18n.format("of.message.other.reset", new Object[0]), "", 9999);
        mc.displayGuiScreen(guiyesno);
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
    drawCenteredString(fontRendererObj, title, width / 2, 15, 16777215);
    super.drawScreen(x, y, f);
    tooltipManager.drawTooltips(x, y, buttonList);
  }
}
