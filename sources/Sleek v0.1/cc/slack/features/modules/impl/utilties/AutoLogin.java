package cc.slack.features.modules.impl.utilties;

import cc.slack.events.impl.game.TickEvent;
import cc.slack.events.impl.network.PacketEvent;
import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.features.modules.api.settings.impl.StringValue;
import cc.slack.utils.client.mc;
import cc.slack.utils.other.TimeUtil;
import io.github.nevalackin.radbus.Listen;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S02PacketChat;
import org.apache.commons.lang3.StringUtils;

@ModuleInfo(
   name = "AutoLogin",
   category = Category.UTILITIES
)
public class AutoLogin extends Module {
   private final StringValue password = new StringValue("Password", "DglaMaska13");
   private String text;
   private final TimeUtil timeUtil = new TimeUtil();

   @Listen
   public void onPacket(PacketEvent event) {
      Packet packet = event.getPacket();
      if (packet instanceof S02PacketChat) {
         S02PacketChat s02PacketChat = (S02PacketChat)packet;
         String text = s02PacketChat.getChatComponent().getUnformattedText();
         if (!StringUtils.containsIgnoreCase(text, "/register") && !StringUtils.containsIgnoreCase(text, "/register password password") && !text.equalsIgnoreCase("/register <password> <password>")) {
            if (StringUtils.containsIgnoreCase(text, "/login password") || StringUtils.containsIgnoreCase(text, "/login") || text.equalsIgnoreCase("/login <password>")) {
               this.text = "/login " + (String)this.password.getValue();
               this.timeUtil.reset();
            }
         } else {
            this.text = "/register " + (String)this.password.getValue() + " " + (String)this.password.getValue();
            this.timeUtil.reset();
         }
      }

   }

   @Listen
   public void onTick(TickEvent event) {
      if (this.timeUtil.hasReached(1500L) && this.text != null && !this.text.equals("")) {
         mc.getPlayer().sendChatMessage(this.text);
         System.out.println(this.text);
         this.text = "";
      }

   }
}
