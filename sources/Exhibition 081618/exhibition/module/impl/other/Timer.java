package exhibition.module.impl.other;

import exhibition.event.Event;
import exhibition.event.RegisterEvent;
import exhibition.event.impl.EventTick;
import exhibition.module.Module;
import exhibition.module.data.ModuleData;
import exhibition.module.data.Setting;

public class Timer extends Module {
   private String GS = "GAMESPEED";

   public Timer(ModuleData data) {
      super(data);
      this.settings.put(this.GS, new Setting(this.GS, 0.3D, "The value the mc timer will be set to.", 0.05D, 0.1D, 5.0D));
   }

   public void onEnable() {
      mc.timer.timerSpeed = 1.0F;
   }

   public void onDisable() {
      mc.timer.timerSpeed = 1.0F;
   }

   @RegisterEvent(
      events = {EventTick.class}
   )
   public void onEvent(Event event) {
      mc.timer.timerSpeed = ((Number)((Setting)this.settings.get(this.GS)).getValue()).floatValue();
   }
}
