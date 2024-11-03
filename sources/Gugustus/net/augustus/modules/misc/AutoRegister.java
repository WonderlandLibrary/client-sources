package net.augustus.modules.misc;

import java.awt.Color;
import net.augustus.events.EventReadPacket;
import net.augustus.events.EventTick;
import net.augustus.modules.Categorys;
import net.augustus.modules.Module;
import net.augustus.utils.TimeHelper;
import net.lenni0451.eventapi.reflection.EventTarget;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S02PacketChat;
import org.apache.commons.lang3.StringUtils;

public class AutoRegister extends Module {
   private final TimeHelper timeHelper = new TimeHelper();
   private String text;

   public AutoRegister() {
      super("AutoRegister", new Color(189, 32, 110), Categorys.MISC);
   }

   @EventTarget
   public void onEventReadPacket(EventReadPacket eventReadPacket) {
      Packet packet = eventReadPacket.getPacket();
      if (packet instanceof S02PacketChat) {
         S02PacketChat s02PacketChat = (S02PacketChat)packet;
         String text = s02PacketChat.getChatComponent().getUnformattedText();
         if (StringUtils.containsIgnoreCase(text, "/register")
            || StringUtils.containsIgnoreCase(text, "/register password password")
            || text.equalsIgnoreCase("/register <password> <password>")) {
            mc.thePlayer.sendChatMessage("/register pass123 pass123");
            this.text = "/register pass123 pass123";
            this.timeHelper.reset();
         } else if (StringUtils.containsIgnoreCase(text, "/login password")
            || StringUtils.containsIgnoreCase(text, "/login")
            || text.equalsIgnoreCase("/login <password>")) {
            mc.thePlayer.sendChatMessage("/login pass123");
            this.text = "/login pass123";
            this.timeHelper.reset();
         }
      }
   }

   @EventTarget
   public void onEventTick(EventTick eventTick) {
      if (this.timeHelper.reached(1500L) && this.text != null && !this.text.equals("")) {
         mc.thePlayer.sendChatMessage(this.text);
         System.out.println(this.text);
         this.text = "";
      }
   }
}
