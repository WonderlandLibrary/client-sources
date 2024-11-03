package xyz.cucumber.base.interf.clientsettings.ext.adds;

import xyz.cucumber.base.interf.clientsettings.ext.Setting;
import xyz.cucumber.base.interf.clientsettings.ext.impl.ClientSetting;
import xyz.cucumber.base.interf.clientsettings.ext.impl.NumberClientSetting;
import xyz.cucumber.base.module.settings.NumberSettings;

public class BlurSetting extends Setting {
   public NumberSettings radius = new NumberSettings("Radius", 10.0, 1.0, 25.0, 0.5);

   public BlurSetting() {
      super("Blur");
      this.addSettings(new ClientSetting[]{new NumberClientSetting(this.radius)});
   }
}
