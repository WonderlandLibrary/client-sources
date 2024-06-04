package optifine;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.GameSettings.Options;

public class GuiAnimationSettingsOF extends GuiScreen
{
  private GuiScreen prevScreen;
  protected String title;
  private GameSettings settings;
  private static GameSettings.Options[] enumOptions = { GameSettings.Options.ANIMATED_WATER, GameSettings.Options.ANIMATED_LAVA, GameSettings.Options.ANIMATED_FIRE, GameSettings.Options.ANIMATED_PORTAL, GameSettings.Options.ANIMATED_REDSTONE, GameSettings.Options.ANIMATED_EXPLOSION, GameSettings.Options.ANIMATED_FLAME, GameSettings.Options.ANIMATED_SMOKE, GameSettings.Options.VOID_PARTICLES, GameSettings.Options.WATER_PARTICLES, GameSettings.Options.RAIN_SPLASH, GameSettings.Options.PORTAL_PARTICLES, GameSettings.Options.POTION_PARTICLES, GameSettings.Options.DRIPPING_WATER_LAVA, GameSettings.Options.ANIMATED_TERRAIN, GameSettings.Options.ANIMATED_TEXTURES, GameSettings.Options.FIREWORK_PARTICLES, GameSettings.Options.PARTICLES };
  
  public GuiAnimationSettingsOF(GuiScreen guiscreen, GameSettings gamesettings)
  {
    prevScreen = guiscreen;
    settings = gamesettings;
  }
  



  public void initGui()
  {
    title = net.minecraft.client.resources.I18n.format("of.options.animationsTitle", new Object[0]);
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
    
    buttonList.add(new GuiButton(210, width / 2 - 155, height / 6 + 168 + 11, 70, 20, Lang.get("of.options.animation.allOn")));
    buttonList.add(new GuiButton(211, width / 2 - 155 + 80, height / 6 + 168 + 11, 70, 20, Lang.get("of.options.animation.allOff")));
    buttonList.add(new net.minecraft.client.gui.GuiOptionButton(200, width / 2 + 5, height / 6 + 168 + 11, net.minecraft.client.resources.I18n.format("gui.done", new Object[0])));
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
        mc.gameSettings.setAllAnimations(true);
      }
      
      if (id == 211)
      {
        mc.gameSettings.setAllAnimations(false);
      }
      
      net.minecraft.client.gui.ScaledResolution sr = new net.minecraft.client.gui.ScaledResolution(mc);
      setWorldAndResolution(mc, sr.getScaledWidth(), sr.getScaledHeight());
    }
  }
  



  public void drawScreen(int x, int y, float f)
  {
    drawDefaultBackground();
    drawCenteredString(fontRendererObj, title, width / 2, 15, 16777215);
    super.drawScreen(x, y, f);
  }
}
