package cc.slack.features.modules.impl.player.nofalls.specials;

import cc.slack.events.impl.player.MotionEvent;
import cc.slack.features.modules.impl.player.nofalls.INoFall;
import cc.slack.utils.client.mc;
import cc.slack.utils.network.PacketUtil;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.play.client.C03PacketPlayer;

public class VulcanNofall implements INoFall {
   int count;
   boolean isFixed;

   public void onEnable() {
      this.count = 0;
      this.isFixed = false;
   }

   public void onMotion(MotionEvent event) {
      if (mc.getPlayer().onGround && this.isFixed) {
         this.isFixed = false;
         this.count = 0;
         mc.getTimer().timerSpeed = 1.0F;
      }

      if (mc.getPlayer().fallDistance > 2.0F) {
         this.isFixed = true;
         mc.getTimer().timerSpeed = 0.9F;
      }

      if (mc.getPlayer().fallDistance > 2.9F) {
         PacketUtil.sendNoEvent(new C03PacketPlayer(true));
         mc.getPlayer().motionY = -0.1D;
         mc.getPlayer().fallDistance = 0.0F;
         EntityPlayerSP var10000 = mc.getPlayer();
         var10000.motionY *= 1.1D;
         if (this.count++ > 5) {
            this.count = 0;
         }
      }

   }

   public String toString() {
      return "Vulcan";
   }
}
