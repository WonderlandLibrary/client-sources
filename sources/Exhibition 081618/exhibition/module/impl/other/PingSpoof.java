package exhibition.module.impl.other;

import exhibition.event.Event;
import exhibition.event.RegisterEvent;
import exhibition.event.impl.EventPacket;
import exhibition.module.Module;
import exhibition.module.data.ModuleData;
import exhibition.module.data.Setting;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C00PacketKeepAlive;

public class PingSpoof extends Module {
   private exhibition.util.Timer timer = new exhibition.util.Timer();
   private List packetList = new CopyOnWriteArrayList();
   private String WAIT = "DELAY";

   public PingSpoof(ModuleData data) {
      super(data);
      this.settings.put(this.WAIT, new Setting(this.WAIT, Integer.valueOf(15), "MS Delay before sending packets again.", 50.0D, 0.0D, 1000.0D));
   }

   @RegisterEvent(
      events = {EventPacket.class}
   )
   public void onEvent(Event event) {
      if (event instanceof EventPacket) {
         int delay = ((Number)((Setting)this.settings.get(this.WAIT)).getValue()).intValue();
         EventPacket ep = (EventPacket)event;
         if (this.packetList.size() > 10) {
            this.packetList.clear();
         }

         this.setSuffix("" + this.packetList.size());
         if (ep.isOutgoing() && ep.getPacket() instanceof C00PacketKeepAlive && mc.thePlayer.isEntityAlive()) {
            this.packetList.add(ep.getPacket());
            event.setCancelled(true);
            if (this.timer.delay((float)delay)) {
               this.timer.reset();
            }
         }

         if (this.timer.delay((float)delay) && !this.packetList.isEmpty()) {
            Packet packet = (Packet)this.packetList.get(0);
            mc.getNetHandler().getNetworkManager().sendPacketNoEvent(packet);
            this.packetList.remove(packet);
            this.timer.reset();
         }
      }

   }
}
