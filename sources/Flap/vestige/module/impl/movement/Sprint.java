package vestige.module.impl.movement;

import vestige.event.Listener;
import vestige.event.impl.TickEvent;
import vestige.module.Category;
import vestige.module.Module;
import vestige.setting.AbstractSetting;
import vestige.setting.impl.BooleanSetting;
import vestige.setting.impl.ModeSetting;
import vestige.util.network.ServerUtil;

public class Sprint extends Module {
   public ModeSetting mode = new ModeSetting("Mode", "Legit", new String[]{"Omni", "Legit"});
   public final BooleanSetting ominibypass = new BooleanSetting("Omni Bypass", () -> {
      return this.mode.is("Omni");
   }, true);

   public Sprint() {
      super("Sprint", Category.MOVEMENT);
      this.addSettings(new AbstractSetting[]{this.mode, this.ominibypass});
      this.setEnabledSilently(true);
   }

   public boolean onDisable() {
      mc.gameSettings.keyBindSprint.pressed = false;
      return false;
   }

   @Listener(1)
   public void onTick(TickEvent event) {
      mc.gameSettings.keyBindSprint.pressed = true;
      if (mc.thePlayer.moveForward <= 0.0F && ServerUtil.isOnHypixel()) {
         mc.thePlayer.setSprinting(false);
      }

   }
}
