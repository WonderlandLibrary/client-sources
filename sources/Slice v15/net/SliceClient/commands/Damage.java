package net.SliceClient.commands;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.play.client.C03PacketPlayer.C04PacketPlayerPosition;

public class Damage extends net.SliceClient.commandbase.Command
{
  public Damage()
  {
    super("Damage", "Damage");
  }
  

  @net.SliceClient.Utils.Val(increment=0.5D, min=0.5D)
  private double damage = 0.5D;
  

  public void execute(String[] args)
  {
    for (int i = 0; i < 70; i++) {
      thePlayersendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(thePlayerposX, thePlayerposY + 0.06D, thePlayerposZ, false));
      thePlayersendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(thePlayerposX, thePlayerposY, thePlayerposZ, false));
    }
    thePlayersendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(thePlayerposX, thePlayerposY + 0.1D, thePlayerposZ, false));
  }
}
