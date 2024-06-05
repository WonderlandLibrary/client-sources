package hawk.modules.player;

import hawk.events.Event;
import hawk.events.listeners.EventSendPacket;
import hawk.events.listeners.EventUpdate;
import hawk.modules.Module;
import hawk.modules.combat.Killaura;
import hawk.settings.ModeSetting;
import hawk.settings.Setting;
import net.minecraft.network.play.client.C03PacketPlayer;

public class Nofall extends Module {
   int packetsent = 0;
   public ModeSetting mode = new ModeSetting("Mode", this, "SpoofGround", new String[]{"SpoofGround", "NoGround", "RedeskyTest", "Vanilla"});

   public void onEvent(Event var1) {
      this.setDisplayname(String.valueOf((new StringBuilder("Nofall §7")).append(this.mode.getMode())));
      if (var1 instanceof EventSendPacket) {
         EventSendPacket var2 = (EventSendPacket)var1;
         if (var2.getPacket() instanceof C03PacketPlayer && !Killaura.HasTarget) {
            C03PacketPlayer var3;
            if (this.mode.is("SpoofGround")) {
               var3 = (C03PacketPlayer)var2.getPacket();
               var3.onground = true;
            }

            if (this.mode.is("NoGround")) {
               var3 = (C03PacketPlayer)var2.getPacket();
               var3.onground = false;
            }

            if (this.mode.is("RedeskyTest")) {
               var3 = (C03PacketPlayer)var2.getPacket();
               if (this.mc.thePlayer.fallDistance > 3.0F) {
                  var3.onground = true;
               }
            }
         }
      }

      if (var1 instanceof EventUpdate && var1.isPre() && this.mode.is("Vanilla") && this.mc.thePlayer.fallDistance > 3.0F) {
         this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
      }

   }

   public Nofall() {
      super("Nofall", 0, Module.Category.PLAYER);
      this.addSettings(new Setting[]{this.mode});
   }
}
