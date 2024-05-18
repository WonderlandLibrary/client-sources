package me.hexxed.mercury.modules;

import me.hexxed.mercury.util.TimeHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C07PacketPlayerDigging.Action;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class BedDestroyer extends me.hexxed.mercury.modulebase.Module
{
  public BedDestroyer()
  {
    super("Bed Destroyer", 0, true, me.hexxed.mercury.modulebase.ModuleCategory.WORLD);
  }
  
  public TimeHelper delay = new TimeHelper();
  private int xOffset;
  private int zOffset;
  private int yOffset;
  
  public void onTick()
  {
    if (mc.theWorld == null) {
      return;
    }
    for (xOffset = -5; xOffset < 6; xOffset += 1) {
      for (zOffset = -5; zOffset < 6; zOffset += 1) {
        for (yOffset = 5; yOffset > -5; yOffset -= 1) {
          double x = mc.thePlayer.posX + xOffset;
          double y = mc.thePlayer.posY + yOffset;
          double z = mc.thePlayer.posZ + zOffset;
          int id = net.minecraft.block.Block.getIdFromBlock(mc.theWorld.getBlockState(new BlockPos(x, y, z)).getBlock());
          if (id == 26) {
            smashBlock(new BlockPos(x, y, z));
            break;
          }
        }
      }
    }
  }
  
  public void smashBlock(BlockPos pos)
  {
    mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, pos, EnumFacing.UP));
    mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, pos, EnumFacing.UP));
  }
}
