package me.hexxed.mercury.modules;

import me.hexxed.mercury.Mercury;
import me.hexxed.mercury.modulebase.Module;
import me.hexxed.mercury.modulebase.Values;
import me.hexxed.mercury.util.TimeHelper;
import me.hexxed.mercury.util.Util;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.BlockPos;

public class CClimb extends Module
{
  public CClimb()
  {
    super("Checker Climb", 62, true, me.hexxed.mercury.modulebase.ModuleCategory.MOVEMENT);
  }
  
  TimeHelper delay = new TimeHelper();
  
  public void onPreMotionUpdate()
  {
    if (!getValuesfastcclimb) return;
    if ((Util.isInsideBlock()) && (mc.gameSettings.keyBindJump.pressed) && (delay.isDelayComplete(150L))) {
      delay.setLastMS();
      mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY + 1.2D, mc.thePlayer.posZ);
    }
  }
  
  public void onBoundingBox(Block block, BlockPos pos)
  {
    if (getMinecraftthePlayer == null)
      return;
    if (((block.getMaterial().blocksMovement()) && (((getMinecraftthePlayer.isCollidedHorizontally) && (!getMinecraftthePlayer.isCollidedVertically)) || (pos.getY() > getMinecraftthePlayer.posY + 1.0D))) || ((Util.isInsideBlock()) && (getMinecraftthePlayer.isSneaking()) && (getMinecraftthePlayer.fallDistance < 2.5F) && (!Jesus.isInLiquid()) && (!getMinecraftthePlayer.isOnLadder()))) {
      getValuesboundingBox = null;
    }
  }
}
