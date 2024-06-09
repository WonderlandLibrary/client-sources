package Squad.Modules.Movement;

import com.darkmagician6.eventapi.EventTarget;

import Squad.Events.EventUpdate;
import Squad.base.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;

public class FastStairs
extends Module
{
public FastStairs()
{
  super("FastStairs", 0, 4919, Category.Movement);
}

@EventTarget
public void onUpdate(EventUpdate e)
{
  BlockPos bp = new BlockPos(Minecraft.getMinecraft().thePlayer.posX, Minecraft.getMinecraft().thePlayer.posY - 1.0D, Minecraft.getMinecraft().thePlayer.posZ);
  if ((Minecraft.getMinecraft().theWorld.getBlockState(bp).getBlock() == Blocks.acacia_stairs) || 
    (Minecraft.getMinecraft().theWorld.getBlockState(bp).getBlock() == Blocks.birch_stairs) || 
    (Minecraft.getMinecraft().theWorld.getBlockState(bp).getBlock() == Blocks.brick_stairs) || 
    (Minecraft.getMinecraft().theWorld.getBlockState(bp).getBlock() == Blocks.dark_oak_stairs) || 
    (Minecraft.getMinecraft().theWorld.getBlockState(bp).getBlock() == Blocks.jungle_stairs) || 
    (Minecraft.getMinecraft().theWorld.getBlockState(bp).getBlock() == Blocks.nether_brick_stairs) || 
    (Minecraft.getMinecraft().theWorld.getBlockState(bp).getBlock() == Blocks.oak_stairs) || 
    (Minecraft.getMinecraft().theWorld.getBlockState(bp).getBlock() == Blocks.quartz_stairs) || 
    (Minecraft.getMinecraft().theWorld.getBlockState(bp).getBlock() == Blocks.red_sandstone_stairs) || 
    (Minecraft.getMinecraft().theWorld.getBlockState(bp).getBlock() == Blocks.sandstone_stairs) || 
    (Minecraft.getMinecraft().theWorld.getBlockState(bp).getBlock() == Blocks.spruce_stairs) || 
    (Minecraft.getMinecraft().theWorld.getBlockState(bp).getBlock() == Blocks.stone_brick_stairs) || 
    (Minecraft.getMinecraft().theWorld.getBlockState(bp).getBlock() == Blocks.stone_stairs)) {
    setSpeed(0.32F);
  }
}

public static void setSpeed(float speed)
{
  Minecraft.getMinecraft().getMinecraft();EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
  double yaw = player.rotationYaw;
  boolean isMoving = (player.moveForward != 0.0F) || (player.moveStrafing != 0.0F);
  boolean isMovingForward = player.moveForward > 0.0F;
  boolean isMovingBackward = player.moveForward < 0.0F;
  boolean isMovingRight = player.moveStrafing > 0.0F;
  boolean isMovingLeft = player.moveStrafing < 0.0F;
  boolean isMovingSideways = (isMovingLeft) || (isMovingRight);
  boolean isMovingStraight = (isMovingForward) || (isMovingBackward);
  if (isMoving)
  {
    if ((isMovingForward) && (!isMovingSideways)) {
      yaw += 0.0D;
    } else if ((isMovingBackward) && (!isMovingSideways)) {
      yaw += 180.0D;
    } else if ((isMovingForward) && (isMovingLeft)) {
      yaw += 45.0D;
    } else if (isMovingForward) {
      yaw -= 45.0D;
    } else if ((!isMovingStraight) && (isMovingLeft)) {
      yaw += 90.0D;
    } else if ((!isMovingStraight) && (isMovingRight)) {
      yaw -= 90.0D;
    } else if ((isMovingBackward) && (isMovingLeft)) {
      yaw += 135.0D;
    } else if (isMovingBackward) {
      yaw -= 135.0D;
    }
    yaw = Math.toRadians(yaw);
    player.motionX = (-Math.sin(yaw) * speed);
    player.motionZ = (Math.cos(yaw) * speed);
  }
}
}
