package net.augustus.modules.combat;

import java.awt.Color;
import net.augustus.events.EventAttackEntity;
import net.augustus.events.EventSendPacket;
import net.augustus.modules.Categorys;
import net.augustus.modules.Module;
import net.augustus.settings.StringValue;
import net.lenni0451.eventapi.reflection.EventTarget;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;

public class Criticals extends Module {
   public StringValue mode = new StringValue(1, "Mode", this, "NoGround", new String[]{"NoGround", "Packet"});

   public Criticals() {
      super("Criticals", Color.RED, Categorys.COMBAT);
   }

   @EventTarget
   public void onEventAttackEntity(EventAttackEntity eventAttackEntity) {
      String var2 = this.mode.getSelected();
      byte var3 = -1;
      switch(var2.hashCode()) {
         case -1911998296:
            if (var2.equals("Packet")) {
               var3 = 0;
            }
         default:
            switch(var3) {
               case 0:
                  mc.thePlayer
                     .sendQueue
                     .addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 6.0E-15, mc.thePlayer.posZ, false));
                  mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(false));
            }
      }
   }

   @EventTarget
   public void onEventSendPacket(EventSendPacket eventSendPacket) {
      Packet packet = eventSendPacket.getPacket();
      String var3 = this.mode.getSelected();
      byte var4 = -1;
      switch(var3.hashCode()) {
         case 370287304:
            if (var3.equals("NoGround")) {
               var4 = 0;
            }
         default:
            switch(var4) {
               case 0:
                  if (packet instanceof C03PacketPlayer) {
                     C03PacketPlayer c03PacketPlayer = (C03PacketPlayer)packet;
                     c03PacketPlayer.setOnGround(false);
                  }
            }
      }
   }
}
