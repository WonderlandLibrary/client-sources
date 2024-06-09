package me.hexxed.mercury.modules;

import me.hexxed.mercury.modulebase.Module;
import me.hexxed.mercury.modulebase.ModuleCategory;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C07PacketPlayerDigging.Action;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class Nuker extends Module
{
  public Nuker()
  {
    super("Nuker", 0, true, ModuleCategory.WORLD);
  }
  
  private int xOffset = -4;
  private int yOffset = 4;
  private int zOffset = -4;
  private Block nukeBlock = null;
  
  public void onClickBlock(BlockPos pos, EnumFacing face)
  {
    nukeBlock = mc.theWorld.getBlockState(new BlockPos(pos.getX(), pos.getY(), pos.getZ())).getBlock();
  }
  
  public void smashBlock(BlockPos pos) {
    mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, pos, EnumFacing.UP));
    mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, pos, EnumFacing.UP));
  }
  
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
          int id = Block.getIdFromBlock(mc.theWorld.getBlockState(new BlockPos(x, y, z)).getBlock());
          if (mc.playerController.isInCreativeMode() ? id != 0 : mc.theWorld.getBlockState(new BlockPos(x, y, z)).getBlock() == nukeBlock) {
            smashBlock(new BlockPos(x, y, z));
            break;
          }
        }
      }
    }
  }
}
