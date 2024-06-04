package net.minecraft.client.gui;

import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.resources.I18n;

public class GuiGameOver extends GuiScreen implements GuiYesNoCallback
{
  private int field_146347_a;
  private boolean field_146346_f = false;
  
  private static final String __OBFID = "CL_00000690";
  
  public GuiGameOver() {}
  
  public void initGui()
  {
    buttonList.clear();
    
    if (mc.theWorld.getWorldInfo().isHardcoreModeEnabled())
    {
      if (mc.isIntegratedServerRunning())
      {
        buttonList.add(new GuiButton(1, width / 2 - 100, height / 4 + 96, I18n.format("deathScreen.deleteWorld", new Object[0])));
      }
      else
      {
        buttonList.add(new GuiButton(1, width / 2 - 100, height / 4 + 96, I18n.format("deathScreen.leaveServer", new Object[0])));
      }
    }
    else
    {
      buttonList.add(new GuiButton(0, width / 2 - 100, height / 4 + 72, I18n.format("deathScreen.respawn", new Object[0])));
      buttonList.add(new GuiButton(1, width / 2 - 100, height / 4 + 96, I18n.format("deathScreen.titleScreen", new Object[0])));
      
      if (mc.getSession() == null)
      {
        buttonList.get(1)).enabled = false;
      }
    }
    
    GuiButton var2;
    
    for (Iterator var1 = buttonList.iterator(); var1.hasNext(); enabled = false)
    {
      var2 = (GuiButton)var1.next();
    }
  }
  

  protected void keyTyped(char typedChar, int keyCode)
    throws java.io.IOException
  {}
  
  protected void actionPerformed(GuiButton button)
    throws java.io.IOException
  {
    switch (id)
    {
    case 0: 
      mc.thePlayer.respawnPlayer();
      mc.displayGuiScreen(null);
      break;
    
    case 1: 
      GuiYesNo var2 = new GuiYesNo(this, I18n.format("deathScreen.quit.confirm", new Object[0]), "", I18n.format("deathScreen.titleScreen", new Object[0]), I18n.format("deathScreen.respawn", new Object[0]), 0);
      mc.displayGuiScreen(var2);
      var2.setButtonDelay(20);
    }
  }
  
  public void confirmClicked(boolean result, int id)
  {
    if (result)
    {
      mc.theWorld.sendQuittingDisconnectingPacket();
      mc.loadWorld(null);
      mc.displayGuiScreen(new GuiMainMenu());
    }
    else
    {
      mc.thePlayer.respawnPlayer();
      mc.displayGuiScreen(null);
    }
  }
  



  public void drawScreen(int mouseX, int mouseY, float partialTicks)
  {
    drawGradientRect(0, 0, width, height, 1615855616, -1602211792);
    net.minecraft.client.renderer.GlStateManager.pushMatrix();
    net.minecraft.client.renderer.GlStateManager.scale(2.0F, 2.0F, 2.0F);
    boolean var4 = mc.theWorld.getWorldInfo().isHardcoreModeEnabled();
    String var5 = var4 ? I18n.format("deathScreen.title.hardcore", new Object[0]) : I18n.format("deathScreen.title", new Object[0]);
    drawCenteredString(fontRendererObj, var5, width / 2 / 2, 30, 16777215);
    net.minecraft.client.renderer.GlStateManager.popMatrix();
    
    if (var4)
    {
      drawCenteredString(fontRendererObj, I18n.format("deathScreen.hardcoreInfo", new Object[0]), width / 2, 144, 16777215);
    }
    
    drawCenteredString(fontRendererObj, I18n.format("deathScreen.score", new Object[0]) + ": " + net.minecraft.util.EnumChatFormatting.YELLOW + mc.thePlayer.getScore(), width / 2, 100, 16777215);
    super.drawScreen(mouseX, mouseY, partialTicks);
  }
  



  public boolean doesGuiPauseGame()
  {
    return false;
  }
  



  public void updateScreen()
  {
    super.updateScreen();
    field_146347_a += 1;
    

    if (field_146347_a == 20) {
      GuiButton var2;
      for (Iterator var1 = buttonList.iterator(); var1.hasNext(); enabled = true)
      {
        var2 = (GuiButton)var1.next();
      }
    }
  }
}
