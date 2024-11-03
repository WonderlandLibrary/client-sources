package vestige.util.world;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockLiquid;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import vestige.util.IMinecraft;
import vestige.util.player.MovementUtil;

public class WorldUtil implements IMinecraft {
   public static BlockInfo getBlockUnder(double y, int maxRange) {
      return getBlockInfo(mc.thePlayer.posX, y - 1.0D, mc.thePlayer.posZ, maxRange);
   }

   public static BlockInfo getBlockInfo(double x, double y, double z, int maxRange) {
      BlockPos pos = new BlockPos(x, y, z);
      EnumFacing playerDirectionFacing = getHorizontalFacing(MovementUtil.getPlayerDirection()).getOpposite();
      ArrayList<EnumFacing> facingValues = new ArrayList();
      facingValues.add(playerDirectionFacing);
      EnumFacing[] var10 = EnumFacing.values();
      int i = var10.length;

      for(int var12 = 0; var12 < i; ++var12) {
         EnumFacing facing = var10[var12];
         if (facing != playerDirectionFacing && facing != EnumFacing.UP) {
            facingValues.add(facing);
         }
      }

      CopyOnWriteArrayList<BlockPos> aaa = new CopyOnWriteArrayList();
      aaa.add(pos);

      for(i = 0; i < maxRange; ++i) {
         ArrayList<BlockPos> ccc = new ArrayList(aaa);
         BlockPos bbbb;
         Iterator var20;
         if (!aaa.isEmpty()) {
            var20 = aaa.iterator();

            while(var20.hasNext()) {
               bbbb = (BlockPos)var20.next();
               Iterator var15 = facingValues.iterator();

               while(var15.hasNext()) {
                  EnumFacing facing = (EnumFacing)var15.next();
                  BlockPos n = bbbb.offset(facing);
                  if (!isAirOrLiquid(n)) {
                     return new BlockInfo(n, facing.getOpposite());
                  }

                  aaa.add(n);
               }
            }
         }

         var20 = ccc.iterator();

         while(var20.hasNext()) {
            bbbb = (BlockPos)var20.next();
            aaa.remove(bbbb);
         }

         ccc.clear();
      }

      return null;
   }

   public static Vec3 getVec3(BlockPos pos, EnumFacing facing, boolean randomised) {
      Vec3 vec3 = new Vec3(pos);
      double amount1 = 0.5D;
      double amount2 = 0.5D;
      if (randomised) {
         amount1 = 0.45D + Math.random() * 0.1D;
         amount2 = 0.45D + Math.random() * 0.1D;
      }

      if (facing == EnumFacing.UP) {
         vec3 = vec3.addVector(amount1, 1.0D, amount2);
      } else if (facing == EnumFacing.DOWN) {
         vec3 = vec3.addVector(amount1, 0.0D, amount2);
      } else if (facing == EnumFacing.EAST) {
         vec3 = vec3.addVector(1.0D, amount1, amount2);
      } else if (facing == EnumFacing.WEST) {
         vec3 = vec3.addVector(0.0D, amount1, amount2);
      } else if (facing == EnumFacing.NORTH) {
         vec3 = vec3.addVector(amount1, amount2, 0.0D);
      } else if (facing == EnumFacing.SOUTH) {
         vec3 = vec3.addVector(amount1, amount2, 1.0D);
      }

      return vec3;
   }

   public static EnumFacing getHorizontalFacing(float yaw) {
      return EnumFacing.getHorizontal(MathHelper.floor_double((double)(yaw * 4.0F / 360.0F) + 0.5D) & 3);
   }

   public static boolean isAir(BlockPos pos) {
      Block block = mc.theWorld.getBlockState(pos).getBlock();
      return block instanceof BlockAir;
   }

   public static boolean isAirOrLiquid(BlockPos pos) {
      Block block = mc.theWorld.getBlockState(pos).getBlock();
      return block instanceof BlockAir || block instanceof BlockLiquid;
   }

   public static MovingObjectPosition raytrace(float yaw, float pitch) {
      float partialTicks = mc.timer.renderPartialTicks;
      float blockReachDistance = mc.playerController.getBlockReachDistance();
      Vec3 vec3 = mc.thePlayer.getPositionEyes(partialTicks);
      Vec3 vec31 = mc.thePlayer.getVectorForRotation(pitch, yaw);
      Vec3 vec32 = vec3.addVector(vec31.xCoord * (double)blockReachDistance, vec31.yCoord * (double)blockReachDistance, vec31.zCoord * (double)blockReachDistance);
      return mc.theWorld.rayTraceBlocks(vec3, vec32, false, false, true);
   }

   public static MovingObjectPosition raytraceLegit(float yaw, float pitch, float lastYaw, float lastPitch) {
      float partialTicks = mc.timer.renderPartialTicks;
      float blockReachDistance = mc.playerController.getBlockReachDistance();
      Vec3 vec3 = mc.thePlayer.getPositionEyes(partialTicks);
      float f = lastPitch + (pitch - lastPitch) * partialTicks;
      float f1 = lastYaw + (yaw - lastYaw) * partialTicks;
      Vec3 vec31 = mc.thePlayer.getVectorForRotation(f, f1);
      Vec3 vec32 = vec3.addVector(vec31.xCoord * (double)blockReachDistance, vec31.yCoord * (double)blockReachDistance, vec31.zCoord * (double)blockReachDistance);
      return mc.theWorld.rayTraceBlocks(vec3, vec32, false, false, true);
   }

   public static boolean isBlockUnder() {
      for(int y = (int)mc.thePlayer.posY; y >= 0; --y) {
         if (!(mc.theWorld.getBlockState(new BlockPos(mc.thePlayer.posX, (double)y, mc.thePlayer.posZ)).getBlock() instanceof BlockAir)) {
            return true;
         }
      }

      return false;
   }

   public static boolean isBlockUnder(int distance) {
      for(int y = (int)mc.thePlayer.posY; y >= (int)mc.thePlayer.posY - distance; --y) {
         if (!(mc.theWorld.getBlockState(new BlockPos(mc.thePlayer.posX, (double)y, mc.thePlayer.posZ)).getBlock() instanceof BlockAir)) {
            return true;
         }
      }

      return false;
   }

   public static boolean negativeExpand(double negativeExpandValue) {
      return mc.theWorld.getBlockState(new BlockPos(mc.thePlayer.posX + negativeExpandValue, mc.thePlayer.posY - 1.0D, mc.thePlayer.posZ + negativeExpandValue)).getBlock() instanceof BlockAir && mc.theWorld.getBlockState(new BlockPos(mc.thePlayer.posX - negativeExpandValue, mc.thePlayer.posY - 1.0D, mc.thePlayer.posZ - negativeExpandValue)).getBlock() instanceof BlockAir && mc.theWorld.getBlockState(new BlockPos(mc.thePlayer.posX - negativeExpandValue, mc.thePlayer.posY - 1.0D, mc.thePlayer.posZ)).getBlock() instanceof BlockAir && mc.theWorld.getBlockState(new BlockPos(mc.thePlayer.posX + negativeExpandValue, mc.thePlayer.posY - 1.0D, mc.thePlayer.posZ)).getBlock() instanceof BlockAir && mc.theWorld.getBlockState(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1.0D, mc.thePlayer.posZ + negativeExpandValue)).getBlock() instanceof BlockAir && mc.theWorld.getBlockState(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1.0D, mc.thePlayer.posZ - negativeExpandValue)).getBlock() instanceof BlockAir;
   }
}
