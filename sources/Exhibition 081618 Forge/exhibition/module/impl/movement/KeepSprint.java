package exhibition.module.impl.movement;

import exhibition.event.Event;
import exhibition.event.RegisterEvent;
import exhibition.event.impl.EventPacket;
import exhibition.management.notifications.dev.DevNotifications;
import exhibition.module.Module;
import exhibition.module.data.ModuleData;
import net.minecraft.network.play.server.S06PacketUpdateHealth;

public class KeepSprint extends Module {
   public KeepSprint(ModuleData data) {
      super(data);
   }

   @RegisterEvent(
      events = {EventPacket.class}
   )
   public void onEvent(Event event) {
      EventPacket e = (EventPacket)event;

      try {
         if (e.isIncoming() && e.getPacket() instanceof S06PacketUpdateHealth) {
            S06PacketUpdateHealth packet = (S06PacketUpdateHealth)e.getPacket();
            DevNotifications.getManager().post("Stats Updated.");
         }
      } catch (ClassCastException var4) {
         DevNotifications.getManager().post("KeepSprint ClassCastException");
      }

   }
}
