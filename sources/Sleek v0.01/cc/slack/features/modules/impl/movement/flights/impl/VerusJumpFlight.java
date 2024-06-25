package cc.slack.features.modules.impl.movement.flights.impl;

import cc.slack.events.impl.network.PacketEvent;
import cc.slack.events.impl.player.CollideEvent;
import cc.slack.events.impl.player.MotionEvent;
import cc.slack.events.impl.player.UpdateEvent;
import cc.slack.features.modules.impl.movement.flights.IFlight;
import cc.slack.utils.client.mc;
import net.minecraft.block.BlockAir;
import net.minecraft.util.AxisAlignedBB;

public class VerusJumpFlight implements IFlight {
   double startY;

   public void onEnable() {
      this.startY = Math.floor(mc.getPlayer().posY);
   }

   public void onPacket(PacketEvent event) {
   }

   public void onUpdate(UpdateEvent event) {
      if (mc.getPlayer().onGround) {
         mc.getPlayer().jump();
      }

      mc.getGameSettings().keyBindJump.pressed = false;
   }

   public void onCollide(CollideEvent event) {
      if (event.getBlock() instanceof BlockAir && event.getY() <= this.startY) {
         event.setBoundingBox(AxisAlignedBB.fromBounds(event.getX(), event.getY(), event.getZ(), event.getX() + 1.0D, this.startY, event.getZ() + 1.0D));
      }

   }

   public void onDisable() {
   }

   public void onMotion(MotionEvent event) {
   }

   public String toString() {
      return "Verus Jump";
   }
}
