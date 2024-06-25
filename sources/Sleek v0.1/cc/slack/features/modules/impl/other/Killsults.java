package cc.slack.features.modules.impl.other;

import cc.slack.events.impl.network.PacketEvent;
import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.features.modules.api.settings.Value;
import cc.slack.features.modules.api.settings.impl.ModeValue;
import cc.slack.utils.client.mc;
import io.github.nevalackin.radbus.Listen;
import java.util.concurrent.ThreadLocalRandom;
import net.minecraft.network.play.server.S02PacketChat;

@ModuleInfo(
   name = "Killsults",
   category = Category.OTHER
)
public class Killsults extends Module {
   private final ModeValue<String> mode = new ModeValue(new String[]{"Universocraft"});

   public Killsults() {
      this.addSettings(new Value[]{this.mode});
   }

   @Listen
   public void onPacket(PacketEvent event) {
      if (event.getPacket() instanceof S02PacketChat) {
         S02PacketChat packet = (S02PacketChat)event.getPacket();
         String message = packet.getChatComponent().getUnformattedText();
         String var4 = (String)this.mode.getValue();
         byte var5 = -1;
         switch(var4.hashCode()) {
         case 0:
            if (var4.equals("")) {
               var5 = 1;
            }
            break;
         case 1933730065:
            if (var4.equals("Universocraft")) {
               var5 = 0;
            }
         }

         switch(var5) {
         case 0:
            if (message.contains(mc.getPlayer().getNameClear()) && message.contains("fue brutalmente asesinado por") || message.contains(mc.getPlayer().getNameClear()) && message.contains("fue empujado al vacÃ\u00ado por") || message.contains(mc.getPlayer().getNameClear()) && message.contains("no resistiÃ³ los ataques de") || message.contains(mc.getPlayer().getNameClear()) && message.contains("pensÃ³ que era un buen momento de morir a manos de") || message.contains(mc.getPlayer().getNameClear()) && message.contains("ha sido asesinado por")) {
               this.sendInsult();
            }
         case 1:
         }
      }

   }

   public void sendInsult() {
      String[] insultslist = new String[]{"U r a piece of yyshit", "Slack > All minecraft clients", "Do you know how to use your hands?", "DG chews me", "Bro, he is using FDP"};
      int randomIndex = ThreadLocalRandom.current().nextInt(0, insultslist.length);
      mc.getPlayer().sendChatMessage(insultslist[randomIndex]);
   }
}
