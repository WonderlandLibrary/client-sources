package rina.turok.bope.bopemod.system;

import rina.turok.bope.Bope;
import rina.turok.bope.bopemod.BopeModule;
import rina.turok.bope.bopemod.hacks.BopeCategory;

public class BopeSystem extends BopeModule {
   int callback = 0;

   public BopeSystem() {
      super(BopeCategory.BOPE_SYS, false);
      this.name = "System";
      this.tag = "System";
      this.description = "Any HUD or update background is here.";
      this.set_active(true);
   }

   public void update() {
      Bope.client_r = Bope.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorR").get_value(1);
      Bope.client_g = Bope.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorG").get_value(1);
      Bope.client_b = Bope.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorB").get_value(1);
   }
}
