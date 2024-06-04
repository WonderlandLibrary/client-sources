package net.minecraft.client.gui;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.GameSettings.Options;
import optifine.Config;
import optifine.GuiAnimationSettingsOF;
import optifine.GuiDetailSettingsOF;
import optifine.GuiOtherSettingsOF;
import optifine.GuiPerformanceSettingsOF;
import optifine.GuiQualitySettingsOF;
import optifine.Lang;
import optifine.TooltipManager;
import shadersmod.client.GuiShaders;

public class GuiVideoSettings extends GuiScreen
{
  private GuiScreen parentGuiScreen;
  protected String screenTitle = "Video Settings";
  
  private GameSettings guiGameSettings;
  
  private static GameSettings.Options[] videoOptions = { GameSettings.Options.GRAPHICS, GameSettings.Options.RENDER_DISTANCE, GameSettings.Options.AMBIENT_OCCLUSION, GameSettings.Options.FRAMERATE_LIMIT, GameSettings.Options.AO_LEVEL, GameSettings.Options.VIEW_BOBBING, GameSettings.Options.GUI_SCALE, GameSettings.Options.USE_VBO, GameSettings.Options.GAMMA, GameSettings.Options.BLOCK_ALTERNATIVES, GameSettings.Options.FOG_FANCY, GameSettings.Options.FOG_START };
  private static final String __OBFID = "CL_00000718";
  private TooltipManager tooltipManager = new TooltipManager(this);
  
  public GuiVideoSettings(GuiScreen par1GuiScreen, GameSettings par2GameSettings)
  {
    parentGuiScreen = par1GuiScreen;
    guiGameSettings = par2GameSettings;
  }
  



  public void initGui()
  {
    screenTitle = I18n.format("options.videoTitle", new Object[0]);
    buttonList.clear();
    

    for (int y = 0; y < videoOptions.length; y++)
    {
      GameSettings.Options x = videoOptions[y];
      
      if (x != null)
      {
        int x1 = width / 2 - 155 + y % 2 * 160;
        int y1 = height / 6 + 21 * (y / 2) - 12;
        
        if (x.getEnumFloat())
        {
          buttonList.add(new optifine.GuiOptionSliderOF(x.returnEnumOrdinal(), x1, y1, x));
        }
        else
        {
          buttonList.add(new optifine.GuiOptionButtonOF(x.returnEnumOrdinal(), x1, y1, x, guiGameSettings.getKeyBinding(x)));
        }
      }
    }
    
    y = height / 6 + 21 * (videoOptions.length / 2) - 12;
    boolean var5 = false;
    int var6 = width / 2 - 155 + 0;
    buttonList.add(new GuiOptionButton(231, var6, y, Lang.get("of.options.shaders")));
    var6 = width / 2 - 155 + 160;
    buttonList.add(new GuiOptionButton(202, var6, y, Lang.get("of.options.quality")));
    y += 21;
    var6 = width / 2 - 155 + 0;
    buttonList.add(new GuiOptionButton(201, var6, y, Lang.get("of.options.details")));
    var6 = width / 2 - 155 + 160;
    buttonList.add(new GuiOptionButton(212, var6, y, Lang.get("of.options.performance")));
    y += 21;
    var6 = width / 2 - 155 + 0;
    buttonList.add(new GuiOptionButton(211, var6, y, Lang.get("of.options.animations")));
    var6 = width / 2 - 155 + 160;
    buttonList.add(new GuiOptionButton(222, var6, y, Lang.get("of.options.other")));
    y += 21;
    buttonList.add(new GuiButton(200, width / 2 - 100, height / 6 + 168 + 11, I18n.format("gui.done", new Object[0])));
  }
  
