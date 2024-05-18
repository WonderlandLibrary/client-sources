package rina.turok.bope.bopemod.hacks.combat;

import java.util.function.Predicate;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.network.play.server.SPacketExplosion;
import rina.turok.bope.bopemod.BopeModule;
import rina.turok.bope.bopemod.events.BopeEventEntity;
import rina.turok.bope.bopemod.events.BopeEventPacket;
import rina.turok.bope.bopemod.hacks.BopeCategory;
import rina.turok.bope.external.BopeEventCancellable;

public class BopeVelocity extends BopeModule {
   @EventHandler
   private Listener<BopeEventPacket.ReceivePacket> damage = new Listener<>(event -> {
      if (event.get_era() == BopeEventCancellable.Era.EVENT_PRE) {
         if (event.get_packet() instanceof SPacketEntityVelocity) {
            SPacketEntityVelocity knockbackx = (SPacketEntityVelocity)event.get_packet();
            if (knockbackx.getEntityID() == this.mc.player.entityId) {
               event.cancel();
               knockbackx.motionX = (int)((float)knockbackx.motionX * 0.0F);
               knockbackx.motionY = (int)((float)knockbackx.motionY * 0.0F);
               knockbackx.motionZ = (int)((float)knockbackx.motionZ * 0.0F);
            }
         } else if (event.get_packet() instanceof SPacketExplosion) {
            event.cancel();
            SPacketExplosion knockback = (SPacketExplosion)event.get_packet();
            knockback.motionX *= 0.0F;
            knockback.motionY *= 0.0F;
            knockback.motionZ *= 0.0F;
         }
      }

   });
   @EventHandler
   private Listener<BopeEventEntity> explosion = new Listener<>(event -> {
      if (event.get_entity() == this.mc.player) {
         event.cancel();
      }
   });

   public BopeVelocity() {
      super(BopeCategory.BOPE_COMBAT, false);
      this.name = "Velocity";
      this.tag = "Velocity";
      this.description = "To change velocity damage, you can do a ant-kk placing in w0 h0.";
      this.release("B.O.P.E - Module - B.O.P.E");
   }

}
