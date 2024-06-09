package exhibition.module.impl.combat;

import exhibition.event.Event;
import exhibition.event.RegisterEvent;
import exhibition.event.impl.EventPacket;
import exhibition.management.notifications.dev.DevNotifications;
import exhibition.module.Module;
import exhibition.module.data.ModuleData;
import exhibition.module.data.Setting;
import exhibition.util.misc.ChatUtil;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S27PacketExplosion;

public class AntiVelocity extends Module {
   public static String HORIZONTAL = "HORIZONTAL";
   public static String VERTICAL = "VERTICAL";
   public Setting receiveKBAlert = new Setting("ALERT", false, "Alerts you when you receive KB packets. Can be used for Hypixel KB checks.");

   public AntiVelocity(ModuleData data) {
      super(data);
      this.settings.put(HORIZONTAL, new Setting(HORIZONTAL, Integer.valueOf(0), "Horizontal velocity percent.", 1.0D, -100.0D, 100.0D));
      this.settings.put(VERTICAL, new Setting(VERTICAL, Integer.valueOf(0), "Vertical velocity percent.", 1.0D, -100.0D, 100.0D));
      this.settings.put("ALERT", this.receiveKBAlert);
   }

   @RegisterEvent(
      events = {EventPacket.class}
   )
   public void onEvent(Event event) {
      EventPacket ep = (EventPacket)event;
      if (!ep.isOutgoing()) {
         boolean packetReceived = false;

         try {
            int vertical;
            int horizontal;
            if (ep.getPacket() instanceof S12PacketEntityVelocity) {
               S12PacketEntityVelocity packet = (S12PacketEntityVelocity)ep.getPacket();
               if (packet.getEntityID() == mc.thePlayer.getEntityId()) {
                  vertical = ((Number)((Setting)this.settings.get(VERTICAL)).getValue()).intValue();
                  horizontal = ((Number)((Setting)this.settings.get(HORIZONTAL)).getValue()).intValue();
                  if (vertical == 0 && horizontal == 0) {
                     ep.setCancelled(true);
                     packetReceived = true;
                  } else {
                     packet.setMotX(horizontal * packet.getX() / 100);
                     packet.setMotY(vertical * packet.getY() / 100);
                     packet.setMotZ(horizontal * packet.getZ() / 100);
                     packetReceived = true;
                  }
               }
            } else if (ep.getPacket() instanceof S27PacketExplosion) {
               S27PacketExplosion packet = (S27PacketExplosion)ep.getPacket();
               vertical = ((Number)((Setting)this.settings.get(VERTICAL)).getValue()).intValue();
               horizontal = ((Number)((Setting)this.settings.get(HORIZONTAL)).getValue()).intValue();
               if (vertical == 0 && horizontal == 0) {
                  ep.setCancelled(true);
                  packetReceived = true;
               } else {
                  packet.setX((float)horizontal * packet.getX() / 100.0F);
                  packet.setY((float)vertical * packet.getY() / 100.0F);
                  packet.setZ((float)horizontal * packet.getZ() / 100.0F);
                  packetReceived = true;
               }
            }
         } catch (Exception var7) {
            ;
         }

         if (packetReceived && ((Boolean)this.receiveKBAlert.getValue()).booleanValue()) {
            DevNotifications.getManager().post("Knockback Packet Received! " + (mc.thePlayer.hurtTime > 0));
            ChatUtil.printChat("§c§oKnockback Taken, tick: " + mc.thePlayer.ticksExisted);
         }

      }
   }
}
