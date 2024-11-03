package net.augustus.modules.player;

import java.awt.Color;
import net.augustus.events.EventTick;
import net.augustus.modules.Categorys;
import net.augustus.modules.Module;
import net.augustus.settings.BooleanValue;
import net.augustus.settings.DoubleValue;
import net.lenni0451.eventapi.reflection.EventTarget;
import net.minecraft.network.play.client.C03PacketPlayer;

public class Regen extends Module {
   public final DoubleValue health = new DoubleValue(2, "Health", this, 20.0, 0.0, 20.0, 0);
   public final DoubleValue hunger = new DoubleValue(1, "MinHunger", this, 5.0, 0.0, 20.0, 0);
   public final DoubleValue packets = new DoubleValue(3, "Packets", this, 100.0, 0.0, 200.0, 0);
   public final BooleanValue groundCheck = new BooleanValue(4, "GroundCheck", this, false);

   public Regen() {
      super("Regen", new Color(224, 111, 49), Categorys.PLAYER);
   }

   @EventTarget
   public void onEventTick(EventTick eventTick) {
      if ((double)mc.thePlayer.getFoodStats().getFoodLevel() > this.hunger.getValue() && (double)mc.thePlayer.getHealth() < this.health.getValue()) {
         for(int i = 0; (double)i < this.packets.getValue(); ++i) {
            if (this.groundCheck.getBoolean()) {
               mc.thePlayer.sendQueue.addToSendQueueDirect(new C03PacketPlayer(mc.thePlayer.onGround));
            } else {
               mc.thePlayer.sendQueue.addToSendQueueDirect(new C03PacketPlayer(true));
            }
         }
      }
   }
}
