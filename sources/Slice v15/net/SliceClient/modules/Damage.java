package net.SliceClient.modules;

import net.SliceClient.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.play.client.C03PacketPlayer.C04PacketPlayerPosition;

public class Damage extends Module
{
  public Damage()
  {
    super("Damage", net.SliceClient.module.Category.EXPLOITS, 16376546);
  }
  
  private double damage = 0.5D;
  
  public void onEnable()
  {
    if (Minecraft.thePlayer != null)
    {
      for (int i = 0; i < 80.0D + 40.0D * (damage - 0.5D); i++)
      {
        thePlayersendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(thePlayerposX, thePlayerposY + 0.049D, thePlayerposZ, false));
        thePlayersendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(thePlayerposX, thePlayerposY, thePlayerposZ, false));
      }
      thePlayersendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(thePlayerposX, thePlayerposY, thePlayerposZ, true));
    }
    Toggle();
  }
}
