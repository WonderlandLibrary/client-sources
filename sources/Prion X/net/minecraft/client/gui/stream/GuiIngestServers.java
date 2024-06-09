package net.minecraft.client.gui.stream;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.stream.IStream;
import net.minecraft.client.stream.IngestServerTester;
import net.minecraft.util.EnumChatFormatting;
import tv.twitch.broadcast.IngestServer;

public class GuiIngestServers extends GuiScreen
{
  private final GuiScreen field_152309_a;
  private String field_152310_f;
  private ServerList field_152311_g;
  private static final String __OBFID = "CL_00001843";
  
  public GuiIngestServers(GuiScreen p_i46312_1_)
  {
    field_152309_a = p_i46312_1_;
  }
  



  public void initGui()
  {
    field_152310_f = net.minecraft.client.resources.I18n.format("options.stream.ingest.title", new Object[0]);
    field_152311_g = new ServerList(mc);
    
    if (!mc.getTwitchStream().func_152908_z())
    {
      mc.getTwitchStream().func_152909_x();
    }
    
    buttonList.add(new GuiButton(1, width / 2 - 155, height - 24 - 6, 150, 20, net.minecraft.client.resources.I18n.format("gui.done", new Object[0])));
    buttonList.add(new GuiButton(2, width / 2 + 5, height - 24 - 6, 150, 20, net.minecraft.client.resources.I18n.format("options.stream.ingest.reset", new Object[0])));
  }
  


  public void handleMouseInput()
    throws java.io.IOException
  {
    super.handleMouseInput();
    field_152311_g.func_178039_p();
  }
  



  public void onGuiClosed()
  {
    if (mc.getTwitchStream().func_152908_z())
    {
      mc.getTwitchStream().func_152932_y().func_153039_l();
    }
  }
  
  protected void actionPerformed(GuiButton button) throws java.io.IOException
  {
    if (enabled)
    {
      if (id == 1)
      {
        mc.displayGuiScreen(field_152309_a);
      }
      else
      {
        mc.gameSettings.streamPreferredServer = "";
        mc.gameSettings.saveOptions();
      }
    }
  }
  



  public void drawScreen(int mouseX, int mouseY, float partialTicks)
  {
    drawDefaultBackground();
    field_152311_g.drawScreen(mouseX, mouseY, partialTicks);
    drawCenteredString(fontRendererObj, field_152310_f, width / 2, 20, 16777215);
    super.drawScreen(mouseX, mouseY, partialTicks);
  }
  
  class ServerList extends net.minecraft.client.gui.GuiSlot
  {
    private static final String __OBFID = "CL_00001842";
    
    public ServerList(Minecraft mcIn)
    {
      super(width, height, 32, height - 35, (int)(fontRendererObj.FONT_HEIGHT * 3.5D));
      setShowSelectionBox(false);
    }
    
    protected int getSize()
    {
      return mc.getTwitchStream().func_152925_v().length;
    }
    
    protected void elementClicked(int slotIndex, boolean isDoubleClick, int mouseX, int mouseY)
    {
      mc.gameSettings.streamPreferredServer = mc.getTwitchStream().func_152925_v()[slotIndex].serverUrl;
      mc.gameSettings.saveOptions();
    }
    
    protected boolean isSelected(int slotIndex)
    {
      return mc.getTwitchStream().func_152925_v()[slotIndex].serverUrl.equals(mc.gameSettings.streamPreferredServer);
    }
    
    protected void drawBackground() {}
    
    protected void drawSlot(int p_180791_1_, int p_180791_2_, int p_180791_3_, int p_180791_4_, int p_180791_5_, int p_180791_6_)
    {
      IngestServer var7 = mc.getTwitchStream().func_152925_v()[p_180791_1_];
      String var8 = serverUrl.replaceAll("\\{stream_key\\}", "");
      String var9 = (int)bitrateKbps + " kbps";
      String var10 = null;
      IngestServerTester var11 = mc.getTwitchStream().func_152932_y();
      
      if (var11 != null)
      {
        if (var7 == var11.func_153040_c())
        {
          var8 = EnumChatFormatting.GREEN + var8;
          var9 = (int)(var11.func_153030_h() * 100.0F) + "%";
        }
        else if (p_180791_1_ < var11.func_153028_p())
        {
          if (bitrateKbps == 0.0F)
          {
            var9 = EnumChatFormatting.RED + "Down!";
          }
        }
        else
        {
          var9 = EnumChatFormatting.OBFUSCATED + "1234" + EnumChatFormatting.RESET + " kbps";
        }
      }
      else if (bitrateKbps == 0.0F)
      {
        var9 = EnumChatFormatting.RED + "Down!";
      }
      
      p_180791_2_ -= 15;
      
      if (isSelected(p_180791_1_))
      {
        var10 = EnumChatFormatting.BLUE + "(Preferred)";
      }
      else if (defaultServer)
      {
        var10 = EnumChatFormatting.GREEN + "(Default)";
      }
      
      drawString(fontRendererObj, serverName, p_180791_2_ + 2, p_180791_3_ + 5, 16777215);
      drawString(fontRendererObj, var8, p_180791_2_ + 2, p_180791_3_ + fontRendererObj.FONT_HEIGHT + 5 + 3, 3158064);
      drawString(fontRendererObj, var9, getScrollBarX() - 5 - fontRendererObj.getStringWidth(var9), p_180791_3_ + 5, 8421504);
      
      if (var10 != null)
      {
        drawString(fontRendererObj, var10, getScrollBarX() - 5 - fontRendererObj.getStringWidth(var10), p_180791_3_ + 5 + 3 + fontRendererObj.FONT_HEIGHT, 8421504);
      }
    }
    
    protected int getScrollBarX()
    {
      return super.getScrollBarX() + 15;
    }
  }
}
