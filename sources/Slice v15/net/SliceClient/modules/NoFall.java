package net.SliceClient.modules;

import net.SliceClient.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;

public class NoFall extends Module
{
  public NoFall()
  {
    super("NoFall", net.SliceClient.module.Category.PLAYER, 16376546);
  }
  
  public void onUpdate()
  {
    if (!getState())
      return;
    if ((Minecraft.thePlayer == null) || (Minecraft.theWorld == null)) {
      return;
    }
    


    if (thePlayerfallDistance > 2.0F) {
      thePlayersendQueue.addToSendQueue(new net.minecraft.network.play.client.C03PacketPlayer(true));
    }
  }
}
