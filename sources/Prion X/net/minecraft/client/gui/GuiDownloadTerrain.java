package net.minecraft.client.gui;

import java.io.IOException;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.play.client.C00PacketKeepAlive;

public class GuiDownloadTerrain extends GuiScreen
{
  private NetHandlerPlayClient netHandlerPlayClient;
  private int progress;
  private static final String __OBFID = "CL_00000708";
  
  public GuiDownloadTerrain(NetHandlerPlayClient p_i45023_1_)
  {
    netHandlerPlayClient = p_i45023_1_;
  }
  



  protected void keyTyped(char typedChar, int keyCode)
    throws IOException
  {}
  


  public void initGui()
  {
    buttonList.clear();
  }
  



  public void updateScreen()
  {
    progress += 1;
    
    if (progress % 20 == 0)
    {
      netHandlerPlayClient.addToSendQueue(new C00PacketKeepAlive());
    }
  }
  



  public void drawScreen(int mouseX, int mouseY, float partialTicks)
  {
    drawBackground(0);
    drawCenteredString(fontRendererObj, I18n.format("multiplayer.downloadingTerrain", new Object[0]), width / 2, height / 2 - 50, 16777215);
    super.drawScreen(mouseX, mouseY, partialTicks);
  }
  



  public boolean doesGuiPauseGame()
  {
    return false;
  }
}
