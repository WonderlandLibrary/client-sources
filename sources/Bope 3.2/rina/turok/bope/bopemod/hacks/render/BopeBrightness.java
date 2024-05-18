package rina.turok.bope.bopemod.hacks.render;

import net.minecraft.client.settings.GameSettings;
import rina.turok.bope.bopemod.BopeModule;
import rina.turok.bope.bopemod.hacks.BopeCategory;

public class BopeBrightness extends BopeModule {
   float old_gamme_value;

   public BopeBrightness() {
      super(BopeCategory.BOPE_RENDER, false);
      this.name = "Brightness";
      this.tag = "Brightness";
      this.description = "Set full gamma.";
      this.release("B.O.P.E - Module - B.O.P.E");
   }

   public void disable() {
      GameSettings var10000 = this.mc.gameSettings;
      var10000.gammaSetting -= 1000.0F;
   }

   public void enable() {
      GameSettings var10000 = this.mc.gameSettings;
      var10000.gammaSetting += 1000.0F;
   }
}
