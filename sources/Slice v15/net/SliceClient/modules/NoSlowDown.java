package net.SliceClient.modules;

import net.SliceClient.module.Category;
import net.SliceClient.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C07PacketPlayerDigging.Action;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class NoSlowDown extends Module
{
  public NoSlowDown()
  {
    super("NoSlowDown", Category.COMBAT, 16376546);
  }
  
  public void onPreMotionUpdate()
  {
    if (Minecraft.thePlayer.isBlocking()) {
      thePlayersendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, new BlockPos(0, 0, 0), EnumFacing.DOWN));
    }
  }
  
  public void onPostMotionUpdate()
  {
    if (Minecraft.thePlayer.isBlocking()) {
      thePlayersendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, thePlayerinventory.getCurrentItem(), 0.0F, 0.0F, 0.0F));
    }
  }
  
  public void onPostUpdate()
  {
    if ((thePlayermotionX == 0.0D) && (thePlayermotionZ == 0.0D)) {}
  }
}
