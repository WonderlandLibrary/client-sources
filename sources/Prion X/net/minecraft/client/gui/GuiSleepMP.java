package net.minecraft.client.gui;

import java.io.IOException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.resources.I18n;

public class GuiSleepMP extends GuiChat
{
  private static final String __OBFID = "CL_00000697";
  
  public GuiSleepMP() {}
  
  public void initGui()
  {
    super.initGui();
    buttonList.add(new GuiButton(1, width / 2 - 100, height - 40, I18n.format("multiplayer.stopSleeping", new Object[0])));
  }
  



  protected void keyTyped(char typedChar, int keyCode)
    throws IOException
  {
    if (keyCode == 1)
    {
      wakeFromSleep();
    }
    else if ((keyCode != 28) && (keyCode != 156))
    {
      super.keyTyped(typedChar, keyCode);
    }
    else
    {
      String var3 = inputField.getText().trim();
      
      if (!var3.isEmpty())
      {
        mc.thePlayer.sendChatMessage(var3);
      }
      
      inputField.setText("");
      mc.ingameGUI.getChatGUI().resetScroll();
    }
  }
  
  protected void actionPerformed(GuiButton button) throws IOException
  {
    if (id == 1)
    {
      wakeFromSleep();
    }
    else
    {
      super.actionPerformed(button);
    }
  }
  
  private void wakeFromSleep()
  {
    NetHandlerPlayClient var1 = mc.thePlayer.sendQueue;
    var1.addToSendQueue(new net.minecraft.network.play.client.C0BPacketEntityAction(mc.thePlayer, net.minecraft.network.play.client.C0BPacketEntityAction.Action.STOP_SLEEPING));
  }
}
