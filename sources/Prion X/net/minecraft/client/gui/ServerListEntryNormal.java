package net.minecraft.client.gui;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.Unpooled;
import java.awt.image.BufferedImage;
import java.net.UnknownHostException;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.ServerList;
import net.minecraft.client.network.OldServerPinger;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerListEntryNormal implements GuiListExtended.IGuiListEntry
{
  private static final Logger logger = ;
  private static final ThreadPoolExecutor field_148302_b = new java.util.concurrent.ScheduledThreadPoolExecutor(5, new ThreadFactoryBuilder().setNameFormat("Server Pinger #%d").setDaemon(true).build());
  private static final ResourceLocation field_178015_c = new ResourceLocation("textures/misc/unknown_server.png");
  private static final ResourceLocation field_178014_d = new ResourceLocation("textures/gui/server_selection.png");
  private final GuiMultiplayer field_148303_c;
  private final Minecraft field_148300_d;
  private final ServerData field_148301_e;
  private final ResourceLocation field_148306_i;
  private String field_148299_g;
  private DynamicTexture field_148305_h;
  private long field_148298_f;
  private static final String __OBFID = "CL_00000817";
  
  protected ServerListEntryNormal(GuiMultiplayer p_i45048_1_, ServerData p_i45048_2_)
  {
    field_148303_c = p_i45048_1_;
    field_148301_e = p_i45048_2_;
    field_148300_d = Minecraft.getMinecraft();
    field_148306_i = new ResourceLocation("servers/" + serverIP + "/icon");
    field_148305_h = ((DynamicTexture)field_148300_d.getTextureManager().getTexture(field_148306_i));
  }
  
  public void drawEntry(int slotIndex, int x, int y, int listWidth, int slotHeight, int mouseX, int mouseY, boolean isSelected)
  {
    if (!field_148301_e.field_78841_f)
    {
      field_148301_e.field_78841_f = true;
      field_148301_e.pingToServer = -2L;
      field_148301_e.serverMOTD = "";
      field_148301_e.populationInfo = "";
      field_148302_b.submit(new Runnable()
      {
        private static final String __OBFID = "CL_00000818";
        
        public void run()
        {
          try {
            field_148303_c.getOldServerPinger().ping(field_148301_e);
          }
          catch (UnknownHostException var2)
          {
            field_148301_e.pingToServer = -1L;
            field_148301_e.serverMOTD = (EnumChatFormatting.DARK_RED + "Can't resolve hostname");
          }
          catch (Exception var3)
          {
            field_148301_e.pingToServer = -1L;
            field_148301_e.serverMOTD = (EnumChatFormatting.DARK_RED + "Can't connect to server.");
          }
        }
      });
    }
    
    boolean var9 = field_148301_e.version > 47;
    boolean var10 = field_148301_e.version < 47;
    boolean var11 = (var9) || (var10);
    field_148300_d.fontRendererObj.drawString(field_148301_e.serverName, x + 32 + 3, y + 1, 16777215);
    List var12 = field_148300_d.fontRendererObj.listFormattedStringToWidth(field_148301_e.serverMOTD, listWidth - 32 - 2);
    
    for (int var13 = 0; var13 < Math.min(var12.size(), 2); var13++)
    {
      field_148300_d.fontRendererObj.drawString((String)var12.get(var13), x + 32 + 3, y + 12 + field_148300_d.fontRendererObj.FONT_HEIGHT * var13, 8421504);
    }
    
    String var23 = var11 ? EnumChatFormatting.DARK_RED + field_148301_e.gameVersion : field_148301_e.populationInfo;
    int var14 = field_148300_d.fontRendererObj.getStringWidth(var23);
    field_148300_d.fontRendererObj.drawString(var23, x + listWidth - var14 - 15 - 2, y + 1, 8421504);
    byte var15 = 0;
    String var17 = null;
    
    int var16;
    String var18;
    if (var11)
    {
      int var16 = 5;
      String var18 = var9 ? "Client out of date!" : "Server out of date!";
      var17 = field_148301_e.playerList;
    }
    else if ((field_148301_e.field_78841_f) && (field_148301_e.pingToServer != -2L)) { int var16;
      int var16;
      if (field_148301_e.pingToServer < 0L)
      {
        var16 = 5;
      } else { int var16;
        if (field_148301_e.pingToServer < 150L)
        {
          var16 = 0;
        } else { int var16;
          if (field_148301_e.pingToServer < 300L)
          {
            var16 = 1;
          } else { int var16;
            if (field_148301_e.pingToServer < 600L)
            {
              var16 = 2;
            } else { int var16;
              if (field_148301_e.pingToServer < 1000L)
              {
                var16 = 3;
              }
              else
              {
                var16 = 4; }
            } } } }
      String var18;
      if (field_148301_e.pingToServer < 0L)
      {
        var18 = "(no connection)";
      }
      else
      {
        String var18 = field_148301_e.pingToServer + "ms";
        var17 = field_148301_e.playerList;
      }
    }
    else
    {
      var15 = 1;
      var16 = (int)(Minecraft.getSystemTime() / 100L + slotIndex * 2 & 0x7);
      
      if (var16 > 4)
      {
        var16 = 8 - var16;
      }
      
      var18 = "Pinging...";
    }
    
    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    field_148300_d.getTextureManager().bindTexture(Gui.icons);
    Gui.drawModalRectWithCustomSizedTexture(x + listWidth - 15, y, var15 * 10, 176 + var16 * 8, 10, 8, 256.0F, 256.0F);
    
    if ((field_148301_e.getBase64EncodedIconData() != null) && (!field_148301_e.getBase64EncodedIconData().equals(field_148299_g)))
    {
      field_148299_g = field_148301_e.getBase64EncodedIconData();
      prepareServerIcon();
      field_148303_c.getServerList().saveServerList();
    }
    
    if (field_148305_h != null)
    {
      func_178012_a(x, y, field_148306_i);
    }
    else
    {
      func_178012_a(x, y, field_178015_c);
    }
    
    int var19 = mouseX - x;
    int var20 = mouseY - y;
    
    if ((var19 >= listWidth - 15) && (var19 <= listWidth - 5) && (var20 >= 0) && (var20 <= 8))
    {
      field_148303_c.func_146793_a(var18);
    }
    else if ((var19 >= listWidth - var14 - 15 - 2) && (var19 <= listWidth - 15 - 2) && (var20 >= 0) && (var20 <= 8))
    {
      field_148303_c.func_146793_a(var17);
    }
    
    if ((field_148300_d.gameSettings.touchscreen) || (isSelected))
    {
      field_148300_d.getTextureManager().bindTexture(field_178014_d);
      Gui.drawRect(x, y, x + 32, y + 32, -1601138544);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      int var21 = mouseX - x;
      int var22 = mouseY - y;
      
      if (func_178013_b())
      {
        if ((var21 < 32) && (var21 > 16))
        {
          Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0F, 32.0F, 32, 32, 256.0F, 256.0F);
        }
        else
        {
          Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0F, 0.0F, 32, 32, 256.0F, 256.0F);
        }
      }
      
      if (field_148303_c.func_175392_a(this, slotIndex))
      {
        if ((var21 < 16) && (var22 < 16))
        {
          Gui.drawModalRectWithCustomSizedTexture(x, y, 96.0F, 32.0F, 32, 32, 256.0F, 256.0F);
        }
        else
        {
          Gui.drawModalRectWithCustomSizedTexture(x, y, 96.0F, 0.0F, 32, 32, 256.0F, 256.0F);
        }
      }
      
      if (field_148303_c.func_175394_b(this, slotIndex))
      {
        if ((var21 < 16) && (var22 > 16))
        {
          Gui.drawModalRectWithCustomSizedTexture(x, y, 64.0F, 32.0F, 32, 32, 256.0F, 256.0F);
        }
        else
        {
          Gui.drawModalRectWithCustomSizedTexture(x, y, 64.0F, 0.0F, 32, 32, 256.0F, 256.0F);
        }
      }
    }
  }
  
  protected void func_178012_a(int p_178012_1_, int p_178012_2_, ResourceLocation p_178012_3_)
  {
    field_148300_d.getTextureManager().bindTexture(p_178012_3_);
    GlStateManager.enableBlend();
    Gui.drawModalRectWithCustomSizedTexture(p_178012_1_, p_178012_2_, 0.0F, 0.0F, 32, 32, 32.0F, 32.0F);
    GlStateManager.disableBlend();
  }
  
  private boolean func_178013_b()
  {
    return true;
  }
  
  private void prepareServerIcon()
  {
    if (field_148301_e.getBase64EncodedIconData() == null)
    {
      field_148300_d.getTextureManager().deleteTexture(field_148306_i);
      field_148305_h = null;
    }
    else
    {
      ByteBuf var2 = Unpooled.copiedBuffer(field_148301_e.getBase64EncodedIconData(), com.google.common.base.Charsets.UTF_8);
      ByteBuf var3 = io.netty.handler.codec.base64.Base64.decode(var2);
      
      BufferedImage var1;
      
      try
      {
        BufferedImage var1 = net.minecraft.client.renderer.texture.TextureUtil.func_177053_a(new ByteBufInputStream(var3));
        Validate.validState(var1.getWidth() == 64, "Must be 64 pixels wide", new Object[0]);
        Validate.validState(var1.getHeight() == 64, "Must be 64 pixels high", new Object[0]);
        








        var2.release();
        var3.release();
      }
      catch (Exception var8)
      {
        logger.error("Invalid icon for server " + field_148301_e.serverName + " (" + field_148301_e.serverIP + ")", var8);
        field_148301_e.setBase64EncodedIconData(null);
        


        var2.release();
        var3.release();
      }
      finally
      {
        var2.release();
        var3.release();
      }
      





      field_148305_h = new DynamicTexture(var1.getWidth(), var1.getHeight());
      field_148300_d.getTextureManager().loadTexture(field_148306_i, field_148305_h);
      

      var1.getRGB(0, 0, var1.getWidth(), var1.getHeight(), field_148305_h.getTextureData(), 0, var1.getWidth());
      field_148305_h.updateDynamicTexture();
    }
  }
  



  public boolean mousePressed(int p_148278_1_, int p_148278_2_, int p_148278_3_, int p_148278_4_, int p_148278_5_, int p_148278_6_)
  {
    if (p_148278_5_ <= 32)
    {
      if ((p_148278_5_ < 32) && (p_148278_5_ > 16) && (func_178013_b()))
      {
        field_148303_c.selectServer(p_148278_1_);
        field_148303_c.connectToSelected();
        return true;
      }
      
      if ((p_148278_5_ < 16) && (p_148278_6_ < 16) && (field_148303_c.func_175392_a(this, p_148278_1_)))
      {
        field_148303_c.func_175391_a(this, p_148278_1_, GuiScreen.isShiftKeyDown());
        return true;
      }
      
      if ((p_148278_5_ < 16) && (p_148278_6_ > 16) && (field_148303_c.func_175394_b(this, p_148278_1_)))
      {
        field_148303_c.func_175393_b(this, p_148278_1_, GuiScreen.isShiftKeyDown());
        return true;
      }
    }
    
    field_148303_c.selectServer(p_148278_1_);
    
    if (Minecraft.getSystemTime() - field_148298_f < 250L)
    {
      field_148303_c.connectToSelected();
    }
    
    field_148298_f = Minecraft.getSystemTime();
    return false;
  }
  

  public void setSelected(int p_178011_1_, int p_178011_2_, int p_178011_3_) {}
  

  public void mouseReleased(int slotIndex, int x, int y, int mouseEvent, int relativeX, int relativeY) {}
  

  public ServerData getServerData()
  {
    return field_148301_e;
  }
}