  protected void actionPerformed(GuiButton button) throws java.io.IOException
  {
    if (enabled)
    {
      int guiScale = guiGameSettings.guiScale;
      
      if ((id < 200) && ((button instanceof GuiOptionButton)))
      {
        guiGameSettings.setOptionValue(((GuiOptionButton)button).returnEnumOptions(), 1);
        displayString = guiGameSettings.getKeyBinding(GameSettings.Options.getEnumOptions(id));
      }
      
      if (id == 200)
      {
        mc.gameSettings.saveOptions();
        mc.displayGuiScreen(parentGuiScreen);
      }
      
      if (guiGameSettings.guiScale != guiScale)
      {
        ScaledResolution scr = new ScaledResolution(mc);
        int var4 = scr.getScaledWidth();
        int var5 = scr.getScaledHeight();
        setWorldAndResolution(mc, var4, var5);
      }
      
      if (id == 201)
      {
        mc.gameSettings.saveOptions();
        GuiDetailSettingsOF scr1 = new GuiDetailSettingsOF(this, guiGameSettings);
        mc.displayGuiScreen(scr1);
      }
      
      if (id == 202)
      {
        mc.gameSettings.saveOptions();
        GuiQualitySettingsOF scr2 = new GuiQualitySettingsOF(this, guiGameSettings);
        mc.displayGuiScreen(scr2);
      }
      
      if (id == 211)
      {
        mc.gameSettings.saveOptions();
        GuiAnimationSettingsOF scr3 = new GuiAnimationSettingsOF(this, guiGameSettings);
        mc.displayGuiScreen(scr3);
      }
      
      if (id == 212)
      {
        mc.gameSettings.saveOptions();
        GuiPerformanceSettingsOF scr4 = new GuiPerformanceSettingsOF(this, guiGameSettings);
        mc.displayGuiScreen(scr4);
      }
      
      if (id == 222)
      {
        mc.gameSettings.saveOptions();
        GuiOtherSettingsOF scr5 = new GuiOtherSettingsOF(this, guiGameSettings);
        mc.displayGuiScreen(scr5);
      }
      
      if (id == 231)
      {
        if ((Config.isAntialiasing()) || (Config.isAntialiasingConfigured()))
        {
          Config.showGuiMessage(Lang.get("of.message.shaders.aa1"), Lang.get("of.message.shaders.aa2"));
          return;
        }
        
        if (Config.isAnisotropicFiltering())
        {
          Config.showGuiMessage(Lang.get("of.message.shaders.af1"), Lang.get("of.message.shaders.af2"));
          return;
        }
        
        if (Config.isFastRender())
        {
          Config.showGuiMessage(Lang.get("of.message.shaders.fr1"), Lang.get("of.message.shaders.fr2"));
          return;
        }
        
        mc.gameSettings.saveOptions();
        GuiShaders scr6 = new GuiShaders(this, guiGameSettings);
        mc.displayGuiScreen(scr6);
      }
    }
  }
  



  public void drawScreen(int x, int y, float z)
  {
    drawDefaultBackground();
    drawCenteredString(fontRendererObj, screenTitle, width / 2, 15, 16777215);
    String ver = Config.getVersion();
    String ed = "HD_U";
    
    if (ed.equals("HD"))
    {
      ver = "OptiFine HD H6";
    }
    
    if (ed.equals("HD_U"))
    {
      ver = "OptiFine HD H6 Ultra";
    }
    
    if (ed.equals("L"))
    {
      ver = "OptiFine H6 Light";
    }
    
    drawString(fontRendererObj, ver, 2, height - 10, 8421504);
    String verMc = "Minecraft 1.8";
    int lenMc = fontRendererObj.getStringWidth(verMc);
    drawString(fontRendererObj, verMc, width - lenMc - 2, height - 10, 8421504);
    super.drawScreen(x, y, z);
    tooltipManager.drawTooltips(x, y, buttonList);
  }
  
  public static int getButtonWidth(GuiButton btn)
  {
    return width;
  }
  
  public static int getButtonHeight(GuiButton btn)
  {
    return height;
  }
  
  public static void drawGradientRect(GuiScreen guiScreen, int left, int top, int right, int bottom, int startColor, int endColor)
  {
    guiScreen.drawGradientRect(left, top, right, bottom, startColor, endColor);
  }
}
