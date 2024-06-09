package exhibition.module.impl.other;

import exhibition.Client;
import exhibition.event.Event;
import exhibition.event.RegisterEvent;
import exhibition.event.impl.EventPacket;
import exhibition.management.notifications.user.Notifications;
import exhibition.module.Module;
import exhibition.module.data.ModuleData;
import exhibition.module.impl.combat.Killaura;
import exhibition.util.misc.ChatUtil;
import net.minecraft.network.play.server.S45PacketTitle;

public class AutoGG extends Module {
   public AutoGG(ModuleData data) {
      super(data);
   }

   @RegisterEvent(
      events = {EventPacket.class}
   )
   public void onEvent(Event event) {
      EventPacket ep = (EventPacket)event.cast();
      if (ep.isIncoming() && ep.getPacket() instanceof S45PacketTitle) {
         S45PacketTitle packet = (S45PacketTitle)ep.getPacket();
         if (packet.getType().equals(S45PacketTitle.Type.TITLE)) {
            String text = packet.getMessage().getUnformattedText();
            if (text.equals("VICTORY!")) {
               ChatUtil.sendChat_NoFilter("/achat gg");
            } else if (text.equals("YOU DIED") && ((Module)Client.getModuleManager().get(Killaura.class)).isEnabled()) {
               ((Module)Client.getModuleManager().get(Killaura.class)).toggle();
               Notifications.getManager().post("Aura Death", "Aura disabled due to death.");
            }
         }
      }

   }
}
