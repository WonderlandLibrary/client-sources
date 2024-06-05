package hawk.modules.render;

import hawk.events.Event;
import hawk.modules.Module;
import hawk.settings.ModeSetting;
import hawk.util.Timer;

public class ClientName extends Module {
   public ModeSetting Name = new ModeSetting("Hawk", this, "Hawk", new String[]{"Sigma", "Lunar"});
   public Timer timer = new Timer();

   public ClientName() {
      super("HeadRotations", 0, Module.Category.RENDER);
   }

   public void onEnable() {
   }

   public void onDisable() {
   }

   public void onEvent(Event var1) {
   }
}
