package hawk.modules.render;

import hawk.modules.Module;

public class Fullbright extends Module {
   public Fullbright() {
      super("Fullbright", 0, Module.Category.RENDER);
   }

   public void onEnable() {
      this.mc.gameSettings.gammaSetting = 100.0F;
      this.mc.rightClickDelayTimer = 0;
   }

   public void onDisable() {
      this.mc.gameSettings.gammaSetting = 1.0F;
   }
}
