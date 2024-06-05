package hawk.modules.player;

import hawk.events.Event;
import hawk.events.listeners.EventMotion;
import hawk.events.listeners.EventSendPacket;
import hawk.modules.Module;
import net.minecraft.network.play.client.C03PacketPlayer;

public class Phase extends Module {
   hawk.util.Timer timer = new hawk.util.Timer();

   public void onEvent(Event var1) {
      if (var1 instanceof EventMotion) {
         var1.isPre();
      }

      boolean var10000 = var1 instanceof EventSendPacket;
   }

   public Phase() {
      super("Phase", 36, Module.Category.PLAYER);
   }

   public void onEnable() {
      this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(this.mc.thePlayer.posX, this.mc.thePlayer.posY - 1.0E-8D, this.mc.thePlayer.posZ, this.mc.thePlayer.rotationYaw, this.mc.thePlayer.rotationPitch, false));
      this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(this.mc.thePlayer.posX, this.mc.thePlayer.posY - 1.0D, this.mc.thePlayer.posZ, this.mc.thePlayer.rotationYaw, this.mc.thePlayer.rotationPitch, false));
   }
}
