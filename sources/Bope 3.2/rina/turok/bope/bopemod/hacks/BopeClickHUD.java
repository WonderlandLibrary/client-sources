package rina.turok.bope.bopemod.hacks;

import rina.turok.bope.Bope;
import rina.turok.bope.bopemod.BopeModule;
import rina.turok.bope.bopemod.guiscreen.settings.BopeSetting;

public class BopeClickHUD extends BopeModule {
   BopeSetting frame_view = this.create("info", "HUDStringsList", "Strings");
   BopeSetting strings_r = this.create("Color R", "HUDStringsColorR", 0, 0, 255);
   BopeSetting strings_g = this.create("Color G", "HUDStringsColorG", 0, 0, 255);
   BopeSetting strings_b = this.create("Color B", "HUDStringsColorB", 200, 0, 255);
   BopeSetting shadow = this.create("Shadow", "HUDStringsShadow", false);
   BopeSetting smooth = this.create("Smooth", "HUDStringsSmooth", false);

   public BopeClickHUD() {
      super(BopeCategory.BOPE_GUI);
      this.name = "HUD";
      this.tag = "HUD";
      this.description = "B.O.P.E HUD for setting pinnables.";
      this.release("B.O.P.E");
   }

   public void enable() {
      if (this.mc.world != null && this.mc.player != null) {
         Bope.get_module_manager().get_module_with_tag("GUI").set_active(false);
         Bope.click_hud.back = false;
         this.mc.displayGuiScreen(Bope.click_hud);
      }

   }
}
