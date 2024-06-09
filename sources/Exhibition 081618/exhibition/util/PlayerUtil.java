package exhibition.util;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;

public class PlayerUtil implements MinecraftUtil {
   public static boolean isInLiquid() {
      if (mc.thePlayer == null) {
         return false;
      } else {
         for(int x = MathHelper.floor_double(mc.thePlayer.boundingBox.minX); x < MathHelper.floor_double(mc.thePlayer.boundingBox.maxX) + 1; ++x) {
            for(int z = MathHelper.floor_double(mc.thePlayer.boundingBox.minZ); z < MathHelper.floor_double(mc.thePlayer.boundingBox.maxZ) + 1; ++z) {
               BlockPos pos = new BlockPos(x, (int)mc.thePlayer.boundingBox.minY, z);
               Block block = mc.theWorld.getBlockState(pos).getBlock();
               if (block != null && !(block instanceof BlockAir)) {
                  return block instanceof BlockLiquid;
               }
            }
         }

         return false;
      }
   }

   public static boolean isOnLiquid() {
      AxisAlignedBB boundingBox = mc.thePlayer.getEntityBoundingBox();
      if (boundingBox == null) {
         return false;
      } else {
         boundingBox = boundingBox.contract(0.01D, 0.0D, 0.01D).offset(0.0D, -0.01D, 0.0D);
         boolean onLiquid = false;
         int y = (int)boundingBox.minY;

         for(int x = MathHelper.floor_double(boundingBox.minX); x < MathHelper.floor_double(boundingBox.maxX + 1.0D); ++x) {
            for(int z = MathHelper.floor_double(boundingBox.minZ); z < MathHelper.floor_double(boundingBox.maxZ + 1.0D); ++z) {
               Block block = mc.theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
               if (block != Blocks.air) {
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

   public static void blinkToPos(double[] startPos, BlockPos endPos, double slack, double[] pOffset) {
      double curX = startPos[0];
      double curY = startPos[1];
      double curZ = startPos[2];

      try {
         double endX = (double)endPos.getX() + 0.5D;
         double endY = (double)endPos.getY() + 1.0D;
         double endZ = (double)endPos.getZ() + 0.5D;
         double distance = Math.abs(curX - endX) + Math.abs(curY - endY) + Math.abs(curZ - endZ);

         for(int count = 0; distance > slack; ++count) {
            distance = Math.abs(curX - endX) + Math.abs(curY - endY) + Math.abs(curZ - endZ);
            if (count > 120) {
               break;
            }

            boolean next = false;
            double diffX = curX - endX;
            double diffY = curY - endY;
            double diffZ = curZ - endZ;
            double offset = (count & 1) == 0 ? pOffset[0] : pOffset[1];
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

            Minecraft.getMinecraft().getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(curX, curY, curZ, true));
         }
      } catch (Exception var29) {
         ;
      }

   }

   public static boolean isMoving() {
      return mc.gameSettings.keyBindForward.isPressed() || mc.gameSettings.keyBindBack.isPressed() || mc.gameSettings.keyBindLeft.isPressed() || mc.gameSettings.keyBindRight.isPressed() || mc.thePlayer.movementInput.moveForward != 0.0F || mc.thePlayer.movementInput.moveStrafe != 0.0F;
   }
}
