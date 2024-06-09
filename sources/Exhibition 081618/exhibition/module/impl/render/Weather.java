package exhibition.module.impl.render;

import exhibition.event.Event;
import exhibition.event.RegisterEvent;
import exhibition.event.impl.EventPacket;
import exhibition.event.impl.EventTick;
import exhibition.module.Module;
import exhibition.module.data.ModuleData;
import exhibition.module.data.Setting;
import net.minecraft.network.play.server.S03PacketTimeUpdate;

public class Weather extends Module {
   private String TIME = "TIME";

   public Weather(ModuleData data) {
      super(data);
      this.settings.put(this.TIME, new Setting(this.TIME, Integer.valueOf(11000), "The time for shit", 500.0D, 0.0D, 24000.0D));
   }

   @RegisterEvent(
      events = {EventTick.class, EventPacket.class}
   )
   public void onEvent(Event event) {
      if (event instanceof EventPacket) {
         EventPacket eventPacket = (EventPacket)event;
         if (eventPacket.isIncoming() && eventPacket.getPacket() instanceof S03PacketTimeUpdate) {
            event.setCancelled(true);
         }
      } else {
         mc.theWorld.setWorldTime((long)((Number)((Setting)this.settings.get(this.TIME)).getValue()).intValue());
      }

   }
}
