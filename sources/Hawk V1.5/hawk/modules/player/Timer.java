package hawk.modules.player;

import hawk.events.Event;
import hawk.events.listeners.EventMotion;
import hawk.modules.Module;
import hawk.settings.NumberSetting;
import hawk.settings.Setting;

public class Timer extends Module {
   public NumberSetting Speed = new NumberSetting("Speed", 1.0D, 0.05D, 4.0D, 0.05D, this);

   public void onEvent(Event var1) {
      if (var1 instanceof EventMotion && var1.isPre()) {
         this.mc.timer.timerSpeed = (float)this.Speed.getValue();
      }

   }

   public Timer() {
      super("Timer", 0, Module.Category.PLAYER);
      this.addSettings(new Setting[]{this.Speed});
   }

   public void onDisable() {
      this.mc.timer.timerSpeed = 1.0F;
   }
}
