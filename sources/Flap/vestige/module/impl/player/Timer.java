package vestige.module.impl.player;

import vestige.event.Listener;
import vestige.event.impl.PostMotionEvent;
import vestige.module.Category;
import vestige.module.Module;
import vestige.setting.AbstractSetting;
import vestige.setting.impl.DoubleSetting;

public class Timer extends Module {
   private final DoubleSetting speed = new DoubleSetting("Speed", 1.1D, 0.1D, 5.0D, 0.1D);

   public Timer() {
      super("Timer", Category.WORLD);
      this.addSettings(new AbstractSetting[]{this.speed});
   }

   public boolean onDisable() {
      net.minecraft.util.Timer var10000 = mc.timer;
      var10000.timerSpeed = 1.0F;
      return false;
   }

   @Listener
   public void onPostMotion(PostMotionEvent event) {
      net.minecraft.util.Timer var10000 = mc.timer;
      var10000.timerSpeed = (float)this.speed.getValue();
   }
}
