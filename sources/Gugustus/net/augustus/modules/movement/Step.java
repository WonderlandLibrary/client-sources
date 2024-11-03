package net.augustus.modules.movement;

import java.awt.Color;
import net.augustus.events.EventPostStep;
import net.augustus.events.EventPreStep;
import net.augustus.events.EventUpdate;
import net.augustus.modules.Categorys;
import net.augustus.modules.Module;
import net.augustus.settings.DoubleValue;
import net.augustus.settings.StringValue;
import net.augustus.utils.EdgeCosts;
import net.augustus.utils.MoveUtil;
import net.augustus.utils.TimeHelper;
import net.lenni0451.eventapi.reflection.EventTarget;
import net.minecraft.block.Block;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;

public class Step extends Module {
   public final DoubleValue height = new DoubleValue(2, "Height", this, 1.0, 1.0, 5.0, 2);
   public final DoubleValue delay = new DoubleValue(3, "Delay", this, 400.0, 0.0, 1000.0, 0);
   public final DoubleValue timer = new DoubleValue(4, "Timer", this, 0.3, 0.05, 1.0, 2);
   private final TimeHelper timeHelper = new TimeHelper();
   public StringValue mode = new StringValue(1, "Modes", this, "Vanilla", new String[]{"Vanilla", "NCPPacket", "NCPMotion", "Lowhop"});
   private boolean shouldStep;
   private boolean shouldTimerReset;

   public Step() {
      super("Step", new Color(129, 147, 164), Categorys.MOVEMENT);
   }

   @Override
   public void onDisable() {
      super.onDisable();
      mc.thePlayer.stepHeight = 0.6F;
   }
   @EventTarget
   public void onUpdate(EventUpdate event) {
      String var3 = this.mode.getSelected();
      switch(var3) {
         case "NCPMotion": {
            if (mc.thePlayer.isCollidedHorizontally) {
               if (mc.thePlayer.isCollidedVertically) {
                  mc.thePlayer.motionY = 0.37;
               }
               MoveUtil.setSpeed(0.28F);
            }
            break;
         }
         case "Lowhop": {
            if (mc.thePlayer.isCollidedHorizontally) {
               if (mc.thePlayer.isCollidedVertically) {
                  mc.thePlayer.motionY = 0.37;
               }
            }
            break;
         }
      }
   }

   @EventTarget
   public void onEventPreStep(EventPreStep eventPreStep) {
      if (this.shouldTimerReset) {
         mc.getTimer().timerSpeed = 1.0F;
         this.shouldTimerReset = false;
      }

      String var2 = this.mode.getSelected();
      switch(var2) {
         case "Vanilla":
         case "NCPPacket":
            eventPreStep.setStepHeight((float)this.height.getValue());
            if (!this.timeHelper.reached((long)this.delay.getValue())) {
               eventPreStep.setStepHeight(0.6F);
               this.shouldStep = false;
            } else {
               this.shouldStep = true;
               this.timeHelper.reset();
            }
      }
   }

