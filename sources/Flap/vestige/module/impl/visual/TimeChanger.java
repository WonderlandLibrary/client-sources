package vestige.module.impl.visual;

import net.minecraft.network.play.server.S03PacketTimeUpdate;
import vestige.event.Listener;
import vestige.event.impl.PacketReceiveEvent;
import vestige.event.impl.RenderEvent;
import vestige.module.Category;
import vestige.module.Module;
import vestige.setting.AbstractSetting;
import vestige.setting.impl.DoubleSetting;

public class TimeChanger extends Module {
   private final DoubleSetting customTime = new DoubleSetting("Custom time", 18000.0D, 0.0D, 24000.0D, 500.0D);

   public TimeChanger() {
      super("Time Changer", Category.VISUAL);
      this.addSettings(new AbstractSetting[]{this.customTime});
   }

   @Listener
   public void onRender(RenderEvent event) {
      mc.theWorld.setWorldTime((long)this.customTime.getValue());
   }

   @Listener
   public void onReceive(PacketReceiveEvent event) {
      if (event.getPacket() instanceof S03PacketTimeUpdate) {
         event.setCancelled(true);
      }

   }
}
