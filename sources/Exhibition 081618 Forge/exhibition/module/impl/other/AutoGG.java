package exhibition.module.impl.other;

import exhibition.event.Event;
import exhibition.event.RegisterEvent;
import exhibition.event.impl.EventChat;
import exhibition.event.impl.EventPacket;
import exhibition.module.Module;
import exhibition.module.data.ModuleData;
import exhibition.util.misc.ChatUtil;
import net.minecraft.network.play.server.S45PacketTitle;

/**
 * @author arithmo & minesense.pub
 */
public class AutoGG extends Module {

   public AutoGG(ModuleData data) {
      super(data);
   }

   @RegisterEvent(
      events = {EventPacket.class}
   )
   @Override
   public void onEvent(Event event) {
      if(event instanceof EventPacket) {
          EventPacket ep = (EventPacket)event.cast();
          if (ep.isIncoming() && ep.getPacket() instanceof S45PacketTitle) {
              S45PacketTitle packet = (S45PacketTitle)ep.getPacket();
              if (packet.getType().equals(S45PacketTitle.Type.TITLE)) {
                 String text = packet.getMessage().getUnformattedText();
                 if (text.equals("VICTORY!") || text.equals("胜利")) {
                    ChatUtil.sendChat_NoFilter("/achat gg");
                 } 
              }
           }
      }
   }
}
