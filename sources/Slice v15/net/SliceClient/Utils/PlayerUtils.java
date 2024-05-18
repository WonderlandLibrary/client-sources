package net.SliceClient.Utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class PlayerUtils
{
  public PlayerUtils() {}
  
  public static boolean isMoving()
  {
    if (((playerlastTickPosX != playerposX) && 
      (playerlastTickPosZ != playerposZ)) || (playermotionX != 0.0D) || 
      (playermotionY != 0.0D) || 
      (playermotionZ != 0.0D)) {
      return true;
    }
    return false;
  }
  
  public boolean isInventoryFull()
  {
    for (int index = 9; index <= 44; index++)
    {
      Helper.mc();net.minecraft.item.ItemStack stack = thePlayerinventoryContainer.getSlot(index).getStack();
      if (stack == null) {
        return false;
      }
    }
    return true;
  }
  
  public double getDistanceToFall()
  {
    double distance = 0.0D;
    for (double i = playerposY; i > 0.0D; i -= 1.0D)
    {
      Helper.blockUtils();net.minecraft.block.Block block = BlockUtils.getBlock(new BlockPos(playerposX, i, playerposZ));
      if ((block.getMaterial() != net.minecraft.block.material.Material.air) && (block.isSolidFullCube()) && (block.isCollidable())) {
        distance = i;
      } else {
        if (i < 0.0D) {
          break;
        }
      }
    }
    double distancetofall = playerposY - distance - 1.0D;
    return distancetofall;
  }
  
  public boolean MovementInput()
  {
    Helper.mc(); if (!gameSettingskeyBindForward.getIsKeyPressed()) { Helper.mc(); if (!gameSettingskeyBindBack.getIsKeyPressed()) {
        Helper.mc(); if (!gameSettingskeyBindLeft.getIsKeyPressed()) { Helper.mc(); if (!gameSettingskeyBindRight.getIsKeyPressed()) { Helper.mc(); if (!gameSettingskeyBindSneak.getIsKeyPressed())
              return false; } } } } return true;
  }
  

  public float[] aimAtLocation(double x, double y, double z, EnumFacing facing)
  {
    Helper.mc();EntitySnowball temp = new EntitySnowball(Minecraft.theWorld);
    posX = (x + 0.5D);
    posY = (y + 0.5D);
    posZ = (z + 0.5D);
    posX += facing.getDirectionVec().getX() * 0.25D;
    posY += facing.getDirectionVec().getY() * 0.25D;
    posZ += facing.getDirectionVec().getZ() * 0.25D;
    return aimAtLocation(posX, posY, posZ);
  }
  
  public float[] aimAtLocation(double positionX, double positionY, double positionZ)
  {
    Helper.mc();double x = positionX - thePlayerposX;
    Helper.mc();double y = positionY - thePlayerposY;
    Helper.mc();double z = positionZ - thePlayerposZ;
    double distance = net.minecraft.util.MathHelper.sqrt_double(x * x + z * z);
    return new float[] { (float)(Math.atan2(z, x) * 180.0D / 3.141592653589793D) - 90.0F, (float)-(Math.atan2(y, distance) * 180.0D / 3.141592653589793D) };
  }
  
  public static void blinkToPos(double[] startPos, BlockPos endPos, double slack, double[] pOffset)
  {
    double curX = startPos[0];
    double curY = startPos[1];
    double curZ = startPos[2];
    double endX = endPos.getX() + 0.5D;
    double endY = endPos.getY() + 1.0D;
    double endZ = endPos.getZ() + 0.5D;
    
    double distance = Math.abs(curX - endX) + Math.abs(curY - endY) + Math.abs(curZ - endZ);
    int count = 0;
    while (distance > slack)
    {
      distance = Math.abs(curX - endX) + Math.abs(curY - endY) + Math.abs(curZ - endZ);
      if (count > 120) {
        break;
      }
      boolean next = false;
      double diffX = curX - endX;
      double diffY = curY - endY;
      double diffZ = curZ - endZ;
      
      double offset = (count & 0x1) == 0 ? pOffset[0] : pOffset[1];
      if (diffX < 0.0D) {
        if (Math.abs(diffX) > offset) {
          curX += offset;
        } else {
          curX += Math.abs(diffX);
        }
      }
      if (diffX > 0.0D) {
        if (Math.abs(diffX) > offset) {
          curX -= offset;
        } else {
          curX -= Math.abs(diffX);
        }
      }
      if (diffY < 0.0D) {
        if (Math.abs(diffY) > 0.25D) {
          curY += 0.25D;
        } else {
          curY += Math.abs(diffY);
        }
      }
      if (diffY > 0.0D) {
        if (Math.abs(diffY) > 0.25D) {
          curY -= 0.25D;
        } else {
          curY -= Math.abs(diffY);
        }
      }
      if (diffZ < 0.0D) {
        if (Math.abs(diffZ) > offset) {
          curZ += offset;
        } else {
          curZ += Math.abs(diffZ);
        }
      }
      if (diffZ > 0.0D) {
        if (Math.abs(diffZ) > offset) {
          curZ -= offset;
        } else {
          curZ -= Math.abs(diffZ);
        }
      }
      Minecraft.getMinecraft();Minecraft.getNetHandler().addToSendQueue(new net.minecraft.network.play.client.C03PacketPlayer.C04PacketPlayerPosition(curX, curY, curZ, true));
      count++;
    }
  }
}
