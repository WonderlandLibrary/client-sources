package exhibition.util.misc;

import exhibition.util.MinecraftUtil;
import exhibition.util.NetUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;

public class BlockUtil implements MinecraftUtil {
   public static float[] getRotationsNeeded(BlockPos pos) {
      double diffX = (double)pos.getX() + 0.5D - mc.thePlayer.posX;
      double diffY = (double)pos.getY() + 0.5D - (mc.thePlayer.posY + (double)mc.thePlayer.height);
      double diffZ = (double)pos.getZ() + 0.5D - mc.thePlayer.posZ;
      double dist = (double)MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
      float yaw = (float)(Math.atan2(diffZ, diffX) * 180.0D / 3.141592653589793D) - 90.0F;
      float pitch = (float)(-(Math.atan2(diffY, dist) * 180.0D / 3.141592653589793D));
      return new float[]{mc.thePlayer.rotationYaw + MathHelper.wrapAngleTo180_float(yaw - mc.thePlayer.rotationYaw), mc.thePlayer.rotationPitch + MathHelper.wrapAngleTo180_float(pitch - mc.thePlayer.rotationPitch)};
   }

   public static float[] updateDirections(BlockPos pos) {
      float[] looks = getRotationsNeeded(pos);
      if (mc.thePlayer.isCollidedVertically) {
         NetUtil.sendPacketNoEvents(new C03PacketPlayer.C05PacketPlayerLook(looks[0], looks[1], mc.thePlayer.onGround));
      }

      return looks;
   }

   public static void updateTool(BlockPos pos) {
      Block block = mc.theWorld.getBlockState(pos).getBlock();
      float strength = 1.0F;
      int bestItemIndex = -1;

      for(int i = 0; i < 9; ++i) {
         ItemStack itemStack = mc.thePlayer.inventory.mainInventory[i];
         if (itemStack != null && itemStack.getStrVsBlock(block) > strength) {
            strength = itemStack.getStrVsBlock(block);
            bestItemIndex = i;
         }
      }

      if (bestItemIndex != -1) {
         mc.thePlayer.inventory.currentItem = bestItemIndex;
      }

   }

   public static boolean isInLiquid() {
      if (mc.thePlayer.isInWater()) {
         return true;
      } else {
         boolean inLiquid = false;
         int y = (int)mc.thePlayer.getEntityBoundingBox().minY;

         for(int x = MathHelper.floor_double(mc.thePlayer.getEntityBoundingBox().minX); x < MathHelper.floor_double(mc.thePlayer.getEntityBoundingBox().maxX) + 1; ++x) {
            for(int z = MathHelper.floor_double(mc.thePlayer.getEntityBoundingBox().minZ); z < MathHelper.floor_double(mc.thePlayer.getEntityBoundingBox().maxZ) + 1; ++z) {
               Block block = mc.theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
               if (block != null && block.getMaterial() != Material.air) {
                  if (!(block instanceof BlockLiquid)) {
                     return false;
                  }

                  inLiquid = true;
               }
            }
         }

         return inLiquid;
      }
   }

   public static boolean isOnLiquid() {
      if (mc.thePlayer == null) {
         return false;
      } else {
         boolean onLiquid = false;
         int y = (int)mc.thePlayer.getEntityBoundingBox().offset(0.0D, -0.01D, 0.0D).minY;

         for(int x = MathHelper.floor_double(mc.thePlayer.getEntityBoundingBox().minX); x < MathHelper.floor_double(mc.thePlayer.getEntityBoundingBox().maxX) + 1; ++x) {
            for(int z = MathHelper.floor_double(mc.thePlayer.getEntityBoundingBox().minZ); z < MathHelper.floor_double(mc.thePlayer.getEntityBoundingBox().maxZ) + 1; ++z) {
               Block block = mc.theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
               if (block != null && block.getMaterial() != Material.air) {
                  if (!(block instanceof BlockLiquid)) {
                     return false;
                  }

                  onLiquid = true;
               }
            }
         }

         return onLiquid;
      }
   }
}
