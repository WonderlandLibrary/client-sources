package net.augustus.modules.movement;

import java.awt.Color;

import net.augustus.events.EventBlockBoundingBox;
import net.augustus.events.EventLadder;
import net.augustus.events.EventUpdate;
import net.augustus.modules.Categorys;
import net.augustus.modules.Module;
import net.augustus.settings.BooleanValue;
import net.augustus.settings.DoubleValue;
import net.augustus.settings.StringValue;
import net.augustus.utils.BlockUtil;
import net.lenni0451.eventapi.reflection.EventTarget;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;

public class FastLadder extends Module {
   public StringValue mode = new StringValue(215, "Mode", this, "Vulcan", new String[] {"Vulcan", "Vanilla"});
   public DoubleValue motionY = new DoubleValue(1, "MotionY", this, 0.4, 0.2, 2.0, 2);
   public BooleanValue stopOnEnd = new BooleanValue(2, "StopOnEnd", this, true);
   private boolean wasOnLadder;

   public FastLadder() {
      super("FastLadder", new Color(166, 168, 50), Categorys.MOVEMENT);
   }

   @EventTarget
   public void onEventLadder(EventLadder eventLadder) {
      switch (mode.getSelected()) {
         case "Vanilla": {
            eventLadder.setMotionYSpeed(this.motionY.getValue());
            break;
         }
      }
   }

   @EventTarget
   public void onCollide(EventBlockBoundingBox e) {
      switch (mode.getSelected()) {
         case "Vulcan": {
            if(e.getBlock().getUnlocalizedName().equals(Blocks.ladder.getUnlocalizedName())) {
               BlockPos blockPos = e.getBlockPos();
               e.setAxisAlignedBB(new AxisAlignedBB(blockPos.getX(), blockPos.getY(), blockPos.getZ(), (blockPos.getX() + 1), (blockPos.getY() + 1), (blockPos.getZ() + 1)));
            }
            break;
         }
      }
   }

   @EventTarget
   public void onEventUpdate(EventUpdate eventUpdate) {
      switch (mode.getSelected()) {
         case "Vanilla": {
            if (this.stopOnEnd.getBoolean() && !mc.thePlayer.isOnLadder() && this.wasOnLadder) {
               mc.thePlayer.motionY = 0.2F;
            }
            this.wasOnLadder = mc.thePlayer.isOnLadder();
            break;
         }
         case "Vulcan": {
            if(BlockUtil.isPlayerNextToBlock(mc.thePlayer, Blocks.ladder)) {
               if(mc.thePlayer.isCollidedHorizontally) {
                  mc.thePlayer.motionY = motionY.getValue();
                  this.wasOnLadder = true;
               } else {
                  if (this.stopOnEnd.getBoolean() && this.wasOnLadder) {
                     mc.thePlayer.motionY = 0.2F;
                  }
                  this.wasOnLadder = false;
               }
            } else {
               this.wasOnLadder = false;
            }
            break;
         }
      }
   }
}
