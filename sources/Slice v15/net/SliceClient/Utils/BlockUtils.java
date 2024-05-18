package net.SliceClient.Utils;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public class BlockUtils
{
  public BlockUtils() {}
  
  public static boolean isOnLiquid()
  {
    Minecraft.getMinecraft();AxisAlignedBB par1AxisAlignedBB = thePlayerboundingBox.offset(0.0D, -0.01D, 0.0D)
      .contract(0.001D, 0.001D, 0.001D);
    int var4 = MathHelper.floor_double(minX);
    int var5 = MathHelper.floor_double(maxX + 1.0D);
    int var6 = MathHelper.floor_double(minY);
    int var7 = MathHelper.floor_double(maxY + 1.0D);
    int var8 = MathHelper.floor_double(minZ);
    int var9 = MathHelper.floor_double(maxZ + 1.0D);
    Vec3 var10 = new Vec3(0.0D, 0.0D, 0.0D);
    for (int var11 = var4; var11 < var5; var11++) {
      for (int var12 = var6; var12 < var7; var12++) {
        for (int var13 = var8; var13 < var9; var13++)
        {
          BlockPos pos = new BlockPos(var11, var12, var13);
          Minecraft.getMinecraft();Block var14 = Minecraft.theWorld.getBlockState(pos).getBlock();
          if ((!(var14 instanceof net.minecraft.block.BlockAir)) && (!(var14 instanceof net.minecraft.block.BlockLiquid))) {
            return false;
          }
        }
      }
    }
    return true;
  }
  
  public static Block getBlock(double x, double y, double z)
  {
    Minecraft.getMinecraft();return Minecraft.theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
  }
  
  public static Block getBlock(BlockPos block)
  {
    Minecraft.getMinecraft();return Minecraft.theWorld.getBlockState(block).getBlock();
  }
  
  public static boolean isInLiquid()
  {
    Minecraft.getMinecraft();AxisAlignedBB par1AxisAlignedBB = thePlayerboundingBox.contract(0.001D, 0.001D, 
      0.001D);
    int var4 = MathHelper.floor_double(minX);
    int var5 = MathHelper.floor_double(maxX + 1.0D);
    int var6 = MathHelper.floor_double(minY);
    int var7 = MathHelper.floor_double(maxY + 1.0D);
    int var8 = MathHelper.floor_double(minZ);
    int var9 = MathHelper.floor_double(maxZ + 1.0D);
    Vec3 var10 = new Vec3(0.0D, 0.0D, 0.0D);
    for (int var11 = var4; var11 < var5; var11++) {
      for (int var12 = var6; var12 < var7; var12++) {
        for (int var13 = var8; var13 < var9; var13++)
        {
          BlockPos pos = new BlockPos(var11, var12, var13);
          Minecraft.getMinecraft();Block var14 = Minecraft.theWorld.getBlockState(pos).getBlock();
          if ((var14 instanceof net.minecraft.block.BlockLiquid)) {
            return true;
          }
        }
      }
    }
    return false;
  }
  
  public static boolean isInsideBlock()
  {
    Minecraft.getMinecraft();int x = MathHelper.floor_double(thePlayerboundingBox.minX);
    do {
      Minecraft.getMinecraft();int y = MathHelper.floor_double(thePlayerboundingBox.minY);
      do
      {
        Minecraft.getMinecraft();int z = MathHelper.floor_double(thePlayerboundingBox.minZ);
        
        do
        {
          Minecraft.getMinecraft();Block block = Minecraft.theWorld.getBlockState(new BlockPos(x, y, z))
            .getBlock();
          
          if ((block != null) && (!(block instanceof net.minecraft.block.BlockAir))) {
            Minecraft.getMinecraft();
            
            Minecraft.getMinecraft();
            AxisAlignedBB boundingBox;
            if ((boundingBox = block.getCollisionBoundingBox(Minecraft.theWorld, 
              new BlockPos(x, y, z), 
              Minecraft.theWorld.getBlockState(new BlockPos(x, y, z)))) != null) {
              Minecraft.getMinecraft(); if (thePlayerboundingBox.intersectsWith(boundingBox)) {
                return true;
              }
            }
          }
          z++;Minecraft.getMinecraft();
        } while (
        
          z < 
          MathHelper.floor_double(thePlayerboundingBox.maxZ) + 1);
        y++;Minecraft.getMinecraft();
      } while (y < 
        MathHelper.floor_double(thePlayerboundingBox.maxY) + 1);
      x++;Minecraft.getMinecraft();
    } while (x < 
      MathHelper.floor_double(thePlayerboundingBox.maxX) + 1);
    




















    return false;
  }
}
