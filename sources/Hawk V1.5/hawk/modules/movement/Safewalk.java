package hawk.modules.movement;

import hawk.modules.Module;

public class Safewalk extends Module {
   public static boolean isEnabled = false;

   public void onEnable() {
      isEnabled = true;
   }

   public Safewalk() {
      super("Safewalk", 0, Module.Category.MOVEMENT);
   }

   public void onDisable() {
      isEnabled = false;
   }
}
