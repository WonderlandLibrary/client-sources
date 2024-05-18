package net.SliceClient.modules;

import net.SliceClient.module.Category;
import net.SliceClient.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.network.play.client.C03PacketPlayer;

public class AntiFire
  extends Module
{
  public AntiFire()
  {
    super("AntiFire", Category.EXPLOITS, 16376546);
  }
  
  public void onUpdate()
  {
    if (!getState())
      return;
    if ((!thePlayercapabilities.isCreativeMode) && (Minecraft.thePlayer.isBurning())) {
      for (int i = 0; i < 100; i++) {
        thePlayersendQueue.addToSendQueue(new C03PacketPlayer());
      }
    }
  }
}
