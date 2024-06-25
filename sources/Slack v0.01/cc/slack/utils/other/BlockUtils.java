package cc.slack.utils.other;

import cc.slack.utils.client.mc;
import cc.slack.utils.player.RotationUtil;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3i;
import net.minecraft.world.World;

public class BlockUtils extends mc {
   public Block getBlock(Vec3 vec3) {
      return getBlock(new BlockPos(vec3));
   }

   public static Block getBlock(BlockPos blockPos) {
      return mc.getWorld() != null && blockPos != null ? mc.getWorld().getBlockState(blockPos).getBlock() : null;
   }

   public static Material getMaterial(BlockPos blockPos) {
      Block block = getBlock(blockPos);
      return block != null ? block.getMaterial() : null;
   }

   public static float getHardness(BlockPos blockPos) {
      return getBlock(blockPos).getPlayerRelativeBlockHardness(mc.getPlayer(), mc.getWorld(), blockPos);
   }

   public static boolean isReplaceable(BlockPos blockPos) {
      Material material = getMaterial(blockPos);
      return material != null && material.isReplaceable();
   }

   public static boolean isReplaceableNotBed(BlockPos blockPos) {
      Material material = getMaterial(blockPos);
      return material != null && material.isReplaceable() && !(getBlock(blockPos) instanceof BlockBed);
   }

   public static boolean isAir(BlockPos blockPos) {
      Material material = getMaterial(blockPos);
      return material == Material.air;
   }

   public static IBlockState getState(BlockPos blockPos) {
      World mc = Minecraft.getMinecraft().theWorld;
      return mc.getBlockState(blockPos);
   }

   public static boolean canBeClicked(BlockPos blockPos) {
      return getBlock(blockPos) != null && ((Block)Objects.requireNonNull(getBlock(blockPos))).canCollideCheck(getState(blockPos), false) && mc.getWorld().getWorldBorder().contains(blockPos);
   }

   public static String getBlockName(int id) {
      return Block.getBlockById(id).getLocalizedName();
   }

   public static boolean isFullBlock(BlockPos blockPos) {
      AxisAlignedBB axisAlignedBB = getBlock(blockPos) != null ? getBlock(blockPos).getCollisionBoundingBox(mc.getWorld(), blockPos, getState(blockPos)) : null;
      if (axisAlignedBB == null) {
         return false;
      } else {
         return axisAlignedBB.maxX - axisAlignedBB.minX == 1.0D && axisAlignedBB.maxY - axisAlignedBB.minY == 1.0D && axisAlignedBB.maxZ - axisAlignedBB.minZ == 1.0D;
      }
   }

   public static double getCenterDistance(BlockPos blockPos) {
      return mc.getPlayer().getDistance((double)blockPos.getX() + 0.5D, (double)blockPos.getY() + 0.5D, (double)blockPos.getZ() + 0.5D);
   }

   public static float[] getCenterRotation(BlockPos blockPos) {
      return RotationUtil.getRotations((double)blockPos.getX() + 0.5D, (double)blockPos.getY() + 0.5D, (double)blockPos.getZ() + 0.5D);
   }

   public static float[] getFaceRotation(EnumFacing face, BlockPos blockPos) {
      Vec3i faceVec = face.getDirectionVec();
      Vec3 blockFaceVec = new Vec3((double)faceVec.getX() * 0.5D, (double)faceVec.getY() * 0.5D, (double)faceVec.getZ() * 0.5D);
      blockFaceVec.add(blockPos.toVec3());
      blockFaceVec.addVector(0.5D, 0.5D, 0.5D);
      return RotationUtil.getRotations(blockFaceVec);
   }

   public static int getAbsoluteValue(BlockPos blockPos) {
      return blockPos.getX() + blockPos.getY() * 1000 + blockPos.getZ() * 200000;
   }

   public static EnumFacing getHorizontalFacingEnum(BlockPos blockPos) {
      double dx = mc.getPlayer().posX - ((double)blockPos.getX() + 0.5D);
      double dz = mc.getPlayer().posZ - ((double)blockPos.getZ() + 0.5D);
      if (dx > 0.0D) {
         if (dz > dx) {
            return EnumFacing.SOUTH;
         } else {
            return -dz > dx ? EnumFacing.NORTH : EnumFacing.EAST;
         }
      } else if (dz > -dx) {
         return EnumFacing.SOUTH;
      } else {
         return dz < dx ? EnumFacing.NORTH : EnumFacing.WEST;
      }
   }

   public static Map<BlockPos, Block> searchBlocks(int radius) {
      Map<BlockPos, Block> blocks = new HashMap();

      for(int x = radius; x >= -radius + 1; --x) {
         for(int y = radius; y >= -radius + 1; --y) {
            for(int z = radius; z >= -radius + 1; --z) {
               BlockPos blockPos = new BlockPos(mc.getPlayer().posX + (double)x, mc.getPlayer().posY + (double)y, mc.getPlayer().posZ + (double)z);
               Block block = getBlock(blockPos);
               if (block != null) {
                  blocks.put(blockPos, block);
               }
            }
         }
      }

      return blocks;
   }

   public static boolean collideBlock(AxisAlignedBB axisAlignedBB, Function<Block, Boolean> collide) {
      for(int x = MathHelper.floor_double(mc.getPlayer().getEntityBoundingBox().minX); x < MathHelper.floor_double(mc.getPlayer().getEntityBoundingBox().maxX) + 1; ++x) {
         for(int z = MathHelper.floor_double(mc.getPlayer().getEntityBoundingBox().minZ); z < MathHelper.floor_double(mc.getPlayer().getEntityBoundingBox().maxZ) + 1; ++z) {
            Block block = getBlock(new BlockPos((double)x, axisAlignedBB.minY, (double)z));
            if (!(Boolean)collide.apply(block)) {
               return false;
            }
         }
      }

      return true;
   }

   public static boolean collideBlockIntersects(AxisAlignedBB axisAlignedBB, Predicate<Block> collide) {
      for(int x = MathHelper.floor_double(mc.getPlayer().getEntityBoundingBox().minX); x < MathHelper.floor_double(mc.getPlayer().getEntityBoundingBox().maxX) + 1; ++x) {
         for(int z = MathHelper.floor_double(mc.getPlayer().getEntityBoundingBox().minZ); z < MathHelper.floor_double(mc.getPlayer().getEntityBoundingBox().maxZ) + 1; ++z) {
            BlockPos blockPos = new BlockPos((double)x, axisAlignedBB.minY, (double)z);
            Block block = getBlock(blockPos);
            if (collide.test(block)) {
               AxisAlignedBB boundingBox = block != null ? block.getCollisionBoundingBox(mc.getWorld(), blockPos, getState(blockPos)) : null;
               if (boundingBox != null && mc.getPlayer().getEntityBoundingBox().intersectsWith(boundingBox)) {
                  return true;
               }
            }
         }
      }

      return false;
   }

   public static Vec3 floorVec3(Vec3 vec3) {
      return new Vec3(Math.floor(vec3.xCoord), Math.floor(vec3.yCoord), Math.floor(vec3.zCoord));
   }
}
