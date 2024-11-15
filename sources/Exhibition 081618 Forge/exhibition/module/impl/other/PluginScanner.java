package exhibition.module.impl.other;

import exhibition.event.Event;
import exhibition.event.RegisterEvent;
import exhibition.event.impl.EventPacket;
import exhibition.module.Module;
import exhibition.module.data.ModuleData;
import exhibition.util.misc.ChatUtil;
import net.minecraft.network.play.server.S3APacketTabComplete;

public class PluginScanner extends Module {
   boolean isListening;
   exhibition.util.Timer timer = new exhibition.util.Timer();

   public PluginScanner(ModuleData data) {
      super(data);
   }

   public void onEnable() {
      this.isListening = true;
      this.timer.reset();
   }

   public void onDisable() {
      this.isListening = false;
      this.timer.reset();
   }

   @RegisterEvent(
      events = {EventPacket.class}
   )
   public void onEvent(Event event) {
      EventPacket ep = (EventPacket)event;
      if (ep.isIncoming() && ep.getPacket() instanceof S3APacketTabComplete && this.isListening && this.timer.delay(20000.0F)) {
         S3APacketTabComplete packet = (S3APacketTabComplete)ep.getPacket();
         String[] pluginsFound = packet.func_149630_c();
         ChatUtil.printChat("§4[§cE§4]§8 §3Found §7[§3" + pluginsFound.length + "§7] §3plugin(s): §8" + pluginsFound + "§3.");
         this.toggle();
      }

   }
}
