package net.SliceClient.commands;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.play.client.C03PacketPlayer.C04PacketPlayerPosition;

public class AACDamage extends net.SliceClient.commandbase.Command
{
  public AACDamage()
  {
    super("AACDamage", "AACDamage");
  }
  

  @net.SliceClient.Utils.Val(increment=0.5D, min=0.5D)
  private double damage = 0.5D;
  

  public void execute(String[] args)
  {
    for (int i = 0; i < 80.0D + 40.0D * (damage - 1.5D); i++)
    {


      thePlayersendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(thePlayerposX, thePlayerposY + 88.179D, thePlayerposZ, false));
      thePlayersendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(thePlayerposX, thePlayerposY, thePlayerposZ, false));
    }
    thePlayersendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(thePlayerposX, thePlayerposY, thePlayerposZ, true));
    
    net.SliceClient.Utils.Util.addChatMessage(net.SliceClient.Slice.prefix + "Damaged 0.5 Hearts!");
  }
}
