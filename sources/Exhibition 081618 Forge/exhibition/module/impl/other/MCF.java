package exhibition.module.impl.other;

import exhibition.event.Event;
import exhibition.event.RegisterEvent;
import exhibition.event.impl.EventMouse;
import exhibition.management.friend.FriendManager;
import exhibition.module.Module;
import exhibition.module.data.ModuleData;
import exhibition.util.misc.ChatUtil;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.input.Mouse;

public class MCF extends Module {
   public MCF(ModuleData data) {
      super(data);
   }

   @RegisterEvent(
      events = {EventMouse.class}
   )
   public void onEvent(Event event) {
      EventMouse em = (EventMouse)event;
      if (em.getButtonID() == 2 && Mouse.getEventButtonState() && mc.objectMouseOver.entityHit != null && mc.objectMouseOver.entityHit instanceof EntityPlayer) {
         EntityPlayer entityPlayer = (EntityPlayer)mc.objectMouseOver.entityHit;
         if (FriendManager.isFriend(entityPlayer.getName())) {
            ChatUtil.printChat("§4[§cE§4]§8 §b" + entityPlayer.getName() + "§7 has been §cunfriended.");
            FriendManager.removeFriend(entityPlayer.getName());
         } else {
            ChatUtil.printChat("§4[§cE§4]§8 §b" + entityPlayer.getName() + "§7 has been §afriended.");
            FriendManager.addFriend(entityPlayer.getName(), entityPlayer.getName());
         }
      }

   }
}
