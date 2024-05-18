package net.SliceClient.modules;

import net.SliceClient.Utils.TimeHelper;
import net.SliceClient.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C07PacketPlayerDigging.Action;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class BedFucker extends Module
{
  public BedFucker()
  {
    super("BedFucker", net.SliceClient.module.Category.WORLD, 16376546);
  }
  
  public TimeHelper delay = new TimeHelper();
  private int xOffset;
  private int zOffset;
  private int yOffset;
  
  public void onUpdate()
  {
    if (!getState()) {
      if ((Minecraft.thePlayer == null) || (Minecraft.theWorld == null))
        return;
      return;
    }
    for (xOffset = -5; xOffset < 6; xOffset += 1) {
      for (zOffset = -5; zOffset < 6; zOffset += 1) {
        for (yOffset = 5; yOffset > -5; yOffset -= 1)
        {
          double x = thePlayerposX + xOffset;
          double y = thePlayerposY + yOffset;
          double z = thePlayerposZ + zOffset;
          int id = net.minecraft.block.Block.getIdFromBlock(Minecraft.theWorld.getBlockState(new BlockPos(x, y, z)).getBlock());
          if (id == 26)
          {
            smashBlock(new BlockPos(x, y, z));
            break;
          }
        }
      }
    }
  }
  
  public void smashBlock(BlockPos pos)
  {
    thePlayersendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, pos, EnumFacing.UP));
    thePlayersendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, pos, EnumFacing.UP));
  }
}
