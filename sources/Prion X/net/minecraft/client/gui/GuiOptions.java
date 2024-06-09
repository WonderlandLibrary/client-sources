package net.minecraft.client.gui;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundCategory;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.GameSettings.Options;
import net.minecraft.client.stream.IStream;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.storage.WorldInfo;

public class GuiOptions extends GuiScreen implements GuiYesNoCallback
{
  private static final GameSettings.Options[] field_146440_f = { GameSettings.Options.FOV };
  
  private final GuiScreen field_146441_g;
  
  private final GameSettings game_settings_1;
  private GuiButton field_175357_i;
  private GuiLockIconButton field_175356_r;
  protected String field_146442_a = "Options";
  private static final String __OBFID = "CL_00000700";
  
  public GuiOptions(GuiScreen p_i1046_1_, GameSettings p_i1046_2_)
  {
    field_146441_g = p_i1046_1_;
    game_settings_1 = p_i1046_2_;
  }
  



  public void initGui()
  {
    int var1 = 0;
    field_146442_a = I18n.format("options.title", new Object[0]);
    GameSettings.Options[] var2 = field_146440_f;
    int var3 = var2.length;
    
    for (int var4 = 0; var4 < var3; var4++)
    {
      GameSettings.Options var5 = var2[var4];
      
      if (var5.getEnumFloat())
      {
        buttonList.add(new GuiOptionSlider(var5.returnEnumOrdinal(), width / 2 - 155 + var1 % 2 * 160, height / 6 - 12 + 24 * (var1 >> 1), var5));
      }
      else
      {
        GuiOptionButton var6 = new GuiOptionButton(var5.returnEnumOrdinal(), width / 2 - 155 + var1 % 2 * 160, height / 6 - 12 + 24 * (var1 >> 1), var5, game_settings_1.getKeyBinding(var5));
        buttonList.add(var6);
      }
      
      var1++;
    }
    
    if (mc.theWorld != null)
    {
      EnumDifficulty var7 = mc.theWorld.getDifficulty();
      field_175357_i = new GuiButton(108, width / 2 - 155 + var1 % 2 * 160, height / 6 - 12 + 24 * (var1 >> 1), 150, 20, func_175355_a(var7));
      buttonList.add(field_175357_i);
      
      if ((mc.isSingleplayer()) && (!mc.theWorld.getWorldInfo().isHardcoreModeEnabled()))
      {
        field_175357_i.func_175211_a(field_175357_i.getButtonWidth() - 20);
        field_175356_r = new GuiLockIconButton(109, field_175357_i.xPosition + field_175357_i.getButtonWidth(), field_175357_i.yPosition);
        buttonList.add(field_175356_r);
        field_175356_r.func_175229_b(mc.theWorld.getWorldInfo().isDifficultyLocked());
        field_175356_r.enabled = (!field_175356_r.func_175230_c());
        field_175357_i.enabled = (!field_175356_r.func_175230_c());
      }
      else
      {
        field_175357_i.enabled = false;
      }
    }
    
    buttonList.add(new GuiButton(110, width / 2 - 155, height / 6 + 48 - 6, 150, 20, I18n.format("options.skinCustomisation", new Object[0])));
    buttonList.add(new GuiButton(8675309, width / 2 + 5, height / 6 + 48 - 6, 150, 20, "Super Secret Settings...")
    {
      private static final String __OBFID = "CL_00000701";
      
      public void playPressSound(SoundHandler soundHandlerIn) {
        net.minecraft.client.audio.SoundEventAccessorComposite var2 = soundHandlerIn.getRandomSoundFromCategories(new SoundCategory[] { SoundCategory.ANIMALS, SoundCategory.BLOCKS, SoundCategory.MOBS, SoundCategory.PLAYERS, SoundCategory.WEATHER });
        
        if (var2 != null)
        {
          soundHandlerIn.playSound(net.minecraft.client.audio.PositionedSoundRecord.createPositionedSoundRecord(var2.getSoundEventLocation(), 0.5F));
        }
      }
    });
    buttonList.add(new GuiButton(106, width / 2 - 155, height / 6 + 72 - 6, 150, 20, I18n.format("options.sounds", new Object[0])));
    buttonList.add(new GuiButton(107, width / 2 + 5, height / 6 + 72 - 6, 150, 20, I18n.format("options.stream", new Object[0])));
    buttonList.add(new GuiButton(101, width / 2 - 155, height / 6 + 96 - 6, 150, 20, I18n.format("options.video", new Object[0])));
    buttonList.add(new GuiButton(100, width / 2 + 5, height / 6 + 96 - 6, 150, 20, I18n.format("options.controls", new Object[0])));
    buttonList.add(new GuiButton(102, width / 2 - 155, height / 6 + 120 - 6, 150, 20, I18n.format("options.language", new Object[0])));
    buttonList.add(new GuiButton(103, width / 2 + 5, height / 6 + 120 - 6, 150, 20, I18n.format("options.multiplayer.title", new Object[0])));
    buttonList.add(new GuiButton(105, width / 2 - 155, height / 6 + 144 - 6, 150, 20, I18n.format("options.resourcepack", new Object[0])));
    buttonList.add(new GuiButton(104, width / 2 + 5, height / 6 + 144 - 6, 150, 20, I18n.format("options.snooper.view", new Object[0])));
    buttonList.add(new GuiButton(200, width / 2 - 100, height / 6 + 168, I18n.format("gui.done", new Object[0])));
  }
  