   @EventTarget
   public void onEventPostStep(EventPostStep eventPostStep) {
      Vec3 stepVec = new Vec3(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);
      String var3 = this.mode.getSelected();
      switch(var3) {
         case "Vanilla":
            if (mc.thePlayer.getEntityBoundingBox().minY - stepVec.yCoord > 0.6 && this.shouldStep && this.timer.getValue() != 1.0) {
               mc.getTimer().timerSpeed = (float)this.timer.getValue();
               this.shouldTimerReset = true;
            }
            break;
         case "NCPPacket":
            if (mc.thePlayer.getEntityBoundingBox().minY - stepVec.yCoord > 0.6 && this.shouldStep) {
               double height = mc.thePlayer.getEntityBoundingBox().minY - stepVec.yCoord;
               int counter = 2;
               if (height == 1.0) {
                  mc.thePlayer
                     .sendQueue
                     .addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(stepVec.xCoord, stepVec.yCoord + 0.42, stepVec.zCoord, false));
                  mc.thePlayer
                     .sendQueue
                     .addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(stepVec.xCoord, stepVec.yCoord + 0.75, stepVec.zCoord, false));
                  counter = 3;
               } else if (height < 0.65) {
                  mc.thePlayer
                     .sendQueue
                     .addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(stepVec.xCoord, stepVec.yCoord + 0.35, stepVec.zCoord, false));
               } else if (height < 0.76) {
                  mc.thePlayer
                     .sendQueue
                     .addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(stepVec.xCoord, stepVec.yCoord + 0.42, stepVec.zCoord, false));
               } else if (height < 0.878) {
                  mc.thePlayer
                     .sendQueue
                     .addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(stepVec.xCoord, stepVec.yCoord + 0.42, stepVec.zCoord, false));
                  mc.thePlayer
                     .sendQueue
                     .addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(stepVec.xCoord, stepVec.yCoord + 0.73, stepVec.zCoord, false));
                  counter = 3;
               } else if (height < 1.26) {
                  mc.thePlayer
                     .sendQueue
                     .addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(stepVec.xCoord, stepVec.yCoord + 0.425, stepVec.zCoord, false));
                  mc.thePlayer
                     .sendQueue
                     .addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(stepVec.xCoord, stepVec.yCoord + 0.779, stepVec.zCoord, false));
                  mc.thePlayer
                     .sendQueue
                     .addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(stepVec.xCoord, stepVec.yCoord + 1.06, stepVec.zCoord, false));
                  counter = 4;
               } else if (height < 1.6) {
                  mc.thePlayer
                     .sendQueue
                     .addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(stepVec.xCoord, stepVec.yCoord + 0.425, stepVec.zCoord, false));
                  mc.thePlayer
                     .sendQueue
                     .addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(stepVec.xCoord, stepVec.yCoord + 0.779, stepVec.zCoord, false));
                  mc.thePlayer
                     .sendQueue
                     .addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(stepVec.xCoord, stepVec.yCoord + 1.0538, stepVec.zCoord, false));
                  mc.thePlayer
                     .sendQueue
                     .addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(stepVec.xCoord, stepVec.yCoord + 1.265, stepVec.zCoord, false));
                  mc.thePlayer
                     .sendQueue
                     .addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(stepVec.xCoord, stepVec.yCoord + 1.3, stepVec.zCoord, false));
                  mc.thePlayer
                     .sendQueue
                     .addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(stepVec.xCoord, stepVec.yCoord + 1.25, stepVec.zCoord, false));
                  counter = 7;
               } else if (height < 2.1) {
                  mc.thePlayer
                     .sendQueue
                     .addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(stepVec.xCoord, stepVec.yCoord + 0.425, stepVec.zCoord, false));
                  mc.thePlayer
                     .sendQueue
                     .addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(stepVec.xCoord, stepVec.yCoord + 0.82, stepVec.zCoord, false));
                  mc.thePlayer
                     .sendQueue
                     .addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(stepVec.xCoord, stepVec.yCoord + 0.7, stepVec.zCoord, false));
                  mc.thePlayer
                     .sendQueue
                     .addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(stepVec.xCoord, stepVec.yCoord + 0.595, stepVec.zCoord, false));
                  mc.thePlayer
                     .sendQueue
                     .addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(stepVec.xCoord, stepVec.yCoord + 1.01, stepVec.zCoord, false));
                  mc.thePlayer
                     .sendQueue
                     .addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(stepVec.xCoord, stepVec.yCoord + 1.37, stepVec.zCoord, false));
                  mc.thePlayer
                     .sendQueue
                     .addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(stepVec.xCoord, stepVec.yCoord + 1.657, stepVec.zCoord, false));
                  mc.thePlayer
                     .sendQueue
                     .addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(stepVec.xCoord, stepVec.yCoord + 1.87, stepVec.zCoord, false));
                  counter = 10;
               }

               if (this.timer.getValue() != 1.0) {
                  mc.getTimer().timerSpeed = (float)Math.max(height == 1.0 ? this.timer.getValue() : this.timer.getValue() * 3.43333 / (double)counter, 0.1);
                  this.shouldTimerReset = true;
               }
            }
      }
   }

   private boolean shouldStep() {
      if (!mc.thePlayer.isCollidedHorizontally) {
         return false;
      } else {
         BlockPos playerBlock = null;

         for(int i = 0; i < 4; ++i) {
            if (EdgeCosts.hasBlockCollision(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - (double)i, mc.thePlayer.posZ))) {
               playerBlock = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - (double)i + 1.0, mc.thePlayer.posZ);
               break;
            }
         }

         float direction = (float)Math.toDegrees(MoveUtil.direction());
         EnumFacing enumFacing = mc.thePlayer.getHorizontalFacing(direction);
         BlockPos startBlock = playerBlock.offset(enumFacing);
         double height = 0.0;

         for(int i = 0; (double)i <= this.height.getValue() + 3.0; ++i) {
            if (EdgeCosts.hasBlockCollision(startBlock)) {
               Block block = mc.theWorld.getBlockState(startBlock).getBlock();
               height += block.getBlockBoundsMaxY();
               if (height > this.height.getValue()) {
                  return false;
               }

               if (i > 0 && !EdgeCosts.hasBlockCollision(startBlock.down())) {
                  return false;
               }

               startBlock = startBlock.up();
            } else {
               if (!EdgeCosts.hasBlockCollision(startBlock.down()) && !EdgeCosts.hasBlockCollision(startBlock)) {
                  return mc.thePlayer.posY < (double)playerBlock.offset(enumFacing).getY() + this.height.getValue();
               }

               ++height;
               startBlock = startBlock.up();
            }
         }

         return true;
      }
   }
}
