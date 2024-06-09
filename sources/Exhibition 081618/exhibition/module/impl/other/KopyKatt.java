package exhibition.module.impl.other;

import exhibition.event.Event;
import exhibition.event.RegisterEvent;
import exhibition.event.impl.EventPacket;
import exhibition.module.Module;
import exhibition.module.data.ModuleData;
import exhibition.util.misc.ChatUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.S02PacketChat;

public class KopyKatt extends Module {
   public static EntityPlayer target;

   public KopyKatt(ModuleData data) {
      super(data);
   }

   @RegisterEvent(
      events = {EventPacket.class}
   )
   public void onEvent(Event event) {
      EventPacket ep = (EventPacket)event;
      if (ep.isIncoming() && ep.getPacket() instanceof S02PacketChat && target != null) {
         S02PacketChat pc = (S02PacketChat)ep.getPacket();
         String message = pc.func_148915_c().getUnformattedText().toString();
         if (message.contains(target.getDisplayName().getUnformattedText().toString()) && message.contains(">")) {
            String[] yourMessage = message.split(">");
            String msg = yourMessage[1];
            msg = msg.replace(mc.thePlayer.getName(), target.getName());
            msg = msg.replace("Im ", "You're ");
            msg = msg.replace("I'm ", "You're ");
            msg = msg.replace("im ", "You're ");
            msg = msg.replace("i'm ", "You're ");
            msg = msg.replace("I ", "You ");
            msg = msg.replace("i ", "You ");
            msg = msg.replace("hate", "love");
            String curseCheck = msg.toLowerCase();
            if (curseCheck.contains("nigger") || curseCheck.contains("fucker") || curseCheck.contains("dyke") || curseCheck.contains("faggot") || curseCheck.contains("asshole") || curseCheck.contains("fgt") || curseCheck.contains("kys") || curseCheck.contains("kill yourself") || curseCheck.contains("jew") || curseCheck.contains("cunt") || curseCheck.contains("chink") || curseCheck.contains("fuck") || curseCheck.contains("gay") || curseCheck.contains("fag") || curseCheck.contains("dick")) {
               msg = "Hey! " + target.getName() + ", that's not nice.";
            }

            ChatUtil.sendChat(msg);
         }
      }

   }
}
