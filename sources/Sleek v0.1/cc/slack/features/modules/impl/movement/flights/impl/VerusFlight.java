package cc.slack.features.modules.impl.movement.flights.impl;

import cc.slack.Slack;
import cc.slack.events.State;
import cc.slack.events.impl.network.PacketEvent;
import cc.slack.events.impl.player.MotionEvent;
import cc.slack.events.impl.player.MoveEvent;
import cc.slack.features.modules.impl.movement.Flight;
import cc.slack.features.modules.impl.movement.flights.IFlight;
import cc.slack.utils.client.mc;
import cc.slack.utils.player.MovementUtil;
import cc.slack.utils.player.TimerUtil;
import net.minecraft.network.PacketDirection;
import net.minecraft.network.play.client.C03PacketPlayer;

public class VerusFlight implements IFlight {
   double moveSpeed = 0.0D;
   int stage = 0;
   int hops = 0;
   int ticks = 0;

   public void onEnable() {
      this.stage = -1;
      this.hops = 0;
      this.ticks = 0;
   }

   public void onMove(MoveEvent event) {
      switch(this.stage) {
      case -1:
         ++this.stage;
         break;
      case 0:
         event.setZeroXZ();
         if (this.hops >= 4 && mc.getPlayer().onGround) {
            ++this.stage;
            return;
         }

         if (mc.getPlayer().onGround) {
            event.setY(0.41999998688697815D);
            this.ticks = 0;
            ++this.hops;
         } else {
            ++this.ticks;
         }
         break;
      case 1:
         TimerUtil.reset();
         event.setZeroXZ();
         if (mc.getPlayer().hurtTime > 0) {
            this.ticks = 0;
            this.moveSpeed = 0.525D;
            ++this.stage;
            event.setY(0.41999998688697815D);
            MovementUtil.setSpeed(event, this.moveSpeed);
         }
         break;
      case 2:
         if (event.getY() < 0.0D) {
            event.setY(-0.033D);
         }

         if (this.ticks == 0) {
            this.moveSpeed *= 7.0D;
         }

         this.moveSpeed -= this.moveSpeed / 159.0D;
         ++this.ticks;
         MovementUtil.setSpeed(event, this.moveSpeed);
         if (mc.getPlayer().hurtTime == 0 && (mc.getPlayer().onGround || mc.getPlayer().isCollidedHorizontally)) {
            ((Flight)Slack.getInstance().getModuleManager().getInstance(Flight.class)).toggle();
         }
      }

   }

   public void onMotion(MotionEvent event) {
      if (event.getState() == State.PRE) {
         event.setYaw(MovementUtil.getDirection());
      }
   }

   public void onPacket(PacketEvent event) {
      if (event.getDirection() == PacketDirection.OUTGOING) {
         if (event.getPacket() instanceof C03PacketPlayer && this.stage == 0 && this.hops >= 1) {
            ((C03PacketPlayer)event.getPacket()).onGround = false;
         }

      }
   }

   public String toString() {
      return "Verus";
   }
}
