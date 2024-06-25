package cc.slack.features.modules.impl.utilties;

import cc.slack.events.impl.network.PacketEvent;
import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.features.modules.api.settings.Value;
import cc.slack.features.modules.api.settings.impl.ModeValue;
import cc.slack.utils.client.mc;
import io.github.nevalackin.radbus.Listen;
import java.util.Iterator;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.util.IChatComponent;

@ModuleInfo(
   name = "AutoPlay",
   category = Category.UTILITIES
)
public class AutoPlay extends Module {
   private final ModeValue<String> mode = new ModeValue(new String[]{"Hypixel", "Universocraft", "Librecraft"});
   private final ModeValue<String> univalue = new ModeValue("Universocraft", new String[]{"Skywars", "Bedwars"});

   public AutoPlay() {
      this.addSettings(new Value[]{this.mode, this.univalue});
   }

   private void process(IChatComponent chatComponent) {
      String value = chatComponent.getChatStyle().getChatClickEvent().getValue();
      if (value != null && value.startsWith("/play") && !value.startsWith("/play skyblock")) {
         mc.getPlayer().sendChatMessage(value);
      } else {
         Iterator var3 = chatComponent.getSiblings().iterator();

         while(var3.hasNext()) {
            IChatComponent component = (IChatComponent)var3.next();
            this.process(component);
         }
      }

   }

   @Listen
   public void onPacket(PacketEvent event) {
      if (event.getPacket() instanceof S02PacketChat) {
         IChatComponent chatComponent = ((S02PacketChat)event.getPacket()).getChatComponent();
         String unformattedText = chatComponent.getUnformattedText();
         String var4 = (String)this.mode.getValue();
         byte var5 = -1;
         switch(var4.hashCode()) {
         case -1248403467:
            if (var4.equals("Hypixel")) {
               var5 = 0;
            }
            break;
         case 1520076232:
            if (var4.equals("Librecraft")) {
               var5 = 2;
            }
            break;
         case 1933730065:
            if (var4.equals("Universocraft")) {
               var5 = 1;
            }
         }

         switch(var5) {
         case 0:
            this.process(chatComponent);
            break;
         case 1:
            if (unformattedText.contains("Jugar de nuevo") || unformattedText.contains("Ha ganado")) {
               String var6 = (String)this.univalue.getValue();
               byte var7 = -1;
               switch(var6.hashCode()) {
               case -467898612:
                  if (var6.equals("Skywars")) {
                     var7 = 0;
                  }
                  break;
               case 1433239148:
                  if (var6.equals("Bedwars")) {
                     var7 = 1;
                  }
               }

               switch(var7) {
               case 0:
                  mc.getPlayer().sendChatMessage("/skywars random");
                  break;
               case 1:
                  mc.getPlayer().sendChatMessage("/bedwars random");
               }
            }
            break;
         case 2:
            if (unformattedText.contains("Â¡Partida finalizada!")) {
               mc.getPlayer().sendChatMessage("/saliryentrar");
            }
         }

      }
   }
}
