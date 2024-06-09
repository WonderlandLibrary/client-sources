package exhibition.module.impl.other;

import exhibition.event.Event;
import exhibition.event.RegisterEvent;
import exhibition.event.impl.EventPacket;
import exhibition.management.friend.Friend;
import exhibition.management.friend.FriendManager;
import exhibition.module.Module;
import exhibition.module.data.ModuleData;
import exhibition.util.misc.ChatUtil;
import java.util.Iterator;
import net.minecraft.network.play.server.S02PacketChat;

public class AutoTPA extends Module {
   public AutoTPA(ModuleData data) {
      super(data);
   }

   @RegisterEvent(
      events = {EventPacket.class}
   )
   public void onEvent(Event event) {
      EventPacket ep = (EventPacket)event;
      if (ep.isIncoming() && ep.getPacket() instanceof S02PacketChat) {
         S02PacketChat s02PacketChat = (S02PacketChat)ep.getPacket();
         if (s02PacketChat.func_148915_c().getFormattedText().contains("has requested to teleport to you") || s02PacketChat.func_148915_c().getFormattedText().contains("has requested that you teleport to them")) {
            Iterator var4 = FriendManager.friendsList.iterator();

            Friend friend;
            do {
               if (!var4.hasNext()) {
                  return;
               }

               friend = (Friend)var4.next();
            } while(!s02PacketChat.func_148915_c().getFormattedText().contains(friend.name) && !s02PacketChat.func_148915_c().getFormattedText().contains(friend.alias.toString()));

            ChatUtil.sendChat_NoFilter("/tpaccept");
         }
      }

   }
}
