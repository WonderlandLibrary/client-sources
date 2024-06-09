package exhibition.module.impl.movement;

import exhibition.event.Event;
import exhibition.event.RegisterEvent;
import exhibition.event.impl.EventMotionUpdate;
import exhibition.module.Module;
import exhibition.module.data.ModuleData;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public class BorderHop extends Module {
   private boolean wasWater = false;
   private int ticks = 0;

   public BorderHop(ModuleData data) {
      super(data);
   }

   @RegisterEvent(
      events = {EventMotionUpdate.class}
   )
   public void onEvent(Event event) {
      EventMotionUpdate em = (EventMotionUpdate)event;
      if (em.isPre()) {
         if (mc.thePlayer.onGround || mc.thePlayer.isOnLadder()) {
            this.wasWater = false;
         }

         if (mc.thePlayer.motionY > 0.0D && this.wasWater) {
            if (mc.thePlayer.motionY <= 0.1127D) {
               mc.thePlayer.motionY *= 1.267D;
            }

            mc.thePlayer.motionY += 0.05172D;
         }

         if (isInLiquid() && !mc.thePlayer.isSneaking()) {
            if (this.ticks < 3) {
               mc.thePlayer.motionY = 0.13D;
               ++this.ticks;
               this.wasWater = false;
            } else {
               mc.thePlayer.motionY = 0.5D;
               this.ticks = 0;
               this.wasWater = true;
            }
         }
      }

   }

   public static boolean isInLiquid() {
      AxisAlignedBB par1AxisAlignedBB = mc.thePlayer.boundingBox.contract(0.001D, 0.001D, 0.001D);
      int var4 = MathHelper.floor_double(par1AxisAlignedBB.minX);
      int var5 = MathHelper.floor_double(par1AxisAlignedBB.maxX + 1.0D);
      int var6 = MathHelper.floor_double(par1AxisAlignedBB.minY);
      int var7 = MathHelper.floor_double(par1AxisAlignedBB.maxY + 1.0D);
      int var8 = MathHelper.floor_double(par1AxisAlignedBB.minZ);
      int var9 = MathHelper.floor_double(par1AxisAlignedBB.maxZ + 1.0D);
      new Vec3(0.0D, 0.0D, 0.0D);

      for(int var12 = var4; var12 < var5; ++var12) {
         for(int var13 = var6; var13 < var7; ++var13) {
            for(int var14 = var8; var14 < var9; ++var14) {
               Block var15 = mc.theWorld.getBlockState(new BlockPos(var12, var13, var14)).getBlock();
               if (var15 instanceof BlockLiquid) {
                  return true;
               }
            }
         }
      }

      return false;
   }
}
