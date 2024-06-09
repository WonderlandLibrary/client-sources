package exhibition.module.impl.other;

import exhibition.event.Event;
import exhibition.event.RegisterEvent;
import exhibition.event.impl.EventPacket;
import exhibition.management.friend.FriendManager;
import exhibition.management.notifications.user.Notifications;
import exhibition.module.Module;
import exhibition.module.data.ModuleData;
import java.util.Iterator;
import net.minecraft.network.play.server.S38PacketPlayerListItem;

public class FriendAlert extends Module {
   private boolean connect;
   private String name;
   private int currentY;
   private int targetY;
   exhibition.util.Timer timer = new exhibition.util.Timer();

   public FriendAlert(ModuleData data) {
      super(data);
   }

   @RegisterEvent(
      events = {EventPacket.class}
   )
   public void onEvent(Event event) {
      EventPacket ep = (EventPacket)event;
      if (ep.isIncoming() && ep.getPacket() instanceof S38PacketPlayerListItem) {
         S38PacketPlayerListItem packet = (S38PacketPlayerListItem)ep.getPacket();
         if (packet.func_179768_b() == S38PacketPlayerListItem.Action.ADD_PLAYER) {
            Iterator var4 = packet.func_179767_a().iterator();

            while(var4.hasNext()) {
               Object o = var4.next();
               S38PacketPlayerListItem.AddPlayerData data = (S38PacketPlayerListItem.AddPlayerData)o;
               if (FriendManager.isFriend(data.field_179964_d.getName())) {
                  Notifications.getManager().post("Friend Alert", "§b" + data.field_179964_d.getName() + " has joined!", 2500L, Notifications.Type.INFO);
               }
            }
         }
      }

   }
}
