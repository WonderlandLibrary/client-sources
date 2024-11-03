package net.augustus.modules.render;

import java.awt.Color;
import net.augustus.events.EventReadPacket;
import net.augustus.events.EventRender3D;
import net.augustus.modules.Categorys;
import net.augustus.modules.Module;
import net.augustus.settings.DoubleValue;
import net.lenni0451.eventapi.reflection.EventTarget;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S03PacketTimeUpdate;

public class Ambiance extends Module {
   public final DoubleValue time = new DoubleValue(1, "Time", this, 1900.0, 0.0, 24000.0, 0);
   public final DoubleValue timeSpeed = new DoubleValue(2, "TimeSpeed", this, 0.0, 0.0, 100.0, 1);
   public final DoubleValue rainStrength = new DoubleValue(3, "Rain", this, 0.0, 0.0, 1.0, 2);
   public final DoubleValue thunderStrength = new DoubleValue(4, "Thunder", this, 0.0, 0.0, 1.0, 2);
   private double counter;
   private float rainStrengthf = 0.0F;
   private float thunderStrengthf = 0.0F;

   public Ambiance() {
      super("Ambiance", new Color(224, 93, 40), Categorys.RENDER);
   }

   @Override
   public void onEnable() {
      super.onEnable();
      this.counter = 0.0;
      if (mc.theWorld != null) {
         this.rainStrengthf = mc.theWorld.getRainStrength(1.0F);
         this.thunderStrengthf = mc.theWorld.getThunderStrength(1.0F);
      }
   }

   @Override
   public void onDisable() {
      super.onDisable();
      this.counter = 0.0;
      if (mc.theWorld != null) {
         mc.theWorld.setRainStrength(this.rainStrengthf);
         mc.theWorld.setThunderStrength(this.thunderStrengthf);
      }
   }

   @EventTarget
   public void onEventTick(EventRender3D render3D) {
      this.counter = this.timeSpeed.getValue() > 0.0 ? this.counter + this.timeSpeed.getValue() : 0.0;
      mc.theWorld.setWorldTime((long)(this.time.getValue() + this.counter));
      if (this.counter > 24000.0) {
         this.counter = 0.0;
      }

      mc.theWorld.setRainStrength((float)this.rainStrength.getValue());
      mc.theWorld.setThunderStrength((float)this.thunderStrength.getValue());
   }

   @EventTarget
   public void onEventReadPacket(EventReadPacket eventReadPacket) {
      Packet packet = eventReadPacket.getPacket();
      if (packet instanceof S03PacketTimeUpdate) {
         eventReadPacket.setCanceled(true);
      }
   }
}
