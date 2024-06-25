package cc.slack.features.modules.impl.player.nofalls.specials;

import cc.slack.events.impl.network.PacketEvent;
import cc.slack.events.impl.player.UpdateEvent;
import cc.slack.features.modules.impl.player.nofalls.INoFall;
import cc.slack.utils.client.mc;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.play.client.C03PacketPlayer;

public class VerusNofall implements INoFall {
   boolean spoof;
   int packet1Count;
   boolean packetModify;

   public void onEnable() {
      this.packetModify = false;
      this.packet1Count = 0;
      this.spoof = false;
   }

   public void onUpdate(UpdateEvent event) {
      if ((double)mc.getPlayer().fallDistance - mc.getPlayer().motionY > 3.0D) {
         mc.getPlayer().motionY = 0.0D;
         EntityPlayerSP var10000 = mc.getPlayer();
         var10000.motionX *= 0.5D;
         var10000 = mc.getPlayer();
         var10000.motionZ *= 0.5D;
         mc.getPlayer().fallDistance = 0.0F;
         this.spoof = true;
      }

      if (mc.getPlayer().fallDistance / 3.0F > (float)this.packet1Count) {
         this.packet1Count = (int)(mc.getPlayer().fallDistance / 3.0F);
         this.packetModify = true;
      }

      if (mc.getPlayer().onGround) {
         this.packet1Count = 0;
      }

   }

   public void onPacket(PacketEvent event) {
      if (this.spoof && event.getPacket() instanceof C03PacketPlayer) {
         ((C03PacketPlayer)event.getPacket()).onGround = true;
         this.spoof = false;
      }

   }

   public String toString() {
      return "Verus";
   }
}
