package cc.slack.features.modules.impl.player.nofalls.specials;

import cc.slack.events.impl.network.PacketEvent;
import cc.slack.events.impl.player.UpdateEvent;
import cc.slack.events.impl.render.RenderEvent;
import cc.slack.features.modules.impl.player.nofalls.INoFall;
import cc.slack.utils.client.mc;
import cc.slack.utils.font.Fonts;
import cc.slack.utils.player.BlinkUtil;
import io.github.nevalackin.radbus.Listen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.network.play.client.C03PacketPlayer;

public class HypixelBlinkNofall implements INoFall {
   boolean spoof;

   public void onEnable() {
      this.spoof = false;
   }

   public void onDisable() {
      BlinkUtil.disable();
   }

   public void onUpdate(UpdateEvent event) {
      if (mc.getPlayer().onGround && this.spoof) {
         this.spoof = false;
         BlinkUtil.disable();
      } else if (mc.getPlayer().offGroundTicks == 1 && mc.getPlayer().motionY < 0.0D) {
         this.spoof = true;
         BlinkUtil.enable(false, true);
      }

   }

   public void onPacket(PacketEvent event) {
      if (this.spoof && event.getPacket() instanceof C03PacketPlayer) {
         ((C03PacketPlayer)event.getPacket()).onGround = true;
      }

   }

   @Listen
   public void onRender(RenderEvent e) {
      if (e.state == RenderEvent.State.RENDER_2D) {
         if (this.spoof) {
            ScaledResolution sr = mc.getScaledResolution();
            Fonts.apple18.drawStringWithShadow("Blinking " + BlinkUtil.getSize(), (float)sr.getScaledWidth() / 2.0F + 10.0F, (float)sr.getScaledHeight() / 2.0F - 10.0F, 16777215);
         }
      }
   }

   public String toString() {
      return "Hypixel Blink";
   }
}