  public String func_175355_a(EnumDifficulty p_175355_1_)
  {
    ChatComponentText var2 = new ChatComponentText("");
    var2.appendSibling(new ChatComponentTranslation("options.difficulty", new Object[0]));
    var2.appendText(": ");
    var2.appendSibling(new ChatComponentTranslation(p_175355_1_.getDifficultyResourceKey(), new Object[0]));
    return var2.getFormattedText();
  }
  
  public void confirmClicked(boolean result, int id)
  {
    mc.displayGuiScreen(this);
    
    if ((id == 109) && (result) && (mc.theWorld != null))
    {
      mc.theWorld.getWorldInfo().setDifficultyLocked(true);
      field_175356_r.func_175229_b(true);
      field_175356_r.enabled = false;
      field_175357_i.enabled = false;
    }
  }
  
  protected void actionPerformed(GuiButton button) throws java.io.IOException
  {
    if (enabled)
    {
      if ((id < 100) && ((button instanceof GuiOptionButton)))
      {
        GameSettings.Options var2 = ((GuiOptionButton)button).returnEnumOptions();
        game_settings_1.setOptionValue(var2, 1);
        displayString = game_settings_1.getKeyBinding(GameSettings.Options.getEnumOptions(id));
      }
      
      if (id == 108)
      {
        mc.theWorld.getWorldInfo().setDifficulty(EnumDifficulty.getDifficultyEnum(mc.theWorld.getDifficulty().getDifficultyId() + 1));
        field_175357_i.displayString = func_175355_a(mc.theWorld.getDifficulty());
      }
      
      if (id == 109)
      {
        mc.displayGuiScreen(new GuiYesNo(this, new ChatComponentTranslation("difficulty.lock.title", new Object[0]).getFormattedText(), new ChatComponentTranslation("difficulty.lock.question", new Object[] { new ChatComponentTranslation(mc.theWorld.getWorldInfo().getDifficulty().getDifficultyResourceKey(), new Object[0]) }).getFormattedText(), 109));
      }
      
      if (id == 110)
      {
        mc.gameSettings.saveOptions();
        mc.displayGuiScreen(new GuiCustomizeSkin(this));
      }
      
      if (id == 8675309)
      {
        mc.entityRenderer.activateNextShader();
      }
      
      if (id == 101)
      {
        mc.gameSettings.saveOptions();
        mc.displayGuiScreen(new GuiVideoSettings(this, game_settings_1));
      }
      
      if (id == 100)
      {
        mc.gameSettings.saveOptions();
        mc.displayGuiScreen(new GuiControls(this, game_settings_1));
      }
      
      if (id == 102)
      {
        mc.gameSettings.saveOptions();
        mc.displayGuiScreen(new GuiLanguage(this, game_settings_1, mc.getLanguageManager()));
      }
      
      if (id == 103)
      {
        mc.gameSettings.saveOptions();
        mc.displayGuiScreen(new ScreenChatOptions(this, game_settings_1));
      }
      
      if (id == 104)
      {
        mc.gameSettings.saveOptions();
        mc.displayGuiScreen(new GuiSnooper(this, game_settings_1));
      }
      
      if (id == 200)
      {
        mc.gameSettings.saveOptions();
        mc.displayGuiScreen(field_146441_g);
      }
      
      if (id == 105)
      {
        mc.gameSettings.saveOptions();
        mc.displayGuiScreen(new GuiScreenResourcePacks(this));
      }
      
      if (id == 106)
      {
        mc.gameSettings.saveOptions();
        mc.displayGuiScreen(new GuiScreenOptionsSounds(this, game_settings_1));
      }
      
      if (id == 107)
      {
        mc.gameSettings.saveOptions();
        IStream var3 = mc.getTwitchStream();
        
        if ((var3.func_152936_l()) && (var3.func_152928_D()))
        {
          mc.displayGuiScreen(new net.minecraft.client.gui.stream.GuiStreamOptions(this, game_settings_1));
        }
        else
        {
          net.minecraft.client.gui.stream.GuiStreamUnavailable.func_152321_a(this);
        }
      }
    }
  }
  



  public void drawScreen(int mouseX, int mouseY, float partialTicks)
  {
    drawDefaultBackground();
    drawCenteredString(fontRendererObj, field_146442_a, width / 2, 15, 16777215);
    super.drawScreen(mouseX, mouseY, partialTicks);
  }
}
