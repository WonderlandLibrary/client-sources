package net.minecraft.client.gui;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.achievement.GuiAchievements;
import net.minecraft.client.resources.I18n;
import net.minecraft.server.integrated.IntegratedServer;

public class GuiIngameMenu extends GuiScreen
{
  private int field_146445_a;
  private int field_146444_f;
  private static final String __OBFID = "CL_00000703";
  
  public GuiIngameMenu() {}
  
  public void initGui()
  {
    field_146445_a = 0;
    buttonList.clear();
    byte var1 = -16;
    boolean var2 = true;
    buttonList.add(new GuiButton(1, width / 2 - 100, height / 4 + 120 + var1, I18n.format("menu.returnToMenu", new Object[0])));
    
    if (!mc.isIntegratedServerRunning())
    {
      buttonList.get(0)).displayString = I18n.format("menu.disconnect", new Object[0]);
    }
    
    buttonList.add(new GuiButton(4, width / 2 - 100, height / 4 + 24 + var1, I18n.format("menu.returnToGame", new Object[0])));
    buttonList.add(new GuiButton(0, width / 2 - 100, height / 4 + 96 + var1, 98, 20, I18n.format("menu.options", new Object[0])));
    GuiButton var3;
    buttonList.add(var3 = new GuiButton(7, width / 2 + 2, height / 4 + 96 + var1, 98, 20, I18n.format("menu.shareToLan", new Object[0])));
    buttonList.add(new GuiButton(5, width / 2 - 100, height / 4 + 48 + var1, 98, 20, I18n.format("gui.achievements", new Object[0])));
    buttonList.add(new GuiButton(6, width / 2 + 2, height / 4 + 48 + var1, 98, 20, I18n.format("gui.stats", new Object[0])));
    enabled = ((mc.isSingleplayer()) && (!mc.getIntegratedServer().getPublic()));
  }
  
  protected void actionPerformed(GuiButton button) throws java.io.IOException
  {
    switch (id)
    {
    case 0: 
      mc.displayGuiScreen(new GuiOptions(this, mc.gameSettings));
      break;
    
    case 1: 
      enabled = false;
      mc.theWorld.sendQuittingDisconnectingPacket();
      mc.loadWorld(null);
      mc.displayGuiScreen(new GuiMainMenu());
    
    case 2: 
    case 3: 
    default: 
      break;
    
    case 4: 
      mc.displayGuiScreen(null);
      mc.setIngameFocus();
      break;
    
    case 5: 
      mc.displayGuiScreen(new GuiAchievements(this, mc.thePlayer.getStatFileWriter()));
      break;
    
    case 6: 
      mc.displayGuiScreen(new net.minecraft.client.gui.achievement.GuiStats(this, mc.thePlayer.getStatFileWriter()));
      break;
    
    case 7: 
      mc.displayGuiScreen(new GuiShareToLan(this));
    }
    
  }
  


  public void updateScreen()
  {
    super.updateScreen();
    field_146444_f += 1;
  }
  



  public void drawScreen(int mouseX, int mouseY, float partialTicks)
  {
    drawDefaultBackground();
    drawCenteredString(fontRendererObj, I18n.format("menu.game", new Object[0]), width / 2, 40, 16777215);
    super.drawScreen(mouseX, mouseY, partialTicks);
  }
}
