package com.darkcart.xdolf.util;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockIce;
import net.minecraft.block.BlockLadder;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockPackedIce;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public final class BlockHelper
{
  private static Minecraft mc = Minecraft.getMinecraft();
  
  public static Block getBlockAtPos(BlockPos inBlockPos)
  {
    IBlockState s = Minecraft.world.getBlockState(inBlockPos);
    return s.getBlock();
  }
  
  public static float[] getBlockRotations(double x, double y, double z)
  {
    double var4 = x - Minecraft.player.posX + 0.5D;
    double var2 = z - Minecraft.player.posZ + 0.5D;
    double var3 = y - (Minecraft.player.posY + Minecraft.player.getEyeHeight() - 1.0D);
    var4 = MathHelper.sqrt_double(var4 * var4 + var2 * var2);
    float var5 = (float)(Math.atan2(var2, var4) * 180.0D / 3.141592653589793D) - 90.0F;
    return new float[] { var5, (float)-(Math.atan2(var3, var4) * 180.0D / 3.141592653589793D) };
  }
  
  public static Block getBlock(Entity entity, double offset)
  {
    if (entity == null) {
      return null;
    }
    int y = (int)entity.getEntityBoundingBox().offset(0.0D, offset, 0.0D).minY;
    for (int x = MathHelper.floor_double(entity.getEntityBoundingBox().minX); x < MathHelper.floor_double(entity.getEntityBoundingBox().maxX) + 1; x++)
    {
      int z = MathHelper.floor_double(entity.getEntityBoundingBox().minZ);
      if (z < MathHelper.floor_double(entity.getEntityBoundingBox().maxZ) + 1) {
        return Minecraft.world.getBlockState(new BlockPos(x, y, z)).getBlock();
      }
    }
    return null;
  }
  
  public static boolean isInLiquid()
  {
    boolean inLiquid = false;
    if ((Minecraft.player == null) || (Minecraft.player.boundingBox == null)) {
      return false;
    }
    int y = (int)Minecraft.player.boundingBox.minY;
    for (int x = MathHelper.floor_double(Minecraft.player.boundingBox.minX); x < MathHelper.floor_double(Minecraft.player.boundingBox.maxX) + 1; x++) {
      for (int z = MathHelper.floor_double(Minecraft.player.boundingBox.minZ); z < MathHelper.floor_double(Minecraft.player.boundingBox.maxZ) + 1; z++)
      {
        Block block = Minecraft.world.getBlockState(new BlockPos(x, y, z)).getBlock();
        if ((block != null) && (!(block instanceof BlockAir)))
        {
          if (!(block instanceof BlockLiquid)) {
            return false;
          }
          inLiquid = true;
        }
      }
    }
    return inLiquid;
  }
  
  public static boolean isInLiquidNew()
  {
    boolean inLiquid = false;
    int y = (int)Minecraft.player.boundingBox.minY;
    for (int x = MathHelper.floor_double(Minecraft.player.boundingBox.minX); x < MathHelper.floor_double(Minecraft.player.boundingBox.maxX) + 1; x++) {
      for (int z = MathHelper.floor_double(Minecraft.player.boundingBox.minZ); z < MathHelper.floor_double(Minecraft.player.boundingBox.maxZ) + 1; z++)
      {
        Block block = Minecraft.world.getBlockState(new BlockPos(x, y, z)).getBlock();
        if ((block != null) && (!(block instanceof BlockAir)))
        {
          if (!(block instanceof BlockLiquid)) {
            return false;
          }
          inLiquid = true;
        }
      }
    }
    return inLiquid;
  }
  
  public static boolean isOnIce()
  {
    boolean onIce = false;
    int y = (int)(Minecraft.player.boundingBox.minY - 1.0D);
    for (int x = MathHelper.floor_double(Minecraft.player.boundingBox.minX); x < MathHelper.floor_double(Minecraft.player.boundingBox.maxX) + 1; x++) {
      for (int z = MathHelper.floor_double(Minecraft.player.boundingBox.minZ); z < MathHelper.floor_double(Minecraft.player.boundingBox.maxZ) + 1; z++)
      {
        Block block = Minecraft.world.getBlockState(new BlockPos(x, y, z)).getBlock();
        if ((block != null) && (!(block instanceof BlockAir)) && (((block instanceof BlockPackedIce)) || ((block instanceof BlockIce)))) {
          onIce = true;
        }
      }
    }
    return onIce;
  }
  
  public static boolean isOnLadder()
  {
    boolean onLadder = false;
    int y = (int)(Minecraft.player.boundingBox.minY - 1.0D);
    for (int x = MathHelper.floor_double(Minecraft.player.boundingBox.minX); x < MathHelper.floor_double(Minecraft.player.boundingBox.maxX) + 1; x++) {
      for (int z = MathHelper.floor_double(Minecraft.player.boundingBox.minZ); z < MathHelper.floor_double(Minecraft.player.boundingBox.maxZ) + 1; z++)
      {
        Block block = Minecraft.world.getBlockState(new BlockPos(x, y, z)).getBlock();
        if ((block != null) && (!(block instanceof BlockAir)))
        {
          if ((!(block instanceof BlockLadder)) && (!(block instanceof BlockLadder))) {
            return false;
          }
          onLadder = true;
        }
      }
    }
    return (onLadder) || (Minecraft.player.isOnLadder());
  }
  
  public static boolean isOnLiquid()
  {
    boolean onLiquid = false;
    int y = (int)(Minecraft.player.boundingBox.minY - 0.01D);
    for (int x = MathHelper.floor_double(Minecraft.player.boundingBox.minX); x < MathHelper.floor_double(Minecraft.player.boundingBox.maxX) + 1; x++) {
      for (int z = MathHelper.floor_double(Minecraft.player.boundingBox.minZ); z < MathHelper.floor_double(Minecraft.player.boundingBox.maxZ) + 1; z++)
      {
        Block block = Minecraft.world.getBlockState(new BlockPos(x, y, z)).getBlock();
        if ((block != null) && (!(block instanceof BlockAir)))
        {
          if (!(block instanceof BlockLiquid)) {
            return false;
          }
          onLiquid = true;
        }
      }
    }
    return onLiquid;
  }
  
  public static EnumFacing getFacing(BlockPos pos)
  {
    EnumFacing[] orderedValues = { EnumFacing.UP, EnumFacing.NORTH, EnumFacing.EAST, EnumFacing.SOUTH, EnumFacing.WEST, EnumFacing.DOWN };
    EnumFacing[] array;
    int length = (array = orderedValues).length;
    for (int i = 0; i < length; i++)
    {
      EnumFacing facing = array[i];
      Entity temp = new EntitySnowball(Minecraft.world);
      temp.posX = (pos.getX() + 0.5D);
      temp.posY = (pos.getY() + 0.5D);
      temp.posZ = (pos.getZ() + 0.5D);
      Entity entity = temp;
      entity.posX += facing.getDirectionVec().getX() * 0.5D;
      Entity entity2 = temp;
      entity2.posY += facing.getDirectionVec().getY() * 0.5D;
      Entity entity3 = temp;
      entity3.posZ += facing.getDirectionVec().getZ() * 0.5D;
      if (Minecraft.player.canEntityBeSeen(temp)) {
        return facing;
      }
    }
    return null;
  }
}
