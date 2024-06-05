package hawk.modules.player;

import hawk.events.Event;
import hawk.events.listeners.EventMotion;
import hawk.events.listeners.EventReceivePacket;
import hawk.modules.Module;
import hawk.settings.ModeSetting;
import hawk.settings.Setting;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;

public class Disabler extends Module {
   public ModeSetting mode = new ModeSetting("Mode", this, "Rede less flags", new String[]{"Rede less flags"});
   double oldPosX;
   double oldPosZ;
   double oldPosY;
   hawk.util.Timer timer = new hawk.util.Timer();
   double oldPitch;
   double oldYaw;

   public void onEvent(Event var1) {
      if (var1 instanceof EventReceivePacket && this.mode.is("Rede less flags")) {
         if (((EventReceivePacket)var1).getPacket() instanceof C0FPacketConfirmTransaction) {
            var1.setCancelled(true);
         } else if (((EventReceivePacket)var1).getPacket() instanceof C0BPacketEntityAction) {
            var1.setCancelled(true);
         } else {
            boolean var10000 = ((EventReceivePacket)var1).getPacket() instanceof C03PacketPlayer.C06PacketPlayerPosLook;
         }
      }

      if (var1 instanceof EventMotion) {
         this.setDisplayname(String.valueOf((new StringBuilder("Disabler §7")).append(this.mode.getMode())));
         this.mode.is("Rede less flags");
      }

   }

   public Disabler() {
      super("Disabler", 0, Module.Category.PLAYER);
      this.addSettings(new Setting[]{this.mode});
   }
}
