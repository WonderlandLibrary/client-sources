package net.SliceClient.modules;

import net.SliceClient.module.Category;
import net.SliceClient.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.client.C03PacketPlayer.C04PacketPlayerPosition;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C07PacketPlayerDigging.Action;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.util.EnumFacing;

public class FastUse extends Module
{
  int random = 0;
  
  public FastUse()
  {
    super("FastUse", Category.PLAYER, 16376546);
  }
  
  public void onUpdate()
  {
    if (!getState()) {
      return;
    }
    if (Minecraft.thePlayer.isUsingItem())
    {
      random += 1;
      if (random >= 28)
      {
        thePlayersendQueue.addToSendQueue(new C07PacketPlayerDigging(
          C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, 
          new net.minecraft.util.BlockPos(0, 0, 0), EnumFacing.DOWN));
        thePlayersendQueue.addToSendQueue(new C09PacketHeldItemChange(1));
        Minecraft.thePlayer.stopUsingItem();
        random = 0;
      }
      thePlayersendQueue.netManager.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(thePlayerposX, thePlayerposY, thePlayerposZ, true));
      
      thePlayersendQueue.netManager.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(thePlayerposX, thePlayerposY, thePlayerposZ, true));
      
      net.minecraft.util.Timer.timerSpeed = 1.12F;
    }
    else
    {
      net.minecraft.util.Timer.timerSpeed = 1.0F;
    }
  }
  

  public void onDisable()
  {
    super.onDisable();
  }
}
