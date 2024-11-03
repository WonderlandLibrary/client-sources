package net.augustus.modules.movement;

import java.awt.Color;
import net.augustus.events.EventTick;
import net.augustus.modules.Categorys;
import net.augustus.modules.Module;
import net.augustus.settings.BooleanValue;
import net.augustus.settings.DoubleValue;
import net.augustus.settings.StringValue;
import net.lenni0451.eventapi.reflection.EventTarget;
import net.minecraft.network.play.client.C03PacketPlayer;

public class Spider extends Module {
   public StringValue mode = new StringValue(4, "Mode", this, "Jump", new String[]{"Basic", "Jump"});
   public final DoubleValue motionToJump = new DoubleValue(3, "JumpMotion", this, -0.2, -0.42, 0.42, 2);
   public final BooleanValue customJumpMotion = new BooleanValue(5, "CustomMotion", this, false);
   public final DoubleValue motion = new DoubleValue(1, "Motion", this, 0.3, 0.0, 2.0, 2);
   public final BooleanValue onGroundPacket = new BooleanValue(2, "GroundPacket", this, false);

   public Spider() {
      super("Spider", new Color(168, 127, 50), Categorys.MOVEMENT);
   }

   @EventTarget
   public void onEventTick(EventTick eventTick) {
      this.setDisplayName(super.getName() + " §8" + this.mode.getSelected());
      if (mc.thePlayer.isCollidedHorizontally && !mc.thePlayer.isOnLadder() && !mc.thePlayer.isInWater() && !mc.thePlayer.isInLava()) {
         String var2 = this.mode.getSelected();
         switch(var2) {
            case "Basic":
               mc.thePlayer.motionY = this.motion.getValue();
               break;
            case "Jump":
               if (mc.thePlayer.onGround) {
                  mc.thePlayer.jump();
               } else if (mc.thePlayer.motionY < this.motionToJump.getValue()) {
                  if (this.customJumpMotion.getBoolean()) {
                     mc.thePlayer.motionY = this.motion.getValue();
                  } else {
                     mc.thePlayer.jump();
                  }

                  if (this.onGroundPacket.getBoolean()) {
                     mc.thePlayer.sendQueue.addToSendQueueDirect(new C03PacketPlayer(true));
                  }
               }
         }
      }
   }

   @Override
   public void onDisable() {
      super.onDisable();
      mc.getTimer().timerSpeed = 1.0F;
   }
}
