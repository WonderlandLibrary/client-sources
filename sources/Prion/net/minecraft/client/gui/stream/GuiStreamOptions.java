package net.minecraft.client.gui.stream;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptionButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.GameSettings.Options;
import net.minecraft.client.stream.IStream;

public class GuiStreamOptions extends net.minecraft.client.gui.GuiScreen
{
  private static final GameSettings.Options[] field_152312_a = { GameSettings.Options.STREAM_BYTES_PER_PIXEL, GameSettings.Options.STREAM_FPS, GameSettings.Options.STREAM_KBPS, GameSettings.Options.STREAM_SEND_METADATA, GameSettings.Options.STREAM_VOLUME_MIC, GameSettings.Options.STREAM_VOLUME_SYSTEM, GameSettings.Options.STREAM_MIC_TOGGLE_BEHAVIOR, GameSettings.Options.STREAM_COMPRESSION };
  private static final GameSettings.Options[] field_152316_f = { GameSettings.Options.STREAM_CHAT_ENABLED, GameSettings.Options.STREAM_CHAT_USER_FILTER };
  private final net.minecraft.client.gui.GuiScreen field_152317_g;
  private final GameSettings field_152318_h;
  private String field_152319_i;
  private String field_152313_r;
  private int field_152314_s;
  private boolean field_152315_t = false;
  private static final String __OBFID = "CL_00001841";
  
  public GuiStreamOptions(net.minecraft.client.gui.GuiScreen p_i1073_1_, GameSettings p_i1073_2_)
  {
    field_152317_g = p_i1073_1_;
    field_152318_h = p_i1073_2_;
  }
  



  public void initGui()
  {
    int var1 = 0;
    field_152319_i = I18n.format("options.stream.title", new Object[0]);
    field_152313_r = I18n.format("options.stream.chat.title", new Object[0]);
    GameSettings.Options[] var2 = field_152312_a;
    int var3 = var2.length;
    


    for (int var4 = 0; var4 < var3; var4++)
    {
      GameSettings.Options var5 = var2[var4];
      
      if (var5.getEnumFloat())
      {
        buttonList.add(new net.minecraft.client.gui.GuiOptionSlider(var5.returnEnumOrdinal(), width / 2 - 155 + var1 % 2 * 160, height / 6 + 24 * (var1 >> 1), var5));
      }
      else
      {
        buttonList.add(new GuiOptionButton(var5.returnEnumOrdinal(), width / 2 - 155 + var1 % 2 * 160, height / 6 + 24 * (var1 >> 1), var5, field_152318_h.getKeyBinding(var5)));
      }
      
      var1++;
    }
    
    if (var1 % 2 == 1)
    {
      var1++;
    }
    
    field_152314_s = (height / 6 + 24 * (var1 >> 1) + 6);
    var1 += 2;
    var2 = field_152316_f;
    var3 = var2.length;
    
    for (var4 = 0; var4 < var3; var4++)
    {
      GameSettings.Options var5 = var2[var4];
      
      if (var5.getEnumFloat())
      {
        buttonList.add(new net.minecraft.client.gui.GuiOptionSlider(var5.returnEnumOrdinal(), width / 2 - 155 + var1 % 2 * 160, height / 6 + 24 * (var1 >> 1), var5));
      }
      else
      {
        buttonList.add(new GuiOptionButton(var5.returnEnumOrdinal(), width / 2 - 155 + var1 % 2 * 160, height / 6 + 24 * (var1 >> 1), var5, field_152318_h.getKeyBinding(var5)));
      }
      
      var1++;
    }
    
    buttonList.add(new GuiButton(200, width / 2 - 155, height / 6 + 168, 150, 20, I18n.format("gui.done", new Object[0])));
    GuiButton var6 = new GuiButton(201, width / 2 + 5, height / 6 + 168, 150, 20, I18n.format("options.stream.ingestSelection", new Object[0]));
    enabled = (((mc.getTwitchStream().func_152924_m()) && (mc.getTwitchStream().func_152925_v().length > 0)) || (mc.getTwitchStream().func_152908_z()));
    buttonList.add(var6);
  }
  
  protected void actionPerformed(GuiButton button) throws java.io.IOException
  {
    if (enabled)
    {
      if ((id < 100) && ((button instanceof GuiOptionButton)))
      {
        GameSettings.Options var2 = ((GuiOptionButton)button).returnEnumOptions();
        field_152318_h.setOptionValue(var2, 1);
        displayString = field_152318_h.getKeyBinding(GameSettings.Options.getEnumOptions(id));
        
        if ((mc.getTwitchStream().func_152934_n()) && (var2 != GameSettings.Options.STREAM_CHAT_ENABLED) && (var2 != GameSettings.Options.STREAM_CHAT_USER_FILTER))
        {
          field_152315_t = true;
        }
      }
      else if ((button instanceof net.minecraft.client.gui.GuiOptionSlider))
      {
        if (id == GameSettings.Options.STREAM_VOLUME_MIC.returnEnumOrdinal())
        {
          mc.getTwitchStream().func_152915_s();
        }
        else if (id == GameSettings.Options.STREAM_VOLUME_SYSTEM.returnEnumOrdinal())
        {
          mc.getTwitchStream().func_152915_s();
        }
        else if (mc.getTwitchStream().func_152934_n())
        {
          field_152315_t = true;
        }
      }
      
      if (id == 200)
      {
        mc.gameSettings.saveOptions();
        mc.displayGuiScreen(field_152317_g);
      }
      else if (id == 201)
      {
        mc.gameSettings.saveOptions();
        mc.displayGuiScreen(new GuiIngestServers(this));
      }
    }
  }
  



  public void drawScreen(int mouseX, int mouseY, float partialTicks)
  {
    drawDefaultBackground();
    drawCenteredString(fontRendererObj, field_152319_i, width / 2, 20, 16777215);
    drawCenteredString(fontRendererObj, field_152313_r, width / 2, field_152314_s, 16777215);
    
    if (field_152315_t)
    {
      drawCenteredString(fontRendererObj, net.minecraft.util.EnumChatFormatting.RED + I18n.format("options.stream.changes", new Object[0]), width / 2, 20 + fontRendererObj.FONT_HEIGHT, 16777215);
    }
    
    super.drawScreen(mouseX, mouseY, partialTicks);
  }
}
